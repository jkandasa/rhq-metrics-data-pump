package org.rhq.metrics.qe.tools.rhqmt.gui.utils;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public enum StyleName {
	
	BUTTON_COMMON("button-common"),
	
	BUTTON_MAIN_MENU("button-main-menu"),
	BUTTON_MAIN_MENU_SELECTED("button-main-menu-selected"),
	
	BUTTON_SIDE_MENU("button-side-menu"),
	BUTTON_SIDE_MENU_SELECTED("button-side-menu-selected"),
	
	LAYOUT_ROOT("layout-root"),
	LAYOUT_MAIN("layout-main"),
	LAYOUT_TOP_MENU("layout-top-menu"),
	LAYOUT_TOP_LOGO_BAR("layout-top-logo-bar"),
	IMAGE_LOGO("image-logo"),
	TEXT_LOGO("text-logo"),
	LAYOUT_SIDE_MENU("layout-side-menu"),
	LAYOUT_CONTENT("layout-content"),
	LAYOUT_FOOTER("layout-footer"),
	LAYOUT_CONTENT_HEADER("layout-content-header"),
	LAYOUT_SUB_PAGE_HEADER("layout-header"),
	LAYOUT_BOTTOM_BUTTON("layout-bottom-button"),
	
	
	ACCORDION_SIDE_MENU("accordion-side-menu"),
	RADIO_BUTTON_MULTI("radio-button-multi"),
	
	TABLE_COMMON("table-common"),
	TREE_SIDE_MENU("tree-side-menu"),
	
	LABEL_ERROR("label-error"),
	
	TWIN_COL_SELECT_COMMON("twin-col-select-common"),
	
	POPUP_WINDOW("popup-window"),
	
	CUSTOM_CHECKBOX("custom-checkbox"),
	HORIZONTAL_RADIO_LIST("horizontal-radio-list"),
	
	TRAY_NOTIFICATION("tray-notification"),
	
	OPTION_GROUP_ANALYTICAL_REPORT("option-group-analytical-report"),
	
	COMMON_TEXT_STYLE("common-textstyle"),
	
	DUNMY("");
	
	private final String style;
	
	private StyleName(String style){
		this.style = style;
	}
	public String toString(){
		return this.style;
	}
}
