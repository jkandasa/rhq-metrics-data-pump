package org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver;

import org.rhq.metrics.qe.tools.rhqmt.server.RESTUri;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RestRealTimeMetricsJob extends RHQMTServer{

    public static JobDetail[] getList() throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        return (JobDetail[]) RHQMTServer.getClient().get(RESTUri.RHQ_METRICS_JOB_REAL_TIME_LIST, JobDetail[].class);
    }
    
    public static JobDetail get(JobDetail jobDetail) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        return (JobDetail) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_JOB_REAL_TIME_GET, jobDetail, JobDetail.class);
    }
    
    public static void add(JobDetail jobDetail) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_JOB_REAL_TIME_ADD, jobDetail);
    }
    
    public static void delete(JobDetail jobDetail) throws Exception{
        if(client == null){
            throw new Exception(ErrorMessgae.NO_CONNECTION);
        }
        RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_JOB_REAL_TIME_DELETE, jobDetail);
    }
}
