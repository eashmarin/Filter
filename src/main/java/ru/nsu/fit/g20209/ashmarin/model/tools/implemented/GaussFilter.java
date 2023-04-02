package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.UnaryOperator;

public class GaussFilter extends ParameterizedTool {
    public GaussFilter(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int windowSize = parameters.get(0).getValue().intValue();

        double[][] convMatrix = new double[windowSize][windowSize];
        for (int i = 0; i < windowSize; i++) {
            for (int j = 0; j < windowSize; j++) {
                convMatrix[i][j] = 1;
            }
        }

        UnaryOperator<Integer> postOperator = (x) -> x / (windowSize * windowSize);
        return ImageUtils.applyConvMatrix(image, convMatrix, postOperator);
        /*BufferedImage filteredImage = ImageUtils.copyOf(image);

        int divider = windowSize * windowSize;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0;
                int sumG = 0;
                int sumB = 0;
                for (int j = 0; j < windowSize; j++) {
                    for (int i = 0; i < windowSize; i++) {
                        int rgb = image.getRGB(x + i - ((x + i) / width) * (x + i + 1) % width, y + j - ((y + j) / height) * (y + j + 1) % height);

                        sumR += ColorUtils.getRed(rgb);
                        sumG += ColorUtils.getGreen(rgb);
                        sumB += ColorUtils.getBlue(rgb);
                    }
                }

                filteredImage.setRGB(x, y, ColorUtils.getRGB(sumR / divider, sumG / divider, sumB / divider));
            }
        }

        return filteredImage;*/
    }
}
