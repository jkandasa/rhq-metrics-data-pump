package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class JobDetail implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2452639466174693335L;
    
    public enum JOB_TYPE{
        REAL_TIME_METRICS,DUMP_METRICS
    }
    
    public enum TARGET_CLASS{
        REAL_TIME_METRICS("org.rhq.metrics.qe.tools.rhqmt.server.scheduler.jobs.SendHawkularMetricsRealTime");
        
        String targetClass =null;
        TARGET_CLASS(String targetClass){
            this.targetClass = targetClass;
        }
        public String toString(){
            return this.targetClass;
        }
    }
    
    private Long id;
    private String name;
    private String jobType;
    private String cronExpression;
    private Integer repeatCount;
    private Long repeatInterval;
    private Date fromTime;
    private Date toTime;
    private Boolean enabled;
    private String targetClass;
    
    private MetricsJobData metricsJobData;
    
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public MetricsJobData getMetricsJobData() {
        return metricsJobData;
    }

    public void setMetricsJobData(MetricsJobData metricsJobData) {
        this.metricsJobData = metricsJobData;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

}
