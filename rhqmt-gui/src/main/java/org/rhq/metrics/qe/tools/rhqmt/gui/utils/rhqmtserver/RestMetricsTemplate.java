package org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver;

import org.rhq.metrics.qe.tools.rhqmt.server.RESTUri;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RestMetricsTemplate extends RHQMTServer{

    public static MetricsTemplate[] getMetricsTemplateList() throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        return (MetricsTemplate[]) RHQMTServer.getClient().get(RESTUri.RHQ_METRICS_TEMPLATE_LIST, MetricsTemplate[].class);
    }
    
    public static MetricsTemplate getMetricsTemplate(MetricsTemplate metricsTemplate) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        return (MetricsTemplate) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_TEMPLATE_GET, metricsTemplate, MetricsTemplate.class);
    }
    
    public static void addMetricsTemplate(MetricsTemplate metricsTemplate) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_TEMPLATE_ADD, metricsTemplate);
    }
    
    public static void editMetricsTemplate(MetricsTemplate metricsTemplate) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_TEMPLATE_EDIT, metricsTemplate);
    }
    
    public static void deleteMetricsTemplate(MetricsTemplate metricsTemplate) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_TEMPLATE_DELETE, metricsTemplate);
    }
}
