package org.rhqmetrics.qe.tools.data.pump.rest.client;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */
public class ServerConnection {

	public RestEasyJSONClient getRestClient(){
		RestEasyJSONClient client = new RestEasyJSONClient();
		client.setServerUrl("http://localhost:8080");
		client.setRootUrl("/rhq-metrics");
		return client;
	}
}
