package org.example.controllers;

import org.example.model.Model;
import org.example.model.parameters.Parameter;
import org.example.model.parameters.ParameterName;
import org.example.model.tools.Tool;
import org.example.model.tools.ToolName;
import org.example.model.tools.categories.ToolCategory;
import org.example.ui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class MainController extends Controller {
    private final Runnable modifyRunnable = () -> {
        mainPanel.setOriginalCanvas(mainPanel.getAppliedCanvas());
        mainPanel.setCurrentCanvas(mainPanel.getAppliedCanvas());
        mainPanel.setCanvasSize(mainPanel.getAppliedCanvas().getWidth(), mainPanel.getAppliedCanvas().getHeight());
        mainPanel.setPreferredSize(new Dimension(mainPanel.getOriginalCanvas().getWidth(), mainPanel.getOriginalCanvas().getHeight()));
        mainPanel.repaint();
    };

    private final NoParameterController noParameterController;
    private final ParameterController parameterController;

    public MainController(Frame frame, Model model) {
        super(frame, model);
        this.noParameterController = new NoParameterController(frame, model);
        this.parameterController = new ParameterController(frame, model);
    }

    @Override
    public void toolButtonClicked(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();

        ToolName toolName = ToolName.valueOf(button.getName());
        Tool tool = model.getToolMap().get(toolName);

        boolean isParameterized = model.getParameterizedTools().contains(tool);

        if (tool.getToolCategory().equals(ToolCategory.NO_MODIFY)) {
            if (isParameterized) {
                parameterController.setOkRunnable(null);
                parameterController.toolButtonClicked(e);
            } else {
                noParameterController.toolButtonClicked(e);
            }
        }

        if (tool.getToolCategory().equals(ToolCategory.MODIFY)) {
            if (isParameterized) {
                parameterController.setOkRunnable(modifyRunnable);
                parameterController.toolButtonClicked(e);
            } else  {
                noParameterController.toolButtonClicked(e);
                modifyRunnable.run();
            }
        }
    }

    public ParameterController getParameterController() {
        return parameterController;
    }

    public void frameResized(ComponentEvent event) {
        Parameter widthParameter = Objects.requireNonNull(model.getParameterList().stream()
                .filter(parameter -> parameter.getName().equals(ParameterName.FRAME_WIDTH))
                .findFirst()
                .orElse(null));
        Parameter heightParameter = Objects.requireNonNull(model.getParameterList().stream()
                .filter(parameter -> parameter.getName().equals(ParameterName.FRAME_HEIGHT))
                .findFirst()
                .orElse(null));

        widthParameter.setValue(frame.getScrollPane().getWidth() - 8);
        heightParameter.setValue(frame.getScrollPane().getHeight() - 8);
    }
}
