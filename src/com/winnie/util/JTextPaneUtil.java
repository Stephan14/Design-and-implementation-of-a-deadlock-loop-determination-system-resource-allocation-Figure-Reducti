package com.winnie.util;

import   javax.swing.*; 
import   java.awt.*; 
import   java.awt.event.*; 
import   javax.swing.text.*; 
import   java.io.*; 

public class JTextPaneUtil {
	protected static  void  insert(JTextPane textPane, String   str,   AttributeSet   attrSet)   { 
        Document   doc   =   textPane.getDocument(); 
        str   = str  +"\n"  ; 
        try   { 
            doc.insertString(doc.getLength(),   str,   attrSet); 
        } 
        catch   (BadLocationException   e)   { 
            System.out.println( "BadLocationException:   "   +   e); 
        } 
    } 

    public  static  void   addDocs(JTextPane textPane,String   str,Color   col,boolean   bold,int   fontSize)   { 
        SimpleAttributeSet   attrSet   =   new   SimpleAttributeSet(); 
        StyleConstants.setForeground(attrSet,   col); 
        //颜色 
        if(bold==true){ 
            StyleConstants.setBold(attrSet,   true); 
        }//字体类型 
        StyleConstants.setFontSize(attrSet,   fontSize); 
        //字体大小 
        insert(textPane ,str,   attrSet); 
    } 

}
