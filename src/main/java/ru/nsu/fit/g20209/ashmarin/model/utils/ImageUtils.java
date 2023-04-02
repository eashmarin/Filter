package ru.nsu.fit.g20209.ashmarin.model.utils;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;

public class ImageUtils {
    public static void drawRect(BufferedImage image, Color color) {
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
    }

    public static BufferedImage ImageToBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        drawRect(bufferedImage, Color.WHITE);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return bufferedImage;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
        if (image.getWidth() == width && image.getHeight() == height) {
            return copyOf(image);
        }

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage scaledBufferedImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = scaledBufferedImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();

        return scaledBufferedImage;
    }

    public static BufferedImage copyOf(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics = copy.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return copy;
    }

    public static BufferedImage applyConvMatrix(BufferedImage image, double[][] convMatrix, UnaryOperator<Integer> postOperator) {
        int width = image.getWidth();
        int height = image.getHeight();

        int n = convMatrix.length / 2;
        int k = n + convMatrix.length % 2;

        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                for (int j = -n; j < k; j++) {
                    for (int i = -n; i < k; i++) {
                        Point coords = getCoords(x, i, y, j, width, height);

                        int rgb = image.getRGB(coords.x, coords.y);
                        sumR += ColorUtils.getRed(rgb) * convMatrix[i + n][j + n];
                        sumG += ColorUtils.getGreen(rgb) * convMatrix[i + n][j + n];
                        sumB += ColorUtils.getBlue(rgb) * convMatrix[i + n][j + n];
                    }
                }

                sumR = postOperator.apply(sumR);
                sumG = postOperator.apply(sumG);
                sumB = postOperator.apply(sumB);

                result.setRGB(x, y, ColorUtils.getRGB(sumR, sumG, sumB));
            }
        }

        return result;
    }

    public static Point getCoords(int x, int i, int y, int j, int width, int height) {
        Point point = new Point();
        if (x + i < 0) {
            point.x = x - i;
        } else {
            point.x = x + i;
        }

        if (y + j < 0) {
            point.y = y - j;
        } else {
            point.y = y + j;
        }

        if (point.x >= width) {
            point.x = point.x - (point.x + 1) % width;
        }

        if (point.y >= height) {
            point.y = point.y - (point.y + 1) % height;
        }

        return point;
    }

    public static BufferedImage quantizeImage(BufferedImage image, List<Parameter> parameters) {
        int redColors = parameters.get(0).getValue().intValue();
        int greenColors = parameters.get(1).getValue().intValue();
        int blueColors = parameters.get(2).getValue().intValue();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        Map<Integer, Integer> lookupMap = new HashMap<>();

        for (int pixel : pixels) {
            int closestRed = ColorUtils.closestInPalette(ColorUtils.getRed(pixel), redColors);
            int closestGreen = ColorUtils.closestInPalette(ColorUtils.getGreen(pixel), greenColors);
            int closestBlue = ColorUtils.closestInPalette(ColorUtils.getBlue(pixel), blueColors);
            lookupMap.put(pixel, ColorUtils.getRGB(closestRed, closestGreen, closestBlue));
        }

        BufferedImage quantizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = lookupMap.get(pixels[i]);
        }

        quantizedImage.setRGB(0, 0, width, height, pixels, 0, width);

        return quantizedImage;
    }

    public static int[][] quantizeColors(List<Parameter> parameters) {
        int[] quantizationNumbers = parameters.stream()
                .mapToInt(p -> p.getValue().intValue())
                .toArray();
        int[] colorSteps = Arrays.stream(quantizationNumbers)
                .map(q -> 255 / (q - 1))
                .toArray();

        int[][] quantizedColors = new int[quantizationNumbers.length][];
        for (int i = 0; i < quantizationNumbers.length; i++) {
            quantizedColors[i] = new int[quantizationNumbers[i]];
        }

        for (int i = 0; i < quantizedColors.length; i++) {
            for (int j = 0; j < quantizedColors[i].length; j++) {
                quantizedColors[i][j] = (j * colorSteps[i]);
            }
        }

        return quantizedColors;
    }
}
