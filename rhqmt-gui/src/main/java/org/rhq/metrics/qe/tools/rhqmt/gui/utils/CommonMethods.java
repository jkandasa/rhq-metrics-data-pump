package org.rhq.metrics.qe.tools.rhqmt.gui.utils;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class CommonMethods {

	public static String getString(Object object){
		if(object == null){
			return "";
		}else{
			return String.valueOf(object);
		}
	}
}
