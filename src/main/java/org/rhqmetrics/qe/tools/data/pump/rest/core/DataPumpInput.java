package org.rhqmetrics.qe.tools.data.pump.rest.core;

import org.kohsuke.args4j.Option;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Nov 03, 2014
 */
public class DataPumpInput {
	 
	@Option(name="-s", usage="RHQ-Metrics Server address with port [localhost:8080]", aliases="--server")
	private String rhqServerAddress="localhost:8080";
	
	@Option(name="-mni", usage="Metric Name or Id", aliases="--metric-name-id")
	private String metricNameId;
	
	@Option(name="-mvl", usage="Metric Value Lowest", aliases="--metric-value-lowest")
	private Double metricValueLowest=0.0;
	
	@Option(name="-mvh", usage="Metric Value Highest", aliases="--metric-value-highest")
	private Double metricValueHighest=100.0;
	
	@Option(name="-di", usage="Data interval in seconds", aliases="--data-interval")
	private Integer dataInterval=60;  
	
	@Option(name="-dc", usage="Data count", aliases="--data-count")
	private Integer dataCount=100;  
    
	
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

	public Integer getDataInterval() {
		return dataInterval;
	}

	public void setDataInterval(Integer dataInterval) {
		this.dataInterval = dataInterval;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
	
}
