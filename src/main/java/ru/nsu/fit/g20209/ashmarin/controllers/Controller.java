package ru.nsu.fit.g20209.ashmarin.controllers;

import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.model.utils.FileUtils;
import ru.nsu.fit.g20209.ashmarin.ui.Frame;
import ru.nsu.fit.g20209.ashmarin.ui.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class Controller {
    protected final Frame frame;
    protected final MainPanel mainPanel;
    protected final Model model;

    public Controller(Frame frame, Model model) {
        this.frame = frame;
        this.mainPanel = frame.getMainPanel();
        this.model = model;
    }

    public void imageClicked() {
        if (Objects.isNull(model.getSelectedTool())) {
            return;
        }

        BufferedImage originalCanvas = mainPanel.getClearCanvas();
        if (mainPanel.getAppliedCanvas().equals(mainPanel.getCurrentCanvas())) {
            mainPanel.setCurrentCanvas(originalCanvas);
        } else {
            mainPanel.setCurrentCanvas(mainPanel.getAppliedCanvas());
        }
        mainPanel.setPreferredSize(new Dimension(mainPanel.getCurrentCanvas().getWidth(), mainPanel.getCurrentCanvas().getHeight()));
        mainPanel.dispatchEvent(new ComponentEvent(mainPanel, ComponentEvent.COMPONENT_RESIZED));

        mainPanel.repaint();
    }

    public void openButtonClicked() {
        FileUtils.handleOpen(mainPanel);
    }

    public void saveButtonClicked() {
        FileUtils.handleSave(mainPanel);
    }

    public void backToOriginalImage() {
        BufferedImage originalImage = mainPanel.getOriginalImage();
        mainPanel.setClearCanvas(originalImage);
        mainPanel.setCurrentCanvas(originalImage);
        mainPanel.setAppliedCanvas(originalImage);
        mainPanel.setPreferredSize(new Dimension(originalImage.getWidth(), originalImage.getHeight()));
        mainPanel.dispatchEvent(new ComponentEvent(mainPanel, ComponentEvent.COMPONENT_RESIZED));
        mainPanel.repaint();
    }

    public abstract void toolButtonClicked(ActionEvent e);
}
