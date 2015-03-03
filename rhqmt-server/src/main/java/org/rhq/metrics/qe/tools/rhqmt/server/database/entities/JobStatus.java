package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class JobStatus implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3017197786358366989L;

    public enum STATUS{
        RUNNING,STOPPED,FAILED
    }
    
    private Long id;
    private Long jobId;
    private String status;
    private Date creationTime;
    private String log;
    private String message;
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
