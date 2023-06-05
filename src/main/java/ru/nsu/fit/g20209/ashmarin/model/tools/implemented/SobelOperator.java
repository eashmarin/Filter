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

        int n = convMatrix1.length / 2;
        int k = n  + convMatrix1.length % 2;

        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR1 = 0;
                int sumG1 = 0;
                int sumB1 = 0;

                int sumR2 = 0;
                int sumG2 = 0;
                int sumB2 = 0;

                for (int i = -n; i < k; i++) {
                    for (int j = -n; j < k; j++) {
                        Point point = ImageUtils.getCoords(x, i, y, j, width, height);

                        int rgb = image.getRGB(point.x, point.y);
                        sumR1 += ColorUtils.getRed(rgb) * convMatrix1[i + n][j + n];
                        sumG1 += ColorUtils.getGreen(rgb) * convMatrix1[i + n][j + n];
                        sumB1 += ColorUtils.getBlue(rgb) * convMatrix1[i + n][j + n];

                        sumR2 += ColorUtils.getRed(rgb) * convMatrix2[i + n][j + n];
                        sumG2 += ColorUtils.getGreen(rgb) * convMatrix2[i + n][j + n];
                        sumB2 += ColorUtils.getBlue(rgb) * convMatrix2[i + n][j + n];
                    }
                }

                int r = (int) Math.sqrt(Math.pow(sumR1, 2) + Math.pow(sumR2, 2));
                int g = (int) Math.sqrt(Math.pow(sumG1, 2) + Math.pow(sumG2, 2));
                int b = (int) Math.sqrt(Math.pow(sumB1, 2) + Math.pow(sumB2, 2));

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

                result.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }

        return result;
    }
}
