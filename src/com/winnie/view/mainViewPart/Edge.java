package com.winnie.view.mainViewPart;

/**
	 * 边
	 * @author YuFeng
	 * @date 2015年9月21日 下午9:51:24
	 */
	public class Edge{
		public GNode to;
//		public Edge next=null;
		public int num;//申请或分配的数量
		public Edge(GNode to, int num) {
			super();
			this.to = to;
			this.num = num;
		}	
	}