package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.Metrics;

import com.codahale.metrics.Timer;
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

    
    public void postHawkularData(Object data) throws Exception{
        Metrics.getMetrics().meter(Metrics.REQUESTS_HAWKULAR_POST_DATA).mark(); //Mark it in the registry
        final Timer timer = Metrics.getMetrics().timer(Metrics.TIMER_HAWKULAR_POST_DATA); //Mark it in the registry
        final Timer.Context context = timer.time();
        try{
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
        }finally{
            context.stop(); //Stop the timer
        }       
    }
}
