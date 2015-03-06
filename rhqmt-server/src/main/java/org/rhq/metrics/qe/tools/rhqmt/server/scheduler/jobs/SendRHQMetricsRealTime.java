package org.rhq.metrics.qe.tools.rhqmt.server.scheduler.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hawkular.metrics.api.jaxrs.NumericDataParams;
import org.hawkular.metrics.api.jaxrs.NumericDataPoint;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.WorkerRestPostHawkularMetrics;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ScheduleDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.HawkularMetricsUri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class SendRHQMetricsRealTime implements Job{
    private static Logger _logger = Logger.getLogger(SendRHQMetricsRealTime.class);

    private void sendMetrics(Long jobId, MetricsJobData jobData){

         /*//Check Tenant is exists
        try {
            TenantParams[] tenantParams = (TenantParams[]) jsonClient.get(HawkularMetricsUri.TENANTS, TenantParams[].class);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
         */

        Random randomMetric = new Random();     

        ArrayList<NumericDataPoint> numericDataPoints = new ArrayList<>();
        
        /*
        long timestamp = new Date().getTime();

        
        long timeStartFrom = timestamp - (jobData.getMetricCount()*jobData.getMetricInterval());

        for(int i=0; i<jobData.getMetricCount();i++){
            numericDataPoints.add(new NumericDataPoint(timeStartFrom+(i*jobData.getMetricInterval()*1000l), 
                    (randomMetric.nextDouble()*(jobData.getMetricValueHighest()-jobData.getMetricValueLowest()))+jobData.getMetricValueLowest()));
        }   
         */
        for(int i=0; i<jobData.getMetricDataCount();i++){
            numericDataPoints.add(new NumericDataPoint(new Date().getTime(), 
                    (randomMetric.nextDouble()*(jobData.getMetricValueHighest()-jobData.getMetricValueLowest()))+jobData.getMetricValueLowest()));
        }
        
        ArrayList<NumericDataParams> dataParamsList = new ArrayList<NumericDataParams>();

        NumericDataParams dataParams = new NumericDataParams();

        dataParams.setName(jobData.getMetricNameId());
        dataParams.setData(numericDataPoints);

        dataParamsList.add(dataParams);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(dataParamsList);
            _logger.debug("JSON String: "+json );
            if(numericDataPoints.size() > jobData.getMetricDataLimit()){
                //TODO: add limit code
            }
            //jsonClient.post(HawkularMetricsUri.METRICS_DATA_NUMERIC.replaceAll(HawkularMetricsUri.TENENT_ID, jobData.getTenantId()), dataParamsList.toArray());
            //Thread thread = new Thread(new WorkerRestPostHawkularMetrics(jobData, dataParamsList.toArray()));
            //thread.start();
        } catch (Exception ex) {
            _logger.warn("Failed, ", ex);
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
