package com.winnie.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.winnie.controller.ControllerImpl;
import com.winnie.interfaces.ConsolePanel;
import com.winnie.interfaces.Controller;
import com.winnie.interfaces.MainBody;
import com.winnie.interfaces.MainPanel;
import com.winnie.interfaces.TablePanel;
import com.winnie.model.Node;
import com.winnie.view.consolePart.ConsoleImpl;
import com.winnie.view.mainViewPart.MainPanelImpl;
import com.winnie.view.tableViewPart.TablePanelImpl;

public class MainBodyImpl extends JFrame implements MainBody{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6675963125210007718L;
	//View层
	protected TablePanel tablePane;
	protected MainPanel mainPanelImpl;
	protected ConsolePanel consoleImpl;
	//控制层
	protected  Controller controller;	
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public MainBodyImpl(Controller controller){
		setController(controller);
		init();
	}
	protected void init(){
		tablePane = new TablePanelImpl();
		add(tablePane.getSelf(),BorderLayout.WEST);

		consoleImpl = ConsoleImpl.getInstance();
		add(consoleImpl.getItem(), BorderLayout.SOUTH);

		mainPanelImpl = new MainPanelImpl(900, 800,tablePane,this);
		add(mainPanelImpl.getItem(), BorderLayout.CENTER);
		
		tablePane.setMainPanel(mainPanelImpl);
		pack();
	}
	
	public static void main(String[] args) {
		MainBodyImpl test= new MainBodyImpl(null);
	}

	/**
	 * 绑定Controller
	 *
	 * @param con
	 * @author YuFeng
	 * @date 2015年9月10日 下午2:46:25
	 */
	@Override
	public void setController(Controller con) {
		if(con == null){
			this.controller = new ControllerImpl(this);
		}else {
			this.controller = con;
		}

	}

	/**
	 * 输出信息
	 *
	 * @param message 信息内容
	 * @param type    信息类型
	 * @author YuFeng
	 * @date 2015年9月10日 下午2:37:01
	 */
	@Override
	public void showMessage(String message, int type) {
		consoleImpl.showMessage(message, type);
	}

	/**
	 * 弹出对话框
	 *
	 * @param message
	 * @param type
	 * @author YuFeng
	 * @date 2015年9月10日 下午2:37:39
	 */
	@Override
	public void showDialog(String message, int type) {
		switch (type) {
		case Controller.INFO:
			JOptionPane.showMessageDialog(null, message,"提示：",type);
			break;
		case Controller.ERROE:
			JOptionPane.showMessageDialog(null, message,"警告",type);
		default:
			break;
		}
	}

	/**
	 * 删除边暂时不做
	 *
	 * @param from
	 * @param to
	 * @author YuFeng
	 * @date 2015年9月10日 下午2:41:22
	 */
	@Override
	public void deleteEdge(int from, int to) {

	}

	/**
	 * 删除节点
	 *
	 * @param aim
	 * @author YuFeng
	 * @date 2015年9月10日 下午2:41:32
	 */
	@Override
	public void deleteNode(int aim) {
		mainPanelImpl.remove(aim);
	}
	public void deleteNode(Node node) {
		mainPanelImpl.deleteNode(node);
	};
	@Override
	public void doIt() {
		// TODO Auto-generated method stub
		controller.cal(mainPanelImpl.getGraph(), mainPanelImpl.getNodes());
	}
	@Override
	public void renew() {
		tablePane.renew();
		consoleImpl.showMessage("重新开始\n", Controller.C_NORMAL);
	}
}
