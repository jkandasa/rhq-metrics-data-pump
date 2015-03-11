package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.InputMetricParams;
import org.rhq.metrics.qe.tools.rhqmt.server.hawkular.InputTenantParams;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsJobData{

    private Long id;
    private Long jobId;
    private String targetServer;
    private String tenantId;
    private String metricNameId;
    private Long metricInterval;
    private Long metricDataCount;
    private Double metricValueLowest;
    private Double metricValueHighest;
    private Long metricDataLimit;
    private Long metricTimeLimit;
    private Long metricLimit;
    private Boolean validateResult;
    private Boolean initialSetupDone;
    
    private List<InputTenantParams> inputTenantParams;
    private List<InputMetricParams> inputMetricParams;
    
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

    public Long getMetricDataLimit() {
        return metricDataLimit;
    }

    public void setMetricDataLimit(Long metricLimit) {
        this.metricDataLimit = metricLimit;
    }

    public Boolean getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(Boolean validateResult) {
        this.validateResult = validateResult;
    }

    public Long getMetricDataCount() {
        return metricDataCount;
    }

    public void setMetricDataCount(Long metricCount) {
        this.metricDataCount = metricCount;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<InputTenantParams> getInputTenantParams() {
        return inputTenantParams;
    }

    public void setInputTenantParams(List<InputTenantParams> inputTenantParams) {
        this.inputTenantParams = inputTenantParams;
    }

    public List<InputMetricParams> getInputMetricParams() {
        return inputMetricParams;
    }

    public void setInputMetricParams(List<InputMetricParams> inputMetricParams) {
        this.inputMetricParams = inputMetricParams;
    }

    public Long getMetricTimeLimit() {
        return metricTimeLimit;
    }

    public void setMetricTimeLimit(Long metricTimeLimit) {
        this.metricTimeLimit = metricTimeLimit;
    }

    public Boolean getInitialSetupDone() {
        return initialSetupDone;
    }

    public void setInitialSetupDone(Boolean initialSetup) {
        this.initialSetupDone = initialSetup;
    }

    public Long getMetricLimit() {
        return metricLimit;
    }

    public void setMetricLimit(Long metricLimit) {
        this.metricLimit = metricLimit;
    }
}
