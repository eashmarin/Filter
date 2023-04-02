package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SobelOperator extends ParameterizedTool {
    public SobelOperator(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int binarizationParam = parameters.get(0).getValue().intValue();
        int width = image.getWidth();
        int height = image.getHeight();

        double[][] convMatrix1 = new double[][]{
                {-1 / 4.0, 0, 1 / 4.0},
                {-2 / 4.0, 0, 2 / 4.0},
                {-1 / 4.0, 0, 1 / 4.0}
        };
        double[][] convMatrix2 = new double[][]{
                {1 / 4.0, 2 / 4.0, 1 / 4.0},
                {0, 0, 0},
                {-1 / 4.0, -2 / 4.0, -1 / 4.0}
        };

        double[][] sobelX = {{-1 / 4.0, 0, 1 / 4.0}, {-2 / 4.0, 0, 2 / 4.0}, {-1 / 4.0, 0, 1 / 4.0}};
        double[][] sobelY = {{1 / 4.0, 2 / 4.0, 1 / 4.0}, {0, 0, 0}, {-1 / 4.0, -2 / 4.0, -1 / 4.0}};

        BufferedImage image1 = ImageUtils.applyConvMatrix(image, convMatrix1, (x) -> x);
        BufferedImage image2 = ImageUtils.applyConvMatrix(image, convMatrix2, (x) -> x);
        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                int r = (int) Math.sqrt(Math.pow(ColorUtils.getRed(rgb1), 2) + Math.pow(ColorUtils.getRed(rgb2), 2));
                int g = (int) Math.sqrt(Math.pow(ColorUtils.getGreen(rgb1), 2) + Math.pow(ColorUtils.getGreen(rgb2), 2));
                int b = (int) Math.sqrt(Math.pow(ColorUtils.getBlue(rgb1), 2) + Math.pow(ColorUtils.getBlue(rgb2), 2));

                if (r > binarizationParam) {
                    r = 255;
                } else {
                    r = 0;
                }
                if (g > binarizationParam) {
                    g = 255;
                } else {
                    g = 0;
                }
                if (b > binarizationParam) {
                    b = 255;
                } else {
                    b = 0;
                }

                r = ColorUtils.correctRange(r);
                g = ColorUtils.correctRange(g);
                b = ColorUtils.correctRange(b);

                result.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }

        return result;
    }
}
