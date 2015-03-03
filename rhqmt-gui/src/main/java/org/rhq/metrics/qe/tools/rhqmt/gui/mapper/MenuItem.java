package org.rhq.metrics.qe.tools.rhqmt.gui.mapper;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;

import com.porotype.iconfont.FontAwesome.Icon;
import com.porotype.iconfont.FontAwesome.IconVariant;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MenuItem {
	private String caption;
	private Icon icon;
	private String description;
	private ViewNavigator navigator;
	private String group;
	private IconVariant[] iconVariant;
	
	public MenuItem(String caption, String description, ViewNavigator navigator, String group, Icon icon, IconVariant...iconVariants){
		setMenuItem(caption, description, icon, navigator, group, iconVariants);
	}
	
	public MenuItem(String caption, String description, ViewNavigator navigator, String group){
		setMenuItem(caption, description, null, navigator, group);
	}
	
	public MenuItem(String caption, ViewNavigator navigator, String group){
		setMenuItem(caption, null, null, navigator, group);
	}
	
	public MenuItem(String caption, String description, ViewNavigator navigator, Icon icon, IconVariant...iconVariants){
		setMenuItem(caption, description, icon, navigator, null, iconVariants);
	}
	
	public MenuItem(String caption, String description, ViewNavigator navigator){
		setMenuItem(caption, description, null, navigator, null);
	}
	
	public MenuItem(String caption, ViewNavigator navigator){
		setMenuItem(caption, null, null, navigator, null);
	}
	
	private void setMenuItem(String caption, String description, Icon icon, ViewNavigator navigator, String group, IconVariant...iconVariants){
		this.caption = caption;
		this.description = description;
		this.icon = icon;
		this.navigator = navigator;
		this.group = group;
		this.iconVariant = iconVariants;
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ViewNavigator getNavigator() {
		return navigator;
	}
	public void setNavigator(ViewNavigator navigator) {
		this.navigator = navigator;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	public IconVariant[] getIconVariant() {
		return iconVariant;
	}

	public void setIconVariant(IconVariant... iconVariant) {
		this.iconVariant = iconVariant;
	}	
}
