package com.winnie.interfaces;

import java.awt.Component;
import java.util.List;

import com.winnie.model.Node;

public interface MainPanel {
	/**
	 * 获得邻接矩阵
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月10日 下午3:13:39
	 */
	public int[][] getGraph();
	/**
	 * 获得节点信息
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月10日 下午3:13:56
	 */
	public List<Node> getList();
	/**
	 * 弹出对话框
	 * @param message
	 * @param type
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:37:39
	 */
	public void showDialog(String message,int type);
	/**
	 * 当前元素是否被设置
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月21日 下午10:10:41
	 */
	public boolean isSettled();
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
	 * @date 2015年10月12日 下午4:04:56
	 */
	public void updateNode(Node node);
	/**
	 * 获得Component
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月14日 上午9:10:14
	 */
	public Component getItem();
	/**
	 * 删除一个具体的节点
	 * @param aim
	 * @author YuFeng   
	 * @date 2015年10月14日 上午9:10:58
	 */
	public void remove(int aim);
	/**
	 * 获得到所有的节点信息
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月14日 上午9:11:21
	 */
	public List<Node> getNodes();
}
