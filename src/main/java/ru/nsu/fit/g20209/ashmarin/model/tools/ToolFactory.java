package ru.nsu.fit.g20209.ashmarin.model.tools;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.implemented.*;

import java.util.List;
import java.util.NoSuchElementException;

public class ToolFactory {
    public static Tool create(ToolEnum toolEnum, List<Parameter> toolParameters) {
        return switch (toolEnum) {
            case GRAYSCALE -> new GrayscaleConverter();
            case GAMMA_CORRECTOR -> new GammaCorrector(toolParameters);
            case GAUSS_FILTER -> new GaussFilter(toolParameters);
            case EMBOSSER -> new EmbossFilter();
            case NEGATIVE -> new NegativeConverter();
            case SHARPENER -> new Sharpener();
            case WATERCOLORIZER -> new Watercolorizer();
            case ROBERTS_OPERATOR -> new RobertsOperator(toolParameters);
            case SOBEL_OPERATOR -> new SobelOperator(toolParameters);
            case FLOYD_STEINBERG_DITHER -> new DitheringFilter(toolParameters);
            case ORDERED_DITHER -> new OrderedDithering(toolParameters);
            case ROTATOR -> new Rotator(toolParameters);
            case RESIZER -> new Resizer(toolParameters);
            case PIXELATE -> new PixelateFilter(toolParameters);
            default -> throw new NoSuchElementException();
        };
    }
}
