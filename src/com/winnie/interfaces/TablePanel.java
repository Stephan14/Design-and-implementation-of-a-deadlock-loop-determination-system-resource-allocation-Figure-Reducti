package com.winnie.interfaces;

import javax.swing.JPanel;

import com.winnie.model.Node;

public interface TablePanel {
	/**
	 * 获得当前选中元素
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月10日 下午3:17:12
	 */
	public Node getCurrentItem();
	/**
	 * 设定当前元素
	 * @param currentItem
	 * @author YuFeng   
	 * @date 2015年9月24日 下午3:37:24
	 */
	public void setCurrentItem(Node currentItem);
//	/**
//	 * 设定Node(设定为安置状态)
//	 * @param aim
//	 * @author YuFeng   
//	 * @date 2015年9月24日 下午6:16:53
//	 */
//	public void setSettled(Node node);
	/**
	 * 设置当前节点为已设置
	 * @author YuFeng   
	 * @date 2015年10月8日 下午1:59:30
	 */
	public void setSettled();
	/**
	 * 设置分配资源
	 * @param source
	 * @param num
	 * @author YuFeng   
	 * @date 2015年9月24日 下午6:17:42
	 */
	public void setUsed(Node source,int num);
	/**
	 * 获得本身
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月11日 下午5:17:04
	 */
	public JPanel getSelf();
	/**
	 * 
	 * @author YuFeng   
	 * @date 2015年10月11日 下午5:21:03
	 */
	public void renew();
	/**
	 * 删除节点，TablePanel删除节点时MainPanel同时删除相应节点
	 * @param node
	 * @author YuFeng   
	 * @date 2015年10月12日 上午9:11:46
	 */
	public void deleteNode(Node node);
	/**
	 * 更新节点信息
	 * @param node
	 * @author YuFeng   
	 * @date 2015年10月12日 下午4:11:30
	 */
	public void updateNode(Node node);
	/**
	 * 设置主界面
	 * @param mainPanel
	 * @author YuFeng   
	 * @date 2015年10月12日 上午9:25:57
	 */
	public void setMainPanel(MainPanel mainPanel);
}
