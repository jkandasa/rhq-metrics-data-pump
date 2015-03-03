package org.rhq.metrics.qe.tools.rhqmt.gui.tables;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestRealTimeMetricsJob;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;

import com.vaadin.data.util.BeanContainer;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RealTimeMetricsJobTable extends BaseTable{
    /**
     * 
     */
    private static final long serialVersionUID = 688910631109185299L;
    private static final LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
    public RealTimeMetricsJobTable(){
        super();
        headers.put("name", "Job Name");
        headers.put("enabled", "Enabled");
        headers.put("cronExpression", "Cron Expression");
        headers.put("repeatCount", "Repeat Count");
        headers.put("repeatInterval", "Repeat Interval");
        headers.put("fromTime", "Start Time");
        headers.put("toTime", "End Time");

        BeanContainer<Long, JobDetail> projects = new BeanContainer<Long, JobDetail>(JobDetail.class);
        projects.setBeanIdProperty("id");
        List<JobDetail> jobList = null;
        try {
            jobList = Arrays.asList(RestRealTimeMetricsJob.getList());
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Failed to load", "Unable to load real time metrics job table, Error: "+e.getMessage()); 
        }      
        projects.addAll(jobList);  

        setContainerDataSource(projects);
        for(String header : headers.keySet()){
            setColumnHeader(header, headers.get(header));
        }
        setVisibleColumns(headers.keySet().toArray());

    }
}
