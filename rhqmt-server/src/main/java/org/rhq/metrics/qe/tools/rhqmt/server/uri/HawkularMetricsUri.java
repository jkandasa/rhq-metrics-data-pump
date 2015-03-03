package org.rhq.metrics.qe.tools.rhqmt.server.uri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class HawkularMetricsUri {
	public static final String RHQ_METRICS_URI = "/hawkular-metrics";
	public static final String PUSH_METRICS = RHQ_METRICS_URI+"/metrics";
	
	public static final String TENENT_ID                  = "@@tenantid@@";
	
	public static final String BASE                        = "/hawkular-metrics";
	public static final String TENANTS                     = BASE+"/tenants";
	
	public static final String METRICS                     = BASE+"/"+TENENT_ID+"/metrics";
	public static final String METRICS_NUMEIRC             = METRICS+"/numeric";
	public static final String METRICS_AVAILABILITY        = METRICS+"/availability";
	
	public static final String METRICS_DATA_NUMERIC        = METRICS_NUMEIRC+"/data";
	public static final String METRICS_DATA_AVAILABILITY   = METRICS_AVAILABILITY+"/data";
}
