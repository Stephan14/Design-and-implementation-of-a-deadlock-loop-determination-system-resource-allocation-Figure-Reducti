package com.winnie.interfaces;



import java.util.List;
import javax.swing.JOptionPane;
import com.winnie.model.Node;
/**
 * 控制层，用于调节视图层与模型层信息交互
 * @author YuFeng
 * @date 2015年9月10日 下午2:45:02
 */
public interface Controller {
	public final int INFO = JOptionPane.INFORMATION_MESSAGE;
	public final int ERROE = JOptionPane.ERROR_MESSAGE;
	public final int C_NORMAL = ConsolePanel.NORMAL;
	public final int C_WARNING = ConsolePanel.WARNING;
	/**
	 * 设置Service
	 * @param service
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:51:38
	 */
	public void setService(Service service);
	/**
	 * 设置视图层
	 * @param mBody
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:54:57
	 */
	public void setMainBody(MainBody mBody);
	
	/**
	 * 处理邻接矩阵
	 * @param m 邻接矩阵
	 * @param src 节点
	 * @author YuFeng   
	 * @date 2015年9月10日 下午2:35:26
	 */
	public void cal(int[][] m,List<Node> src);
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
	 * 删除边(选做)
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
}
