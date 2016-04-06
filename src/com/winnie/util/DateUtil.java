package com.winnie.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static String getDate(){
		return format.format(new Date());
	}
	public static void main(String[] args) {
		System.out.println(getDate());
	}
}
