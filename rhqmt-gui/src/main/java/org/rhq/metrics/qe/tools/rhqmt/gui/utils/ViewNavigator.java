package org.rhq.metrics.qe.tools.rhqmt.gui.utils;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public enum ViewNavigator {

    //Real navigator
    VIEW_MAIN(""),

    //Top Menu Items
    VIEW_DASHBOARD(VIEW_MAIN+"/dashboard"),
    VIEW_METRICS(VIEW_MAIN+"/metrics"),
    VIEW_SETTINGS(VIEW_MAIN+"/settings"),

    //Metrics, Modules
    SM_METRICS_PAST("past"),
    SM_METRICS_REALTIME("realtime"),
    SM_METRICS_TEMPLATE("template"),
    //Metrics Template
    SM_METRICS_TEMPLATE_ADD("add"),
    SM_METRICS_TEMPLATE_EDIT("edit"),
    SM_METRICS_TEMPLATE_DELETE("delete"),

    //Metrics real time
    SM_METRICS_REALTIME_ADD("add"),
    SM_METRICS_REALTIME_EDIT("edit"),
    SM_METRICS_REALTIME_DELETE("delete"),

    //Metrics, View
    VIEW_METRICS_PAST(VIEW_METRICS+"/"+SM_METRICS_PAST),

    VIEW_METRICS_REALTIME(VIEW_METRICS+"/"+SM_METRICS_REALTIME),
    VIEW_METRICS_REALTIME_ADD(VIEW_METRICS_REALTIME+"/"+SM_METRICS_TEMPLATE_ADD),
    VIEW_METRICS_REALTIME_EDIT(VIEW_METRICS_REALTIME+"/"+SM_METRICS_TEMPLATE_EDIT),
    VIEW_METRICS_REALTIME_DELETE(VIEW_METRICS_REALTIME+"/"+SM_METRICS_TEMPLATE_DELETE),	
    
    VIEW_METRICS_TEMPLATE(VIEW_METRICS+"/"+SM_METRICS_TEMPLATE),
    VIEW_METRICS_TEMPLATE_ADD(VIEW_METRICS_TEMPLATE+"/"+SM_METRICS_TEMPLATE_ADD),
    VIEW_METRICS_TEMPLATE_EDIT(VIEW_METRICS_TEMPLATE+"/"+SM_METRICS_TEMPLATE_EDIT),
    VIEW_METRICS_TEMPLATE_DELETE(VIEW_METRICS_TEMPLATE+"/"+SM_METRICS_TEMPLATE_DELETE), 

    //Settings, Modules
    SM_SETTINGS_CONNECTION("connection"),
    //Settings, View
    VIEW_SETTINGS_CONNECTION(VIEW_SETTINGS+"/"+SM_SETTINGS_CONNECTION),


    DUNMY("");

    private final String view;

    private ViewNavigator(String view){
        this.view = view;
    }
    public String toString(){
        return this.view;
    }


}
