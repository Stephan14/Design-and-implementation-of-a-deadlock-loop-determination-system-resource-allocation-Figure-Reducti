package com.winnie.model;

public class Node {
	/**
	 * 节点类型，用于区分节点类别
	 */
	protected int type;
	/**
	 * 节点的名称，因为节点都会有名称
	 */
	protected String name;
	
	protected int index;
	public Node() {
		super();
	}
	public Node(int type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
