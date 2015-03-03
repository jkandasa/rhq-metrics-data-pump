package org.rhq.metrics.qe.tools.rhqmt.gui.tables;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;

import com.vaadin.data.util.BeanContainer;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsTemplateTable extends BaseTable{
    /**
     * 
     */
    private static final long serialVersionUID = -411107476856462030L;
    private static final LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
    public MetricsTemplateTable(){
        super();
        headers.put("name", "Template Name");
        headers.put("metricNameId", "Metric Name/Id");
        headers.put("metricInterval", "Metric Interval");
        headers.put("metricCount", "Metric Count");
        headers.put("metricValueLowest", "Metric Value Lowest");
        headers.put("metricValueHighest", "Metric Value Highest");
        headers.put("metricLimit", "Metric Limit");

        BeanContainer<Long, MetricsTemplate> projects = new BeanContainer<Long, MetricsTemplate>(MetricsTemplate.class);
        projects.setBeanIdProperty("id");
        List<MetricsTemplate> templateList = null;
        try {
            templateList = Arrays.asList(RestMetricsTemplate.getMetricsTemplateList());
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Failed to load", "Unable to load the metrics table, Error: "+e.getMessage()); 
        }      
        projects.addAll(templateList);  

        setContainerDataSource(projects);
        for(String header : headers.keySet()){
            setColumnHeader(header, headers.get(header));
        }
        setVisibleColumns(headers.keySet().toArray());

    }
}
