package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;

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
        JerseyJSONClient jsonClient = null;
        if(hawkularUrl != null){
            jsonClient = new JerseyJSONClient(hawkularUrl);
            _logger.debug("Hawkular: "+ hawkularUrl+ ", restUrl: "+restGetUrl+", JobId: "+jobId);
            return jsonClient.get(restGetUrl, dataClass);
        }
        return null;
    }
}
