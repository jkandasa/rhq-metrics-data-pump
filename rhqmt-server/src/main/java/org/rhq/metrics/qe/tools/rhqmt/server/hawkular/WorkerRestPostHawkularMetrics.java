package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.jersey.api.client.ClientResponse;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerRestPostHawkularMetrics{
    protected Logger _logger = Logger.getLogger(WorkerRestPostHawkularMetrics.class.getName());
    protected String hawkularUrl;
    protected String restPostUrl;

    public WorkerRestPostHawkularMetrics(String hawkularUrl, String restPostUrl){
        this.hawkularUrl = hawkularUrl;
        this.restPostUrl = restPostUrl;
    }

    @Timed
    public void postHawkularData(Object data) throws Exception{
        JerseyJSONClient jsonClient = null;
        if(hawkularUrl != null){
            jsonClient = new JerseyJSONClient(hawkularUrl);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(data);
            _logger.debug("Hawkular: "+ hawkularUrl+ ", restUrl: "+restPostUrl);
            _logger.debug("Hawkular Object JSON String: "+json );
            
            ClientResponse clientResponse =  jsonClient.post(restPostUrl, data);
            _logger.debug("Status: "+ clientResponse.getStatus());
        }
    }
}
