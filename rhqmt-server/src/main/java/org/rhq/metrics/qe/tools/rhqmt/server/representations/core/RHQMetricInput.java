package org.rhq.metrics.qe.tools.rhqmt.server.representations.core;


import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Nov 27, 2014
 */

public class RHQMetricInput  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9105063721370889383L;
	
	private String rhqServer;
	private String metricNameId;
	private Integer metricCount;
	private Integer metricInterval;
	private Double metricValueLowest;
	private Double metricValueHighest;
	private Date metricStartTime;
	private Date metricEndTime;
	private String template;
	private Integer metricLimit;
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this).toString();
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}


	public String getRhqServer() {
		return rhqServer;
	}


	public void setRhqServer(String rhqServer) {
		this.rhqServer = rhqServer;
	}


	public Integer getMetricLimit() {
		return metricLimit;
	}


	public void setMetricLimit(Integer metricLimit) {
		this.metricLimit = metricLimit;
	}

}