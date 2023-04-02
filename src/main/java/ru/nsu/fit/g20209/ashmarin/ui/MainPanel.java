package ru.nsu.fit.g20209.ashmarin.ui;

import ru.nsu.fit.g20209.ashmarin.controllers.Controller;
import ru.nsu.fit.g20209.ashmarin.model.utils.FileUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MainPanel extends JPanel {
    private BufferedImage currentCanvas;
    private BufferedImage appliedCanvas;
    private BufferedImage clearCanvas;
    private BufferedImage originalImage;

    public MainPanel() {
        super();

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createDashedBorder(Color.BLACK, 5, 4)
        ));
        clearCanvas = FileUtils.loadImageFromFile(Objects.requireNonNull(getClass().getClassLoader().getResource("test_image.jpg")).getPath());
        currentCanvas = clearCanvas;
        originalImage = ImageUtils.copyOf(clearCanvas);

        setPreferredSize(new Dimension(clearCanvas.getWidth(), clearCanvas.getHeight()));
    }

    public void setCanvasSize(int width, int height) {
        this.clearCanvas = ImageUtils.scaleBufferedImage(clearCanvas, width, height);
        this.currentCanvas = ImageUtils.scaleBufferedImage(currentCanvas, width, height);
        this.appliedCanvas = ImageUtils.scaleBufferedImage(appliedCanvas, width, height);
    }

    public BufferedImage getClearCanvas() {
        return clearCanvas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentCanvas, 4, 4, currentCanvas.getWidth(), currentCanvas.getHeight(), this);
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

    public void setClearCanvas(BufferedImage newCanvas) {
        this.clearCanvas = newCanvas;
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


    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }
}
