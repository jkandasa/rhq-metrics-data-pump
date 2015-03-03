package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomComboBox extends ComboBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7401842812412260280L;

	public CustomComboBox(){
		
	}
	
	public CustomComboBox(String caption){
		this.callMe(caption, false, null, null);
	}
	
	public CustomComboBox(String caption, boolean required, String requiredErrorMessage){
		this.callMe(caption, required, requiredErrorMessage, null);
	}
	
	public CustomComboBox(String caption, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, required, requiredErrorMessage, validator);
	}
	
	private void callMe(String caption, boolean required, String requiredErrorMessage, Validator validator){
		setCaption(caption);
		setInputPrompt(caption);
		setSizeUndefined();
		if(required){ 
			setRequired(required);
			setRequiredError(requiredErrorMessage);
		}
		if(validator != null){
			addValidator(validator);
		}
		setImmediate(true);
		setWidth(WidgetsWidth.COMBO_BOX.toString());
	}
}
