package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomTextField extends TextField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2561735070719307667L;
	
	public CustomTextField(String caption){
		this.callMe(caption, 100, false, null, null);
	}
	
	public CustomTextField(String caption, boolean required, String requiredErrorMessage){
		this.callMe(caption, 100, required, requiredErrorMessage, null);
	}
	
	public CustomTextField(String caption, int maxLength, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, maxLength, required, requiredErrorMessage, validator);
	}
	
	public CustomTextField(String caption, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, 100, required, requiredErrorMessage, validator);
	}
	
	public CustomTextField(String caption, Validator validator){
		this.callMe(caption, 100, false, null, validator);
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
		setWidth(WidgetsWidth.TEXT_BOX.toString());
		setStyleName(ValoTheme.TEXTFIELD_SMALL);
	}
}
