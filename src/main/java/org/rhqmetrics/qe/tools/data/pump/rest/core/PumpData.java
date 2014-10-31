package org.rhqmetrics.qe.tools.data.pump.rest.core;

import java.util.ArrayList;
import java.util.Date;

import org.rhqmetrics.qe.tools.data.pump.rest.client.ServerConnection;
import org.rhqmetrics.qe.tools.data.pump.rest.mapper.RHQMetrics;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */
public class PumpData {

	public static void main(String[] args) throws Exception {
		ServerConnection connection = new ServerConnection();
		ArrayList<RHQMetrics> rhqMetrics = new ArrayList<RHQMetrics>();
		long timestamp = new Date().getTime();
		
		for(int i=0; i<5000;i++){
			rhqMetrics.add(new RHQMetrics("simple-test", timestamp-(5000*60000)+(i*60000), Math.random()*100.0));
		}
		System.out.println("data: "+rhqMetrics.get(0).getValue());
		connection.getRestClient().post("/metrics", rhqMetrics.toArray());

	}

}
