package ru.nsu.fit.g20209.ashmarin.controllers;

import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.parameters.ParameterName;
import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolCategory;
import ru.nsu.fit.g20209.ashmarin.ui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class MainController extends Controller {
    private final Runnable modifyRunnable = () -> {
        //mainPanel.setOriginalCanvas(mainPanel.getAppliedCanvas());
        //mainPanel.setOriginalCanvas(mainPanel.getWidth(), mainPanel.getHeight());
        //mainPanel.setCurrentCanvas(mainPanel.getAppliedCanvas());
        mainPanel.setCanvasSize(mainPanel.getAppliedCanvas().getWidth(), mainPanel.getAppliedCanvas().getHeight());
        //frame.getScrollPane().setPreferredSize(new Dimension(mainPanel.getAppliedCanvas().getWidth() - 4, mainPanel.getAppliedCanvas().getHeight() - 4));
        mainPanel.setPreferredSize(new Dimension(mainPanel.getOriginalCanvas().getWidth(), mainPanel.getOriginalCanvas().getHeight()));
        mainPanel.repaint();
        mainPanel.dispatchEvent(new ComponentEvent(mainPanel, ComponentEvent.COMPONENT_RESIZED));
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

        ToolEnum toolEnum = ToolEnum.valueOf(button.getName());
        Tool tool = model.getToolMap().get(toolEnum);

        boolean isParameterized = model.getParameterizedTools().contains(tool);

        if (toolEnum.getCategory().equals(ToolCategory.NO_MODIFY)) {
            frame.getMenu().setSelectedTool(toolEnum);
            frame.getToolBar().setSelectedTool(toolEnum);
            if (isParameterized) {
                parameterController.setOkRunnable(null);
                parameterController.toolButtonClicked(e);
            } else {
                noParameterController.toolButtonClicked(e);
            }
        }

        if (toolEnum.getCategory().equals(ToolCategory.MODIFY)) {
            if (isParameterized) {
                parameterController.setOkRunnable(modifyRunnable);
                parameterController.toolButtonClicked(e);
            } else {
                noParameterController.toolButtonClicked(e);
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

        widthParameter.setValue(frame.getScrollPane().getWidth() - 8 - 4);
        heightParameter.setValue(frame.getScrollPane().getHeight() - 8 - 4);
    }
}
