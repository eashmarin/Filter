package ru.nsu.fit.g20209.ashmarin.ui;

import ru.nsu.fit.g20209.ashmarin.controllers.Controller;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolBar extends JToolBar {
    private final ButtonGroup toolButtons = new ButtonGroup();

    private final Button openFileBtn;
    private final Button saveFileBtn;

    private final Controller mainController;

    public ToolBar(Controller mainController) {
        super();

        this.mainController = mainController;

        openFileBtn = new Button(getClass().getClassLoader().getResource("open_file.png"));
        saveFileBtn = new Button(getClass().getClassLoader().getResource("save_file.png"));
        openFileBtn.setToolTipText("Open");
        saveFileBtn.setToolTipText("Save");

        openFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.openButtonClicked();
            }
        });
        saveFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.saveButtonClicked();
            }
        });

        add(openFileBtn);
        add(saveFileBtn);

        add(new JToolBar.Separator());

        for (ToolEnum toolEnum : ToolEnum.values()) {
            Button newButton = new Button(getClass().getClassLoader().getResource(toolEnum.getTitle().replace(' ', '_') + ".png"));
            newButton.setName(toolEnum.toString());
            newButton.setToolTipText(toolEnum.getTitle());

            newButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainController.toolButtonClicked(e);
                }
            });

            toolButtons.add(newButton);
            add(newButton);
        }

        setBackground(Color.WHITE);

        setFloatable(false);
    }

    public void setSelectedTool(ToolEnum toolEnum) {
        toolButtons.getElements()
                .asIterator()
                .forEachRemaining(btn -> {
                    if (btn.getName().equals(toolEnum.toString())) btn.setSelected(true);
                });
    }
}