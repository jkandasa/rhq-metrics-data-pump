package org.rhq.metrics.qe.tools.rhqmt.server.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMTServerDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
@Path(LocalUri.ROOT+LocalUri.SERVER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServerData {
	
	@GET
	@Path(LocalUri.SERVER_DETAIL)
	@Timed
	public RHQMTServerDetail getServerDetail(){
		RHQMTServerDetail serverDetail = new RHQMTServerDetail();
		serverDetail.setVersion("0.0.1 (Version hardcoded! Fix this)");
		serverDetail.setServerTime(new Date().getTime());
		return serverDetail;
	}
}
