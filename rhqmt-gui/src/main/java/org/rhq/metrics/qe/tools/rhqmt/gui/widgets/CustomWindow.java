package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import com.vaadin.ui.Window;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomWindow extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8983038470103413248L;
	
	public CustomWindow(String caption){
		super(caption);
		center();
	}

}
