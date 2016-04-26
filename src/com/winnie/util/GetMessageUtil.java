package com.winnie.util;

import javax.swing.JOptionPane;

public class GetMessageUtil {
	/**
	 * 获得一个整数
	 * @param message 提示信息
	 * @return
	 * @throws Exception
	 * @author YuFeng   
	 * @date 2015年9月28日 上午10:27:30
	 */
	public static int getInteger(String message) throws Exception{
		String num;
		do{
			num = JOptionPane.showInputDialog(message);
			if(num == null){
				throw new Exception("输入内容为空！");
			}
			if(CheckNumber.isInteger(num)){
				return Integer.parseInt(num);
			}else{
				JOptionPane.showMessageDialog(null, "请输入数字");
			}
		}while(true);
	}
	/**
	 * 获得一个有范围的整数
	 * @param min 下界
	 * @param max 上界
	 * @param message 提示信息
	 * @return
	 * @throws Exception
	 * @author YuFeng   
	 * @date 2015年9月28日 上午10:27:42
	 */
	public static int getInteger(int min,int max,String message) throws Exception{
		if(min>max){
			int temp = max;
			max = min;
			min = temp;
		}
		while(true){
			int aim = getInteger(message);
			if(aim>=min && aim <=max){
				return aim;
			}
			JOptionPane.showMessageDialog(null, "输入数字超出范围！");
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getInteger(1,1,"请输入1"));
	}
}
