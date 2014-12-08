package org.rhq.metrics.qe.tools.rhqmt.cli;

import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMTServerDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 05, 2014
 */
public class RHQMTServer {
	private static JerseyJSONClient client = null;
	private static String serversionUri=LocalUri.SERVER+LocalUri.SERVER_DETAIL;
	private static boolean connectionStatus = false;
	
	public static RHQMTServerDetail connect(String serverUrl) throws Exception{
		if(! isConnectionStatus()){
			client= null;
		}
		if(client == null){
			if(serverUrl == null){
				client = new JerseyJSONClient("http://localhost:8090");
			}else{
				if(! serverUrl.startsWith("http")){
					serverUrl= "http://"+serverUrl;
				}
				client = new JerseyJSONClient(serverUrl);
			}
		}
		RHQMTServerDetail rhqmtServerDetail = (RHQMTServerDetail) client.get(serversionUri, RHQMTServerDetail.class);
		setConnectionStatus(true);
		return rhqmtServerDetail;
	}
	
	public static void disconnect(){
		if(client != null){
			client = null;
		}
		setConnectionStatus(false);
	}
	
	public static boolean isConnected(){
		return isConnectionStatus();
	}
	
	public static JerseyJSONClient getClient(){
		return client;
	}

	private static boolean isConnectionStatus() {
		return connectionStatus;
	}

	private static void setConnectionStatus(boolean connectionStatus) {
		RHQMTServer.connectionStatus = connectionStatus;
	}
}
