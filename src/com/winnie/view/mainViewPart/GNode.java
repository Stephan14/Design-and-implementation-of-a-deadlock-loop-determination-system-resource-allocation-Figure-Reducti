package com.winnie.view.mainViewPart;

import java.util.ArrayList;
import java.util.List;

import com.winnie.model.Node;
import com.winnie.view.Item;

/**
 * 用于记录面板中具体的节点
 * @author YuFeng
 * @date 2015年9月26日 下午3:28:27
 */
public class GNode extends Item{
	public List<Edge> edges = new ArrayList<Edge>();
	public int newIndex = 0;
	public GNode(Node node) {
		super(node);
	}
	/**
	 * 添加新边
	 * @param edge
	 * @author YuFeng   
	 * @date 2015年10月5日 下午5:01:37
	 */
	public void add(Edge edge){
		if(((GNode)edge.to).edges.contains(this)){
			return ;
		}
		edges.add(edge);
	}
	public boolean contains(GNode to){
		for (Edge e : edges) {
			if(e.to.equals(to)){
				return true;
			}
		}
		return false; 
	} 
	public int getNum(GNode to){
		for (Edge e : edges) {
			if(e.to.equals(to)){
				return e.num;
			}
		}
		return -1;
	}
	public void update(GNode to , int nums){
		
		for (Edge e : edges) {
			if(e.to.equals(to)){
				e.num+=nums;
				if(e.num==0){
					break;
				}
				return ;
			}
		}
		remove(to);
	}
	public void remove(GNode to){
		Edge aim = null;
		for (Edge e : edges) {
			if(e.to.equals(to)){
				aim = e;
				break;
			}
		}
		edges.remove(aim);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GNode){
			return node.equals(((GNode)obj).node);
		}else{
			return false;
		}
		
	}
	
}