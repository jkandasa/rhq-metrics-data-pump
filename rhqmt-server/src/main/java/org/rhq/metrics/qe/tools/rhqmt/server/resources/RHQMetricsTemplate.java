package org.rhq.metrics.qe.tools.rhqmt.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.MetricsTemplateService;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Path(LocalUri.ROOT+LocalUri.RHQ+LocalUri.METRICS+LocalUri.TEMPLATE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RHQMetricsTemplate {
	
    @GET
    @Path(LocalUri.LIST)
    @Timed
    public Object[] getMetricsTemplateList(){
        return new MetricsTemplateService().getAll().toArray();
    }
    
    @POST
    @Path(LocalUri.GET)
    @Timed
    public MetricsTemplate getMetricsTemplate(MetricsTemplate metricsTemplate){
        return new MetricsTemplateService().getOne(metricsTemplate);
    }
    
    @POST
    @Path(LocalUri.ADD)
    @Timed
    public void addMetricsTemplate(MetricsTemplate metricsTemplate){
        new MetricsTemplateService().add(metricsTemplate);
    }
    
    
    @POST
    @Path(LocalUri.EDIT)
    @Timed
    public void editMetricsTemplate(MetricsTemplate metricsTemplate){
        new MetricsTemplateService().update(metricsTemplate);
    }
    
    
    @POST
    @Path(LocalUri.DELETE)
    @Timed
    public void deleteMetricsTemplate(MetricsTemplate metricsTemplate){
        new MetricsTemplateService().delete(metricsTemplate);
    }
}
