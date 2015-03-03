package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomButton extends Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2640587791296227491L;
	
	public CustomButton(String caption, String description, Icon icon){
		this.callMe(caption, description, icon);
	}
	
	public CustomButton(String caption, String description){
		this.callMe(caption, description, null);
	}
	
	public CustomButton(String caption){
		this.callMe(caption, null, null);
	}
	
	private void callMe(String caption, String description, Icon icon){

		if(icon != null){
			setCaption(icon+" "+caption);
		}else{
			setCaption(caption);
		}
		
		setDescription(description);
		setHtmlContentAllowed(true);
		setWidth("120px");
		setStyleName(ValoTheme.BUTTON_SMALL);
	
	}

}
