package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class OrderedDithering extends ParameterizedTool {
    public OrderedDithering(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int redColors =  parameters.get(0).getValue().intValue();
        int greenColors = parameters.get(1).getValue().intValue();
        int blueColors = parameters.get(2).getValue().intValue();
        int width = image.getWidth();
        int height = image.getHeight();
        int size = calcMatrixSize(redColors + greenColors + blueColors);

        int[][] ditherMatrix = generateDitherMatrix(size);
        double[][] normalizedMatrix = new double[size][size];

        double maxVal = size * size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                normalizedMatrix[i][j] = ditherMatrix[i][j] / maxVal;
            }
        }

        BufferedImage ditheredImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                double matrixValue = normalizedMatrix[x % size][y % size];

                double red = (ColorUtils.getRed(rgb) + (int) ((255.0 / redColors) * (matrixValue - 0.5)));
                double green = (ColorUtils.getGreen(rgb) + (int) ((255.0 / greenColors) * (matrixValue - 0.5)));
                double blue = (ColorUtils.getBlue(rgb) + (int) ((255.0 / blueColors) * (matrixValue - 0.5)));

                ditheredImage.setRGB(x, y, ColorUtils.getRGB(
                        ColorUtils.closestInPalette((int) red, redColors),
                        ColorUtils.closestInPalette((int) green, greenColors),
                        ColorUtils.closestInPalette((int) blue, blueColors)
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
            return new int[][]{
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
                    matrix[y][x] = value + 3;
                }
                if (x >= n / 2 && y >= n / 2) {
                    matrix[y][x] = value + 1;
                }
            }
        }

        return matrix;
    }
}
