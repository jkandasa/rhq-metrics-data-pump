package org.rhq.metrics.qe.tools.rhqmt.server;

import com.codahale.metrics.MetricRegistry;



public class Metrics{
    public static final MetricRegistry metricRegistry = new MetricRegistry();
    
    public static final String REQUESTS_HAWKULAR_ADD_DATA       = "requests-hawkular-add-data";
    public static final String REQUESTS_HAWKULAR_POST_DATA      = "requests-hawkular-post-data";
    
    public static final String TIMER_HAWKULAR_POST_DATA         = "timer-hawkular-post-data";
    public static final String TIMER_HAWKULAR_TENANT_CREATION   = "timer-hawkular-tenant-creation";
    
    
    public static MetricRegistry getMetrics() {
        return metricRegistry;
    }
}
