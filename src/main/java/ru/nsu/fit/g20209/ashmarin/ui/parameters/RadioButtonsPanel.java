package ru.nsu.fit.g20209.ashmarin.ui.parameters;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RadioButtonsPanel extends ParameterPanel {
    private final JRadioButton btn1 = new JRadioButton("Nearest neighbour");
    private final JRadioButton btn2 = new JRadioButton("Bilinear");
    private final JRadioButton btn3 = new JRadioButton("Bicubic");
    private final ButtonGroup btnGroup = new ButtonGroup();

    public RadioButtonsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        /*Box box1 = new Box(BoxLayout.X_AXIS);
        Box box2 = new Box(BoxLayout.X_AXIS);
        Box box3 = new Box(BoxLayout.X_AXIS);*/

        add(btn1);
        add(btn2);
        add(btn3);

        btnGroup.add(btn1);
        btnGroup.add(btn2);
        btnGroup.add(btn3);

        btn1.setSelected(true);
        textField = new JTextField("1");

        btn1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText("1");
            }
        });
        btn2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText("2");
            }
        });
        btn3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText("3");
            }
        });
    }
}
