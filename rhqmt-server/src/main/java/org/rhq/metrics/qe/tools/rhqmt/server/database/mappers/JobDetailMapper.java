package org.rhq.metrics.qe.tools.rhqmt.server.database.mappers;

import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface JobDetailMapper {
    public List<JobDetail> getAll();
    public List<JobDetail> getAllRealTimeMetrics();
    public List<JobDetail> get(JobDetail jobDetail);
    public void add(JobDetail jobDetail);
    public void enable(JobDetail jobDetail);
    public void disable(JobDetail jobDetail);
    public void delete(JobDetail jobDetail);
}
