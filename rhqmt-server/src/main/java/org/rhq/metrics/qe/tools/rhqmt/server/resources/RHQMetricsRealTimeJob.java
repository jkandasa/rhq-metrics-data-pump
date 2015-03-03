package org.rhq.metrics.qe.tools.rhqmt.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.JobDetailService;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Path(LocalUri.ROOT+LocalUri.RHQ+LocalUri.METRICS+LocalUri.JOB+LocalUri.REAL_TIME)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RHQMetricsRealTimeJob {
	
    @GET
    @Path(LocalUri.LIST)
    @Timed
    public Object[] getList(){
        return new JobDetailService().getAllRealTimeMetrics().toArray();
    }
    
    @POST
    @Path(LocalUri.GET)
    @Timed
    public JobDetail get(JobDetail jobDetail){
        jobDetail.setJobType(JobDetail.JOB_TYPE.REAL_TIME_METRICS.toString());
        return new JobDetailService().getOne(jobDetail);
    }
    
    @POST
    @Path(LocalUri.ADD)
    @Timed
    public void add(JobDetail jobDetail){
        jobDetail.setJobType(JobDetail.JOB_TYPE.REAL_TIME_METRICS.toString());
        jobDetail.setTargetClass(JobDetail.TARGET_CLASS.REAL_TIME_METRICS.toString());
        new JobDetailService().add(jobDetail);
    }    
    
    @POST
    @Path(LocalUri.DELETE)
    @Timed
    public void delete(JobDetail jobDetail){
        new JobDetailService().delete(jobDetail);
    }
}
