package org.rhq.metrics.qe.tools.dw.server.push.rhq.metrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.dw.server.representations.core.SimpleInput;
import org.rhq.metrics.qe.tools.dw.server.representations.rhq.RHQMetrics;
import org.rhq.metrics.qe.tools.dw.server.uri.RHQuri;
import org.rhq.metrics.qe.tools.rest.client.ServerConnection;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
public class PumpData {
	private static Logger _logger = Logger.getLogger(PumpData.class.getName());
	
	public void sendData(SimpleInput simpleInput){
		_logger.info("Received a request: "+simpleInput);
		
		ServerConnection connection=null;

		if(simpleInput.getRhqServerAddress() != null){
			if(simpleInput.getRhqServerAddress().startsWith("http")){
				connection = new ServerConnection(simpleInput.getRhqServerAddress(), null);
			}else{
				connection = new ServerConnection("http://"+simpleInput.getRhqServerAddress()+":"+simpleInput.getRhqServerPort(), null);
			}
		}
		
		Random randomMetric = new Random();		
		ArrayList<RHQMetrics> rhqMetrics = new ArrayList<RHQMetrics>();
		long timestamp = new Date().getTime();

		long timeStartFrom = timestamp - (simpleInput.getMetricCount()*simpleInput.getMetricInterval()*1000);

		for(int i=0; i<simpleInput.getMetricCount();i++){
			rhqMetrics.add(
					new RHQMetrics(simpleInput.getMetricNameId(), 
							timeStartFrom+(i*simpleInput.getMetricInterval()*1000), 
							(randomMetric.nextDouble()*(simpleInput.getMetricValueHighest()-simpleInput.getMetricValueLowest()))+simpleInput.getMetricValueLowest()));
			//System.out.println("data: "+new Date(rhqMetrics.get(i).getTimestamp()));
		}

		try {
			connection.getRestClient().postRHQMetrics(RHQuri.PUSH_METRIC, rhqMetrics.toArray());
		} catch (Exception ex) {
			_logger.error("Error on sending data..", ex);
		}
		_logger.info("Completed the request: "+simpleInput);

	}


}
