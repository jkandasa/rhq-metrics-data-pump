package org.rhq.metrics.qe.tools.rhqmt.gui.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.SideMenuButton;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class BaseView extends HorizontalSplitPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5218787861046108195L;
	protected Logger _logger = Logger.getLogger(BaseView.class);

	protected Accordion menuAccordion = new Accordion();

	protected VerticalLayout content = new VerticalLayout();
	private ArrayList<Button> sideMenu = new ArrayList<Button>();
	

	@SuppressWarnings("serial")
	protected Accordion getMenuBar(ArrayList<MenuItem> sideMenus){
		menuAccordion.removeAllComponents();
		menuAccordion.setStyleName(StyleName.ACCORDION_SIDE_MENU.toString());
		ArrayList<String> groups = new ArrayList<String>();
		for(MenuItem sideMenuItem : sideMenus){
			if(! groups.contains(sideMenuItem.getGroup())){
				groups.add(sideMenuItem.getGroup());
			}
		}
		_logger.info("Groups: "+groups);
		LinkedHashMap<String, VerticalLayout> groupsMap = new LinkedHashMap<String, VerticalLayout>();
		for(String group: groups){
			groupsMap.put(group, new VerticalLayout());
		}
		for(final MenuItem sideMenuItem : sideMenus){
			final Button mButton = new SideMenuButton(sideMenuItem);
			mButton.setStyleName(StyleName.BUTTON_SIDE_MENU.toString());
			mButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection();
					event.getButton().addStyleName(StyleName.BUTTON_SIDE_MENU_SELECTED.toString());
					UI.getCurrent().getNavigator().navigateTo(sideMenuItem.getNavigator().toString());
					//TODO: call another layout
				}
			});
			sideMenu.add(mButton);
			groupsMap.get(sideMenuItem.getGroup()).addComponent(mButton);
			groupsMap.get(sideMenuItem.getGroup()).setComponentAlignment(mButton, Alignment.TOP_LEFT);
		}

		for(String group : groups){
			menuAccordion.addTab(groupsMap.get(group), group);
		}		
		menuAccordion.setHeight("99%");
		return menuAccordion;
	}


	private void clearMenuSelection() {
		for(int componentCount =0; componentCount< sideMenu.size();componentCount++){
			if (sideMenu.get(componentCount) instanceof Button) {
				sideMenu.get(componentCount).removeStyleName(StyleName.BUTTON_SIDE_MENU_SELECTED.toString());
			}
		}
	}
}
