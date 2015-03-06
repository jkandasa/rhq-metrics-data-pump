package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import java.util.ArrayList;

import org.hawkular.metrics.api.jaxrs.AvailabilityDataParams;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobStatusMessage;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.JobStatusMessageService;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.HawkularMetricsUri;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerValidateRestGetHawkularMetricsDataAvailability extends WorkerRestGetHawkularMetrics implements Runnable{

    private ArrayList<AvailabilityDataParams> availabilityDataParams;
    private String tenantId;
    public WorkerValidateRestGetHawkularMetricsDataAvailability(Long jobId, String hawkularUrl,
            String tenantId, ArrayList<AvailabilityDataParams> availabilityDataParams) {
        super(jobId, hawkularUrl);
        this.availabilityDataParams = availabilityDataParams;
        this.tenantId = tenantId;
    }

    private String getUri(AvailabilityDataParams txAvailabilityDataParam){
        String uri = new HawkularMetricsUri().getAvailabilityUri(
                this.tenantId, 
                txAvailabilityDataParam.getName(), 
                HawkularMetricsUri.METRICS_DATA_RETRIEVE_AVAILABILITY);
        return uri+"?end="+(txAvailabilityDataParam.getData().get(0).getTimestamp()+1) //Adding 1 millisecond to get very recent value
                +"&start="+(txAvailabilityDataParam.getData().get(
                        txAvailabilityDataParam.getData().size()-1).getTimestamp()); 
    }

    public void validate(){
        try{
            for(AvailabilityDataParams txAvailabilityDataParam : availabilityDataParams){
                AvailabilityDataParams rxAvailabilityDataParam = 
                        (AvailabilityDataParams) this.getHawkularData(AvailabilityDataParams.class, 
                                this.getUri(txAvailabilityDataParam));
                _logger.debug("Tx: "+txAvailabilityDataParam);
                _logger.debug("Rx: "+rxAvailabilityDataParam);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String txJson = ow.writeValueAsString(txAvailabilityDataParam);
                if(! txJson.equals(ow.writeValueAsString(rxAvailabilityDataParam))){
                    _logger.warn("Tx and Rx data is mismatching...Tenant Id: "+tenantId+", MetricId: "+txAvailabilityDataParam.getName());
                    _logger.info("Tx: "+txAvailabilityDataParam);
                    _logger.info("Rx: "+rxAvailabilityDataParam);
                    new JobStatusMessageService().add(
                            new JobStatusMessage(
                                    jobId, 
                                    JobStatusMessage.TYPE.REALTIME_METRIC.toString(), 
                                    JobStatusMessage.STATUS.FAILED.toString(),
                                    "Tx data and Rx data are mismatching...Tenant Id: "+tenantId+", MetricId: "+
                                    txAvailabilityDataParam.getName()));
                }
            }
        }catch (Exception ex){
            _logger.error("Exception, ", ex);
        }       
    }

    @Timed
    @Override
    public void run() {
        this.validate();
    } 
}
