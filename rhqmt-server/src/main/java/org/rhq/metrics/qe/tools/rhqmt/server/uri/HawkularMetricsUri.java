package org.rhq.metrics.qe.tools.rhqmt.server.uri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class HawkularMetricsUri {
    public static final String RHQ_METRICS_URI = "/hawkular-metrics";
    public static final String PUSH_METRICS = RHQ_METRICS_URI+"/metrics";

    public static final String TENENT_ID                   = "@@tenantid@@";
    public static final String NUMERIC_ID                  = "@@numericid@@";
    public static final String AVAILABILITY_ID             = "@@availabilityid@@";

    public static final String BASE                        = "/hawkular-metrics";
    public static final String TENANTS                     = BASE+"/tenants";

    public static final String METRICS                     = BASE+"/"+TENENT_ID+"/metrics";
    public static final String METRICS_NUMEIRC             = METRICS+"/numeric";
    public static final String METRICS_AVAILABILITY        = METRICS+"/availability";

    public static final String METRICS_DATA_NUMERIC        = METRICS_NUMEIRC+"/data";
    public static final String METRICS_DATA_AVAILABILITY   = METRICS_AVAILABILITY+"/data";

    public static final String METRICS_DATA_RETRIEVE_NUMERIC        = METRICS_NUMEIRC+"/"+NUMERIC_ID+"/data";
    public static final String METRICS_DATA_RETRIEVE_AVAILABILITY   = METRICS_AVAILABILITY+"/"+AVAILABILITY_ID+"/data";
    
    public String getUri(String tenantId, String url){
        return url.replaceAll(TENENT_ID, tenantId);
    }
    
    public String getNumericUri(String tenantId, String numericId, String url){
        return url.replaceAll(TENENT_ID, tenantId).replaceAll(NUMERIC_ID, numericId);
    }
    
    public String getAvailabilityUri(String tenantId, String availabilityId, String url){
        return url.replaceAll(TENENT_ID, tenantId).replaceAll(AVAILABILITY_ID, availabilityId);
    }
}
