package org.example.ui;

import org.example.controllers.Controller;
import org.example.controllers.NoParameterController;
import org.example.model.utils.FileUtils;
import org.example.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MainPanel extends JPanel {
    private BufferedImage currentCanvas;
    private BufferedImage appliedCanvas;
    private BufferedImage originalCanvas;

    public MainPanel(int width, int height) {
        super();

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createDashedBorder(Color.BLACK, 5, 4)
        ));
        originalCanvas = FileUtils.loadImageFromFile(Objects.requireNonNull(getClass().getClassLoader().getResource("test_image.jpg")).getPath());
        currentCanvas = originalCanvas;

/*        setCanvasSize(width, height);
        repaint();*/

        setPreferredSize(new Dimension(originalCanvas.getWidth(), originalCanvas.getHeight()));
    }

    public void setCanvasSize(int width, int height) {
        this.originalCanvas = ImageUtils.scaleBufferedImage(originalCanvas, width, height);
        this.currentCanvas = ImageUtils.copyOf(originalCanvas);
        this.appliedCanvas = ImageUtils.copyOf(originalCanvas);
    }

    public BufferedImage getOriginalCanvas() {
        return originalCanvas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(currentCanvas, 4, 4, this);

        /*Graphics2D graphics = (Graphics2D) g.create();

        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        graphics.setStroke(dashed);
        graphics.drawRect(4, 4, currentCanvas.getWidth(), currentCanvas.getHeight());

        graphics.dispose();*/
    }

    public BufferedImage getCurrentCanvas() {
        return currentCanvas;
    }

    public void setCurrentCanvas(BufferedImage newCanvas) {
        this.currentCanvas = newCanvas;
    }

    public BufferedImage getAppliedCanvas() {
        return appliedCanvas;
    }

    public void setAppliedCanvas(BufferedImage appliedCanvas) {
        this.appliedCanvas = appliedCanvas;
    }

    public void setOriginalCanvas(BufferedImage newCanvas) {
        this.originalCanvas = newCanvas;
    }

    public void insertControllerActions(Controller controller) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.imageClicked();
            }
        });
    }


}
