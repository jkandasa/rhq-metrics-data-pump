package org.rhq.metrics.qe.tools.rhqmt.server.database.mappers;

import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobStatusMessage;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface JobStatusMessageMapper {
    public List<JobStatusMessage> getAll();
    public List<JobStatusMessage> get(JobStatusMessage jobStatusMessage);
    public void add(JobStatusMessage jobStatusMessage);
    public void delete(JobStatusMessage jobStatusMessage);
}
