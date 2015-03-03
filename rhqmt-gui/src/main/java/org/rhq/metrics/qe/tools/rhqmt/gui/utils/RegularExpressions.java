package org.rhq.metrics.qe.tools.rhqmt.gui.utils;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RegularExpressions {
	public static String REGEX_NAME= "^[a-zA-Z0-9-_@. ]{2,50}$";
	public static String REGEX_COMMON_NAME= "^\\s*[a-zA-Z0-9_-]{2,50}$";
	public static String REGEX_DESCRIPTION= "^\\s*\\S*{5,300}$";
}
