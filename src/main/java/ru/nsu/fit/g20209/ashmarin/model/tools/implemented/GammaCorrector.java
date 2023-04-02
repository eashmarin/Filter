package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class GammaCorrector extends ParameterizedTool {
    public GammaCorrector(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double gamma = parameters.get(0).getValue().doubleValue();
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage correctedImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int r = ColorUtils.getRed(rgb);
                int g = ColorUtils.getGreen(rgb);
                int b = ColorUtils.getBlue(rgb);

                r = (int) (Math.pow(r / 255.0, gamma) * 255);
                g = (int) (Math.pow(g / 255.0, gamma) * 255);
                b = (int) (Math.pow(b / 255.0, gamma) * 255);

                correctedImage.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }

        return correctedImage;
    }
}
