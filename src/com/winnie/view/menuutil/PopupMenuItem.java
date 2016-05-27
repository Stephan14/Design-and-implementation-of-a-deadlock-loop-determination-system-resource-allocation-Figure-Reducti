package com.winnie.view.menuutil;

import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.MouseEvent;
/**
 * 右键菜单
 * @author YuFeng
 * @date 2015年9月23日 下午7:52:34
 */
public interface PopupMenuItem {
	public void action(MouseEvent e);
	public void addItem(String name,ItemPerform perform);
	public void setComponent(Component component);
	public PopupMenu getMenu();
}	
