package com.winnie.view.menuutil;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Feng on 2015/10/10.
 */
public class Sample extends JFrame {
    public Sample(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,500);

        final MyPopmenu m = new MyPopmenu(getContentPane());
        m.addItem("Hello", new ItemPerform() {
            @Override
            public void perform(ActionEvent e) {
                System.out.println("Hello");
            }
        });
        m.addItem("World", new ItemPerform() {
            @Override
            public void perform(ActionEvent e) {
                System.out.println("World");
            }
        });
        getContentPane().add(m.getMenu());
        getContentPane().addMouseListener(new MouseInputAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                m.action(e);
            }
        });
    }

    public static void main(String[] args) {
        Sample s = new Sample();
    }
}
