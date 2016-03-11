package com.winnie.interfaces;

import java.awt.Component;

/**
 * 控制台组件，用于显示信息
 * @author YuFeng
 * @date 2015年9月10日 下午3:18:51
 */
public interface ConsolePanel {
	public final int NORMAL = 1;
	public final int WARNING = 2;
	/**
	 * 输出信息
	 * @param message 信息内容
	 * @param type 信息类型 type 1 正常 2 警告
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:37:01
	 */
	public void showMessage(String message,int type);
	/**
	 * 获得Component组件
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月14日 上午9:13:34
	 */
	public Component getItem();
}
