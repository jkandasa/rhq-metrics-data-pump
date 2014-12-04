package org.rhq.metrics.qe.tools.rest.client;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */
public class ServerConnection {

	private String serverUrl = "http://localhost:8080";
	private String rootUrl = "/rhq-metrics";

	public ServerConnection(String serverUrl, String rootUrl){
		if(serverUrl != null){
			this.serverUrl=serverUrl;
		}
		if(rootUrl != null){
			this.rootUrl=rootUrl;
		}
	}

	public RestEasyJSONClient getRestClient(){
		RestEasyJSONClient client = new RestEasyJSONClient();
		client.setServerUrl(this.serverUrl);
		client.setRootUrl(this.rootUrl);
		return client;
	}
}