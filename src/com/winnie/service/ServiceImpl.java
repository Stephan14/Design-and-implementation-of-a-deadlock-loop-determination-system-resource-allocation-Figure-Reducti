package com.winnie.service;

import java.util.ArrayList;
import java.util.List;

import com.winnie.interfaces.Controller;
import com.winnie.interfaces.Service;
import com.winnie.model.Node;
import com.winnie.model.Source;
/**
 * Service的实现类之一
 * @author YuFeng
 * @date 2015年10月10日 下午8:34:21
 */
public class ServiceImpl implements Service{
	protected Controller controller = null;
	public ServiceImpl(Controller con){
		setController(con);
	}
	@Override
	public void setController(Controller con) {
		this.controller =con;
	}

	@Override
	public List<Integer> cal(int[][] m, List<Node> src) {
		if(m.length == 0){
			return new ArrayList<Integer>();
		}
		TopologicalSort cal = new TopologicalSort(controller);
		cal.copyMap(m);
		cal.topologicalSort(toMessages(src));
		cal.panDuanHuiLu();
		return cal.getTopologicalsequence();
	}
	/**
	 * 将List<node>转化成本List<NodeMessage>
	 * @param src
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月10日 下午8:04:33
	 */
	public List<NodeMessage> toMessages(List<Node> src){
		List<NodeMessage> result = new ArrayList<NodeMessage>();
		for (Node n : src) {
			result.add(node2NodeMessage(n));
		}
		return result;
	}
	/**
	 * Node转化成NodeMessage
	 * @param n
	 * @return
	 * @author YuFeng   
	 * @date 2015年10月10日 下午8:33:17
	 */
	public NodeMessage node2NodeMessage(Node n){
		if(n.getType() == 1 ){
			return new NodeMessage(1, 1);
		}else{
			return new NodeMessage(2, ((Source)n).getNums()) ;
		}
	}
}
