package org.rhq.metrics.qe.tools.rhqmt.server;

import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class RESTUri {
	public static final String RHQ_METRICS_SEND 	          = LocalUri.RHQ+LocalUri.METRICS+LocalUri.SEND;
	public static final String RHQ_METRICS_GENERATE           = LocalUri.RHQ+LocalUri.METRICS+LocalUri.GENERATE;
	public static final String RHQ_METRICS_TEMPLATE_LIST      = LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE+LocalUri.LIST;
	public static final String RHQ_METRICS_TEMPLATE_GET       = LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE+LocalUri.GET;
	public static final String RHQ_METRICS_TEMPLATE_ADD       = LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE+LocalUri.ADD;
	public static final String RHQ_METRICS_TEMPLATE_EDIT      = LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE+LocalUri.EDIT;
	public static final String RHQ_METRICS_TEMPLATE_DELETE    = LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE+LocalUri.DELETE;
	
	public static final String RHQ_METRICS_JOB_REAL_TIME_LIST   = LocalUri.RHQ+LocalUri.METRICS+LocalUri.JOB+LocalUri.REAL_TIME+LocalUri.LIST;
	public static final String RHQ_METRICS_JOB_REAL_TIME_GET    = LocalUri.RHQ+LocalUri.METRICS+LocalUri.JOB+LocalUri.REAL_TIME+LocalUri.GET;
	public static final String RHQ_METRICS_JOB_REAL_TIME_ADD    = LocalUri.RHQ+LocalUri.METRICS+LocalUri.JOB+LocalUri.REAL_TIME+LocalUri.ADD;
	public static final String RHQ_METRICS_JOB_REAL_TIME_DELETE = LocalUri.RHQ+LocalUri.METRICS+LocalUri.JOB+LocalUri.REAL_TIME+LocalUri.DELETE;
}
