package org.example.ui;

import org.example.tools.ToolName;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    private final static Dimension MAX_SIZE = new Dimension(35, 35);
    private final static Dimension ICON_SIZE = new Dimension(30, 30);
    public ToolButton(ToolName toolName) {
        super();

        switch (toolName) {
            case GAMMA_CORRECTOR -> System.out.println("sda");
            // load icons here
            default -> System.out.println("default");
        }

        setMaximumSize(MAX_SIZE);
    }
}
