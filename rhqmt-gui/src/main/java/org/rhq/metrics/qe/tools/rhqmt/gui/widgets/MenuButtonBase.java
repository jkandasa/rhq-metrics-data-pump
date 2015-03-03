package org.rhq.metrics.qe.tools.rhqmt.gui.widgets;

import org.rhq.metrics.qe.tools.rhqmt.gui.mapper.MenuItem;

import com.porotype.iconfont.FontAwesome.Icon;
import com.porotype.iconfont.FontAwesome.IconVariant;
import com.vaadin.ui.NativeButton;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MenuButtonBase extends NativeButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192620797262121320L;

	public MenuButtonBase(String caption, String description, Icon icon){
		initMe(caption, description, icon);
	}
	
	public MenuButtonBase(String caption, String description, Icon icon, IconVariant...iconVariants){
		initMe(caption, description, icon, iconVariants);
	}
		
	public MenuButtonBase(String caption, Icon icon, IconVariant...iconVariants){
		initMe(caption, null, icon,iconVariants);
	}
	
	public MenuButtonBase(String caption){
		initMe(caption, null, null);
	}
	
	public MenuButtonBase(String caption, String description){
		initMe(caption, description, null);
	}
	
	public MenuButtonBase(MenuItem menuItem){
		initMe(menuItem.getCaption(), menuItem.getDescription(), menuItem.getIcon(), menuItem.getIconVariant());
	}
	
	private void initMe(String caption, String description, Icon icon, IconVariant...iconVariants){
		if(icon != null){
			if(iconVariants != null){
				setCaption(icon.variant(iconVariants)+" "+caption);
			}else{
				setCaption(icon+" "+caption);
			}			
		}else{
			setCaption(caption);
		}
		setDescription(description);
		setHtmlContentAllowed(true);
	}

}
