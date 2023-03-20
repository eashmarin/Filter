package org.example.controllers;

import org.example.model.Model;
import org.example.model.utils.FileUtils;
import org.example.ui.Frame;
import org.example.ui.MainPanel;

import java.awt.event.ActionEvent;
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

        BufferedImage originalCanvas = mainPanel.getOriginalCanvas();
        if (mainPanel.getAppliedCanvas().equals(mainPanel.getCurrentCanvas())) {
            mainPanel.setCurrentCanvas(originalCanvas);
        } else {
            mainPanel.setCurrentCanvas(mainPanel.getAppliedCanvas());
        }

        mainPanel.repaint();
    }

    public void openButtonClicked() {
        FileUtils.handleOpen(mainPanel);
    }

    public void saveButtonClicked() {
        FileUtils.handleSave(mainPanel);
    }

    public abstract void toolButtonClicked(ActionEvent e);
}
