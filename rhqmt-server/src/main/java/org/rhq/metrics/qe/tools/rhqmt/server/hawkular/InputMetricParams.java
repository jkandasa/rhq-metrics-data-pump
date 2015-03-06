package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class InputMetricParams extends InputTenantParams{
    public enum METRICS_TYPE{
        NUMERIC, AVAILABILITY
    }
    
    private METRICS_TYPE metricsType;

    public String toString(){
        return ToStringBuilder.reflectionToString(this).toString();
    }
    
    public InputMetricParams(){
        super();
    }
    
    public InputMetricParams(String name){
        super(name);
    }
    
    public InputMetricParams(String name, METRICS_TYPE metricsType){
        super(name);
        this.setMetricsType(metricsType);
    }
    
    public InputMetricParams(String name, Integer min, Integer max){
        super(name, min, max);
    }
    
    public InputMetricParams(String name, Integer min, Integer max, METRICS_TYPE metricsType){
        super(name, min, max);
        this.setMetricsType(metricsType);
    }

    public METRICS_TYPE getMetricsType() {
        return metricsType;
    }

    public void setMetricsType(METRICS_TYPE metricsType) {
        this.metricsType = metricsType;
    }

    

}
