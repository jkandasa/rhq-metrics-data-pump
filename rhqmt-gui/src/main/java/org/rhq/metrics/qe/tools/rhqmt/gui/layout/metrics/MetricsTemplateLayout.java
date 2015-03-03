package org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics;


import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.tables.MetricsTemplateTable;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RestMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;
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
public class MetricsTemplateLayout extends VerticalLayout{

    /**
     * 
     */
    private static final long serialVersionUID = -4880446499217905152L;

    @SuppressWarnings("serial")
    public MetricsTemplateLayout(){
        setSizeFull();
        setMargin(false);

        BottomButtonLayout buttons = new BottomButtonLayout();


        Button add = new CustomButton("Add", "Add new template", Icon.plus);

        final Button edit = new CustomButton("Edit","Edit selected template", Icon.edit); 
        final Button delete = new CustomButton("Delete", "Delete selected template", Icon.remove_sign);

        edit.setEnabled(false);
        delete.setEnabled(false);

        //Add Buttons
        buttons.addLeftSideComponents(add, edit, delete);

        //Add title bar
        HorizontalLayout title = new HeaderLayout("Metrics Templates", Icon.building);
        addComponent(title);

        //Add Table
        final Table table = new MetricsTemplateTable();
        addComponent(table);
        addComponent(buttons);



        setComponentAlignment(title, Alignment.TOP_LEFT);
        setComponentAlignment(table, Alignment.TOP_CENTER);
        setComponentAlignment(buttons, Alignment.BOTTOM_LEFT);
        setExpandRatio(table, 1);


        table.addItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                edit.setEnabled(true);
                delete.setEnabled(true);
            }
        });

        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Object rowId = table.getValue();
                Long id = (Long)table.getContainerProperty(rowId,"id").getValue();
                UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_TEMPLATE_EDIT.toString()+"/"+id);
            }
        });

        // Handle the events with an anonymous class
        add.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_TEMPLATE_ADD.toString());
            }
        });

        delete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ConfirmDialog.show(UI.getCurrent(), "WARNING!", 
                        "You are about to delete a metric template..."
                                + "\nThere is no option to recover it back!"
                                + "\nWould you like to continue?", 
                                "Continue", "Cancel", new ConfirmDialog.Listener() {
                    @Override
                    public void onClose(ConfirmDialog dialog) {
                        if(dialog.isConfirmed()){
                            Object rowId = table.getValue();
                            Long projectId = (Long)table.getContainerProperty(rowId,"id").getValue();
                            deleteTemplate(projectId);
                            UI.getCurrent().getNavigator().navigateTo(ViewNavigator.VIEW_METRICS_TEMPLATE.toString());
                        }                       
                    }
                });
            }
        });
    }

    private void deleteTemplate(Long templateId){
        MetricsTemplate metricsTemplate = new MetricsTemplate();
        try {
            metricsTemplate.setId(templateId);
            RestMetricsTemplate.deleteMetricsTemplate(metricsTemplate);
        } catch (Exception e) {
            GUINotification.alwaysShowErrorMessgae("Unable to delete", "Unable to delete selected template, Error: "+e.getMessage());
        }
    }
}
