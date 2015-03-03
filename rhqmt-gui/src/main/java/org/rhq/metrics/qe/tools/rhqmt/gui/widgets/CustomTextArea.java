package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomTextArea extends TextArea{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747278240855374957L;
	
	public CustomTextArea(String caption){
		this.callMe(caption, 100, false, null, null);
	}
	
	public CustomTextArea(String caption, int maxLength, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, maxLength, required, requiredErrorMessage, validator);
	}
	
	private void callMe(String caption, int maxLength, boolean required, String requiredErrorMessage, Validator validator){
		setCaption(caption);
		setInputPrompt(caption);
		//setNullRepresentation("");
		setNullSettingAllowed(false);
		setMaxLength(maxLength);	
		setRows(3);
		if(required){ 
			setRequired(required);
			setRequiredError(requiredErrorMessage);
		}
		if(validator != null){
			addValidator(validator);
		}
		setImmediate(true);
		setWidth(WidgetsWidth.TEXT_AREA.toString());
		setStyleName(ValoTheme.TEXTFIELD_SMALL);

	}

}
