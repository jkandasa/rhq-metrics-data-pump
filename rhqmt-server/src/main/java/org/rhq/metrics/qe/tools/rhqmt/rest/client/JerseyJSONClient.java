package org.rhq.metrics.qe.tools.rhqmt.rest.client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Nov 21, 2014
 */
public class JerseyJSONClient {	
	private String serverUrl;
	private int MIN_STATUS_CODE = 200;
	private int MAX_STATUS_CODE = 299;
	
	private Client client = null;
	private WebResource webResource = null;
	private ClientResponse response = null;
	
	public JerseyJSONClient(){
		this.serverUrl = "http://localhost:8080";
	}
	
	public JerseyJSONClient(String serverUrl){
		this.serverUrl = serverUrl;
	}
	
	private void checkStatus(ClientResponse response){
		if (!((response.getStatus() >= MIN_STATUS_CODE) && (response.getStatus() <= MAX_STATUS_CODE))) {
			throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus()+", "+response.getStatusInfo().toString());
		}
	}
	
	public <T> Object post(String uri, Object data, Class<T> dataClass) throws Exception{		
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);
		this.checkStatus(response);
		return response.getEntity(dataClass);
	}
	
	public ClientResponse post(String uri, Object data) throws Exception{
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);
		this.checkStatus(response);
		return response;
	}
	
	
	public <T> Object put(String uri, Object data, Class<T> dataClass) throws Exception{
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, data);
		this.checkStatus(response);
		return response.getEntity(dataClass);
	}
	
	public <T> Object get(String uri, Class<T> dataClass) throws Exception{
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		this.checkStatus(response);
		return response.getEntity(dataClass);
	}
	
	public <T> Object delete(String uri, Class<T> dataClass) throws Exception{
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		this.checkStatus(response);
		return response.getEntity(dataClass);
	}
	
	public <T> Object delete(String uri, Object data, Class<T> dataClass) throws Exception{
		client = Client.create(); 
		webResource = client.resource(serverUrl+uri);
		response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, data);
		this.checkStatus(response);
		return response.getEntity(dataClass);
	}
	
	public ClientResponse delete(String uri, Object data) throws Exception{
        client = Client.create(); 
        webResource = client.resource(serverUrl+uri);
        response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, data);
        this.checkStatus(response);
        return response;
    }
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
