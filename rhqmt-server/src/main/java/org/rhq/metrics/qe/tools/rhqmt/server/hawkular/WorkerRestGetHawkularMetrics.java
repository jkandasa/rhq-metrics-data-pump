package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.Metrics;

import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerRestGetHawkularMetrics {
    protected Logger _logger = Logger.getLogger(WorkerRestPostHawkularMetrics.class.getName());
    protected String hawkularUrl;
    protected Long jobId;

    public WorkerRestGetHawkularMetrics(Long jobId, String hawkularUrl){
        this.jobId = jobId;
        this.hawkularUrl = hawkularUrl;
    }

    @Timed
    public <T> Object getHawkularData(Class<T> dataClass, String restGetUrl) throws Exception{
        final Timer timer = Metrics.getMetrics().timer(Metrics.TIMER_REQUESTS_HAWKULAR_GET_DATA); //Mark it in the registry
        final Timer.Context context = timer.time();
        try{
            JerseyJSONClient jsonClient = null;
            if(hawkularUrl != null){
                jsonClient = new JerseyJSONClient(hawkularUrl);
                _logger.debug("Hawkular: "+ hawkularUrl+ ", restUrl: "+restGetUrl+", JobId: "+jobId);
                return jsonClient.get(restGetUrl, dataClass);
            }
        }finally{
            context.stop();
        }       
        return null;
    }
}
