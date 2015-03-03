package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.TextField;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomEmailField extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8419140932671747525L;

	public CustomEmailField(String caption, String description, int maxLength, boolean required, String requiredErrorMessage){
		this.callMe(caption, description, maxLength, required, requiredErrorMessage, null);
	}
	
	public CustomEmailField(String caption, String description, int maxLength, boolean required, String requiredErrorMessage, String invalidEmailMessage){
		this.callMe(caption, description, maxLength, required, requiredErrorMessage, invalidEmailMessage);
	}
	
	public CustomEmailField(String caption){
		this.callMe(caption, null, 100, false, null, null);
	}
	
	private void callMe(String caption, String description, int maxLength, boolean required, String requiredMessage, String invalidEmailString){
		setCaption(caption);
		setImmediate(true);
		setInputPrompt(caption);
		setDescription(description);
		setMaxLength(maxLength);
		if(required){
			setRequired(true);
			setRequiredError(requiredMessage);
		}
		if(invalidEmailString == null){
			addValidator(new EmailValidator("Invalid email"));	
		}else{
			addValidator(new EmailValidator(invalidEmailString));
		}
		
		setWidth(WidgetsWidth.EMAIL_BOX.toString());
	}
}
