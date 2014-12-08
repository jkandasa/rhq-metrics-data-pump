package org.rhq.metrics.qe.tools.rhqmt.server.push.rhq.metrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricSimpleInput;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.rhq.RHQMetrics;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.RHQuri;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
public class PumpData {
	private static Logger _logger = Logger.getLogger(PumpData.class.getName());

	public ExecutionStatus sendMetrics(RHQMetricSimpleInput simpleInput){
		long startTime = new Date().getTime();
		ExecutionStatus executionStatus = new ExecutionStatus();
		_logger.info("Received a request: "+simpleInput);

		JerseyJSONClient jsonClient = null;

		if(simpleInput.getRhqServer() != null){
			if(simpleInput.getRhqServer().startsWith("http")){
				jsonClient = new JerseyJSONClient(simpleInput.getRhqServer());
			}else{
				jsonClient = new JerseyJSONClient("http://"+simpleInput.getRhqServer());
			}
		}

		try {
			long restStartTime = new Date().getTime();
			jsonClient.post(RHQuri.PUSH_METRICS, generateMetrics(simpleInput).toArray());
			executionStatus.setRestRequestTimeTaken(new Date().getTime()-restStartTime);
			executionStatus.setSuccess(true);
		} catch (Exception ex) {
			executionStatus.setSuccess(false);
			executionStatus.setErrorMessage(ex.getMessage());
			executionStatus.setThrowable(ex.fillInStackTrace());
			_logger.error("Error on sending data..", ex);
		}
		_logger.info("Completed a request: "+simpleInput);

		//Time taken in ms
		executionStatus.setTimeTaken(new Date().getTime() - startTime );

		return executionStatus;
	}

	public Object[] getMetrics(RHQMetricSimpleInput simpleInput){
		_logger.info("Received a request: "+simpleInput);
		return generateMetrics(simpleInput).toArray(); 
	}

	private ArrayList<RHQMetrics> generateMetrics(RHQMetricSimpleInput simpleInput){
		Random randomMetric = new Random();		
		ArrayList<RHQMetrics> rhqMetrics = new ArrayList<RHQMetrics>();
		long timestamp = new Date().getTime();

		long timeStartFrom = timestamp - (simpleInput.getMetricCount()*simpleInput.getMetricInterval()*1000);

		for(int i=0; i<simpleInput.getMetricCount();i++){
			rhqMetrics.add(
					new RHQMetrics(simpleInput.getMetricNameId(), 
							timeStartFrom+(i*simpleInput.getMetricInterval()*1000), 
							(randomMetric.nextDouble()*(simpleInput.getMetricValueHighest()-simpleInput.getMetricValueLowest()))+simpleInput.getMetricValueLowest()));
		}		
		return rhqMetrics;
	}


}
