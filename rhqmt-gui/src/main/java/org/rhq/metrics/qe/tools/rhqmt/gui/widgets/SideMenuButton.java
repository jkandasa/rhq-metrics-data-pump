package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;

import com.porotype.iconfont.FontAwesome.Icon;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class SideMenuButton extends MenuButtonBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 293430503790677628L;

	public SideMenuButton(String caption, String description, Icon icon) {
		super(caption, description, icon);
		initMe();
	}
	
	public SideMenuButton(String caption, String description) {
		super(caption, description);
		initMe();
	}
	
	public SideMenuButton(String caption) {
		super(caption);
		initMe();
	}
	
	public SideMenuButton(MenuItem menuItem) {
		super(menuItem);
		initMe();
	}
	
	private void initMe(){
		//setWidth("100%");
	}

}
