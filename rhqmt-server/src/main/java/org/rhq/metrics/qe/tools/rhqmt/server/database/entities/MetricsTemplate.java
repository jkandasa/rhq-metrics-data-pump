package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsTemplate implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3240105297084337373L;
    
    private Long id;
    private String name;
    private String metricNameId;
    private Long metricInterval;
    private Long metricCount;
    private Double metricValueLowest;
    private Double metricValueHighest;
    private Long metricLimit; 
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetricNameId() {
        return metricNameId;
    }

    public void setMetricNameId(String metricNameId) {
        this.metricNameId = metricNameId;
    }

    public Long getMetricInterval() {
        return metricInterval;
    }

    public void setMetricInterval(Long metricInterval) {
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

    public Long getMetricLimit() {
        return metricLimit;
    }

    public void setMetricLimit(Long metricLimit) {
        this.metricLimit = metricLimit;
    }

    public Long getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(Long metricCount) {
        this.metricCount = metricCount;
    }

}
