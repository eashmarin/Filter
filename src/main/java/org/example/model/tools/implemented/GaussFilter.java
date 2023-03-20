package org.example.model.tools.implemented;

import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;
import org.example.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class GaussFilter extends ParameterizedTool {
    public GaussFilter(List<Parameter> parameters, ToolCategory toolCategory) {
        super(parameters, toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int windowSize = parameters.get(0).getValue().intValue();
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage filteredImage = ImageUtils.copyOf(image);

        int divider = windowSize * windowSize;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                for (int j = 0; j < windowSize; j++) {
                    for (int i = 0; i < windowSize; i++) {
                        int rgb = image.getRGB(x + i - ((x + i) / width) * (x + i + 1) % width, y + j - ((y + j) / height) * (y + j + 1) % height);

                        sumR += ColorUtils.getRed(rgb);
                        sumG += ColorUtils.getGreen(rgb);
                        sumB += ColorUtils.getBlue(rgb);
                    }
                }

                filteredImage.setRGB(x, y, ColorUtils.getRGB(sumR / divider, sumG / divider, sumB / divider));
            }
        }

        return filteredImage;
    }
}
