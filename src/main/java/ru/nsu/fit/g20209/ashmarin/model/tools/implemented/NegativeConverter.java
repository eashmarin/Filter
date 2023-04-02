package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.function.UnaryOperator;

public class NegativeConverter implements Tool {
    public NegativeConverter() {
        super();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double[][] convMatrix = new double[][] {
                {0, 0, 0},
                {0, -1, 0},
                {0, 0, 0}
        };
        UnaryOperator<Integer> postOperator = (x) -> 255 + x;
        return ImageUtils.applyConvMatrix(image, convMatrix, postOperator);
    }
}
