package org.rhq.metrics.qe.tools.rhqmt.server.database.entities;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class JobStatusMessage implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3017197786358366989L;

    public enum TYPE{
        REALTIME_METRIC
    }
    public enum STATUS{
        RUNNING,
        STOPPED,
        FAILED,
        INFO,
        WARNING,
        ERROR,
        SUCCESS,
        DISBLED,
        ENABLED
    }
    
    private Long id;
    private Long jobId;
    private String type;
    private String status;
    private Date creationTime;
    private String message;
    
    public JobStatusMessage(){
        super();
    }
    
    public JobStatusMessage(Long jobId, String type, String status, String message){
        this.jobId = jobId;
        this.type = type;
        this.status = status;
        this.message = message;
    }
    
    
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
