package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static final Dimension MIN_SIZE = new Dimension(640, 480);
    private static final Dimension PREF_SIZE= new Dimension(800, 600);

    public Frame(String title) {
        super(title);

        MainPanel mainPanel = new MainPanel();
        ToolBar toolBar = new ToolBar();

        Container container = getContentPane();
        container.add(toolBar, BorderLayout.NORTH);
        container.add(mainPanel);

        setMinimumSize(MIN_SIZE);
        setPreferredSize(PREF_SIZE);
        setVisible(true);
        pack();
    }
}
