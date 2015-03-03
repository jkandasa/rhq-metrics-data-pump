package org.rhq.metrics.qe.tools.rhqmt.gui.view;

import org.rhq.metrics.qe.tools.rhqmt.gui.layout.settings.ConnectionLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.UrlActionMapper;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Accordion;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class SettingsView  extends BaseView implements View{

	/**
	 * 
	 */
    private static final long serialVersionUID = 2666471397374808813L;
    
	public SettingsView(){
		content.setMargin(false);
		setSizeFull();
		addStyleName(StyleName.LAYOUT_MAIN.toString());
		Accordion menuBar = this.getMenuBar(new ViewMenus().getSettingsMenu());
		menuBar.setHeight("100%");

		//--------------------------------------------------------------
		//Content Panel
		//--------------------------------------------------------------
		content.setSizeFull();
		//content.addStyleName(StyleName.LAYOUT_CONTENT_SETTINGS.toString());
		content.setCaption("Settings");

		//Adding All the layout to root layout
		addComponent(menuBar);
		addComponent(content);

		setFirstComponent(menuBar);
		setSecondComponent(content);
		setSplitPosition(15,Unit.PERCENTAGE);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		content.removeAllComponents();
		UrlActionMapper parameters = new UrlActionMapper(event.getParameters());
		if(parameters.getAction() != null){
			if(parameters.getAction().equals(ViewNavigator.SM_SETTINGS_CONNECTION.toString())){
				content.addComponent(new ConnectionLayout());
			}
		}
	}

}