package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import java.util.ArrayList;

import org.hawkular.metrics.api.jaxrs.AvailabilityDataParams;
import org.hawkular.metrics.api.jaxrs.NumericDataParams;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerRestHawkularAddData extends WorkerRestPostHawkularMetrics implements Runnable{

    private MetricsJobData jobData;
    private InputMetricParams.METRICS_TYPE type;
    private String tenantId;
    private ArrayList<NumericDataParams> numericDataParams;
    private ArrayList<AvailabilityDataParams> availabilityDataParams;
    
    @SuppressWarnings("unchecked")
    public WorkerRestHawkularAddData(String hawkularUrl, String restPostUrl,
            ArrayList<?> dataParams, InputMetricParams.METRICS_TYPE type, 
            String tenantId, MetricsJobData jobData) {
        super(hawkularUrl, restPostUrl);
        this.jobData = jobData;
        this.type = type;
        this.tenantId = tenantId;
        if(type.equals(InputMetricParams.METRICS_TYPE.NUMERIC)){
            this.numericDataParams = (ArrayList<NumericDataParams>) dataParams;
        }else if(type.equals(InputMetricParams.METRICS_TYPE.AVAILABILITY)){
            this.availabilityDataParams = (ArrayList<AvailabilityDataParams>) dataParams;
        }
    }
    
    @Timed
    @Override
    public void run() {
        try {
            if(type.equals(InputMetricParams.METRICS_TYPE.NUMERIC)){
                this.postHawkularData(numericDataParams);
            }else if(type.equals(InputMetricParams.METRICS_TYPE.AVAILABILITY)){
                this.postHawkularData(availabilityDataParams);
            }
            if(this.jobData.getValidateResult()){
                _logger.debug("Validation enabled...");
                if(type.equals(InputMetricParams.METRICS_TYPE.NUMERIC)){
                    new WorkerValidateRestGetHawkularMetricsDataNumeric(this.jobData.getJobId(), 
                            hawkularUrl, tenantId, numericDataParams).validate();
                }else if(type.equals(InputMetricParams.METRICS_TYPE.AVAILABILITY)){
                    new WorkerValidateRestGetHawkularMetricsDataAvailability(this.jobData.getJobId(), 
                            hawkularUrl, tenantId, availabilityDataParams).validate();
                }else{
                    _logger.warn("Type not inplemented -> " +type);
                }
            }else{
                _logger.debug("Validation disabled...");
            }

        } catch (Exception ex) {
            _logger.error("Exception, ", ex);
        }
    }

}
