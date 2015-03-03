package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomPopupDateField extends PopupDateField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7616890140541632230L;
	public CustomPopupDateField(String caption, String dateFormat, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, dateFormat, required, requiredErrorMessage, validator);
	}
	
	private void callMe(String caption, String dateFormat, boolean required, String requiredErrorMessage, Validator validator){
		setCaption(caption);
		setInputPrompt(caption);
		if(dateFormat != null){
			setDateFormat(dateFormat);
		}else{
			setDateFormat("hh:mm a, dd-MMM-yyyy");
		}
		if(required){ 
			setRequired(required);
			setRequiredError(requiredErrorMessage);
		}
		if(validator != null){
			addValidator(validator);
		}
		setImmediate(true);
		setWidth(WidgetsWidth.DATE_BOX.toString());
		setStyleName(ValoTheme.DATEFIELD_SMALL);
	}
}
