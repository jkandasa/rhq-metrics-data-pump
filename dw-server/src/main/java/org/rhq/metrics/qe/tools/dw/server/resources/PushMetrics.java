package org.rhq.metrics.qe.tools.dw.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.dw.server.push.rhq.metrics.PumpData;
import org.rhq.metrics.qe.tools.dw.server.representations.core.SimpleInput;
import org.rhq.metrics.qe.tools.dw.server.uri.LocalUri;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
@Path(LocalUri.ROOT+LocalUri.PUSH_METRICS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PushMetrics {
	
	@POST
	@Path(LocalUri.SIMPLE_INPUT)
	@Timed
	public void pushMetricsSimpleInput(SimpleInput simpleInput){
		new PumpData().sendData(simpleInput);
	}
}
