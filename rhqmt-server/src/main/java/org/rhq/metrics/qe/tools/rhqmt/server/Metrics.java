package org.rhq.metrics.qe.tools.rhqmt.server;

import com.codahale.metrics.MetricRegistry;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class Metrics{
    public static final MetricRegistry metricRegistry = new MetricRegistry();
    
    public static final String COUNTER_HAWKULAR             = "counter-hk";
    public static final String TIMER_HAWKULAR               = "timer-hk";
    
    
    public static final String COUNTER_REQUESTS_HAWKULAR_GET_DATA       = COUNTER_HAWKULAR+":get-data";
    public static final String COUNTER_REQUESTS_HAWKULAR_POST_DATA      = COUNTER_HAWKULAR+":post-data";
    
    public static final String TIMER_REQUESTS_HAWKULAR_GET_DATA       = TIMER_HAWKULAR+":get-data";
    public static final String TIMER_REQUESTS_HAWKULAR_POST_DATA      = TIMER_HAWKULAR+":post-data";
    
    
    public static final String TIMER_HAWKULAR_TENANT_CREATION   = TIMER_HAWKULAR+":tenant-creation";
    
    
    public static MetricRegistry getMetrics() {
        return metricRegistry;
    }
}
