package com.winnie.view.tableViewPart;

import javax.swing.event.TableModelEvent;

import com.winnie.interfaces.TableItem;
import com.winnie.util.MyLimitedTable;

public class NewTable extends MyLimitedTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6801039850601448812L;
	TableItem item = null;
	public NewTable(TableItem item){
		this.item = item;
	}
	@Override
	protected void onchange(TableModelEvent e) {
		// TODO Auto-generated method stub
		super.onchange(e);
		item.onchange(e);
	}
	
}
