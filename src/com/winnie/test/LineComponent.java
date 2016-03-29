package com.winnie.test;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class LineComponent extends JComponent {
	/**起点坐标*/
	protected int x0,y0;
	/**终点坐标*/
	protected int x1,y1;
	
	protected String msg;
	public LineComponent(int x0, int y0, int x1, int y1) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	@Override
	public boolean contains(int x, int y) {
		int tempy =calY(x);
		if(x<Math.min(x0, x1)&&x>Math.max(x0, x1)){
			return false;
		}
		return y>tempy-5&&y<tempy+5;
	}
	public int calY(int x){
		return (int)((double)(y1-y0)/(x1-x0)*(x-x0)+y0);
	}
	/**
	 * 当鼠标在组建上方时，对组件进行加粗
	 * @author YuFeng   
	 * @date 2015年9月22日 上午9:21:03
	 */
	protected void paintMouseOn(){
		Graphics g = getGraphics();
		g.drawLine(getX()+1,getY(), getX()+ 50+1,getY()+50);
		g.drawLine(getX()+0-1,getY()+0, getX()+ 50-1,getY()+50);
	}
	@Override
	protected void paintComponent(Graphics g) {

		Graphics g2 = (Graphics2D)g;
		g2.drawString(msg,(x0+x1)/2,(y1+y0)/2);
		g2.drawLine(x0,y0,x1,y1);
		
		super.paintComponent(g);
	}
	public static void main(String[] args) {
		LineComponent line =new LineComponent(1, 1, 4, 4);
		System.out.println(line.calY(3));
	}
	public int getX0() {
		return x0;
	}
	public void setX0(int x0) {
		this.x0 = x0;
	}
	public int getY0() {
		return y0;
	}
	public void setY0(int y0) {
		this.y0 = y0;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
