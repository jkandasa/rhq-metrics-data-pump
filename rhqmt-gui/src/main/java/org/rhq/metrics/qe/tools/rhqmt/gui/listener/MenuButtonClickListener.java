package org.rhq.metrics.qe.tools.rhqmt.gui.listener;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
//Menu navigation button listener
public class MenuButtonClickListener implements Button.ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3124675826835230241L;

	 String menuitem;
     public MenuButtonClickListener(String menuitem) {
         this.menuitem = menuitem;
     }

     @Override
     public void buttonClick(ClickEvent event) {
         // Navigate to a specific state
         UI.getCurrent().getNavigator().navigateTo(menuitem);
     }

}
