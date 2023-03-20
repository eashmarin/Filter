package org.example.model.tools.implemented;

import org.example.model.tools.Tool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class EmbossFilter extends Tool {
    public EmbossFilter(ToolCategory toolCategory) {
        super(toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage embossedImage = new BufferedImage(width, height, image.getType());

        int[][] convolutionMatrix = {{0, 1, 0}, {-1, 0, 1}, {0, -1, 0}};

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0, sumG = 0, sumB = 0;
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < 3; i++) {
                        int rgb = image.getRGB(x + i - ((x + i) / width) * (x + i + 1) % width, y + j - ((y + j) / height) * (y + j + 1) % height);

                        sumR += convolutionMatrix[i][j] * ColorUtils.getRed(rgb);
                        sumG += convolutionMatrix[i][j] * ColorUtils.getGreen(rgb);
                        sumB += convolutionMatrix[i][j] * ColorUtils.getBlue(rgb);
                    }
                }

                int r = ColorUtils.correctRange(sumR + 128);
                int g = ColorUtils.correctRange(sumG + 128);
                int b = ColorUtils.correctRange(sumB + 128);

                embossedImage.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }
        return embossedImage;
    }
}
