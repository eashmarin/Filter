package org.example.model.tools.implemented;

import org.example.model.tools.Tool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class GrayscaleConverter extends Tool {
    public GrayscaleConverter(ToolCategory toolCategory) {
        super(toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage grayScaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = image.getRGB(j, i);
                int gray = (int) (0.299 * ColorUtils.getRed(rgb) + 0.587 * ColorUtils.getGreen(rgb) + 0.114 * ColorUtils.getBlue(rgb));
                grayScaleImage.setRGB(j, i, ColorUtils.getRGB(gray, gray, gray));
            }
        }

        return grayScaleImage;
    }
}
