package ru.nsu.fit.g20209.ashmarin.ui;

import ru.nsu.fit.g20209.ashmarin.controllers.Controller;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolCategory;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JMenuBar {
    private final ButtonGroup toolButtons = new ButtonGroup();
    private final ButtonGroup modifyButtons = new ButtonGroup();

    private final JMenuItem openFileBtn;
    private final JMenuItem saveFileBtn;

    public MenuBar(Controller controller) {
        super();

        openFileBtn = new JMenuItem("Open");
        saveFileBtn = new JMenuItem("Save");

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

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openFileBtn);
        fileMenu.add(saveFileBtn);
        add(fileMenu);

        JMenu toolMenu = new JMenu("Tools");
        JMenu modifyMenu = new JMenu("Modify");

        for (ToolEnum toolEnum : ToolEnum.values()) {
            if (toolEnum.getCategory().equals(ToolCategory.MODIFY)) {
                continue;
            }

            JRadioButtonMenuItem newButton = new JRadioButtonMenuItem(toolEnum.getTitle());
            newButton.setName(toolEnum.toString());

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

        for (ToolEnum toolEnum : ToolEnum.values()) {
            if (toolEnum.getCategory().equals(ToolCategory.MODIFY)) {
                JMenuItem newButton = new JMenuItem(toolEnum.getTitle());
                newButton.setName(toolEnum.toString());

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

        JMenuItem originalImgItem = new JMenuItem("Back to original");
        originalImgItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.backToOriginalImage();
            }
        });
        modifyMenu.add(originalImgItem);

        add(modifyMenu);

        JMenu aboutItem = new JMenu("About");
        aboutItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(aboutItem.getTopLevelAncestor(),
                        "ICGFilter is a program that allows user to apply various filters to images " +
                                "\nmade by Evgeny Ashmarin as a part of ICG course", "About", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        add(aboutItem);
    }

    public void setSelectedTool(ToolEnum toolEnum) {
        toolButtons.getElements()
                .asIterator()
                .forEachRemaining(btn -> {
                    if (btn.getName().equals(toolEnum.toString()))
                        btn.setSelected(true);
                });
    }
}
