package org.rhq.metrics.qe.tools.rhqmt.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadPoolConfiguration {   
   
    @JsonProperty
    private int corePoolSize = 10; 
    
    @JsonProperty
    private int maxPoolSize = 1000; 
    
    @JsonProperty
    private long keepAliveTime = 5000;
    
    @JsonProperty
    private int waitingQueueCapacity = 10000;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getWaitingQueueCapacity() {
        return waitingQueueCapacity;
    }

    public void setWaitingQueueCapacity(int waitingQueueCapacity) {
        this.waitingQueueCapacity = waitingQueueCapacity;
    }
    
    
   

}
