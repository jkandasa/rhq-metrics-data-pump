package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.WidgetsWidth;

import com.vaadin.data.Validator;
import com.vaadin.ui.OptionGroup;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CustomOptionGroup extends OptionGroup{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421241937795159919L;

	public CustomOptionGroup(){

	}

	public CustomOptionGroup(String caption){
		this.callMe(caption, false, null, null);
	}

	public CustomOptionGroup(String caption, boolean required, String requiredErrorMessage){
		this.callMe(caption, required, requiredErrorMessage, null);
	}

	public CustomOptionGroup(String caption, boolean required, String requiredErrorMessage, Validator validator){
		this.callMe(caption, required, requiredErrorMessage, validator);
	}

	private void callMe(String caption, boolean required, String requiredErrorMessage, Validator validator){
		setCaption(caption);
		setSizeUndefined();
		if(required){ 
			setRequired(required);
			setRequiredError(requiredErrorMessage);
		}
		if(validator != null){
			addValidator(validator);
		}
		setImmediate(true);
		setWidth(WidgetsWidth.RADIO_BOX.toString());
		setStyleName(StyleName.HORIZONTAL_RADIO_LIST.toString());
	}

}
