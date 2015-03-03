package org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.settings.RhqmtServerSettings;
import org.rhq.metrics.qe.tools.rhqmt.rest.client.JerseyJSONClient;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMTServerDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricInput;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.hawkular.RHQMetrics;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;
import org.rhq.metrics.qe.tools.rhqmt.server.RESTUri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RHQMTServer {
	protected static JerseyJSONClient client = null;
	private static String serversionUri=LocalUri.SERVER+LocalUri.SERVER_DETAIL;
	private static boolean connectionStatus = false;
	
	public static RHQMTServerDetail connect(String serverUrl) throws Exception{
		if(! isConnectionStatus()){
			client= null;
		}
		if(client == null){
			if(serverUrl == null){
				client = new JerseyJSONClient(RhqmtServerSettings.getUrl());
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
	
	public static ExecutionStatus sendMetricsToServer(RHQMetricInput rhqMetricInput) throws Exception{
		if(client == null){
			throw new Exception(ErrorMessgae.NO_CONNECTION);
		}
		return (ExecutionStatus) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_SEND, rhqMetricInput, ExecutionStatus.class);
	}
	
	public static RHQMetrics[] generateMetrics(RHQMetricInput rhqMetricInput) throws Exception{
		if(client == null){
			throw new Exception(ErrorMessgae.NO_CONNECTION);
		}
		return (RHQMetrics[]) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_GENERATE, rhqMetricInput, RHQMetrics[].class);
	}
	
	
}
