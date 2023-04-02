package ru.nsu.fit.g20209.ashmarin.ui;

import ru.nsu.fit.g20209.ashmarin.controllers.MainController;
import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.ui.parameters.ParameterDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Frame extends JFrame {
    private static final Dimension MIN_SIZE = new Dimension(640, 480);
    private static final Dimension PREF_SIZE= new Dimension(800, 600);
    private final ToolBar toolBar;
    private final MenuBar menuBar;
    private final MainPanel mainPanel;
    private final JScrollPane scrollPane;

    private final ParameterDialog parameterDialog;

    private final MainController mainController;

    public Frame(Model model) {
        super();

        mainPanel = new MainPanel();

        parameterDialog = new ParameterDialog();

        mainController = new MainController(this, model);

        parameterDialog.insertControllerActions(mainController.getParameterController());

        scrollPane = new JScrollPane(
                mainPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setMinimumSize(MIN_SIZE);
        scrollPane.setPreferredSize(PREF_SIZE);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        scrollPane.setAutoscrolls(true);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, mainPanel);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        mainPanel.scrollRectToVisible(view);
                    }
                }
            }
        };

        mainPanel.addMouseListener(mouseAdapter);
        mainPanel.addMouseMotionListener(mouseAdapter);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                mainController.frameResized(e);
            }
        });

        toolBar = new ToolBar(mainController);
        menuBar = new MenuBar(mainController);

        mainPanel.insertControllerActions(mainController);

        setJMenuBar(menuBar);

        Container container = getContentPane();
        container.add(toolBar, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        setMinimumSize(MIN_SIZE);
        setPreferredSize(PREF_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ICGFilter");
        setVisible(true);
        pack();
    }

    public ParameterDialog getParameterDialog() {
        return parameterDialog;
    }

    public BufferedImage getCanvas() {
        return mainPanel.getOriginalCanvas();
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public MenuBar getMenu() {
        return menuBar;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }
}
