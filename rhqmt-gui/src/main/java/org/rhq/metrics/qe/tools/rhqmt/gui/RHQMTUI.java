package org.rhq.metrics.qe.tools.rhqmt.gui;


import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.Footer;
import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.IonIconsCDN;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;
import org.rhq.metrics.qe.tools.rhqmt.gui.view.DashboardView;
import org.rhq.metrics.qe.tools.rhqmt.gui.view.MetricsView;
import org.rhq.metrics.qe.tools.rhqmt.gui.view.SettingsView;
import org.rhq.metrics.qe.tools.rhqmt.gui.view.ViewMenus;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.MainMenuButton;

import com.porotype.iconfont.FontAwesome;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@SuppressWarnings("serial")
@Theme("rhqmtgui-theme")
@Title("RHQ Metrics Tool (RHQMT) - GUI")
public class RHQMTUI extends UI {
	private static Logger _logger = Logger.getLogger(RHQMTUI.class);

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = RHQMTUI.class, widgetset = "org.rhq.metrics.qe.tools.rhqmt.gui.AppWidgetSet")
	public static class Servlet extends VaadinServlet {

	}

	CssLayout root = new CssLayout();
	VerticalLayout mainLayout = new VerticalLayout();
	VerticalLayout loginLayout;

	HorizontalLayout menu = new HorizontalLayout();
	CssLayout content = new CssLayout();


	private Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		FontAwesome.load();
		IonIconsCDN.load();
		setDefaultErrorHandler();
		setLocale(Locale.US);

		setContent(root);
		root.addStyleName(StyleName.LAYOUT_ROOT.toString());
		root.addStyleName(StyleName.COMMON_TEXT_STYLE.toString());
		root.setSizeFull();
		_logger.debug("Session ID: "+request.getWrappedSession(true).getId());
		buildMainView();
	}

	private HorizontalLayout getLogoMenuComponents(boolean isMainPage){
		HorizontalLayout topMenu = new HorizontalLayout();

		topMenu.addStyleName(StyleName.COMMON_TEXT_STYLE.toString());
		topMenu.addStyleName(StyleName.LAYOUT_TOP_LOGO_BAR.toString());
		topMenu.setHeight(null);
		topMenu.setWidth("100%");

		// Branding element
		CssLayout branding = new CssLayout();
		branding.addStyleName(StyleName.LAYOUT_TOP_LOGO_BAR.toString());
		Label logo = new Label("RHQ Metrics Tool (RHQMT) - GUI", ContentMode.HTML);
		logo.setSizeUndefined();
		logo.setStyleName(StyleName.TEXT_LOGO.toString());


		//Adding branding in to Horizintal Layout
		HorizontalLayout logoSpace = new HorizontalLayout();
		logoSpace.setSpacing(true);



		logoSpace.setSizeUndefined();

		logoSpace.addComponents(logo);
		logoSpace.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

		// Adding Components
		topMenu.addComponent(logoSpace);
		topMenu.setComponentAlignment(logoSpace, Alignment.MIDDLE_LEFT);
		return topMenu;
	}

	private void setTopMenus(){
		menu.removeAllComponents();
		menu.addStyleName(StyleName.LAYOUT_TOP_MENU.toString());
		menu.setSizeUndefined();
		menu.setSpacing(true);

		for (final MenuItem mainMenu : new ViewMenus().getMainMenu()) {
			final Button menuButton = new MainMenuButton(mainMenu);	
			menuButton.setStyleName(StyleName.BUTTON_MAIN_MENU.toString());
			menuButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection();
					event.getButton().addStyleName(StyleName.BUTTON_MAIN_MENU_SELECTED.toString());
					UI.getCurrent().getNavigator().navigateTo(mainMenu.getNavigator().toString());
				}
			});
			menu.addComponent(menuButton);
			menu.setComponentAlignment(menuButton, Alignment.MIDDLE_LEFT);
		}
	}



	private void buildMainView() {

		navigator = new Navigator(this, content);
		navigator.addView(ViewNavigator.VIEW_DASHBOARD.toString(), new DashboardView());
		navigator.addView(ViewNavigator.VIEW_METRICS.toString(), new MetricsView());
		navigator.addView(ViewNavigator.VIEW_SETTINGS.toString(), new SettingsView());


		root.removeAllComponents();
		mainLayout.removeAllComponents();
		content.removeAllComponents();

		root.addComponent(mainLayout); 
		mainLayout.setSizeFull();
		mainLayout.addStyleName(StyleName.LAYOUT_MAIN.toString());

		//Menu Layout
		HorizontalLayout logoLayout = this.getLogoMenuComponents(true);


		//Footer Layout 
		HorizontalLayout footer  = new Footer();



		// Content
		content.setSizeFull();
		content.addStyleName(StyleName.LAYOUT_CONTENT.toString());


		mainLayout.addComponent(logoLayout);
		this.setTopMenus();
		mainLayout.addComponent(menu);
		mainLayout.addComponent(content);
		mainLayout.addComponent(footer);

		mainLayout.setComponentAlignment(logoLayout, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(menu, Alignment.TOP_LEFT);
		mainLayout.setComponentAlignment(content, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(footer, Alignment.BOTTOM_LEFT);

		mainLayout.setExpandRatio(content, 1);

		String f = Page.getCurrent().getUriFragment();
		if (f != null && f.startsWith("!")) {
			f = f.substring(1);
		}
		if (f == null || f.equals("") || f.equals("/")) {
			navigator.navigateTo(ViewNavigator.VIEW_DASHBOARD.toString());
			menu.getComponent(0).addStyleName(StyleName.BUTTON_MAIN_MENU_SELECTED.toString());
		}else{
			navigator.navigateTo(f);
		}
	}

	private void clearMenuSelection() {
		for(int componentCount =0; componentCount< menu.getComponentCount();componentCount++){
			if (menu.getComponent(componentCount) instanceof Button) {
				menu.getComponent(componentCount).removeStyleName(StyleName.BUTTON_MAIN_MENU_SELECTED.toString());
			}
		}
	}
	private void setDefaultErrorHandler(){
		// Configure the error handler for the UI
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				// Find the final cause
				String cause = "<b>The click failed because:</b><br/>";
				for (Throwable t = event.getThrowable(); t != null;
						t = t.getCause())
					if (t.getCause() == null) // We're at final cause
						cause += t.getClass().getName() + "<br/>";


				Notification.show(cause, Type.ERROR_MESSAGE);
				// errorNotification.show(UI.getCurrent().getPage(), Type.ERROR_MESSAGE);

				// Display the error message in a custom fashion
				//layout.addComponent(new Label(cause, ContentMode.HTML));

				// Do the default error handling (optional)
				doDefault(event);
			} 
		});
	}
}