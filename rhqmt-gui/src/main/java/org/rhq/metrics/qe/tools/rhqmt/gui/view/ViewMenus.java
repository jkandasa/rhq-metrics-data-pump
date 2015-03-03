package org.rhq.metrics.qe.tools.rhqmt.gui.view;

import java.util.ArrayList;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;

import com.porotype.iconfont.FontAwesome.Icon;
import com.porotype.iconfont.FontAwesome.IconVariant;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ViewMenus {

	public ArrayList<MenuItem> getMainMenu(){
		ArrayList<MenuItem> mainMenus = new ArrayList<MenuItem>();

		MenuItem dashboardMenu = new MenuItem("Dashboard","Dashboard View", ViewNavigator.VIEW_DASHBOARD, Icon.beer, IconVariant.SIZE_LARGE);
		MenuItem metricsMenu = new MenuItem("Metrics","Metrics View", ViewNavigator.VIEW_METRICS, Icon.bar_chart, IconVariant.SIZE_LARGE);
		MenuItem settingsMenu = new MenuItem("Settings","Settings View", ViewNavigator.VIEW_SETTINGS, Icon.cogs, IconVariant.SIZE_LARGE);
		
		mainMenus.add(dashboardMenu);
		mainMenus.add(metricsMenu);
		mainMenus.add(settingsMenu);
		
		return mainMenus;
	}
	
	public ArrayList<MenuItem> getMetricsMenu(){
		ArrayList<MenuItem> sideMenus = new ArrayList<MenuItem>();
		sideMenus.add(new MenuItem("Generate Metrics","Generate Metrics" ,ViewNavigator.VIEW_METRICS_PAST, "Metrics",Icon.bar_chart ));
		sideMenus.add(new MenuItem("Realtime Metrics","Schedule Metrics", ViewNavigator.VIEW_METRICS_REALTIME, "Metrics", Icon.time));
		sideMenus.add(new MenuItem("Metrics Template","Metrics Template", ViewNavigator.VIEW_METRICS_TEMPLATE, "Metrics", Icon.time));
		return sideMenus;
	}
	
	public ArrayList<MenuItem> getSettingsMenu(){
		ArrayList<MenuItem> settingsMenus = new ArrayList<MenuItem>();
		settingsMenus.add(new MenuItem("Connection","Connection - RHQMT Server" ,ViewNavigator.VIEW_SETTINGS_CONNECTION, "RHQMT Server",Icon.cog ));
		return settingsMenus;
	}
}
