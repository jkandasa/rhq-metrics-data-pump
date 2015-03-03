package org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics;


import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomTextField;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;

import com.porotype.iconfont.FontAwesome.Icon;
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
public class AddEditMetricsTemplate extends VerticalLayout{
    /**
     * 
     */
    private static final long serialVersionUID = -7365945875850365668L;
    private Logger _logger = Logger.getLogger(AddEditMetricsTemplate.class);

    private CustomTextField name = new CustomTextField("Template Name", true, "Template name is a mandatory field");
    private CustomTextField metricName = new CustomTextField("Name or ID",true, "'Name or ID' is a mandatory field");
    private CustomTextField metricValueLowest = new CustomTextField("Value Lowest", true, "'Value Lowest' is a mandatory field");
    private CustomTextField metricValueHighest = new CustomTextField("Value Highest", true, "'Value Highest' is a mandatory field");
    private CustomTextField metricCount = new CustomTextField("Count",true, "'Count' is a mandatory field");
    private CustomTextField metricInterval = new CustomTextField("Interval(Sec)",true, "'Interval' is a mandatory field");
    private CustomTextField metricLimit = new CustomTextField("Limit",true, "'Limit' is a mandatory field"); 

    @SuppressWarnings("serial")
    public AddEditMetricsTemplate(final Long templateId, final ViewNavigator returnLocation) {
        setSizeFull();
        //Add title bar
        HorizontalLayout title = new HeaderLayout("Metrics Template", Icon.building);
        addComponent(title);

        //Set TAB index
        name.setTabIndex(1);
        metricName.setTabIndex(2);
        metricValueLowest.setTabIndex(3);
        metricValueHighest.setTabIndex(4);
        metricCount.setTabIndex(5);
        metricInterval.setTabIndex(6);
        metricLimit.setTabIndex(7);


        FormLayout form = new FormLayout();
        BottomButtonLayout buttonLayout = new BottomButtonLayout();
        buttonLayout.setSizeUndefined();
        form.setMargin(true);
        form.addComponents(name, metricName, metricValueLowest, metricValueHighest, metricCount, metricInterval, metricLimit);

        addComponents(form,buttonLayout);

        setComponentAlignment(title, Alignment.TOP_LEFT);
        setComponentAlignment(form, Alignment.TOP_LEFT);
        setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);
        setExpandRatio(form, 1);

        //Define Size:      
        form.setSizeUndefined();
        Button cancel = new CustomButton("Cancel", "Go back to user list", Icon.backward);      

        cancel.addClickListener(new ClickListener() {           
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(returnLocation.toString());
            }
        });
        Button add = new CustomButton("Add", "Click to add new user", Icon.plus);
        add.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if(isvalid()){
                    add();
                    UI.getCurrent().getNavigator().navigateTo(returnLocation.toString());   
                }
            }
        });

        Button update = new CustomButton("Update", "Update Project Settings", Icon.check);
        update.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if(isvalid()){
                    update(templateId);
                    UI.getCurrent().getNavigator().navigateTo(returnLocation.toString());
                }               
            }
        });
        
        MetricsTemplate metricsTemplate = new MetricsTemplate();

        if(templateId != null){
            metricsTemplate.setId(Long.valueOf(templateId));
            try {
                metricsTemplate = RestMetricsTemplate.getMetricsTemplate(metricsTemplate);
            } catch (Exception e) {
                GUINotification.alwaysShowErrorMessgae("Error", "Unable to procress, Error: "+e.getMessage());
            }
            name.setValue(metricsTemplate.getName());
            metricName.setValue(metricsTemplate.getMetricNameId());
            metricCount.setValue(String.valueOf(metricsTemplate.getMetricCount()));
            metricInterval.setValue(String.valueOf(metricsTemplate.getMetricInterval()));
            metricLimit.setValue(String.valueOf(metricsTemplate.getMetricLimit()));
            metricValueHighest.setValue(String.valueOf(metricsTemplate.getMetricValueHighest()));
            metricValueLowest.setValue(String.valueOf(metricsTemplate.getMetricValueHighest()));

            buttonLayout.addComponent(update);
            buttonLayout.setComponentAlignment(update, Alignment.MIDDLE_LEFT);
        }else{
            

            buttonLayout.addComponent(add);
            buttonLayout.setComponentAlignment(add, Alignment.MIDDLE_LEFT);
        }       
        buttonLayout.addComponent(cancel);
        buttonLayout.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);
    }

    private boolean isvalid(){
        try{
            name.validate();
            metricName.validate();
            metricValueLowest.validate();
            metricValueHighest.validate();
            metricCount.validate();
            metricInterval.validate();
            metricLimit.validate();
            return true;
        }catch(Exception ex){
            Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
            return false;
        }  
    }

    private void mapSettings(MetricsTemplate metricsTemplate){
        metricsTemplate.setName(name.getValue());
        metricsTemplate.setMetricNameId(metricName.getValue());
        metricsTemplate.setMetricValueLowest(Double.valueOf(metricValueLowest.getValue()));
        metricsTemplate.setMetricValueHighest(Double.valueOf(metricValueHighest.getValue()));
        metricsTemplate.setMetricCount(Long.valueOf(metricCount.getValue()));
        metricsTemplate.setMetricInterval(Long.valueOf(metricInterval.getValue()));
        metricsTemplate.setMetricLimit(Long.valueOf(metricLimit.getValue()));       
    }

    private void add(){
        MetricsTemplate metricsTemplate = new MetricsTemplate();
        mapSettings(metricsTemplate);
        try {
            RestMetricsTemplate.addMetricsTemplate(metricsTemplate);
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Error", "Unable to procress, Error: "+e.getMessage());
        }       
    }

    private void update(Long id){
        MetricsTemplate metricsTemplate = new MetricsTemplate();
        mapSettings(metricsTemplate);
        metricsTemplate.setId(id);
        try {
            RestMetricsTemplate.editMetricsTemplate(metricsTemplate);
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Error", "Unable to procress, Error: "+e.getMessage());
        }         }
}
