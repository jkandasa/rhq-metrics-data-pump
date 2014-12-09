package org.rhq.metrics.qe.tools.rhqmt.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.rhqmt.server.push.rhq.metrics.ProcessRHQMetrics;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricInput;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
@Path(LocalUri.ROOT+LocalUri.RHQ+LocalUri.METRICS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RHQMetricsResource {
	
	@POST
	@Path(LocalUri.SEND)
	@Timed
	public ExecutionStatus sendRHQMetrics(RHQMetricInput rhqMetricInput){
		return new ProcessRHQMetrics().sendMetrics(rhqMetricInput);
	}
	@POST
	@Path(LocalUri.GENERATE)
	@Timed
	public Object[] getRHQMetrics(RHQMetricInput rhqMetricInput){
		return new ProcessRHQMetrics().getMetrics(rhqMetricInput);
	}
}
