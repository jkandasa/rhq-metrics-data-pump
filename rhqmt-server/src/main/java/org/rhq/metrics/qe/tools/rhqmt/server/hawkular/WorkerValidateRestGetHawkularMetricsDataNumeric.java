package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import java.util.List;

import org.hawkular.metrics.api.jaxrs.NumericDataParams;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobStatusMessage;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.JobStatusMessageService;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.HawkularMetricsUri;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerValidateRestGetHawkularMetricsDataNumeric extends WorkerRestGetHawkularMetrics implements Runnable{

    private List<NumericDataParams> numericDataParams;
    private String tenantId;
    public WorkerValidateRestGetHawkularMetricsDataNumeric(Long jobId, String hawkularUrl,
            String tenantId, List<NumericDataParams> numericDataParams) {
        super(jobId, hawkularUrl);
        this.numericDataParams = numericDataParams;
        this.tenantId = tenantId;
    }

    private String getUri(NumericDataParams txNumericDataParam){
        String uri = new HawkularMetricsUri().getNumericUri(
                this.tenantId, txNumericDataParam.getName(), 
                HawkularMetricsUri.METRICS_DATA_RETRIEVE_NUMERIC);
        return uri+"?end="+(txNumericDataParam.getData().get(0).getTimestamp()+1) //Adding 1 millisecond to get very recent value
                +"&start="+(txNumericDataParam.getData().get(
                        txNumericDataParam.getData().size()-1).getTimestamp()); 
    }

    public void validate(){
        try{
            for(NumericDataParams txNumericDataParam : numericDataParams){
                NumericDataParams rxNumericDataParam = 
                        (NumericDataParams) this.getHawkularData(NumericDataParams.class, 
                                this.getUri(txNumericDataParam));
                _logger.debug("Tx: "+txNumericDataParam);
                _logger.debug("Rx: "+rxNumericDataParam);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String txJson = ow.writeValueAsString(txNumericDataParam);
                if(! txJson.equals(ow.writeValueAsString(rxNumericDataParam))){
                    _logger.warn("Tx and Rx data is mismatching...Tenant Id: "+tenantId+", MetricId: "+txNumericDataParam.getName());
                    _logger.info("Tx: "+txNumericDataParam);
                    _logger.info("Rx: "+rxNumericDataParam);                    
                    new JobStatusMessageService().add(
                            new JobStatusMessage(
                                    jobId, 
                                    JobStatusMessage.TYPE.REALTIME_METRIC.toString(), 
                                    JobStatusMessage.STATUS.FAILED.toString(),
                                    "Tx data and Rx data are mismatching...Tenant Id: "+tenantId+", MetricId: "+
                                    txNumericDataParam.getName()));
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
