package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class PixelateFilter extends ParameterizedTool {
    public PixelateFilter(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int blockSize = parameters.get(0).getValue().intValue();

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                int avgRGB = getAverageColor(image, x, y, width, height, blockSize);
                for (int yy = y; yy < y + blockSize && yy < height; yy++) {
                    for (int xx = x; xx < x + blockSize && xx < width; xx++) {
                        result.setRGB(xx, yy, avgRGB);
                    }
                }
            }
        }

        return result;
    }

    private int getAverageColor(BufferedImage image, int x, int y, int width, int height, int blockSize) {
        int r = 0, g = 0, b = 0;

        for (int yy = y; yy < y + blockSize; yy++) {
            for (int xx = x; xx < x + blockSize; xx++) {
                Point coords = ImageUtils.getCoords(xx, 0, yy, 0, width, height);
                int pixel = image.getRGB(coords.x, coords.y);
                r += ColorUtils.getRed(pixel);
                g += ColorUtils.getGreen(pixel);
                b += ColorUtils.getBlue(pixel);
            }
        }

        r /= blockSize * blockSize;
        g /= blockSize * blockSize;
        b /= blockSize * blockSize;

        return ColorUtils.getRGB(r, g, b);
    }
}
