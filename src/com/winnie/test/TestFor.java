package com.winnie.test;

import java.util.ArrayList;
import java.util.List;

public class TestFor {
	public static void main(String[] args) {
		List<String> s = new ArrayList<String>();
		s.add("asdf");
		s.add("asdf");
		s.add("asdf");
		s.add("b");
		s.add("asdf");
		s.add("asdf");
		s.add("asdf");
		s.add("c");
		for(String temp: s){
			if(s.equals("b"))break;
		}
		s.remove(null);
		for (String temp: s) {
			System.out.println(temp);
		}
	}
}	
