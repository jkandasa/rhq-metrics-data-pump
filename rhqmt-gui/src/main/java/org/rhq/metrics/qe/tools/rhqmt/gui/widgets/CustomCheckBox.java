package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import com.vaadin.ui.CheckBox;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomCheckBox extends CheckBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6487309031273640812L;
	
	public CustomCheckBox(String caption, String description){
		setCaption(caption);
		setDescription(description);
	}
	
	public CustomCheckBox(String caption){
		setCaption(caption);
	}

}
