package org.example.ui;

import org.example.controllers.Controller;
import org.example.model.tools.ToolName;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBar extends JMenuBar {
    private final ButtonGroup toolButtons = new ButtonGroup();
    private final ButtonGroup modifyButtons = new ButtonGroup();

    private final JMenuItem openFileBtn;
    private final JMenuItem saveFileBtn;

    private final JMenuItem shrinkBtn;

    private final Controller controller;

    public MenuBar(Controller controller) {
        super();

        this.controller = controller;

        openFileBtn = new JMenuItem("Open");
        saveFileBtn = new JMenuItem("Save");

        shrinkBtn = new JMenuItem("Shrink Image");

        openFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openButtonClicked();
            }
        });
        saveFileBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveButtonClicked();
            }
        });
        /*shrinkBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.shrinkImage();
            }
        });*/

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openFileBtn);
        fileMenu.add(saveFileBtn);
        add(fileMenu);

        JMenu toolMenu = new JMenu("Tools");
        for (ToolName toolName : ToolName.values()) {
            if (toolName.equals(ToolName.SHRINKER) || toolName.equals(ToolName.IMAGE_ROTATOR)) {
                continue;
            }

            JRadioButtonMenuItem newButton = new JRadioButtonMenuItem(toolName.name());
            newButton.setName(toolName.toString());

            newButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.toolButtonClicked(e);
                }
            });

            toolButtons.add(newButton);
            toolMenu.add(newButton);
        }
        add(toolMenu);

        JMenu modifyMenu = new JMenu("Modify");
        for (ToolName toolName : ToolName.values()) {
            if (toolName.equals(ToolName.SHRINKER) || toolName.equals(ToolName.IMAGE_ROTATOR)) {
                JMenuItem newButton = new JMenuItem(toolName.name());
                newButton.setName(toolName.toString());

                newButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.toolButtonClicked(e);
                    }
                });

                modifyButtons.add(newButton);
                modifyMenu.add(newButton);
            }
        }
        add(modifyMenu);
    }
}
