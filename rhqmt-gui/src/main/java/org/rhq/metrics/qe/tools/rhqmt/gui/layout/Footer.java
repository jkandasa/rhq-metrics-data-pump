package org.rhq.metrics.qe.tools.rhqmt.gui.layout;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.settings.RhqmtServerSettings;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RHQMTServer;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class Footer extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2598702015788368945L;

	private Label footerTxtRight = new Label(); 
	private Label footerTxtLeft = new Label(); 
	
	public Footer(){
		setFooterTxtLeft("RHQMT Server: "+RhqmtServerSettings.getUrl()+", Status:"+(RHQMTServer.isConnected() == true ? "Connected": "Not Connected")+", Version: "+RhqmtServerSettings.getVersion());
		addComponents(footerTxtLeft, footerTxtRight);
		setComponentAlignment(footerTxtLeft, Alignment.MIDDLE_LEFT);
		setComponentAlignment(footerTxtRight, Alignment.MIDDLE_RIGHT);
		setSizeUndefined();
		setWidth("100%");
		setMargin(new MarginInfo(false, true, false, true));
		addStyleName(StyleName.LAYOUT_FOOTER.toString());
	}

	public String getFooterTxtRight() {
		return footerTxtRight.getCaption();
	}

	public void setFooterTxtRight(String footerTxtRight) {
		this.footerTxtRight.setCaption(footerTxtRight);
	}

	public String getFooterTxtLeft() {
		return footerTxtLeft.getCaption();
	}

	public void setFooterTxtLeft(String footerTxtLeft) {
		this.footerTxtLeft.setCaption(footerTxtLeft);
	}
	
	public void updateFooterTxtLeft() {
		setFooterTxtLeft("RHQMT Server: "+RhqmtServerSettings.getUrl()+", Status:"+(RHQMTServer.isConnected() == true ? "Connected": "Not Connected")+", Version: "+RhqmtServerSettings.getVersion());
	}
}
