package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.PasswordField;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomPasswordField extends PasswordField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2107544215250491502L;
	
	public CustomPasswordField(String caption, int maxLength, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, maxLength, required, requiredErrorMessage, validator);
	}
	
	public CustomPasswordField(String caption){
		this.callMe(caption, 100, false, null, null);
	}

	private void callMe(String caption, int maxLength, boolean required, String requiredErrorMessage, Validator validator){
		setCaption(caption);
		setInputPrompt(caption);
		setNullRepresentation("");
		setNullSettingAllowed(false);
		setMaxLength(maxLength);		
		if(required){ 
			setRequired(required);
			setRequiredError(requiredErrorMessage);
		}
		if(validator != null){
			addValidator(validator);
		}
		setImmediate(true);
		setWidth(WidgetsWidth.PASSWORD_BOX.toString());
	}
}
