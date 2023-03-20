package org.example.controllers;

import org.example.model.Model;
import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.ToolName;
import org.example.ui.Frame;
import org.example.ui.MainPanel;
import org.example.ui.parameters.ParameterDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ParameterController extends Controller {
    private Runnable okRunnable;
    private final ParameterDialog parameterDialog;

    public ParameterController(Frame frame, Model model) {
        super(frame, model);
        parameterDialog = frame.getParameterDialog();
    }

    @Override
    public void toolButtonClicked(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();

        ToolName toolName = ToolName.valueOf(button.getName());
        ParameterizedTool parameterizedTool = (ParameterizedTool) model.getToolMap().get(toolName);

        boolean canBeControlled = parameterizedTool.getParameters().stream().anyMatch(Parameter::isControlled);

        if (canBeControlled) {
            frame.getParameterDialog().setParameters(toolName, parameterizedTool.getParameters());
            frame.getParameterDialog().setVisible(true);
        } else {
            mainPanel.setCurrentCanvas(parameterizedTool.apply(mainPanel.getOriginalCanvas()));
            mainPanel.setAppliedCanvas(mainPanel.getCurrentCanvas());
            frame.getMainPanel().repaint();
            model.setSelectedTool(parameterizedTool);

            if (Objects.nonNull(okRunnable)) {
                okRunnable.run();
            }
        }
    }

    public void okBtnClicked(ActionEvent e) {
        ToolName toolName = ToolName.valueOf(parameterDialog.getName());
        ParameterizedTool tool = (ParameterizedTool) model.getToolMap().get(toolName);
        for (Parameter parameter : model.getParameterList()) {
            if (tool.getParameters().contains(parameter)) {
                parameter.setValue(parameterDialog.getValue(parameter.getName()));
            }
        }

        MainPanel mainPanel = frame.getMainPanel();
        BufferedImage canvas = mainPanel.getOriginalCanvas();
        mainPanel.setCurrentCanvas(tool.apply(canvas));
        mainPanel.setAppliedCanvas(mainPanel.getCurrentCanvas());
        frame.getMainPanel().repaint();

        model.setSelectedTool(tool);

        parameterDialog.dispose();
        if (Objects.nonNull(okRunnable)) {
            okRunnable.run();
        }
        //System.out.println(model.getParameterList().stream().filter(p -> p.getName().equals(ParameterName.GAUSS_FILTER_WINDOW_SIZE)).findFirst().orElse(null).getValue
    }

    public void cancelBtnClicked(ActionEvent e) {
        parameterDialog.dispose();
    }

    public void setOkRunnable(Runnable runnable) {
        okRunnable = runnable;
    }
}
