package org.rhq.metrics.qe.tools.rhqmt.gui.tables;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;

import com.vaadin.ui.Table;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class BaseTable extends Table{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7800464477907479175L;

	public BaseTable(){
		super();
		setCaption(null);
		setSelectable(true);
		//setMultiSelect(true);
		setFooterVisible(true);
		//setPageLength(15);
		setStyleName(StyleName.TABLE_COMMON.toString());
		setSizeFull();
		setNullSelectionAllowed(false); //By disabling this user cannot de-select a row, 
										//once the selected any one of the row should be selected in the table
	}
}
