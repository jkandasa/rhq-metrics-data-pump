package org.rhq.metrics.qe.tools.dw.server.representations.rhq;

import java.io.Serializable;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Oct 31, 2014
 */

public class RHQMetrics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7878350247102722900L;

	String id;
	long timestamp;
	double value;

	public RHQMetrics(){

	}

	public RHQMetrics(String id, long timestamp, double value){
		this.id=id;
		this.timestamp=timestamp;
		this.value=value;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}