package com.winnie.interfaces;


public interface MainBody {
	/**
	 * 绑定Controller
	 * @param con
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:46:25
	 */
	public void setController(Controller con);
	/**
	 * 输出信息
	 * @param message 信息内容
	 * @param type 信息类型
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:37:01
	 */
	public void showMessage(String message,int type);
	/**
	 * 弹出对话框
	 * @param message
	 * @param type
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:37:39
	 */
	public void showDialog(String message,int type);
	/**
	 * 删除边
	 * @param from
	 * @param to
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:41:22
	 */
	public void deleteEdge(int from,int to);
	/**
	 * 删除节点
	 * @param aim
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:41:32
	 */
	public void deleteNode(int aim);
	/**
	 * 执行约简过程
	 * @author YuFeng   
	 * @date 2015年10月10日 下午10:07:57
	 */
	public void doIt();
	/**
	 * 返回初始状态
	 * @author YuFeng   
	 * @date 2015年10月11日 下午5:15:12
	 */
	public void renew();
}
