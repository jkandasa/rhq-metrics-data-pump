package org.rhqmetrics.qe.tools.data.pump.rest.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.rhqmetrics.qe.tools.data.pump.rest.client.ServerConnection;
import org.rhqmetrics.qe.tools.data.pump.rest.mapper.RHQMetrics;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */
public class PumpData {

	public static void sendData(DataPumpInput dataPumpInput) throws Exception {
		ServerConnection connection=null;

		if(dataPumpInput.getRhqServerAddress() != null){
			if(dataPumpInput.getRhqServerAddress().startsWith("http")){
				connection = new ServerConnection(dataPumpInput.getRhqServerAddress(), null);
			}else{
				connection = new ServerConnection("http://"+dataPumpInput.getRhqServerAddress(), null);
			}
		}

		System.out.println("Server Address: "+connection.getRestClient().getServerUrl());
		Random randomMetric = new Random();		
		ArrayList<RHQMetrics> rhqMetrics = new ArrayList<RHQMetrics>();
		long timestamp = new Date().getTime();

		long timeStartFrom = timestamp - (dataPumpInput.getDataCount()*dataPumpInput.getDataInterval()*1000);

		for(int i=0; i<dataPumpInput.getDataCount();i++){
			rhqMetrics.add(
					new RHQMetrics(dataPumpInput.getMetricNameId(), 
							timeStartFrom+(i*dataPumpInput.getDataInterval()*1000), 
							(randomMetric.nextDouble()*(dataPumpInput.getMetricValueHighest()-dataPumpInput.getMetricValueLowest()))+dataPumpInput.getMetricValueLowest()));
			//System.out.println("data: "+new Date(rhqMetrics.get(i).getTimestamp()));
		}

		connection.getRestClient().post("/metrics", rhqMetrics.toArray());

	}

}
