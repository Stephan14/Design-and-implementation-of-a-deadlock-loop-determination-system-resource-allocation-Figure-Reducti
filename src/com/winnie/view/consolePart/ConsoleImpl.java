package com.winnie.view.consolePart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.winnie.interfaces.ConsolePanel;
import com.winnie.util.DateUtil;
import com.winnie.util.JTextPaneUtil;


/**
 * 目前难点，设置焦点在最后一行
 * @author YuFeng
 * @date 2015年9月22日 下午8:46:07
 */
public class ConsoleImpl extends JScrollPane implements ConsolePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4123900031332544253L;
	private JTextPaneUtil util = new JTextPaneUtil();
	JTextPane edit;
	protected static ConsoleImpl console;
	protected ConsoleImpl(){
		
		edit=new JTextPane();
		edit.setEditable(false);
		this.setViewportView(edit);
		edit.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				super.mouseClicked(e);
//				showMessage("message", 2);
//			}
		});
		
		setPreferredSize(new Dimension(500,200));
	}
	public static  ConsoleImpl getInstance(){
		if(console == null){
			console = new ConsoleImpl();
		}
		return console;
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ConsoleImpl());
		frame.pack();
		frame.setVisible(true);
	}
	@Override
	public void showMessage(String message, int type) {
		edit.selectAll();
		if(edit.getSelectedText() != null){
			edit.setCaretPosition(edit.getSelectedText().length());
			edit.requestFocus();
		}else{
			edit.setCaretPosition(0);
			edit.requestFocus();
			
		}
		
		message=DateUtil.getDate()+"   "+message;
		if(type==1){
			util.addDocs(edit, message, Color.BLACK, false, 14);
		}else{
			util.addDocs(edit, message, Color.RED, false, 14);
		}
		update(getGraphics());
	}
	@Override
	public Component getItem() {
		return this;
	}
}
