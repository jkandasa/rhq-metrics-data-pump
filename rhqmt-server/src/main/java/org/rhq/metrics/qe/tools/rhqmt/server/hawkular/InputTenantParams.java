package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class InputTenantParams {
    
    private String name;
    private Integer min;
    private Integer max;
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this).toString();
    }
    
    public InputTenantParams(){
        super();
    }
    
    public InputTenantParams(String name){
        super();
        this.name = name;
    }
    
    public InputTenantParams(String name, Integer min, Integer max){
        super();
        this.name = name;
        this.min = min;
        this.max = max;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMin() {
        return min;
    }
    public void setMin(Integer min) {
        this.min = min;
    }
    public Integer getMax() {
        return max;
    }
    public void setMax(Integer max) {
        this.max = max;
    }
}
