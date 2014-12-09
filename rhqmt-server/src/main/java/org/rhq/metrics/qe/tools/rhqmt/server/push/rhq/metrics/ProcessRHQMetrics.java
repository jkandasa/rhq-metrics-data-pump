package org.rhq.metrics.qe.tools.rhqmt.server.push.rhq.metrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricInput;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.rhq.RHQMetrics;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.RHQuri;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
public class ProcessRHQMetrics {
	private static Logger _logger = Logger.getLogger(ProcessRHQMetrics.class.getName());
	private static int METRICS_MAXIMUM_LIMIT_SEND = 10000;

	public ExecutionStatus sendMetrics(RHQMetricInput rhqMetricInput){
		long startTime = new Date().getTime();
		ExecutionStatus executionStatus = new ExecutionStatus();
		_logger.info("Received a request: "+rhqMetricInput);

		JerseyJSONClient jsonClient = null;

		if(rhqMetricInput.getRhqServer() != null){
			if(rhqMetricInput.getRhqServer().startsWith("http")){
				jsonClient = new JerseyJSONClient(rhqMetricInput.getRhqServer());
			}else{
				jsonClient = new JerseyJSONClient("http://"+rhqMetricInput.getRhqServer());
			}
		}

		try {

			ArrayList<RHQMetrics> rhqMetrics = generateMetrics(rhqMetricInput);
			_logger.info("Generated Metrics, Number of metrics: "+rhqMetrics.size());
			long restStartTime = new Date().getTime();
			if(rhqMetrics.size() < METRICS_MAXIMUM_LIMIT_SEND){
				jsonClient.post(RHQuri.PUSH_METRICS, rhqMetrics.toArray());
			}else{
				for(int sendCount = 0; sendCount < rhqMetrics.size();){
					if((sendCount+METRICS_MAXIMUM_LIMIT_SEND) <= rhqMetrics.size()){
						jsonClient.post(RHQuri.PUSH_METRICS, rhqMetrics.subList(sendCount, sendCount+METRICS_MAXIMUM_LIMIT_SEND).toArray());
						sendCount += METRICS_MAXIMUM_LIMIT_SEND;
					}else{
						jsonClient.post(RHQuri.PUSH_METRICS, rhqMetrics.subList(sendCount, sendCount + (rhqMetrics.size() % METRICS_MAXIMUM_LIMIT_SEND)).toArray());
						sendCount = rhqMetrics.size();
					}
				}
			}			
			//TODO: If sends more than one request, REST execution time calculation will be wrong, fix this.
			executionStatus.setRestRequestTimeTaken(new Date().getTime()-restStartTime);
			executionStatus.setSuccess(true);
		} catch (Exception ex) {
			executionStatus.setSuccess(false);
			executionStatus.setErrorMessage(ex.getMessage());
			executionStatus.setThrowable(ex.fillInStackTrace());
			_logger.error("Error on sending data..", ex);
		}
		_logger.debug("Completed a request: "+rhqMetricInput);

		//Time taken in ms
		executionStatus.setTimeTaken(new Date().getTime() - startTime );

		return executionStatus;
	}

	public Object[] getMetrics(RHQMetricInput rhqMetricInput){
		_logger.debug("Received a request: "+rhqMetricInput);
		return generateMetrics(rhqMetricInput).toArray(); 
	}

	private ArrayList<RHQMetrics> generateMetrics(RHQMetricInput rhqMetricInput){
		if(rhqMetricInput.getMetricLimit() != null){
			METRICS_MAXIMUM_LIMIT_SEND = rhqMetricInput.getMetricLimit() ;
		}
		Random randomMetric = new Random();		
		ArrayList<RHQMetrics> rhqMetrics = new ArrayList<RHQMetrics>();
		long timestamp = new Date().getTime();

		long timeStartFrom = timestamp - (rhqMetricInput.getMetricCount()*rhqMetricInput.getMetricInterval()*1000l);
		long endTime = timestamp;
		
		if(rhqMetricInput.getMetricStartTime() != null){
			timeStartFrom = rhqMetricInput.getMetricStartTime().getTime();
		}

		//TODO: Right it's able to generate for future date. which is wrong. Fix this. 
		//Right now this will not generate for future date. If we needed we have to remove end-date as current date
	
		if(rhqMetricInput.getMetricEndTime() != null){
			endTime = rhqMetricInput.getMetricEndTime().getTime();
		}

		_logger.debug("Metric Start Time: "+new Date(timeStartFrom)+", Metric End Time: "+new Date(endTime));
		
		for(int i=0; i<rhqMetricInput.getMetricCount();i++){
			if( (timeStartFrom+(i*rhqMetricInput.getMetricInterval()*1000l)) <= endTime){
				rhqMetrics.add(
						new RHQMetrics(rhqMetricInput.getMetricNameId(), 
								timeStartFrom+(i*rhqMetricInput.getMetricInterval()*1000l), 
								(randomMetric.nextDouble()*(rhqMetricInput.getMetricValueHighest()-rhqMetricInput.getMetricValueLowest()))+rhqMetricInput.getMetricValueLowest()));
			}else{
				_logger.info("Reached end time before the actual metric count over, Number of metrics: "+rhqMetrics.size());
				break;
			}
		}		
		return rhqMetrics;
	}


}
