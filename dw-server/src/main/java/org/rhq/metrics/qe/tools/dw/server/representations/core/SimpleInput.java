package org.rhq.metrics.qe.tools.dw.server.representations.core;


import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Nov 27, 2014
 */

public class SimpleInput  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9105063721370889383L;
	
	private String rhqServerAddress;
	private Integer rhqServerPort;
	private String metricNameId;
	private Integer metricCount;
	private Integer metricInterval;
	private Double metricValueLowest;
	private Double metricValueHighest;
	private Date metricStartTime;
	private Date metricEndTime;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this).toString();
	}
	
	public String getRhqServerAddress() {
		return rhqServerAddress;
	}
	public void setRhqServerAddress(String rhqServerAddress) {
		this.rhqServerAddress = rhqServerAddress;
	}
	public String getMetricNameId() {
		return metricNameId;
	}
	public void setMetricNameId(String metricNameId) {
		this.metricNameId = metricNameId;
	}
	public Integer getMetricCount() {
		return metricCount;
	}
	public void setMetricCount(Integer metricCount) {
		this.metricCount = metricCount;
	}
	public Integer getMetricInterval() {
		return metricInterval;
	}
	public void setMetricInterval(Integer metricInterval) {
		this.metricInterval = metricInterval;
	}
	public Double getMetricValueLowest() {
		return metricValueLowest;
	}
	public void setMetricValueLowest(Double metricValueLowest) {
		this.metricValueLowest = metricValueLowest;
	}
	public Double getMetricValueHighest() {
		return metricValueHighest;
	}
	public void setMetricValueHighest(Double metricValueHighest) {
		this.metricValueHighest = metricValueHighest;
	}
	public Date getMetricStartTime() {
		return metricStartTime;
	}
	public void setMetricStartTime(Date metricStartTime) {
		this.metricStartTime = metricStartTime;
	}
	public Date getMetricEndTime() {
		return metricEndTime;
	}
	public void setMetricEndTime(Date metricEndTime) {
		this.metricEndTime = metricEndTime;
	}
	public Integer getRhqServerPort() {
		return rhqServerPort;
	}
	public void setRhqServerPort(Integer rhqServerPort) {
		this.rhqServerPort = rhqServerPort;
	}

}