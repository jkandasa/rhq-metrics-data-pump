package org.rhq.metrics.qe.tools.rhqmt.server.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.rhq.metrics.qe.tools.rhqmt.server.ThreadPool;
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
                        
                        ThreadPool.getExecutorService().execute(new WorkerRestHawkularCreateTenants(
                                jobData.getTargetServer(), 
                                HawkularMetricsUri.TENANTS,
                                tenant,
                                jobData));
                    }
                }else{
                    TenantParams tenant = new TenantParams();
                    tenant.setId(tenantParams.getName());
                    ThreadPool.getExecutorService().execute(new WorkerRestHawkularCreateTenants(
                            jobData.getTargetServer(), 
                            HawkularMetricsUri.TENANTS,
                            tenant,
                            jobData));
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

        if(jobData.getMetricLimit() > 0){
            //Process for Numeric Metrics
            if(jobData.getMetricLimit() < numericDataParamsList.size()){
                int repeatCount =  numericDataParamsList.size()/jobData.getMetricLimit().intValue();
                int balanceCount = numericDataParamsList.size()%jobData.getMetricLimit().intValue();
                for(int currentCount=0;currentCount<repeatCount;currentCount++){
                    postMetricsData(jobData, 
                            numericDataParamsList.
                            subList(currentCount*jobData.getMetricLimit().intValue(), 
                                    (currentCount+1)*jobData.getMetricLimit().intValue()), 
                             null,
                             tenantName);
                }

                if(balanceCount > 0){
                    postMetricsData(jobData, 
                            numericDataParamsList.
                            subList(repeatCount*jobData.getMetricLimit().intValue(), 
                                    numericDataParamsList.size()), 
                            null,
                            tenantName);
                }

            }else{
                postMetricsData(jobData, 
                        numericDataParamsList, 
                        null,
                        tenantName);
            }
            //Process for availability metrics
            if(jobData.getMetricLimit() < availabilityDataParamsList.size()){
                int repeatCount =  availabilityDataParamsList.size()/jobData.getMetricLimit().intValue();
                int balanceCount = availabilityDataParamsList.size()%jobData.getMetricLimit().intValue();

                for(int currentCount=0;currentCount<repeatCount;currentCount++){
                    postMetricsData(jobData, 
                            null,
                            availabilityDataParamsList.
                            subList(currentCount*jobData.getMetricLimit().intValue(), 
                                    (currentCount+1)*jobData.getMetricLimit().intValue()), 
                            tenantName);
                }

                if(balanceCount > 0){
                    postMetricsData(jobData,
                            null,
                            availabilityDataParamsList.
                            subList(repeatCount*jobData.getMetricLimit().intValue(), 
                                    availabilityDataParamsList.size()), 
                            tenantName);
                }


            }else{
                postMetricsData(jobData, 
                        null, 
                        availabilityDataParamsList,
                        tenantName);
            }
        }else{
            postMetricsData(jobData, 
                    numericDataParamsList, 
                    availabilityDataParamsList,
                    tenantName);
        }               
    }

    private void postMetricsData(MetricsJobData jobData, 
            List<NumericDataParams> numericDataParamsList, 
            List<AvailabilityDataParams> availabilityDataParamsList,
            String tenantName){
        //Process Tenant rest post
        if(availabilityDataParamsList != null && availabilityDataParamsList.size() > 0){
            ThreadPool.getExecutorService().execute(new WorkerRestHawkularAddData(
                    jobData.getTargetServer(), 
                    new HawkularMetricsUri().getUri(tenantName, HawkularMetricsUri.METRICS_DATA_AVAILABILITY),
                    availabilityDataParamsList,
                    InputMetricParams.METRICS_TYPE.AVAILABILITY,
                    tenantName,
                    jobData));
        }

        if(numericDataParamsList != null && numericDataParamsList.size() > 0){
            ThreadPool.getExecutorService().execute(new WorkerRestHawkularAddData(
                    jobData.getTargetServer(), 
                    new HawkularMetricsUri().getUri(tenantName, HawkularMetricsUri.METRICS_DATA_NUMERIC),
                    numericDataParamsList,
                    InputMetricParams.METRICS_TYPE.NUMERIC,
                    tenantName,
                    jobData));
        }
    }

    private void processMetric(String tenantId, String metricName, InputMetricParams metricParams, 
            MetricsJobData jobData, long referenceTime, long timeIncrement,
            ArrayList<NumericDataParams> numericDataParamsList,
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
            
            AvailabilityDataParams availabilityDataParams = null;
            
            if(jobData.getMetricDataLimit() < jobData.getMetricDataCount()){
                int repeatCount =  jobData.getMetricDataCount().intValue()/jobData.getMetricDataLimit().intValue();
                int balanceCount = jobData.getMetricDataCount().intValue()%jobData.getMetricDataLimit().intValue();
                
                for(int currentCount=0;currentCount<repeatCount;currentCount++){
                    availabilityDataParams = new AvailabilityDataParams();
                    availabilityDataParams.setName(metricName);
                    availabilityDataParams.setData(availabilityDataPoints
                            .subList(currentCount*jobData.getMetricDataLimit().intValue(), 
                            (currentCount+1)*jobData.getMetricDataLimit().intValue()));
                    availabilityDataParams.setTenantId(tenantId);
                    availabilityDataParamsList.add(availabilityDataParams);
                }
                if(balanceCount > 0){
                    availabilityDataParams = new AvailabilityDataParams();
                    availabilityDataParams.setName(metricName);
                    availabilityDataParams.setData(availabilityDataPoints
                            .subList(repeatCount*jobData.getMetricDataLimit().intValue(), 
                                    availabilityDataPoints.size()));
                    availabilityDataParams.setTenantId(tenantId);
                    availabilityDataParamsList.add(availabilityDataParams);
                }
            }else{
                availabilityDataParams = new AvailabilityDataParams();
                availabilityDataParams.setName(metricName);
                availabilityDataParams.setData(availabilityDataPoints);
                availabilityDataParams.setTenantId(tenantId);
                availabilityDataParamsList.add(availabilityDataParams);
            }
        }else if(metricParams.getMetricsType().equals(InputMetricParams.METRICS_TYPE.NUMERIC)){                
            ArrayList<NumericDataPoint> numericDataPoints = new ArrayList<>();
            for(int i=0; i<jobData.getMetricDataCount();i++){
                //Adding in the index 0, because, 
                //while verifying, hawkular returns in descending order
                numericDataPoints.add(0, new NumericDataPoint(referenceTime+(i*timeIncrement), 
                        (randomMetric.nextDouble()*(jobData.getMetricValueHighest()
                                -jobData.getMetricValueLowest()))+jobData.getMetricValueLowest()));
            }
            NumericDataParams dataParams = null;
            
            if(jobData.getMetricDataLimit() < jobData.getMetricDataCount()){
                int repeatCount =  jobData.getMetricDataCount().intValue()/jobData.getMetricDataLimit().intValue();
                int balanceCount = jobData.getMetricDataCount().intValue()%jobData.getMetricDataLimit().intValue();
                
                for(int currentCount=0;currentCount<repeatCount;currentCount++){
                    dataParams = new NumericDataParams();
                    dataParams.setName(metricName);
                    dataParams.setData(numericDataPoints
                            .subList(currentCount*jobData.getMetricDataLimit().intValue(), 
                            (currentCount+1)*jobData.getMetricDataLimit().intValue()));
                    dataParams.setTenantId(tenantId);
                    numericDataParamsList.add(dataParams);
                }
                if(balanceCount > 0){
                    dataParams = new NumericDataParams();
                    dataParams.setName(metricName);
                    dataParams.setData(numericDataPoints
                            .subList(repeatCount*jobData.getMetricDataLimit().intValue(), 
                                    numericDataPoints.size()));
                    dataParams.setTenantId(tenantId);
                    numericDataParamsList.add(dataParams);
                }
            }else{
                dataParams = new NumericDataParams();
                dataParams.setName(metricName);
                dataParams.setData(numericDataPoints);
                dataParams.setTenantId(tenantId);
                numericDataParamsList.add(dataParams);
            }
        }
    }

    @Override
    public void execute(JobExecutionContext jonExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = (JobDataMap)jonExecutionContext.getJobDetail().getJobDataMap();
        try {
            _logger.debug("Id:"+jobDataMap.getLong(ScheduleDetail.JOB_ID)+", Type:"
        +jobDataMap.getString(ScheduleDetail.JOB_TYPE)+", Data:"+jobDataMap.get(ScheduleDetail.JOB_DATA));
            this.sendMetrics(jobDataMap.getLong(ScheduleDetail.JOB_ID), 
                    (MetricsJobData) jobDataMap.get(ScheduleDetail.JOB_DATA));
        } catch (Exception ex) {
            _logger.error("Exception, ", ex);
        }
    }

}
