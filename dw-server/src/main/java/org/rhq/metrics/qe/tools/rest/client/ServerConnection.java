package org.rhq.metrics.qe.tools.rest.client;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */
public class ServerConnection {

	private String serverUrl = "http://localhost:8080";

	public ServerConnection(String serverUrl){
		if(serverUrl != null){
			this.serverUrl=serverUrl;
		}
	}
	
	public ServerConnection(){
		
	}
	
	public JerseyJSONClient getRestClient(){
		JerseyJSONClient client = new JerseyJSONClient();
		client.setServerUrl(this.serverUrl);
		return client;
	}
}