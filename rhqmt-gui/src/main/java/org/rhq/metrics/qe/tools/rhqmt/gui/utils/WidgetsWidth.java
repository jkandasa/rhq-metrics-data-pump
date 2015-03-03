package org.rhq.metrics.qe.tools.rhqmt.gui.utils;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public enum WidgetsWidth {


	//Text Box
	RADIO_BOX("275px"),
	COMBO_BOX("275px"),
	TEXT_BOX("275px"),
	TEXT_AREA("275px"),
	PASSWORD_BOX("275px"),
	EMAIL_BOX("275px"),		
	DATE_BOX("275px"),
	COMBO_BOX_DECISION("120px"),
	TEXT_BOX_DECISION("120px"),
	DUNMY("");

	private final String width;

	private WidgetsWidth(String width){
		this.width = width;
	}
	public String toString(){
		return this.width;
	}




}
