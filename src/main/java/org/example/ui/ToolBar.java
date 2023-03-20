package org.example.ui;

import org.example.controllers.Controller;
import org.example.controllers.ParameterController;
import org.example.model.tools.ToolName;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

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
        /*shrinkBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.shrinkImage();
            }
        });
        stretchBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.strecthImage();
            }
        });*/

        add(openFileBtn);
        add(saveFileBtn);
        //add(shrinkBtn);
        //add(stretchBtn);

        add(new JToolBar.Separator());

        for (ToolName toolName: ToolName.values()) {
            Button newButton = new Button(getClass().getClassLoader().getResource(toolName.getTitle() + ".png"));
            newButton.setName(toolName.toString());

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
}
