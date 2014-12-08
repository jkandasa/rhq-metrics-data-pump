package org.rhq.metrics.qe.tools.rhqmt.server.representations.core;

import java.io.Serializable;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 05, 2014
 */
public class ExecutionStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1722596759643250355L;

	private Boolean success =null;
	private String errorMessage=null;
	private Long timeTaken=null;
	private Long restRequestTimeTaken=null;
	private Throwable throwable = null;


	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	public Long getRestRequestTimeTaken() {
		return restRequestTimeTaken;
	}
	public void setRestRequestTimeTaken(Long restRequestTimeTaken) {
		this.restRequestTimeTaken = restRequestTimeTaken;
	}
}
