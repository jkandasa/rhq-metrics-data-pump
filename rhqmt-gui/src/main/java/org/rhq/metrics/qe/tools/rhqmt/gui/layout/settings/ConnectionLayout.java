package org.rhq.metrics.qe.tools.rhqmt.gui.layout.settings;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.Footer;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.settings.RhqmtServerSettings;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RHQMTServer;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomTextArea;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomTextField;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMTServerDetail;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ConnectionLayout extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593582813608546334L;
	
	private CustomTextField connectionUrl = new CustomTextField("RHQMT Server URL");
	private CustomTextArea version = new CustomTextArea("RHQMT Server Version");
	private CustomTextField status = new CustomTextField("Connection Status");
	private Logger _logger = Logger.getLogger(ConnectionLayout.class.getName()); 

	@SuppressWarnings("serial")
    public ConnectionLayout(){
		setSizeFull();
		setMargin(false);

		//Add title bar
		HorizontalLayout title = new HeaderLayout("Connection Settings", Icon.cog);


		//Text boxes (body)
		HorizontalLayout formLayout = new HorizontalLayout();
		FormLayout leftLayout 	= new FormLayout();

		leftLayout.setMargin(true);

		formLayout.addComponents(leftLayout);
		formLayout.setComponentAlignment(leftLayout, Alignment.TOP_LEFT);

		connectionUrl.setValue(RhqmtServerSettings.getUrl());
		
		leftLayout.addComponents(connectionUrl, status, version);
		
		version.setValue(RhqmtServerSettings.getVersion());
		version.setReadOnly(true);
		
		if(RHQMTServer.isConnected()){
			status.setValue("Connected");
		}else{
			status.setValue("Not Connected");
		}
		status.setReadOnly(true);
		

		//Buttons
		BottomButtonLayout buttons = new BottomButtonLayout();
		Button updateButton = new CustomButton("Update", "Update Connection Settings", Icon.edit);
		//Add Buttons
		buttons.addLeftSideComponents(updateButton);

		// Adding all the components to page
		addComponents(title, formLayout, buttons);		
		setComponentAlignment(title, Alignment.TOP_LEFT);
		setComponentAlignment(formLayout, Alignment.TOP_LEFT);
		setComponentAlignment(buttons, Alignment.BOTTOM_LEFT);		
		setExpandRatio(formLayout, 1);

		updateButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				RhqmtServerSettings.setUrl(connectionUrl.getValue());
				RHQMTServer.disconnect();
				try {
					RHQMTServerDetail rhqmtServerDetail = RHQMTServer.connect(RhqmtServerSettings.getUrl());
	                if (RHQMTServer.isConnected()) {
	                	RhqmtServerSettings.setVersion(rhqmtServerDetail.getVersion());
	                	 GUINotification.showInfoMessgae("Success", "Connection Settings are updated successfully...");
                    }else{
	                	RhqmtServerSettings.setVersion("Error Getting connection");
    	                GUINotification.showErrorMessgae("Failed","There was an error on connection update...");
                    }
                } catch (Exception ex) {
	                _logger.error("Exception on Connection, ", ex);
	                RhqmtServerSettings.setVersion("Error: "+ex.getMessage());
	                GUINotification.showErrorMessgae("Failed", "There was an error on connection update...<br>Error Message: "+ex.getMessage());
                }
				version.setReadOnly(false);
            	version.setValue(RhqmtServerSettings.getVersion());
            	version.setReadOnly(true);
            	
            	status.setReadOnly(false);
            	if(RHQMTServer.isConnected()){
        			status.setValue("Connected");
        		}else{
        			status.setValue("Not Connected");
        		}
        		status.setReadOnly(true);
        		//Footer.updateFooter();
			}
		});
	}

}
