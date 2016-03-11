package com.winnie.exceptions;

public class CanNotApplyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6211314620731451193L;
	public CanNotApplyException(){
		super("默认值不满足要求");
	}
}
