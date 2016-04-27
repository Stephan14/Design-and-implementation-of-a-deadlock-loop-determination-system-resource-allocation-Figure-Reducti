package com.winnie.util;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.winnie.view.tableViewPart.MyTableModel;
/**
 * 改造JTable
 * @author YuFeng
 * @date 2015年9月15日 下午10:08:39
 */
public class MyTable extends JTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1874040423482114276L;
	/**列计数*/
	protected int columnCount=0;
	/**行计数*/
	protected int rowCount=0;
	protected MyTableModel   defaultModel=null;
	protected Color gridColor = new Color(255,255,255);//网格颜色
	protected int rowHeight = 30;//行高度
    /**
     * 设置表格样式
     */
    {
    	this.setGridColor(gridColor);
        this.setRowHeight(rowHeight);
        setPreferredScrollableViewportSize(new   Dimension(400,   300));
    }
    /**
     * 设置表格样式
     */
    public TableCellRenderer getCellRenderer(int row, int column) {
        return new MyCellRenderer();
    }
	public MyTable(){
		defaultModel=new MyTableModel();
		this.setModel(defaultModel);
	}
	/**
	 * 
	 * @param columns 列名
	 */
	public MyTable(String[] columns){
		defaultModel=new MyTableModel(null,columns);
		this.setModel(defaultModel);
	}
	/**
	 * 获得当前选中行
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 上午8:20:09
	 */
	public String[] getCurRow(){
		int currow=getSelectedRow();
		if(currow<0) return null;
		
		String [] result= new String[columnCount];
		for (int i = 0; i < result.length; i++) {
			result[i]=(String) getValueAt(currow, i);
		}
		return result;
	}
	/**
	 * 获得表格的全部信息
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 上午8:43:23
	 */
	public String[][] getAll(){
		String[][] result = new String[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				result[i][j] = (String) getValueAt(i, j);
			}
		}
		return result;
	}
	/**
	 * 添加一列
	 * @param columnName
	 * @author YuFeng   
	 * @date 2015年9月17日 上午8:13:36
	 */
	public void addColumn(String columnName){
		defaultModel.addColumn(columnName);
		columnCount++;
	}
	public void removeColumn(int index){
		if(index<columnCount){
			TableColumnModel   columnModel   =   this.getColumnModel();
			TableColumn   tableColumn   =   columnModel.getColumn(index);
			columnModel.removeColumn(tableColumn);
		}
	}
	/**
	 * 添加一行数据
	 * @param rows
	 * @author YuFeng   
	 * @date 2015年9月17日 上午8:13:19
	 */
	public void addRow(String[] rows ){
		if(rows.length>columnCount){
			return;
		}else{
			defaultModel.addRow(rows);
			rowCount++;
		}
	}
	/**
	 * 删除某一行
	 * @param index 行的下标
	 * @author YuFeng   
	 * @date 2015年9月17日 上午9:46:25
	 */
	public void removeRow(int index){
		if(index<rowCount){
			defaultModel.removeRow(index);
		}
		rowCount--;
	}
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame j =new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyTable table = new MyTable();
		
		table.addColumn("dsf");
		table.addColumn("asdfadsf");
		table.addRow(new String[]{"a"});
		table.addRow(new String[]{"a"});
		
		table.addColumn("dsf");
		table.addColumn("asdfadsf");
		table.addRow(new String[]{"a"});
		table.addRow(new String[]{"a"});
		
		table.addColumn("dsf");
		table.addColumn("asdfadsf");
		table.addRow(new String[]{"a"});
		table.addRow(new String[]{"a"});
		
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane   s   =   new   JScrollPane(table);
		j.getContentPane().add(s);
		j.pack();
		j.setVisible(true);
		System.out.println(table.getCurRow());
		for(String[] out:table.getAll()){
			for(String i:out){
				System.out.println(i);
			}
		}
		table.removeColumn(0);
//		table.removeRow(1);
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.updateComponentTreeUI(j);
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setLookAndFeel(){
		try {
			
		   
		} catch (Exception e) {
		}
       
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public void addForbidden(int column){
		defaultModel.addForbidden(column);
	}
	public boolean checkRow(int index){
		return index>=0&&index<rowCount;
	}
}
