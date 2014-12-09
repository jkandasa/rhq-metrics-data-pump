package org.rhq.metrics.qe.tools.rhqmt.cli;

import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class RESTUri {
	public static final String RHQ_METRICS_SEND 	= LocalUri.RHQ+LocalUri.METRICS+LocalUri.SEND;
	public static final String RHQ_METRICS_GENERATE = LocalUri.RHQ+LocalUri.METRICS+LocalUri.GENERATE;
}
