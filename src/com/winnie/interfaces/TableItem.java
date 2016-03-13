package com.winnie.interfaces;

import javax.swing.event.TableModelEvent;

public interface TableItem {
	/**
	 * Table改变时TableItem要执行的事件
	 * @param e
	 * @author YuFeng   
	 * @date 2015年10月12日 下午3:51:13
	 */
	public void onchange(TableModelEvent e);
}
