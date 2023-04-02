package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class RobertsOperator extends ParameterizedTool {
    public RobertsOperator(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double binarization = parameters.get(0).getValue().doubleValue();
        int width = image.getWidth();
        int height = image.getHeight();

        double[][] convMatrix1 = {{1, 0}, {0, -1}};
        double[][] convMatrix2 = {{0, 1}, {-1, 0}};

        BufferedImage image1 = ImageUtils.applyConvMatrix(image, convMatrix1, ColorUtils::correctRange);
        BufferedImage image2 = ImageUtils.applyConvMatrix(image, convMatrix2, ColorUtils::correctRange);

        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                int r = ColorUtils.getRed(rgb1) + ColorUtils.getRed(rgb2);
                int g = ColorUtils.getGreen(rgb1) + ColorUtils.getGreen(rgb2);
                int b = ColorUtils.getBlue(rgb1) + ColorUtils.getBlue(rgb2);

                if (r > binarization) {
                    r = 255;
                } else {
                    r = 0;
                }
                if (g > binarization) {
                    g = 255;
                } else {
                    g = 0;
                }
                if (b > binarization) {
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