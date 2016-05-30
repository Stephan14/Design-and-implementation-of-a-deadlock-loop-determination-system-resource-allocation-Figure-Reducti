package com.winnie.view.tableViewPart;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.winnie.exceptions.CanNotApplyException;
import com.winnie.interfaces.TablePanel;
import com.winnie.model.Node;
import com.winnie.model.Source;
import com.winnie.util.CheckNumber;
import com.winnie.util.MyLimitedTable;

public class SourcePane extends ProcessPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9103882458630915154L;
	private int index=0;
	protected List<Integer> used ;
			
	protected static final String[] columns = {"编号","名称","数量","已分配","剩余"};
	public SourcePane(TablePanel parent){
		super(parent);
//		init();//构造函数中已经会执行init()
		//添加右键菜单
		//添加选中事件
		System.out.println("____"+used.size());
	}
	public void init(){
//		table.setModel(new DefaultTableModel(){
//			private static final long serialVersionUID = -876760751888279710L;
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				if(column==0||column==3||column==4){
//					return false;
//				}
//				else{
//					return true;
//				}
//			}
//		});
		editList.add(1);
		editList.add(2);
		used = new ArrayList<Integer>();
		panel.setViewportView(table);
		for(String col:columns){
			table.addColumn(col);
		}
		try {
			table.addLimit(0, MyLimitedTable.INTEGER, "1");
			table.addLimit(1, MyLimitedTable.NOTNULL, "请输入名称");
			table.addLimit(2, MyLimitedTable.INTEGER, "1");
		} catch (CanNotApplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.addForbidden(0);
		table.addForbidden(3);
		table.addForbidden(4);
	}
	/**
	 * 获得当前选中节点，以便向视图中添加
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月22日 下午9:26:44
	 */
	public Node getSelected(){
		int row = table.getSelectedRow();
		if(row<0||settled.get(row)){
			return null;
		}
		com.winnie.model.Source result = new com.winnie.model.Source();
		String[] msg = table.getCurRow();
		result.setName(msg[1]);
		result.setUsed(0);
		result.setNums(Integer.parseInt(msg[2]));
		return result;
	}
	public void addTestData() {
		if(check()){
			settled.add(false);
			used.add(0);
			index++;
			table.addRow(new String[]{index+"","asdfafsdf","10","0","10"});
		}else{
			JOptionPane.showMessageDialog(null, "现有资源信息不完善！");
		}
	};
	@Override
	public void add(){
		if(check()){
			settled.add(false);
			used.add(0);
			index++;
			table.addRow(new String[]{"资源"+index+"","","1","0","1"});
		}else{
			JOptionPane.showMessageDialog(null, "现有资源信息不完善！");
		}
	}
	
	@Override
	public Node getCurNode() {
		if(!check()){
			return null;
		}
		Node current = null;
		int row = table.getSelectedRow();
		if(row<0){
		}else{
			if(checkSettled(row)) return null;
			String temp=table.getAll()[row][1];
			if(temp==null||temp.trim().equals("")){
				return null;
			}
			temp=table.getAll()[row][2];
			if(temp==null||temp.trim().equals("")){
				return null;
			}
			current = new Source(table.getAll()[row][1],Integer.parseInt(table.getAll()[row][2]));
			current.setIndex(row);
		}
		return current;
	}
	public Node getCurNode(int index){
		if(table.checkRow(index)){
			setUsed(index, 0);
			String temp=table.getAll()[index][1];
			if(temp==null||temp.trim().equals("")){
				return null;
			}
			temp=table.getAll()[index][2];
			if(temp==null||temp.trim().equals("")){
				return null;
			}
			Node current = new Source(table.getAll()[index][1],Integer.parseInt(table.getAll()[index][2]));
			current.setIndex(index);
			return current;
		}
		return null;
	}
	
	public void setSettled(int index){
		settled.set(index, true);
	}
	/**
	 * 设置分配
	 * @param index
	 * @param num
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月24日 下午3:26:32
	 */
	public boolean setUsed(int index,int num){
		if(index>=0){
			String[] src = table.getAll()[index];
			int all = Integer.parseInt(src[2]);
			int haveUsed = Integer.parseInt(src[3]);
			if(all<haveUsed+num){
				JOptionPane.showMessageDialog(null, "分配数超出限制");
				return false;
			}else{
				table.setValueAt(haveUsed+num+"", index, 3);
				table.setValueAt(all-haveUsed-num+"", index, 4);
				used.set(index, used.get(index)+num);
				return true;
			}
		}
		return false;
		
	}
	/**
	 * 检查现有数据是否符合要求
	 */
	public boolean check(){
		boolean flag = super.check();
		if(!flag){
			return flag;
		}
		String temp[][] = table.getAll();
		
		for(int i = 0; i < temp.length; i++){
			if(!CheckNumber.isInteger(temp[i][2])){
				return false;
			}
		}
		
		return true;
	}
	public static void main(String[] args) {
		JFrame f =new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.add(new SourcePane());
		f.pack();
	}
	public void renew(){
		for (int i = 0; i < settled.size(); i++) {
			settled.set(i, false);
		}
		for (int i = 0; i < used.size(); i++) {
			setUsed(i, 0-used.get(i));
		}
	}
	@Override
	public void onchange(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		int column = e.getColumn();
		if(column==2){
			String[]message = table.getCurRow();
			String s = message[column];
			String aim = message[column+1];
			if(Integer.parseInt(s)<Integer.parseInt(aim)){
				JOptionPane.showMessageDialog(null, "资源数量少于已分配数量！");
				table.setValueAt(aim, row, column);
				return;
			}
		}
		super.onchange(e);
	}
	
}
