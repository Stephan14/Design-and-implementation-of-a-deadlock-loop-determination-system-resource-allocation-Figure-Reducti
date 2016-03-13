package com.winnie.interfaces;

import java.util.List;

import com.winnie.model.Node;

/**
 * 底层运算
 * @author YuFeng
 * @date 2015年9月10日 下午2:45:46
 */
public interface Service {
	/**
	 * 绑定Controller
	 * @param con
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:46:25
	 */
	public void setController(Controller con);
	/**
	 * 处理邻接矩阵
	 * @param m 邻接矩阵
	 * @param src 节点
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:35:26
	 */
	public List<Integer> cal(int[][] m,List<Node> src);
}
