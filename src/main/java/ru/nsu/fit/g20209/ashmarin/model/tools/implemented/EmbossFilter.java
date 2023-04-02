package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.function.UnaryOperator;

public class EmbossFilter implements Tool {
    public EmbossFilter() {
        super();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double[][] convolutionMatrix = {{0, 1, 0}, {-1, 0, 1}, {0, -1, 0}};
        UnaryOperator<Integer> postOperator = (x) -> ColorUtils.correctRange(x + 128);

        return ImageUtils.applyConvMatrix(image, convolutionMatrix, postOperator);
    }
}
