package ru.nsu.fit.g20209.ashmarin.controllers;

import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;
import ru.nsu.fit.g20209.ashmarin.ui.Frame;

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
        ((Component) frame).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        (frame.getMenu()).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        AbstractButton button = (AbstractButton) e.getSource();

        ToolEnum toolEnum = ToolEnum.valueOf(button.getName());
        Tool tool = model.getToolMap().get(toolEnum);

        BufferedImage appliedImage = tool.apply(mainPanel.getClearCanvas());
        mainPanel.setCurrentCanvas(appliedImage);
        mainPanel.setAppliedCanvas(appliedImage);
        mainPanel.setPreferredSize(new Dimension(mainPanel.getAppliedCanvas().getWidth(), mainPanel.getAppliedCanvas().getHeight()));
        mainPanel.dispatchEvent(new ComponentEvent(mainPanel, ComponentEvent.COMPONENT_RESIZED));
        mainPanel.repaint();

        ((Component) frame).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        (frame.getMenu()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        model.setSelectedTool(tool);
    }
}
