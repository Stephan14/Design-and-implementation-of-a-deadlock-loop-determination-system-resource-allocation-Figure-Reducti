package com.winnie.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.winnie.interfaces.ImageComponent;
import com.winnie.model.Node;
import com.winnie.view.menuutil.ItemPerform;
import com.winnie.view.menuutil.MyPopmenu;

public class ImageLabel extends JComponent implements ImageComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1534384662275774447L;
	/**
	 * 背景图片
	 */
	//protected ImageIcon imag=new ImageIcon("/Users/md101/Documents/MATLAB/improve_SIFT_Test/pictures/feiji/8cb1cb1349540923db22c1a29658d109b2de49a6.jpg");
	protected ImageIcon imag;
	/**
	 * 图片上的文字
	 */
	protected String text="H";
	/**
	 * 默认组件的宽度
	 */
	protected  int width=50;
	/**
	 * 默认组件的长度
	 */
	protected int height=50;
	/**
	 * 组件透明度
	 */
	protected float alpha=1;

	protected int x;
	
	protected int y;
	public ImageLabel(){
//		createPopmenu();
	}
	
    protected  MyPopmenu popmenu = null;
	
    protected Color currentColor = Color.YELLOW;
    
	public void createPopmenu(){
		popmenu = new MyPopmenu(this.getItem());
		popmenu.addItem("Red", new ItemPerform() {
			
			@Override
			public void perform(ActionEvent e) {
				currentColor = Color.RED;
				repaint();	
			}
		});
		
		popmenu.addItem("Green", new ItemPerform() {
			
			@Override
			public void perform(ActionEvent e) {
				currentColor = Color.GREEN;
				repaint();
			}
		});
		
		popmenu.addItem("Blue", new ItemPerform() {
			
			@Override
			public void perform(ActionEvent e) {
				currentColor = Color.BLUE;
				repaint();
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseClicked(MouseEvent e) {
	                super.mouseClicked(e);
	                popmenu.action(e);
	         }
		});
	}
	/**
	 * 执行构造函数时执行的方法块，暂时没用到
	 */
	{
		//设置ToolTipText后 frame 的click事件失效
//		setToolTipText(text+",可拖动重新定位");
		setBounds(200, 200, 500, 500);
	}
	/**
	 * 绘制组件
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)(g);
		Composite composite = g2.getComposite();//备份合成模式
		// 设置绘图使用透明合成规则
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha)); 
		
		super.paint(g);
		//resizeImage(height, width);
		//imag.paintIcon(this, g, 0, 0);
		drawString((Graphics2D)(g));
		g2.setComposite(composite);
	}
	/**
	 * 重新设置图片的大小-
	 * @param length
	 * @param width
	 * @author YuFeng   
	 * @date 2015年9月1日 下午7:08:18
	 */
	public void resizeImage(int length,int width){
		Image img=imag.getImage();
		img = img.getScaledInstance(200, 100, Image.SCALE_DEFAULT); 
		imag = new ImageIcon(img);
	}
	/**
	 * 向图片上写文字
	 * @param g2
	 * @author YuFeng   
	 * @date 2015年9月1日 下午3:53:30
	 */
	public void drawString(Graphics2D g2){
		
		int locationx, locationy; 
		Font src = g2.getFont();
		Color color = g2.getColor();
		g2.setColor(Color.black);
		Font nFont = new Font("Times New Roman", Font.BOLD, 14);//设置新的字体
		g2.setFont(nFont);
		//设置位置
		if(text.length() <= 7){
			locationx = 20-(text.length()*3);
			locationy = 28;
			g2.drawString(text, locationx, locationy);
		}else{
			
			locationx = 5;
			locationy = 20;		
			g2.drawString(text.substring(0, 6), locationx, locationy);

			
			locationx = 15-text.substring(6).length()*3;
			locationy = 32;
			g2.drawString(text.substring(6), locationx, locationy);
		}
		g2.setFont(src);
		g2.setColor(color);
	}
	/**
	 * 设置组件大小
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(500,500);
	}
	@Override
	public void setContent(String name) {
		text=name;
		repaint();
	}

	@Override
	public void setImage(ImageIcon imag) {
		this.imag=imag;
		repaint();
	}
	@Override
	public JComponent getItem() {
		return this;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setLayout(null);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		ImageLabel item=new ImageLabel();
		Item item =new Item(new Node(1, "sdfe"));
		j.add(item.getComponent());
//		item.setSize(50,50);
		item.paint(100, 50, (float) 0.5) ;
		j.pack();
		j.setVisible(true);
	}
	@Override
	public void paint(int x, int y, float alpha) {
		this.x = x;
		this.y = y;
		setBounds(x, y, width, height);
		this.alpha = alpha;
		repaint();
	}
	@Override
	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		this.width = width;
		this.height = height;
	}
	
}
