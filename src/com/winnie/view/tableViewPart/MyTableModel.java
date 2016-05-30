package com.winnie.view.tableViewPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8876981200488101724L;
	protected Map<Integer,Integer> forbiddenColumn = new HashMap<Integer, Integer>();
	public MyTableModel()
	{
		super();
	}
	public MyTableModel(Object[][] object, Object[] columns) {
		super(object,columns);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return !forbiddenColumn.containsKey(column); 
	}
	public void addForbidden(int index){
		forbiddenColumn.put(index, index);
	}
	public void removeForbidden(int index){
		if(forbiddenColumn.containsKey(index));
		forbiddenColumn.remove(index);
	}
	public static void main(String[] args) {
		List<Integer> forbiddenColumn = new ArrayList<Integer>();
		forbiddenColumn.add(1);
		System.out.println(forbiddenColumn.contains(1));
	}
}
