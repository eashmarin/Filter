package org.example.model.tools.implemented;

import org.example.model.tools.Tool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class NegativeConverter extends Tool {
    public NegativeConverter(ToolCategory toolCategory) {
        super(toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage correctedImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int r = 255 - ColorUtils.getRed(rgb);
                int g = 255 - ColorUtils.getGreen(rgb);
                int b = 255 - ColorUtils.getBlue(rgb);

                correctedImage.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }

        return correctedImage;
    }
}
