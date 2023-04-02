package ru.nsu.fit.g20209.ashmarin.ui;

import ru.nsu.fit.g20209.ashmarin.model.utils.FileUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

public class Button extends JButton {
    private final static int ICON_SIZE = 30;
    private final static Dimension SIZE = new Dimension(35, 35);

    public Button(URL iconURL) {
        super();

        if (Objects.nonNull(iconURL)) {
            BufferedImage iconImage = ImageUtils.scaleBufferedImage(FileUtils.loadImageFromFile(iconURL.getPath()), ICON_SIZE, ICON_SIZE);
            setIcon(new ImageIcon(iconImage));
        }

        setFocusable(false);
        setBorderPainted(false);
        setPreferredSize(SIZE);
        setMaximumSize(SIZE);
    }
}
