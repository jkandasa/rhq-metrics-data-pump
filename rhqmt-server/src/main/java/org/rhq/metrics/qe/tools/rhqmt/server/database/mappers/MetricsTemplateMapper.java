package org.rhq.metrics.qe.tools.rhqmt.server.database.mappers;

import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface MetricsTemplateMapper {
    public List<MetricsTemplate> getAll();
    public List<MetricsTemplate> get(MetricsTemplate metricsTemplate);
    public void add(MetricsTemplate metricsTemplate);
    public void delete(MetricsTemplate metricsTemplate);
    public void update(MetricsTemplate metricsTemplate);
}
