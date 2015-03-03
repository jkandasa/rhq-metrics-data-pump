package org.rhq.metrics.qe.tools.rhqmt.gui.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class UrlActionMapper {
	private String menu;
	private String action;
	private String subAction;
	private String value;

	public UrlActionMapper(String parameters){
		if(parameters != null){
			String[] parameter = parameters.split("/");
			
			if(parameter.length >0){
				this.action = parameter[0].trim();
			}
			if(parameter.length >1){
				this.subAction = parameter[1].trim();
			}
			if(parameter.length >2){
				this.value = parameter[2].trim();
			}
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubAction() {
		return subAction;
	}

	public void setSubAction(String subAction) {
		this.subAction = subAction;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this).toString();
	}


}
