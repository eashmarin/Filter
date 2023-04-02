package ru.nsu.fit.g20209.ashmarin.model.utils;

import ru.nsu.fit.g20209.ashmarin.ui.MainPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static void handleOpen(MainPanel mainPanel) {
        JFileChooser fileChooser = new JFileChooser();
        setImageFilters(fileChooser);

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            BufferedImage newCanvas = loadImageFromFile(fileChooser.getSelectedFile().getPath());
            mainPanel.setOriginalCanvas(newCanvas);
            mainPanel.setCurrentCanvas(newCanvas);
            mainPanel.repaint();
        }
    }

    public static void handleSave(MainPanel mainPanel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        setImageFilters(fileChooser);

        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.CANCEL_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (filePath.lastIndexOf('.') == -1) {
            filePath += fileChooser.getFileFilter().getDescription().substring(1);
        }

        writeImageToFile(mainPanel.getCurrentCanvas(), filePath);
    }

    private static void setImageFilters(JFileChooser fileChooser) {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpeg", "jpeg"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.bmp", "bmp"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));

    }

    public static BufferedImage loadImageFromFile(String filePath) {
        Image image;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage newCanvas = ImageUtils.ImageToBufferedImage(image);

        Graphics2D graphics = newCanvas.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return newCanvas;
    }

    private static void writeImageToFile(RenderedImage canvas, String path) {
        try {
            FileOutputStream os = new FileOutputStream(path);
            ImageIO.write(canvas, getFileExtension(path), os);
            os.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getFileExtension(String filePath) {
        String extension = "";

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }

        return extension;
    }
}
