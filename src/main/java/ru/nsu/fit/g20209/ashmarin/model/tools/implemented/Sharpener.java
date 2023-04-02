package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class Sharpener implements Tool {
    public Sharpener() {
        super();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double[][] convolutionMatrix = new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        return ImageUtils.applyConvMatrix(image, convolutionMatrix, ColorUtils::correctRange);
    }
}
