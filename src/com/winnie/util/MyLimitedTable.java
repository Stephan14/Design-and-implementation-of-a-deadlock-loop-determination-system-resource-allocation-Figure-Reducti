package com.winnie.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.winnie.exceptions.CanNotApplyException;



/**
 * 添加了约束的JTabel,目前只添加非空约束、整数约束、浮点数约束
 * 其中 type=0，无约束，type=1非空，type=2 整数约束 type=3浮点数约束
 * @author YuFeng
 * @date 2015年9月17日 上午8:53:37
 */
public class MyLimitedTable extends MyTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4171044609932595566L;
	public static final int NOTNULL=1;
	public static final int INTEGER=2;
	public static final int FLOAT = 3;
	protected List<Integer> cannotEditRow = new ArrayList<Integer>();
	protected List<Integer> cannotEditColmn = new ArrayList<Integer>();
	protected List<Integer> limit=new ArrayList<Integer>();
	protected List<String > defaultValue = new ArrayList<String>();
	public MyLimitedTable(){
		
		defaultModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE){
					onchange(e);
				}
				
			}

			
		});
	}
	/**
	 * 将index列的约束设置为type
	 * @param index
	 * @param type
  	 * @author YuFeng   
	 * @throws CanNotApplyException 
	 * @date 2015年9月17日 上午9:21:10
	 */
	public boolean addLimit(int index ,int type,String defaultValue) throws CanNotApplyException{
//		if(index<columnCount){
//			if(!checkLimit(defaultValue, type)){
//				throw new CanNotApplyException();//默认值不满足要求，抛出异常退出
//			}
//			while(limit.size()<columnCount){
//				limit.add(0);
//				this.defaultValue.add("");
//			}
//			int temp =limit.get(index);
//			limit.set(index, type);
//			if(checkColumn(index)){
//				return true;
//			}else{
//				limit.set(index, temp);
//				
//				this.defaultValue.set(index, defaultValue);
//				System.out.println(this.defaultValue.get(index));
//				return false;
//			}	
//		}else{
//			return false;
//		}
		if(checkColumn(index)){
			if(!checkLimit(defaultValue, type)){
				throw new CanNotApplyException();//默认值不满足要求，抛出异常退出
			}
			while(limit.size()<columnCount){
				limit.add(0);
				this.defaultValue.add("");
			}
			limit.set(index, type);
			this.defaultValue.set(index, defaultValue);
//			System.out.println(this.defaultValue.get(index));
			return true;
		}else{
			return false;
		}	
	
	}
	/**
	 * @param i
	 * @param j
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午9:59:07
	 */
	public boolean checkLimit(int i,int j){
		if(i<0||j<0) return false;
		if(limit.size()<=j){
			return true;
		}else{
			return checkLimit(getValueAt(i, j), limit.get(j));
		}
	}
	/**
	 * 检查某一元素是否满足约束
	 * @param src
	 * @param limit
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月18日 上午7:59:08
	 */
	public boolean checkLimit(Object src,int limit){
		switch(limit){
		case 0:return true;
		case 1:return !(src==null||((String)src).equals(""));
		case 2:return CheckNumber.isInteger((String)src);
		case 3:return CheckNumber.isFloat((String)src);
		}
		return true;
	}
	/**
	 * 检测某一列的约束
	 * @param column
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午2:27:30
	 */
	public boolean checkLimit(int column){
		switch(limit.get(column)){
			case 0:return true;
			case 1:return checkNotNull(column);
			case 2:return checkInteger(column);
			case 3:return checkFolat(column);
		}
		return true;
	}
	
	/**
	 * 检测浮点数约束
	 * @param column
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午2:27:14
	 */
	public boolean checkFolat(int column){
		if(checkColumn(column)) return false;
		for (int i = 0; i < rowCount ; i++) {
			if(!CheckNumber.isFloat((String)getValueAt(i, column))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 检查数字约束
	 * @param column
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午2:02:09
	 */
	public boolean checkInteger(int column){
		if(checkColumn(column)) return false;
		for (int i = 0; i < rowCount ; i++) {
			if(!CheckNumber.isInteger((String)getValueAt(i, column))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 检测非空约束
	 * @param column 列的编号
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午1:48:17
	 */
	public boolean checkNotNull(int column){
		if(!checkColumn(column)) return false;
		for (int i = 0; i < rowCount ; i++) {
			if(getValueAt(i, column)==null||((String)getValueAt(i, column)).equals("")){
				return false;
			}
		}
		return true;
	}
	/**
	 * 检查列号是否符合要求
	 * @param column
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 下午1:59:37
	 */
	public boolean checkColumn(int column){
		return column>=0&&column<columnCount;
	}
	/**
	 * 检查某一列是否满足约束type
	 * @param column
	 * @param type
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月18日 上午8:08:31
	 */
	public boolean checkColumn(int column,int type){
		if(checkColumn(column)){
			switch (type) {
			case 1:
				return checkNotNull(column);
			case 2:
				return checkInteger(column);
			case 3:
				return checkFolat(column);

			default:
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame j =new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyLimitedTable table = new MyLimitedTable();
		
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
//		System.out.println(table.getCurRow());
//		for(String[] out:table.getAll()){
//			for(String i:out){
//				System.out.println(i);
//			}
//		}
//		table.removeColumn(0);
//		table.removeRow(1);
		try{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		UIManager.setLookAndFeel(new PlasticLookAndFeel());
		SwingUtilities.updateComponentTreeUI(j);
		}catch(Exception e){
			
		}
		
//		try {
////			System.out.println(table.addLimit(0, 2,"1"));
//		} catch (CanNotApplyException e) {
//			e.printStackTrace();
//		}
		table.setValueAt("asfd",1, 2);
		table.setValueAt("", 1, 2);
//		System.out.println(table.checkColumn(0, 1));
//		System.out.println(table.checkColumn(1, 1));
		
	}
	public void closeRow(int row){
		if(row>=0&&row<rowCount){
			cannotEditRow.add(row);
		}
	}
	public void closeColumn(int column){
		if(checkColumn(column)){
			cannotEditColmn.add(column);
		}
	}
	
	protected void onchange(TableModelEvent e) {
		int x=e.getFirstRow();
		int y=e.getColumn();
		if(x<0||y<0) return ;
//		System.out.println(x+" "+y+" "+limit.size());
		if(!checkLimit(x, y)){
			JOptionPane.showMessageDialog(null, "输入数据违反约束", "错误信息", JOptionPane.ERROR_MESSAGE);
//			System.out.println(defaultValue.get(y));
			setValueAt(defaultValue.get(y), x ,y);
//			System.out.println(getValueAt(x, y));
			repaint();
		}
	}
}
