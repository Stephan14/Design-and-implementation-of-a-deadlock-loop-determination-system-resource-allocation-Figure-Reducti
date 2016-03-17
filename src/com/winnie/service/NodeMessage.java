package com.winnie.service;

public class NodeMessage extends Object
{	
	private int type;
	private int number;
	
	NodeMessage(int ty, int num)
	{
		type = ty;
		number = num;
	}
	
	public int getType()
	{
		return type;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	
	public static void main(String[] args)
	{
		NodeMessage node = new NodeMessage(33,2);
		System.out.println("种类："+node.getType());
		System.out.println("数量："+node.getNumber());
	}
}
