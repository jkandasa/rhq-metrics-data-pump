package org.rhq.metrics.qe.tools.rhqmt.server.representations.core;

import java.io.Serializable;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class RHQMTServerDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1595018651281986751L;
	
	private String version = null;
	private Long serverTime = null;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Long getServerTime() {
		return serverTime;
	}
	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}


}
