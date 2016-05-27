package com.winnie.view.menuutil;

import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;


public class MyPopmenu implements PopupMenuItem{
	protected PopupMenu menu = new PopupMenu();
	protected Component component = null;
	
	public  MyPopmenu(Component component){
		this.component=component;
		component.add(menu);
	}
	@Override
	public void action(MouseEvent e) {
		int mods=e.getModifiers();
		//鼠标右键
		if((mods&InputEvent.BUTTON3_MASK)!=0){
		//弹出菜单
			menu.show(component,e.getX(),e.getY());
		}
	}
	@Override
	public void addItem(String name, final ItemPerform perform) {
		MenuItem item = new MenuItem();
		item.setLabel(name);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perform.perform(e);
			}
		});
		menu.add(item);
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	@Override
	public PopupMenu getMenu() {
		return menu;
	}
	
}
