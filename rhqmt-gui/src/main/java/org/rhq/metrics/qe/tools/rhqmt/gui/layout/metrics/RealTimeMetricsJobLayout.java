package org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics;


import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.tables.RealTimeMetricsJobTable;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestRealTimeMetricsJob;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
import org.vaadin.dialogs.ConfirmDialog;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RealTimeMetricsJobLayout extends VerticalLayout{
    /**
     * 
     */
    private static final long serialVersionUID = 9074438475885038061L;

    @SuppressWarnings("serial")
    public RealTimeMetricsJobLayout(){
        setSizeFull();
        setMargin(false);

        BottomButtonLayout buttons = new BottomButtonLayout();


        Button add = new CustomButton("Add", "Add new realtime job", Icon.plus);

        final Button edit = new CustomButton("Edit","Edit selected job", Icon.edit); 
        final Button delete = new CustomButton("Delete", "Delete selected job", Icon.remove_sign);

        final Button enable = new CustomButton("Enable", "Enable", Icon.check);
        final Button disable = new CustomButton("Disable", "disable", Icon.remove);

        edit.setEnabled(false);
        delete.setEnabled(false);
        enable.setEnabled(false);
        disable.setEnabled(false);
        
        //Add Buttons
        buttons.addLeftSideComponents(add, edit, delete);
        buttons.addRightSideComponents(enable, disable);

        //Add title bar
        HorizontalLayout title = new HeaderLayout("Real Time Metrics", Icon.building);
        addComponent(title);

        //Add Table
        final Table table = new RealTimeMetricsJobTable();
        addComponent(table);
        addComponent(buttons);



        setComponentAlignment(title, Alignment.TOP_LEFT);
        setComponentAlignment(table, Alignment.TOP_CENTER);
        setComponentAlignment(buttons, Alignment.BOTTOM_LEFT);
        setExpandRatio(table, 1);


        table.addItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                //edit.setEnabled(true);
                delete.setEnabled(true);
                //enable.setEnabled(true);
                //disable.setEnabled(true);
            }
        });

        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Object rowId = table.getValue();
                Long id = (Long)table.getContainerProperty(rowId,"id").getValue();
                UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_REALTIME_EDIT.toString()+"/"+id);
            }
        });

        // Handle the events with an anonymous class
        add.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_REALTIME_ADD.toString());
            }
        });

        delete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ConfirmDialog.show(UI.getCurrent(), "WARNING!", 
                        "You are about to delete a metric job..."
                                + "\nThere is no option to recover it back!"
                                + "\nWould you like to continue?", 
                                "Continue", "Cancel", new ConfirmDialog.Listener() {
                    @Override
                    public void onClose(ConfirmDialog dialog) {
                        if(dialog.isConfirmed()){
                            Object rowId = table.getValue();
                            Long id = (Long)table.getContainerProperty(rowId,"id").getValue();
                            deleteTemplate(id);
                            UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_REALTIME.toString());
                        }                       
                    }
                });
            }
        });
    }

    private void deleteTemplate(Long id){
        JobDetail jobDetail = new JobDetail();
        try {
            jobDetail.setId(id);
            RestRealTimeMetricsJob.delete(jobDetail);
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Unable to delete", "Unable to delete selected job, Error: "+e.getMessage());
        }
    }
}
