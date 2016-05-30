package com.winnie.view.tableViewPart;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;

import com.winnie.interfaces.TableItem;
import com.winnie.interfaces.TablePanel;
import com.winnie.model.Node;
import com.winnie.util.MyLimitedTable;

public class ProcessPane extends JPanel implements TableItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8312895639536875859L;
	protected JScrollPane panel = new  JScrollPane();
	protected MyLimitedTable table = new NewTable(this);
	private int index=0;
	protected List<Boolean> settled = new ArrayList<Boolean>();
	
	protected TablePanel parentPanel;
	protected JPanel jPanel = new JPanel();
	
	protected static final String[] columns = {"编号","名称","已分配"};
	
	protected List<Integer> editList = new ArrayList<Integer>();
	public ProcessPane(TablePanel parent){
		setTablePanel(parent);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(panel);
		init();
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(table.getSelectedRow()>=0){
					setCurNode(parentPanel);
				}
			}
		});
		JButton addButton = new JButton("添加行");
		JButton deleteButton = new JButton("删除行");
		JButton check  = new JButton("查看选中行");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		deleteButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(getCurNode());
			}
		});
//		add(addButton);
//		add(deleteButton );
//		add(check);
		
		jPanel.add(addButton);
		jPanel.add(deleteButton);
//		jPanel.add(check);
		add(jPanel);
		for (int i = 0; i < 10; i++) {
//			addTestData();
		}
		setUI();
	}
	public void setUI(){
		try {
			LookAndFeel test = UIManager.getLookAndFeel();
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			UIManager.setLookAndFeel(test);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		
	}
	public void init(){
		editList.add(1);
		panel.setViewportView(table);
		for(String col:columns)
			table.addColumn(col);
		//添加右键菜单
		//添加选中事件
		this.setPreferredSize(new Dimension(500,500));
		table.addForbidden(0);
		table.addForbidden(2);
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
		com.winnie.model.Process result = new com.winnie.model.Process();
		String[] msg = table.getCurRow();
		result.setName(msg[1]);
		return result;
	}
	/**
	 * 添加测试数据
	 * 
	 * @author YuFeng   
	 * @date 2015年10月11日 下午12:57:24
	 */
	public void addTestData(){
		if(check()){
			settled.add(false);
			index++;
			table.addRow(new String[]{index+"","test","否"});
		}else{
			JOptionPane.showMessageDialog(null, "进程名称不能为空！");
		}
	}
	/**
	 * 添加新的一行
	 * @author YuFeng   
	 * @date 2015年9月24日 下午2:36:40
	 */
	public void add(){
		if(check()){
			settled.add(false);
			index++;
			table.addRow(new String[]{"进程"+index+"","","否"});
		}else{
			JOptionPane.showMessageDialog(null, "进程名称不能为空！");
		}
	}
	public void delete(){
		int row = table.getSelectedRow();
		if(row<0){
			return ;
		}
		Node node = getCurNode(row);
		parentPanel.deleteNode(node);
		table.removeRow(row);
		index--;
		for (int i = row; i < table.getRowCount(); i++) {
			table.setValueAt(i+1+"", i, 0);
		}
		settled.remove(row);
	}
	public void setSettled(int index){
		settled.set(index, true);
		table.setValueAt("是", index, 2);
	}
	/**
	 * 检测现有元素是否满足要求
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月24日 下午2:32:23
	 */
	public boolean check(){
		String[][] src =  table.getAll();
		for(String[] s :src){
			if(s[1]==null||s[1].trim().equals("")){
				return false;
			}
		}
		return true;
	}
	/**
	 * 获得表格中当前结点的信息
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月25日 下午10:10:12
	 */
	public Node getCurNode(){
		if(!check()) return null;
		Node current = null;
		int row = table.getSelectedRow();
		if(row<0){
		}else{
			if(checkSettled(row)) return null;
			return getCurNode(row);
		}
		return current;
	}
	public Node getCurNode(int index){
		if(table.checkRow(index)){
			String temp=table.getAll()[index][1];
			if(temp==null||temp.trim().equals("")){
				return null;
			}
			Node current = new Node(1, temp);
			current.setIndex(index);
			return current;
		}
		return null;
	}
	/**
	 * 设置当前结点,并更改父容器的当前结点
	 * @param panel
	 * @author YuFeng   
	 * @date 2015年9月24日 下午3:38:57
	 */
	public void setCurNode(TablePanel panel){
		try {
			panel.setCurrentItem(getCurNode());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void setTablePanel(TablePanel panel){
		this.parentPanel = panel;
	}
	public boolean checkSettled(int index){
		if(index>=0&&index<settled.size()){
			return settled.get(index);
		}
		return true;
	}
	public static void main(String[] args) {
		JFrame f =new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new ProcessPane(null));
		f.pack();
	}
	public void renew(){
		for (int i = 0; i < settled.size(); i++) {
			settled.set(i, false);
			table.setValueAt("否",i, 2);
		}
	}
	@Override
	public void onchange(TableModelEvent e) {
//		System.out.println(e.getFirstRow());
//		System.out.println(e.getColumn());
		int row = e.getFirstRow();
		if(row>=0&&editList.contains(e.getColumn())){
			parentPanel.updateNode(getCurNode(row));
		}
	}
}
