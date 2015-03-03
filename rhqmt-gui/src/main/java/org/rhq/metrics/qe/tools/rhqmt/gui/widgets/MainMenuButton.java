package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;

import com.porotype.iconfont.FontAwesome.Icon;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MainMenuButton extends MenuButtonBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 293430503790677628L;

	public MainMenuButton(String caption, String description, Icon icon) {
		super(caption, description, icon);
		initMe();
	}
	
	public MainMenuButton(String caption, String description) {
		super(caption, description);
		initMe();
	}
	
	public MainMenuButton(String caption) {
		super(caption);
		initMe();
	}
	
	public MainMenuButton(MenuItem menuItem) {
		super(menuItem);
		initMe();
	}
	
	private void initMe(){
		setWidth("165px");
	}

}
