package com.winnie.test;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.AnnotationUtils;

public class JustTest extends AnnotationUtils {
	public static void main(String[] args) {
		String num = JOptionPane.showInputDialog("asdf");
		System.out.println(num);
	}
}
