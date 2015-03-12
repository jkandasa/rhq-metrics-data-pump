package org.rhq.metrics.qe.tools.rhqmt.server;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class GraphiteReporterConfiguration {
    @JsonProperty
    private String hostname; 
    
    @JsonProperty
    private int port = 80; 
    
    @JsonProperty
    private long sendEverySeconds = 60;
    
    @JsonProperty
    private Boolean enabled = false;
    
    @JsonProperty
    private String baseReference = "HAWKULAR-MG_";
    

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getSendEverySeconds() {
        return sendEverySeconds;
    }

    public void setSendEverySeconds(long sendEverySeconds) {
        this.sendEverySeconds = sendEverySeconds;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getBaseReference() {
        return baseReference;
    }

    public void setBaseReference(String baseReference) {
        this.baseReference = baseReference;
    }
}
