package org.rhq.metrics.qe.tools.rhqmt.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class DashboardView  extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 227763696906880981L;
	public DashboardView(){
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();		
	}

}