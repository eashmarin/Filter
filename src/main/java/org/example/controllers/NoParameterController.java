package org.example.controllers;

import org.example.model.Model;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.Tool;
import org.example.model.tools.ToolName;
import org.example.model.utils.ImageUtils;
import org.example.ui.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class NoParameterController extends Controller{
    public NoParameterController(Frame frame, Model model) {
        super(frame, model);
    }

    @Override
    public void toolButtonClicked(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();

        ToolName toolName = ToolName.valueOf(button.getName());
        Tool tool = model.getToolMap().get(toolName);

        BufferedImage appliedImage = tool.apply(mainPanel.getOriginalCanvas());
        mainPanel.setCurrentCanvas(appliedImage);
        mainPanel.setAppliedCanvas(appliedImage);
        mainPanel.repaint();

        model.setSelectedTool(tool);
    }
}
