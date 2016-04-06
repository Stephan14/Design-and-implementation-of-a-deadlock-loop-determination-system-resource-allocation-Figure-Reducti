package com.winnie.util;

import org.apache.commons.lang3.StringUtils;

public class CheckNumber {
	/**
	 * 判断是否为整数
	 * @param aim
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 上午9:08:51
	 */
	public static boolean isInteger(String aim){
		aim = aim.trim();
		if(aim.startsWith("-")){
			return StringUtils.isNumeric(aim.substring(1));
		}
		return StringUtils.isNumeric(aim);
	}
	/**
	 * 判断目标串是否为浮点数
	 * @param aim
	 * @return
	 * @author YuFeng   
	 * @date 2015年9月17日 上午9:09:36
	 */
	public static boolean isFloat(String aim){

		String[] parts=aim.split("\\.");
		if(parts.length>2){
			return false;
		}else{
			boolean flag =true;
			for (int i = 0; i < parts.length; i++) {
				flag=flag&&isInteger(parts[i]);
			}
			return flag;
		}
	}
	public static void main(String[] args) {
		
		System.out.println(isFloat("-11.11 "));
		System.out.println(Integer.parseInt("-1"));
		//System.out.println(isFloat("11"));
	}
}
