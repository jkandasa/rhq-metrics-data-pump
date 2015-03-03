package org.rhq.metrics.qe.tools.rhqmt.gui.layout;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class BottomButtonLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2408276971143062325L;
	
	private HorizontalLayout leftSideComponents = new HorizontalLayout();
	private HorizontalLayout rightSideComponents = new HorizontalLayout();

	public BottomButtonLayout(){
		setSpacing(true);
		setMargin(false);
		setSizeUndefined();
		setStyleName(StyleName.LAYOUT_BOTTOM_BUTTON.toString());
		
		rightSideComponents.setMargin(false);
		rightSideComponents.setSpacing(true);
		
		leftSideComponents.setMargin(false);
		leftSideComponents.setSpacing(true);
		
		addComponents(leftSideComponents, rightSideComponents);
		setComponentAlignment(leftSideComponents, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightSideComponents, Alignment.MIDDLE_RIGHT);
	}
	
	public void addRightSideComponents(Component...components){
		rightSideComponents.addComponents(components);
		for(Component component : components){
			rightSideComponents.setComponentAlignment(component, Alignment.MIDDLE_RIGHT);
		}
	}
	
	public void addLeftSideComponents(Component...components){
		leftSideComponents.addComponents(components);
		for(Component component : components){
			leftSideComponents.setComponentAlignment(component, Alignment.MIDDLE_LEFT);
		}
	}
	

	
}
