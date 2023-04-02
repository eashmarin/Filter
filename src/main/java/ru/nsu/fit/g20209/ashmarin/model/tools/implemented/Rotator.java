package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Rotator extends ParameterizedTool {
    public Rotator(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int angle = parameters.get(0).getValue().intValue();

        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));

        int newWidth = (int) Math.round(oldHeight * Math.abs(sin) + oldWidth * Math.abs(cos));
        int newHeight = (int) Math.round(oldWidth * Math.abs(sin) + oldHeight * Math.abs(cos));

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, image.getType());
        ImageUtils.drawRect(rotated, Color.WHITE);

        int oldCenterX = oldWidth / 2;
        int oldCenterY = oldHeight / 2;

        int newCenterX = newWidth / 2;
        int newCenterY = newHeight / 2;

        int whiteRGB = Color.WHITE.getRGB();
        for (int newY = 0; newY < newHeight; newY++) {
            for (int newX = 0; newX < newWidth; newX++) {
                int oldX = (int) Math.round((newX - newCenterX) * cos - (newY - newCenterY) * sin + oldCenterX);
                int oldY = (int) Math.round((newX - newCenterX) * sin + (newY - newCenterY) * cos + oldCenterY);

                if (oldX >= 0 && oldX < oldWidth && oldY >= 0 && oldY < oldHeight) {
                    int color = image.getRGB(oldX, oldY);
                    rotated.setRGB(newX, newY, color);
                } else {
                    rotated.setRGB(newX, newY, whiteRGB);
                }
            }
        }

        return rotated;
    }
}
