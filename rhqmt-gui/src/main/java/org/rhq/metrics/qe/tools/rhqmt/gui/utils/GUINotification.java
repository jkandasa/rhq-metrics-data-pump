package org.rhq.metrics.qe.tools.rhqmt.gui.utils;

import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomNotification;

import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class GUINotification {
	
	public static void showErrorMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_ERROR, 3);
	}
	
	public static void showInfoMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_SUCCESS, 3);
	}
	
	public static void showWarnMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_WARNING, 3);
	}
	
	public static void alwaysShowErrorMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_ERROR, -1);
	}
	
	public static void alwaysShowInfoMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_SUCCESS, -1);
	}
	
	public static void alwaysShowWarnMessgae(String message, String description){
		showMessgae(message, description, ValoTheme.NOTIFICATION_WARNING, -1);
	}
	
	
	
	public static void showMessgae(String message, String description, String type, int delay){
		CustomNotification customNotification = new CustomNotification(message, description);
		customNotification.setStyleName(customNotification.getStyleName()+" "+type);
		customNotification.setDelayMsec(delay*1000);
		customNotification.show(UI.getCurrent().getPage());
	}
}
