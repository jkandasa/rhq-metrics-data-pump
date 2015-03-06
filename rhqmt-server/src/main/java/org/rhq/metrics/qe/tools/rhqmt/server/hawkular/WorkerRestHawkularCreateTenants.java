package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import org.hawkular.metrics.api.jaxrs.TenantParams;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobStatusMessage;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;
import org.rhq.metrics.qe.tools.rhqmt.server.database.services.JobStatusMessageService;

import com.codahale.metrics.annotation.Timed;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class WorkerRestHawkularCreateTenants extends WorkerRestPostHawkularMetrics implements Runnable{

    private MetricsJobData jobData;
    private TenantParams tenantParams;
    public WorkerRestHawkularCreateTenants(String hawkularUrl, String restPostUrl,
            TenantParams tenantParams, MetricsJobData jobData) {
        super(hawkularUrl, restPostUrl);
        this.jobData = jobData;
        this.tenantParams = tenantParams;
    }

    @Timed
    @Override
    public void run() {
        try {
            this.postHawkularData(this.tenantParams);
        } catch (Exception ex) {
            new JobStatusMessageService().add(
                    new JobStatusMessage(
                            jobData.getJobId(), 
                            JobStatusMessage.TYPE.REALTIME_METRIC.toString(), 
                            JobStatusMessage.STATUS.FAILED.toString(),
                            "Tenant Creation failed, Tenant Id: "+tenantParams.getId()+
                            ", Error code: "+ex.getMessage()));
            _logger.warn("Tenant creation failed, ", ex);
        }
    }

}
