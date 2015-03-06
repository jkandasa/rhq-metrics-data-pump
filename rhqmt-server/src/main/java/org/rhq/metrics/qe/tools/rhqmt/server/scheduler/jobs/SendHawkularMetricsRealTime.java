package org.rhq.metrics.qe.tools.rhqmt.server.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hawkular.metrics.api.jaxrs.AvailabilityDataParams;
import org.hawkular.metrics.api.jaxrs.AvailabilityDataPoint;
import org.hawkular.metrics.api.jaxrs.NumericDataParams;
import org.hawkular.metrics.api.jaxrs.NumericDataPoint;
import org.hawkular.metrics.api.jaxrs.TenantParams;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.MetricsJobDataService;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.InputMetricParams;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.InputTenantParams;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.WorkerRestHawkularAddData;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.WorkerRestHawkularCreateTenants;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ScheduleDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.HawkularMetricsUri;

import com.codahale.metrics.annotation.Timed;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class SendHawkularMetricsRealTime implements Job{
    private static Logger _logger = Logger.getLogger(SendHawkularMetricsRealTime.class);

    @Timed
    private void sendMetrics(Long jobId, MetricsJobData jobData){
        _logger.debug("Job Data: "+jobData);
        //Create Tenants, if it's not done...
        if(! jobData.getInitialSetupDone()){
            for(InputTenantParams tenantParams : jobData.getInputTenantParams()){
                if(tenantParams.getMin() != null){
                    for(int tenantNo = tenantParams.getMin(); tenantNo <= tenantParams.getMax(); tenantNo++){
                        TenantParams tenant = new TenantParams();
                        tenant.setId(tenantParams.getName()+tenantNo);
                        Thread thread = new Thread(new WorkerRestHawkularCreateTenants(
                                jobData.getTargetServer(), 
                                HawkularMetricsUri.TENANTS,
                                tenant,
                                jobData));
                        thread.start();
                    }
                }else{
                    TenantParams tenant = new TenantParams();
                    tenant.setId(tenantParams.getName());
                    Thread thread = new Thread(new WorkerRestHawkularCreateTenants(
                            jobData.getTargetServer(), 
                            HawkularMetricsUri.TENANTS,
                            tenant,
                            jobData));
                    thread.start();
                }
            }


            jobData.setInitialSetupDone(true);

            MetricsJobData metricsJobData = new MetricsJobData();
            metricsJobData.setId(jobData.getId());
            metricsJobData.setInitialSetupDone(true);
            new MetricsJobDataService().update(metricsJobData);
        }

        //Get Tenant details
        for(InputTenantParams tenantParams : jobData.getInputTenantParams()){
            _logger.debug("Tenants: "+tenantParams);
            if(tenantParams.getMin() != null){
                for(int tenantNo = tenantParams.getMin(); tenantNo <= tenantParams.getMax(); tenantNo++){
                    this.processTenant(tenantParams.getName()+tenantNo, jobData);
                }
            }else{
                this.processTenant(tenantParams.getName(), jobData);
            }
        }
    }

    private void processTenant(String tenantName, MetricsJobData jobData){
        ArrayList<NumericDataParams> numericDataParamsList = new ArrayList<NumericDataParams>();
        ArrayList<AvailabilityDataParams> availabilityDataParamsList = new ArrayList<AvailabilityDataParams>();

        Random randomMetric = new Random();     
        long timeIncrement = (jobData.getMetricTimeLimit()*1000l)/jobData.getMetricDataCount(); 
        long referenceTime = new Date().getTime() - (jobData.getMetricTimeLimit()*1000l);

        for(InputMetricParams metricParams : jobData.getInputMetricParams()){
            _logger.debug("Metric Detail: "+metricParams);
            if(metricParams.getMin() != null){
                for(int metricNo=metricParams.getMin(); metricNo<=metricParams.getMax(); metricNo++){
                    this.processMetric(tenantName, metricParams.getName()+metricNo, metricParams, jobData, referenceTime, 
                            timeIncrement, numericDataParamsList, availabilityDataParamsList, randomMetric);
                }
            }else{
                this.processMetric(tenantName, metricParams.getName(), metricParams, jobData, referenceTime, 
                        timeIncrement, numericDataParamsList, availabilityDataParamsList, randomMetric);
            }
        }

        //Process Tenant rest post
        if(availabilityDataParamsList.size() > 0){
            Thread thread = new Thread(new WorkerRestHawkularAddData(
                    jobData.getTargetServer(), 
                    new HawkularMetricsUri().getUri(tenantName, HawkularMetricsUri.METRICS_DATA_AVAILABILITY),
                    availabilityDataParamsList,
                    InputMetricParams.METRICS_TYPE.AVAILABILITY,
                    tenantName,
                    jobData));
            thread.start();
        }

        if(numericDataParamsList.size() > 0){
            Thread thread = new Thread(new WorkerRestHawkularAddData(
                    jobData.getTargetServer(), 
                    new HawkularMetricsUri().getUri(tenantName, HawkularMetricsUri.METRICS_DATA_NUMERIC),
                    numericDataParamsList,
                    InputMetricParams.METRICS_TYPE.NUMERIC,
                    tenantName,
                    jobData));
            thread.start();
        }        
    }

    private void processMetric(String tenantId, String metricName, InputMetricParams metricParams, 
            MetricsJobData jobData, long referenceTime, long timeIncrement, ArrayList<NumericDataParams> numericDataParamsList,
            ArrayList<AvailabilityDataParams> availabilityDataParamsList, Random randomMetric){
        if(metricParams.getMetricsType().equals(InputMetricParams.METRICS_TYPE.AVAILABILITY)){
            ArrayList<AvailabilityDataPoint> availabilityDataPoints = new ArrayList<>();
            for(int i=0; i<jobData.getMetricDataCount();i++){
                AvailabilityDataPoint availabilityDataPoint = new AvailabilityDataPoint();
                availabilityDataPoint.setTimestamp(referenceTime+(i*timeIncrement));
                if(randomMetric.nextBoolean()){
                    availabilityDataPoint.setValue("up");
                }else{
                    availabilityDataPoint.setValue("down");
                }
                //Adding in the index 0, because, 
                //while verifying, hawkular returns in descending order
                availabilityDataPoints.add(0, availabilityDataPoint); 
            }
            AvailabilityDataParams availabilityDataParams = new AvailabilityDataParams();
            availabilityDataParams.setName(metricName);
            availabilityDataParams.setData(availabilityDataPoints);
            availabilityDataParams.setTenantId(tenantId);
            availabilityDataParamsList.add(availabilityDataParams);
        }else if(metricParams.getMetricsType().equals(InputMetricParams.METRICS_TYPE.NUMERIC)){                
            ArrayList<NumericDataPoint> numericDataPoints = new ArrayList<>();
            for(int i=0; i<jobData.getMetricDataCount();i++){
                //Adding in the index 0, because, 
                //while verifying, hawkular returns in descending order
                numericDataPoints.add(0, new NumericDataPoint(referenceTime+(i*timeIncrement), 
                        (randomMetric.nextDouble()*(jobData.getMetricValueHighest()
                                -jobData.getMetricValueLowest()))+jobData.getMetricValueLowest()));
            }
            NumericDataParams dataParams = new NumericDataParams();
            dataParams.setName(metricName);
            dataParams.setData(numericDataPoints);
            dataParams.setTenantId(tenantId);
            numericDataParamsList.add(dataParams);            
        }
    }

    @Override
    public void execute(JobExecutionContext jonExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = (JobDataMap)jonExecutionContext.getJobDetail().getJobDataMap();
        try {
            _logger.debug("Id:"+jobDataMap.getLong(ScheduleDetail.JOB_ID)+", Type:"+jobDataMap.getString(ScheduleDetail.JOB_TYPE)+", Data:"+jobDataMap.get(ScheduleDetail.JOB_DATA));
            this.sendMetrics(jobDataMap.getLong(ScheduleDetail.JOB_ID), (MetricsJobData) jobDataMap.get(ScheduleDetail.JOB_DATA));
        } catch (Exception ex) {
            _logger.error("Exception, ", ex);
        }
    }

}
