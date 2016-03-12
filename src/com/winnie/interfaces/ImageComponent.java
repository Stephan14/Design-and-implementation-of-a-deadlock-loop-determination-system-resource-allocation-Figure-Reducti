package com.winnie.interfaces;



import javax.swing.ImageIcon;
import javax.swing.JComponent;
/**
 * 资源、线程的组件
 * @author YuFeng
 * @date 2015年8月31日 下午10:00:19
 */
public interface ImageComponent  {
	/**
	 * 设置文字
	 * @param name
	 * @author YuFeng   
	 * @date 2015年8月31日 下午9:58:12
	 */
	public void setContent(String name);
	/**
	 * 设置图像
	 * @param imag
	 * @author YuFeng   
	 * @date 2015年8月31日 下午10:01:11
	 */
	public void setImage(ImageIcon imag);
	/**
	 * 获得组件
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月1日 上午8:21:04
	 */
	public JComponent getItem();
	/**
	 * 在 (x,y)处绘制组件
	 * @param x
	 * @param y
	 * @param alpha 透明度
	 * @author YuFeng   
	 * @date 2015年9月24日 下午10:09:57
	 */
	public void paint(int x,int y,float alpha);
	/**
	 * 设置组件尺寸
	 * @param width
	 * @param height
	 * @author YuFeng   
	 * @date 2015年9月24日 下午10:10:41
	 */
	public void setSize(int width , int height);
}
