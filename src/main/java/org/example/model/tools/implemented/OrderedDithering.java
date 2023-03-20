package org.example.model.tools.implemented;

import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;
import org.example.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class OrderedDithering extends ParameterizedTool {
    public OrderedDithering(List<Parameter> parameters, ToolCategory toolCategory) {
        super(parameters, toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = calcMatrixSize(parameters.get(0).getValue().intValue());

        int[][] ditherMatrix = generateDitherMatrix(size);
        double[][] normalizedMatrix = new double[size][size];

        double maxVal = size * size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                normalizedMatrix[i][j] = ditherMatrix[i][j] / maxVal;
            }
        }

        int[][] quantizedColors = ImageUtils.getQuantizedColors(image, parameters);

        BufferedImage ditheredImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                double matrixValue = normalizedMatrix[x % size][y % size];

                int red = (ColorUtils.getRed(rgb) + (int) ((255 / quantizedColors[0].length) * matrixValue));
                int green = (ColorUtils.getGreen(rgb) + (int) ((255 / quantizedColors[1].length) * matrixValue));
                int blue = (ColorUtils.getBlue(rgb) + (int) ((255 / quantizedColors[2].length) * matrixValue));

                ditheredImage.setRGB(x, y, ColorUtils.getRGB(
                        findClosestTo(red, quantizedColors[0]),
                        findClosestTo(green, quantizedColors[1]),
                        findClosestTo(blue, quantizedColors[2])
                ));
            }
        }

        return ditheredImage;
    }

    private int calcMatrixSize(int colorVariants) {
        int size = 1;
        do {
            size *= 2;
        } while (Math.pow(size, 2) < colorVariants);

        return size;
    }

    private int[][] generateDitherMatrix(int n) {
        if (n == 2) {
            return new int[][] {
                    {0, 2},
                    {3, 1}
            };
        }

        int[][] prevMatrix = generateDitherMatrix(n / 2);
        int[][] matrix = new int[n][n];

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                int value = 4 * prevMatrix[y % (n / 2)][x % (n / 2)];
                if (x < n / 2 && y < n / 2) {
                    matrix[y][x] = value;
                }
                if (x >= n / 2 && y < n / 2) {
                    matrix[y][x] = value + 2;
                }
                if (x < n / 2 && y >= n / 2) {
                    matrix[y][x] = value  + 3;
                }
                if (x >= n / 2 && y >= n / 2) {
                    matrix[y][x] = value + 1;
                }
            }
        }

        return matrix;
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
