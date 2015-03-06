package org.rhq.metrics.qe.tools.rhqmt.server.database.mappers;

import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface MetricsJobDataMapper {
    public List<MetricsJobData> getAll();
    public List<MetricsJobData> get(MetricsJobData metricsJobData);
    public void add(MetricsJobData metricsJobData);
    public void enable(MetricsJobData metricsJobData);
    public void disable(MetricsJobData metricsJobData);
    public void delete(MetricsJobData metricsJobData);
    public void update(MetricsJobData metricsJobData);
}
