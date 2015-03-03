package org.rhq.metrics.qe.tools.rhqmt.gui.layout;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class HeaderLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461223349802156380L;
	
	public HeaderLayout(String caption, Icon icon) {
		initMe(caption, icon);
	}
	
	public HeaderLayout(String caption) {
		initMe(caption, null);
	}
	
	private void initMe(String caption, Icon icon){
		setStyleName(StyleName.LAYOUT_SUB_PAGE_HEADER.toString());
		setSizeUndefined();
		
		Label title = null;
		if(icon != null){
			title = new Label(icon+" "+caption, ContentMode.HTML);
		}else{
			title = new Label(caption);
		}
		title.setSizeUndefined();
		
		addComponent(title);
		setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
	}

}
