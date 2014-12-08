package org.rhq.metrics.qe.tools.rhqmt.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.rhqmt.server.push.rhq.metrics.PumpData;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricSimpleInput;
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
	@Path(LocalUri.SEND+LocalUri.SIMPLE)
	@Timed
	public ExecutionStatus sendMetricsSimpleInput(RHQMetricSimpleInput simpleInput){
		return new PumpData().sendMetrics(simpleInput);
	}
	@POST
	@Path(LocalUri.RECEIVE+LocalUri.SIMPLE)
	@Timed
	public Object[] getMetricsSimpleInput(RHQMetricSimpleInput simpleInput){
		return new PumpData().getMetrics(simpleInput);
	}
}
