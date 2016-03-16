package com.winnie.model;

public class Process extends Node{
	{
		type=1;
	}
	public Process(){
		
	}
	public Process(String name){
		this.name=name;
	}
	@Override
	public String toString() {
		return "进程 "+name  ;
	}
	
}
