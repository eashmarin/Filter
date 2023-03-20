package org.example.model.utils;

import org.example.model.parameters.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        BufferedImage scaledBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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

    public static BufferedImage applyConvMatrix(BufferedImage image, double[][] convMatrix) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                for (int j = 0; j < convMatrix.length; j++) {
                    for (int i = 0; i < convMatrix[j].length; i++) {
                        int rgb = image.getRGB(x + i - ((x + i) / width) * (x + i + 1) % width, y + j - ((y + j) / height) * (y + j + 1) % height);

                        sumR += ColorUtils.getRed(rgb) * convMatrix[i][j];
                        sumG += ColorUtils.getGreen(rgb) * convMatrix[i][j];
                        sumB += ColorUtils.getBlue(rgb) * convMatrix[i][j];
                    }
                }

                sumR = ColorUtils.correctRange(sumR);
                sumG = ColorUtils.correctRange(sumG);
                sumB = ColorUtils.correctRange(sumB);

                result.setRGB(x, y, ColorUtils.getRGB(sumR, sumG, sumB));
            }
        }

        return result;
    }

    public static int[][] getQuantizedColors(BufferedImage image, List<Parameter> parameters) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        int[] quantizationNumbers = parameters.stream()
                .mapToInt(p -> p.getValue().intValue())
                .toArray();
        int[] colorSteps = Arrays.stream(quantizationNumbers)
                .map(q -> 256 / q)
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

        /*Map<Integer, Integer> lookupMap = new HashMap<>();

        for (int pixel : pixels) {
            int closestRed = findClosestTo(ColorUtils.getRed(pixel), quantizedColors[0]);
            int closestGreen = findClosestTo(ColorUtils.getGreen(pixel), quantizedColors[1]);
            int closestBlue = findClosestTo(ColorUtils.getBlue(pixel), quantizedColors[2]);
            lookupMap.put(pixel, ColorUtils.getRGB(closestRed, closestGreen, closestBlue));
        }

        BufferedImage quantizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = lookupMap.get(pixels[i]);
        }

        quantizedImage.setRGB(0, 0, width, height, pixels, 0, width);*/

        return quantizedColors;
    }

    public static BufferedImage quantizeImage(BufferedImage image, List<Parameter> parameters) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        int[] quantizationNumbers = parameters.stream()
                .mapToInt(p -> p.getValue().intValue())
                .toArray();
        int[] colorSteps = Arrays.stream(quantizationNumbers)
                .map(q -> 256 / q)
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

        Map<Integer, Integer> lookupMap = new HashMap<>();

        for (int pixel : pixels) {
            int closestRed = findClosestTo(ColorUtils.getRed(pixel), quantizedColors[0]);
            int closestGreen = findClosestTo(ColorUtils.getGreen(pixel), quantizedColors[1]);
            int closestBlue = findClosestTo(ColorUtils.getBlue(pixel), quantizedColors[2]);
            lookupMap.put(pixel, ColorUtils.getRGB(closestRed, closestGreen, closestBlue));
        }

        BufferedImage quantizedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = lookupMap.get(pixels[i]);
        }

        quantizedImage.setRGB(0, 0, width, height, pixels, 0, width);

        return quantizedImage;
    }

    private static int findClosestTo(int value, int[] array) {
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
