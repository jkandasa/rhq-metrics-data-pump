package org.rhq.metrics.qe.tools.rhqmt.gui.tables;

import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;

import com.vaadin.ui.TreeTable;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class BaseTreeTable extends TreeTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3235651456974954552L;

	public BaseTreeTable(){
		super();
		setCaption(null);
		setSelectable(true);
		setFooterVisible(true);
		//setPageLength(15);
		setStyleName(StyleName.TABLE_COMMON.toString());
		setSizeFull();
		setNullSelectionAllowed(false); //By disabling this user cannot de-select a row, 
										//once the selected any one of the row should be selected in the table
	}
}
