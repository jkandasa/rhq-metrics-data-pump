package org.rhq.metrics.qe.tools.rhqmt.gui.mapper.settings;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RhqmtServerSettings {
	private static String url = "http://localhost:8090";
	private static String version = "Not Connected";
	
	
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		RhqmtServerSettings.url = url;
	}
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		RhqmtServerSettings.version = version;
	}
}
