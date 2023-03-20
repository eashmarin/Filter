package org.example.model.tools.implemented;

import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;
import org.example.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.*;

public class DitheringFilter extends ParameterizedTool {
    public DitheringFilter(List<Parameter> parameters, ToolCategory toolCategory) {
        super(parameters, toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[] k = new double[]{7.0 / 16, 3.0 / 16, 5.0 / 16, 1.0 / 16};

        int[][] quantizedColors = ImageUtils.getQuantizedColors(image, parameters);

        BufferedImage ditheringImage = ImageUtils.copyOf(image);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int closestColorRed = findClosestTo(ColorUtils.getRed(ditheringImage.getRGB(x, y)), quantizedColors[0]);
                int closestColorGreen = findClosestTo(ColorUtils.getGreen(ditheringImage.getRGB(x, y)), quantizedColors[1]);
                int closestColorBlue = findClosestTo(ColorUtils.getBlue(ditheringImage.getRGB(x, y)), quantizedColors[2]);

                int eRed = Math.abs(ColorUtils.getRed(ditheringImage.getRGB(x, y)) - closestColorRed);
                int eGreen = Math.abs(ColorUtils.getGreen(ditheringImage.getRGB(x, y)) - closestColorGreen);
                int eBlue = Math.abs(ColorUtils.getBlue(ditheringImage.getRGB(x, y)) - closestColorBlue);

                ditheringImage.setRGB(x, y, ColorUtils.getRGB(
                        closestColorRed,
                        closestColorGreen,
                        closestColorBlue
                ));

                int[] xs = new int[]{x + 1, x - 1, x, x + 1};
                int[] ys = new int[]{y, y + 1, y + 1, y + 1};

                for (int i = 0; i < xs.length; i++) {
                    int RGB = ditheringImage.getRGB(xs[i], ys[i]);
                    ditheringImage.setRGB(xs[i], ys[i], ColorUtils.getRGB(
                            (int) (k[i] * eRed + ColorUtils.getRed(RGB)),
                            (int) (k[i] * eGreen + ColorUtils.getGreen(RGB)),
                            (int) (k[i] * eBlue + ColorUtils.getBlue(RGB))
                    ));
                }
                /*ditheringImage.setRGB(x + 1, y, ColorUtils.getRGB(
                        7 * eRed / 16 + ColorUtils.getRed(RGB),
                        7 * eGreen / 16 + ColorUtils.getGreen(RGB),
                        7 * eBlue / 16 + ColorUtils.getBlue(RGB))
                );
                RGB =
                ditheringImage.setRGB(x - 1, y + 1, ColorUtils.getRGB(
                        3 * eRed / 16 + ColorUtils.getRed(RGB),
                        3 * eGreen / 16 + ColorUtils.getGreen(RGB),
                        3 * eBlue / 16 + ColorUtils.getBlue(RGB))
                );
                RGB = ditheringImage.getRGB(x, y + 1);
                ditheringImage.setRGB(x, y + 1, ColorUtils.getRGB(
                        5 * eRed / 16 + ColorUtils.getRed(RGB),
                        5 * eGreen / 16 + ColorUtils.getGreen(RGB),
                        5 * eBlue / 16 + ColorUtils.getBlue(RGB))
                );
                RGB = ditheringImage.getRGB(x + 1, y + 1);
                ditheringImage.setRGB(x + 1, y + 1, ColorUtils.getRGB(
                        eRed / 16 + ColorUtils.getRed(RGB),
                        eGreen / 16 + ColorUtils.getGreen(RGB),
                        eBlue / 16 + ColorUtils.getBlue(RGB))
                );*/
            }

            //ditheringImage.setRGB(width - 1, y, ColorUtils.getRGB());
        }

        return ditheringImage;
    }

    private int findClosestTo(int value, int[] array) {
        int closestValue = array[0];

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            int diff = Math.abs(value - array[i]);
            if (diff < min) {
                min = diff;
                closestValue = array[i];
            }
        }

        return closestValue;
    }
}
