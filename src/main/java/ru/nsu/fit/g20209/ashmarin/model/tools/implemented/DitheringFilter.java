package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.*;

public class DitheringFilter extends ParameterizedTool {
    public DitheringFilter(List<Parameter> parameters) {
        super(parameters);
    }

    private final double[] k = new double[]{7.0, 3.0, 5.0, 1.0};

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        //int[][] quantizedColors = ImageUtils.quantizeColors(parameters);

        BufferedImage inputImage = ImageUtils.copyOf(image);
        BufferedImage ditheringImage = new BufferedImage(width, height, image.getType());//ImageUtils.quantizeImage(image, parameters, quantizedColors);

        fillBorders(ditheringImage, width, height);

        int[] xs = new int[4];
        int[] ys = new int[4];

        for (int y = 0; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int oldRed = ColorUtils.getRed(inputImage.getRGB(x, y));
                int oldGreen = ColorUtils.getGreen(inputImage.getRGB(x, y));
                int oldBlue = ColorUtils.getBlue(inputImage.getRGB(x, y));

                int newRed = ColorUtils.closestInPalette(oldRed, parameters.get(0).getValue().intValue());
                int newGreen = ColorUtils.closestInPalette(oldGreen, parameters.get(1).getValue().intValue());
                int newBlue = ColorUtils.closestInPalette(oldBlue, parameters.get(2).getValue().intValue());

                ditheringImage.setRGB(x, y, ColorUtils.getRGB(newRed, newGreen, newBlue));

                int eRed = oldRed - newRed;
                int eGreen = oldGreen - newGreen;
                int eBlue = oldBlue - newBlue;

                xs[0] = x + 1;
                xs[1] = x - 1;
                xs[2] = x;
                xs[3] = x + 1;

                ys[0] = y;
                ys[1] = y + 1;
                ys[2] = y + 1;
                ys[3] = y + 1;

                for (int i = 0; i < xs.length; i++) {
                    int RGB = inputImage.getRGB(xs[i], ys[i]);
                    double red = ColorUtils.getRed(RGB);
                    double green = ColorUtils.getGreen(RGB);
                    double blue = ColorUtils.getBlue(RGB);

                    red += k[i] * eRed / 16.0;
                    green += k[i] * eGreen / 16.0;
                    blue += k[i] * eBlue / 16.0;

                    inputImage.setRGB(xs[i], ys[i], ColorUtils.getRGB((int) (red), (int) (green), (int) (blue)));
                }
            }
        }

        return ditheringImage;
    }

    private void fillBorders(BufferedImage image, int width, int height) {
        for (int x = 0; x < width; x++) {
            setClosestColorAt(image, x, 0);
            setClosestColorAt(image, x, height - 1);
        }

        for (int y = 0; y < height; y++) {
            setClosestColorAt(image, 0, y);
            setClosestColorAt(image, width - 1, y);
        }
    }

    private void setClosestColorAt(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);
        int closestRed = ColorUtils.closestInPalette(ColorUtils.getRed(rgb), parameters.get(0).getValue().intValue());
        int closestGreen = ColorUtils.closestInPalette(ColorUtils.getGreen(rgb), parameters.get(1).getValue().intValue());
        int closestBlue = ColorUtils.closestInPalette(ColorUtils.getBlue(rgb), parameters.get(2).getValue().intValue());
        image.setRGB(x, y, ColorUtils.getRGB(closestRed, closestGreen, closestBlue));
    }
}
