package org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics;


import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestRealTimeMetricsJob;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomCheckBox;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomPopupDateField;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomTextField;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class AddEditRealTimeMetricsJob extends VerticalLayout{
    /**
     * 
     */
    private static final long serialVersionUID = -7365945875850365668L;
    private Logger _logger = Logger.getLogger(AddEditRealTimeMetricsJob.class);

    private CustomTextField name = new CustomTextField("Job Name", true, "Job name is a mandatory field");
    private CustomTextField cronExpression = new CustomTextField("Cron Expression");
    private CustomTextField repeatCount = new CustomTextField("Repeat Count");
    private CustomTextField repeatInterval = new CustomTextField("Repeat Interval");
    private CustomPopupDateField fromDate = new CustomPopupDateField("Start Date", null, false, null, null);
    private CustomPopupDateField toDate = new CustomPopupDateField("End Date", null, false, null, null);
    private CustomCheckBox enabled = new CustomCheckBox("Enable");

    private CustomTextField rhqServerUrl = new CustomTextField("Hawkular-Metrics Server[host:port]", true, "'Hawkular-Metrics Server[host:port]' is a mandatory field");
    private CustomTextField metricName = new CustomTextField("Metric Name(s)",true, "'Name or ID' is a mandatory field");
    private CustomTextField tenantId = new CustomTextField("Tenant Name(s)",true, "'Tenant ID' is a mandatory field");
    private CustomTextField metricValueLowest = new CustomTextField("Value Lowest", true, "'Value Lowest' is a mandatory field");
    private CustomTextField metricValueHighest = new CustomTextField("Value Highest", true, "'Value Highest' is a mandatory field");
    private CustomTextField metricDataCount = new CustomTextField("Metric Data Count",true, "'Metric Data Count' is a mandatory field");
    private CustomTextField metricLimit = new CustomTextField("Metric Limit",true, "'Metric Limit' is a mandatory field");
    private CustomTextField metricDataLimit = new CustomTextField("Metric Data Limit",true, "'Metric Data Limit' is a mandatory field");    
    private CustomTextField metricTimeLimit = new CustomTextField("Metric Time Limit(sec)",true, "'Metric Time Limit' is a mandatory field"); 
    private CustomCheckBox validateResult = new CustomCheckBox("Verify","Verify it on hawkular-metrics server");

    @SuppressWarnings("serial")
    public AddEditRealTimeMetricsJob(final Long jobId, final ViewNavigator returnLocation) {
        setSizeFull();
        //Add title bar
        HorizontalLayout title = new HeaderLayout("Metrics Template", Icon.building);
        addComponent(title);

        //Text boxes (body)
        HorizontalLayout formLayout = new HorizontalLayout();
        FormLayout leftLayout   = new FormLayout();
        FormLayout rightLayout  = new FormLayout();

        leftLayout.setMargin(true);
        rightLayout.setMargin(true);

        formLayout.addComponents(leftLayout, rightLayout);
        formLayout.setComponentAlignment(leftLayout, Alignment.TOP_LEFT);
        formLayout.setComponentAlignment(rightLayout, Alignment.TOP_RIGHT);


        toDate.setResolution(Resolution.MINUTE);
        fromDate.setResolution(Resolution.MINUTE);

        //Set TAB index
        name.setTabIndex(1);
        cronExpression.setTabIndex(2);
        repeatCount.setTabIndex(3);
        repeatInterval.setTabIndex(4);
        fromDate.setTabIndex(5);
        toDate.setTabIndex(6);
        enabled.setTabIndex(7);
        validateResult.setTabIndex(8);

        rhqServerUrl.setTabIndex(9);
        tenantId.setTabIndex(10);
        metricName.setTabIndex(10);
        metricValueLowest.setTabIndex(11);
        metricValueHighest.setTabIndex(12);
        metricDataCount.setTabIndex(13);
        metricTimeLimit.setTabIndex(14);
        metricLimit.setTabIndex(15);
        metricDataLimit.setTabIndex(16);
        

        name.setValue("Test Job");
        cronExpression.setValue(null);
        repeatCount.setValue("0");
        repeatInterval.setValue("60");
        enabled.setValue(true);
        rhqServerUrl.setValue("localhost:8080");
        tenantId.setValue("test-tenant");
        metricName.setValue("n:test-numeric,a:test-availability");
        metricValueHighest.setValue("100");
        metricValueLowest.setValue("0");
        validateResult.setValue(false);
        
        metricDataCount.setValue("1");
        metricTimeLimit.setValue("60");
        metricLimit.setValue("-1");
        metricDataLimit.setValue("1000");

        leftLayout.addComponents(name, cronExpression, repeatCount, repeatInterval, fromDate, toDate, enabled, validateResult);
        rightLayout.addComponents(rhqServerUrl, tenantId, metricName, metricValueLowest, metricValueHighest, metricDataCount, metricTimeLimit, metricLimit, metricDataLimit);


        //Buttons
        BottomButtonLayout buttons = new BottomButtonLayout();

        // Adding all the components to page
        addComponents(title, formLayout, buttons);      
        setComponentAlignment(title, Alignment.TOP_LEFT);
        setComponentAlignment(formLayout, Alignment.TOP_LEFT);
        setComponentAlignment(buttons, Alignment.BOTTOM_LEFT);      
        setExpandRatio(formLayout, 1);

        Button cancel = new CustomButton("Cancel", "Go back", Icon.backward);      

        cancel.addClickListener(new ClickListener() {           
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(returnLocation.toString());
            }
        });
        Button add = new CustomButton("Add", "Click to add new job", Icon.plus);
        add.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if(isvalid()){
                    add();
                    UI.getCurrent().getNavigator().navigateTo(returnLocation.toString());   
                }
            }
        });

        buttons.addLeftSideComponents(add, cancel);
    }

    private boolean isvalid(){
        try{
            name.validate();
            tenantId.validate();
            metricName.validate();
            metricValueLowest.validate();
            metricValueHighest.validate();
            metricTimeLimit.validate();
            metricDataCount.validate();
            metricLimit.validate();

            return true;
        }catch(Exception ex){
            Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
            return false;
        }  
    }

    private void mapSettings(JobDetail jobDetail){
        MetricsJobData jobData = new MetricsJobData(); 

        jobDetail.setName(name.getValue());
        jobDetail.setCronExpression(cronExpression.getValue());
        jobDetail.setEnabled(enabled.getValue());
        jobDetail.setRepeatCount(Integer.valueOf(repeatCount.getValue()));
        jobDetail.setRepeatInterval(Long.valueOf(repeatInterval.getValue()));
        jobDetail.setFromTime(fromDate.getValue());
        jobDetail.setToTime(toDate.getValue());

        jobData.setTargetServer(rhqServerUrl.getValue());
        jobData.setTenantId(tenantId.getValue());
        jobData.setMetricNameId(metricName.getValue());
        jobData.setMetricValueLowest(Double.valueOf(metricValueLowest.getValue()));
        jobData.setMetricValueHighest(Double.valueOf(metricValueHighest.getValue()));
        jobData.setMetricDataCount(Long.valueOf(metricDataCount.getValue()));
        jobData.setMetricDataLimit(Long.valueOf(metricDataLimit.getValue()));
        jobData.setMetricTimeLimit(Long.valueOf(metricTimeLimit.getValue()));
        jobData.setValidateResult(validateResult.getValue());
        jobData.setMetricLimit(Long.valueOf(metricLimit.getValue()));

        jobDetail.setMetricsJobData(jobData);

    }

    private void add(){
        JobDetail jobDetail = new JobDetail();
        mapSettings(jobDetail);
        try {
            RestRealTimeMetricsJob.add(jobDetail);
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Error", "Unable to procress, Error: "+e.getMessage());
        }       
    }
}
