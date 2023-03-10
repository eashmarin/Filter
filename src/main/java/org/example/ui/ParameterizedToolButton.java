package org.example.ui;

import org.example.parameters.Parameter;
import org.example.tools.ParameterizedTool;
import org.example.tools.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ParameterizedToolButton extends ToolButton {
    public ParameterizedToolButton(ParameterizedTool parameterizedTool) {
        super(parameterizedTool.getToolName());

        addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParameterDialog.showUp(parameterizedTool.getParameters());
            }
        });
    }
}
