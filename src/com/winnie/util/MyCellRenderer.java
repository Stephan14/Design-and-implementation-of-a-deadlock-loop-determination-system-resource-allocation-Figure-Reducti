package com.winnie.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -7615274653694206259L;

	private Color selectionColor = new Color( 	0, 197 ,205);//行选择颜色
	private Color evenRowColor = new Color(113,242,241);//奇数行颜色
	private Color oddRowColor = new Color(255,255,255);//偶数行颜色



    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){

    Component cell = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);  

    this.setColor(cell, table, isSelected, hasFocus, row, column);

        return cell;

    }

    /*
     * 设置颜色
     */

    private void setColor(Component component,JTable table,boolean isSelected,boolean hasFocus,int row,int column){

    if(isSelected){

        component.setBackground(selectionColor);

        setBorder(null);//去掉边

    }else{

        if(row%2 == 0){

           component.setBackground(evenRowColor);  

        }else{

           component.setBackground(oddRowColor);

        }

    }

    }

    {
    	setHorizontalAlignment(SwingConstants.CENTER);
    }

}
