package com.winnie.model;

public class Source extends Node {
	{
		type=2;
	}
	protected int nums;
	protected int used;
	
	public Source(){
		
	}
	public Source(String name,int nums){
		this.name=name;
		this.nums=nums;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public int getUsed() {
		return used;
	}
	public int getRemains() {
		return nums-used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	@Override
	public String toString() {
		return "资源"+name;
	}
	
}
