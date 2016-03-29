package com.winnie.test;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class DrawLine extends JFrame implements MouseListener,MouseMotionListener{
	private int x0=0,y0=0,x=0,y=0;
	JPanel jp=new JPanel();
	DrawLine(){
		jp.addMouseListener(this);
		jp.addMouseMotionListener(this);
		add(jp);
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.drawLine(x0, y0, x, y);
	}
	public static void main(String[] args) {
		new DrawLine();
	}
	public void mouseDragged(MouseEvent arg0) {
		x=arg0.getXOnScreen();
		y=arg0.getYOnScreen();
		repaint();
	}
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		x0=arg0.getXOnScreen();
		y0=arg0.getYOnScreen();
	}
	public void mouseReleased(MouseEvent arg0) {
		x=arg0.getXOnScreen();
		y=arg0.getYOnScreen();
		repaint();
	}
	public class Line extends JComponent{
		{
			
		}
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			g.drawString("2", 0,0);
			super.paintComponent(g);
		}
		
	}
}