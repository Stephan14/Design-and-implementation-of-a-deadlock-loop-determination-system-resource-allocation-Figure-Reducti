package com.winnie.service;

import java.util.*;

import com.winnie.interfaces.Controller;

public class TopologicalSort
{
	//图
	private int map[][];
	//记录结点的出度
	private int count[];
	//生成的拓扑序列
	private ArrayList<Integer> topologicalsequence; 
	//记录线程是否满足条件
	private boolean isfree[];
	//复制图
	protected Controller con = null;
	public TopologicalSort(Controller con){
		this.con =con;
	}
	
	public TopologicalSort(){
		
	}
	void copyMap(int sameMap[][]) 
	{
		map = new int[sameMap.length][sameMap[0].length];
		count = new int[map.length];
		isfree = new boolean[map.length];
		topologicalsequence = new ArrayList<Integer>();
		
		for(int i = 0; i < sameMap.length; i++)
		{
			for(int j = 0; j < sameMap[i].length; j++)
			{
				map[i][j] = sameMap[i][j];
			}
		}
	}
	//获得图的行数
	public int getRow()
	{
		return map.length;
	}
	
	//获得图的列数
	public   int getColumn()
	{
		return map[0].length;
	}
	//获得count数组
	public void getCount()
	{
		for(int index = 0; index < count.length; index++)
		{
			System.out.print(count[index]+" ");
			System.out.println(" ");
		}
	}
	public int getSumofColumn(int column)
	{
		int sum = 0;
		
		for(int index = 0; index < map.length; index++)
		{
			sum += map[index][column];
		}
		
		return sum;
	}
	
	private void panduanProcess(List<NodeMessage> message){
		for(int i = 0; i < message.size(); ++i){
			isfree[i] = true;
			if(count[i] > 0 && message.get(i).getType() == 1){
				for(int j = 0; j < message.size(); ++j){
					if((map[i][j] > 0 && (message.get(j).getNumber()-count[j] < map[i][j]))){
						isfree[i] = false;
					}
				}
			}else if(count[i]!=0){
				isfree[i] = false;
			}
			System.out.print(isfree[i]+" ");
		}
		System.out.println("");
	}
	//拓扑排序
	public void topologicalSort(List<NodeMessage> message)
	{
		//计算每个结点的出度
				for(int rindex = 0; rindex < getRow(); rindex++)
				{
					for(int cindex = 0; cindex < getColumn(); cindex++)
					{
						count[rindex] += map[rindex][cindex];
					}
					//System.out.println("count"+rindex+":"+count[rindex]);
				}
				panduanProcess(message);
				//删除符合条件的线程和资源
				for(int index = 0; index < count.length; index++)
				{
					
					if(count[index] == -1)
						continue;
					else if((isfree[index] == true && message.get(index).getType() == 1) ||
							(count[index] + getSumofColumn(index) <= message.get(index).getNumber() && message.get(index).getType() == 2))
					{
						topologicalsequence.add(index);
						//不用对线程结点进行处理但要对资源结点进行处理
						if(message.get(index).getType() == 2)
						{
							//将资源所在的行置为0
							for(int i = 0; i < map.length; i++)
							{
								if(map[index][i] != 0)
								{
									map[index][i] = 0;
								}
							}
							//将资源所在的列减去值
							for(int i = 0; i < map.length; i++)
							{
								count[i] -= map[i][index] ;
								map[i][index] = 0; 
							}
						}else if(message.get(index).getType() == 1){//进程
							for(int i = 0; i < map.length; i++)
							{
								if(map[i][index] != 0)
								{
									count[i] -=map[i][index];
									map[i][index] = 0;
								}
							}
							
							for(int i = 0; i < map.length; i++){
								if (map[index][i] != 0) {
									map[index][i] = 0;
								}
							}
						}
						
						count[index] = -1;
						index = -1;
						panduanProcess(message);
					}
				}
				System.out.println(topologicalsequence);
	}
	
	//判断回路
	public void panDuanHuiLu()
	{
		if (topologicalsequence.size() != map.length)
		{
			System.out.println("有回路");
			con.showMessage("有回路",Controller.C_WARNING);
			con.showDialog("有回路", Controller.ERROE);
		}
		else if (topologicalsequence.size() == map.length)
		{
			con.showMessage("没有回路",Controller.C_NORMAL);
			con.showDialog("没有回路", Controller.INFO);
			System.out.println("没有回路");
		}
	}
	public ArrayList<Integer> getTopologicalsequence() {
		return topologicalsequence;
	}
	
	public static void main(String[] args) 
	{
		int juzhen[][] = { {0,0,0,0,0,0,0,0,1,0,0,0,0}, {0,0,0,0,0,0,0,0,0,1,0,0,0},{0,0,0,0,0,0,0,0,1,0,0,0,0},{0,0,0,0,0,0,0,0,1,1,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,1,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0},{1,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,1,0,0,0,0,0,0,0,0},{0,0,0,1,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,1,0,0,0,0,0,0},{0,0,0,0,0,1,0,0,0,0,0,0,0}};
		TopologicalSort sort = new TopologicalSort();
		ArrayList<NodeMessage> message = new ArrayList<NodeMessage>();
		
		sort.copyMap(juzhen);
		NodeMessage tempNode1 = new NodeMessage(1,1);
		message.add(tempNode1);
		NodeMessage tempNode2 = new NodeMessage(1,1);
		message.add(tempNode2);
		NodeMessage tempNode3 = new NodeMessage(1,1);
		message.add(tempNode3);
		NodeMessage tempNode4 = new NodeMessage(1,1);
		message.add(tempNode4);
		NodeMessage tempNode5 = new NodeMessage(1,1);
		message.add(tempNode5);
		NodeMessage tempNode6 = new NodeMessage(1,1);
		message.add(tempNode6);
		NodeMessage tempNode7 = new NodeMessage(1,1);
		message.add(tempNode7);
		
		NodeMessage tempNode8 = new NodeMessage(2,1);
		message.add(tempNode8);
		NodeMessage tempNode9 = new NodeMessage(2,1);
		message.add(tempNode9);
		NodeMessage tempNode10 = new NodeMessage(2,2);
		message.add(tempNode10);
		NodeMessage tempNode11 = new NodeMessage(2,1);
		message.add(tempNode11);
		NodeMessage tempNode12 = new NodeMessage(2,1);
		message.add(tempNode12);
		NodeMessage tempNode13 = new NodeMessage(2,1);
		message.add(tempNode13);
		sort.topologicalSort(message);
		sort.getCount();
		sort.panDuanHuiLu();
		System.out.println("列数："+sort.getColumn());
		System.out.println("行数："+sort.getRow());
		
	}  
	
}
