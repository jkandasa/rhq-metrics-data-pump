package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomNotification extends Notification{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8000187461037753783L;

	
	public CustomNotification(String caption) {
		super(caption);
		initMe();
	}
	
	public CustomNotification(String caption, String description) {
		super(caption, description);
		initMe();
	}

	private void initMe(){
		setPosition(Position.BOTTOM_CENTER);
		setHtmlContentAllowed(true);
		setDelayMsec(1000*3);
		setStyleName(ValoTheme.NOTIFICATION_BAR+" "+ValoTheme.NOTIFICATION_TRAY+" "+ValoTheme.NOTIFICATION_CLOSABLE);
	}
}
