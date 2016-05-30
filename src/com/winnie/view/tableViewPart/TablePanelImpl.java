package com.winnie.view.tableViewPart;


import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.winnie.interfaces.MainBody;
import com.winnie.interfaces.MainPanel;
import com.winnie.interfaces.TablePanel;
import com.winnie.model.Node;

/**
 * 难点:
 * 	1.右键菜单
 * 	2.设置某一列不允许修改
 * 	
 * @author YuFeng
 * @date 2015年9月22日 下午9:17:10
 */
public class TablePanelImpl extends JPanel implements TablePanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5530214898984838644L;
	
	protected ProcessPane processPane = new ProcessPane(this);
	protected SourcePane sourcePane = new SourcePane(this);
	protected MainPanel mainPanel = null;
	protected Node currentItem =null;
	public TablePanelImpl() {
		processPane.setTablePanel(this);
		sourcePane.setTablePanel(this);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(processPane);
		add(sourcePane);
//		this.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println(getCurrentItem());
//				setSettled();
//			}
//		});
	}
	public TablePanelImpl(MainPanel mainPanel){
		this();
		setMainPanel(mainPanel);
	}
	
	@Override
	public Node getCurrentItem() {
		return currentItem;
	}
	/**
	 * 设定当前元素
	 * @param currentItem
	 * @author YuFeng   
	 * @date 2015年9月24日 下午3:37:24
	 */
	public void setCurrentItem(Node currentItem) {
		this.currentItem = currentItem;
	}
	/**
	 * 设定Node
	 * @param aim
	 * @author YuFeng   
	 * @date 2015年9月24日 下午6:16:53
	 */
	public void setSettled(){
		if(currentItem==null)return ;
		if(currentItem.getType()==1){
			processPane.setSettled(currentItem.getIndex());
		}else{
			sourcePane.setSettled(currentItem.getIndex());
		}
		currentItem = null;
	}
	/**
	 * 设置分配资源
	 * @param source
	 * @param num
	 * @author YuFeng   
	 * @date 2015年9月24日 下午6:17:42
	 */
	public void setUsed(Node source,int num){
		if(source==null||source.getType()!=2){
			System.out.println("something wrong");
			return ;
		}
		sourcePane.setUsed(source.getIndex(), num);
	}
	/**
	 * 删除节点
	 * @param node
	 * @author YuFeng   
	 * @date 2015年9月24日 下午6:18:40
	 */
	public void releaseNode(Node node){
		
	}
	public static void main(String[] args) {
		JFrame frame  = new JFrame();
		frame.add(new TablePanelImpl(null));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	public void renew(){
		processPane.renew();
		sourcePane.renew();
	}

	@Override
	public JPanel getSelf() {
		return this;
	}
	
	public void deleteNode(Node node){
		if (mainPanel!=null) {
			mainPanel.deleteNode(node);
		}
	}
	public void updateNode(Node node){
		if (mainPanel!=null) {
			mainPanel.updateNode(node);
		}
	}
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
}
