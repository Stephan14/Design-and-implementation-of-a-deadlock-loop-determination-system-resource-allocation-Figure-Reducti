package com.winnie.controller;

import java.util.List;

import com.winnie.interfaces.Controller;
import com.winnie.interfaces.MainBody;
import com.winnie.interfaces.Service;
import com.winnie.model.Node;
import com.winnie.service.ServiceImpl;
import com.winnie.view.MainBodyImpl;

public class ControllerImpl implements Controller{
	protected Service service;
	protected MainBody mainBody;
	public ControllerImpl(MainBody mainBody){
		setMainBody(mainBody);
		init();
	}
	protected void init(){
		setService(null);
//		setMainBody(null);
		mainBody.setController(this);
	}
	@Override
	public void setService(Service service) {
		if(service == null){
			this.service = new ServiceImpl(this);
		}else{
			this.service = service;
		}
	}

	@Override
	public void setMainBody(MainBody mBody) {
		if(mBody == null){
			this.mainBody = new MainBodyImpl(this);
		}else{
			this.mainBody = mBody;
		}
	}

	@Override
	public void cal(int[][] m, List<Node> src) {
		for (int i = 0; i < m.length; i++) {
			System.out.println(src.get(i).getType()+"_)___");
		}
		List<Integer> result = service.cal(m, src);
		for (int i = 0; i < result.size() ; i++) {
			int cur = result.get(i);
			deleteNode(result.get(i));
			Node n = src.get(cur);
			if(n.getType() == 1){
				mainBody.showMessage("进程"+n.getName()+" 获得所有请求资源。",Controller.C_NORMAL);
			}else{
				mainBody.showMessage("资源"+n.getName()+" 分配所有请求。", Controller.C_NORMAL);
			}
			src.remove( src.get(cur));
			for (int j = i+1; j <result.size() ; j++) {
				int temp = result.get(j);
				if(temp>cur) {
					result.set(j, temp - 1);
				}
			}
		}
	}

	@Override
	public void showMessage(String message, int type) {
		mainBody.showMessage(message,type);
	}

	@Override
	public void showDialog(String message, int type) {
		mainBody.showDialog(message,type);
	}

	/**
	 * ѡ��
	 * @param from
	 * @param to
	 */
	@Override
	public void deleteEdge(int from, int to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNode(int aim) {
		// TODO Auto-generated method stub
		mainBody.deleteNode(aim);
	}

}
