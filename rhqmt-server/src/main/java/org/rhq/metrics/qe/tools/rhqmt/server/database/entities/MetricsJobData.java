package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsJobData implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3505951305702315536L;

    private Long id;
    private Long jobId;
    private String targetServer;
    private String tenantId;
    private String metricNameId;
    private Long metricInterval;
    private Long metricCount;
    private Double metricValueLowest;
    private Double metricValueHighest;
    private Long metricLimit;
    private Boolean validateResult;
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getTargetServer() {
        return targetServer;
    }

    public void setTargetServer(String targetServer) {
        this.targetServer = targetServer;
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

    public Boolean getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(Boolean validateResult) {
        this.validateResult = validateResult;
    }

    public Long getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(Long metricCount) {
        this.metricCount = metricCount;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
