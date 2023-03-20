package org.example.model.tools;

import org.example.model.parameters.Parameter;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.tools.implemented.*;

import java.util.List;

public class ToolFactory {
    public static Tool create(ToolName toolName, List<Parameter> toolParameters) {
        return switch (toolName) {
            case GRAYSCALE -> new GrayscaleConverter(ToolCategory.NO_MODIFY);
            case GAMMA_CORRECTOR -> new GammaCorrector(toolParameters, ToolCategory.NO_MODIFY);
            case GAUSS_FILTER -> new GaussFilter(toolParameters, ToolCategory.NO_MODIFY);
            case EMBOSSER -> new EmbossFilter(ToolCategory.NO_MODIFY);
            case NEGATIVE -> new NegativeConverter(ToolCategory.NO_MODIFY);
            case SHARPENER -> new Sharpener(ToolCategory.NO_MODIFY);
            case WATERCOLORIZER -> new Watercolorizer(ToolCategory.NO_MODIFY);
            case ROBERTS_OPERATOR -> new RobertsOperator(toolParameters, ToolCategory.NO_MODIFY);
            case SOBEL_OPERATOR -> new SobelOperator(toolParameters, ToolCategory.NO_MODIFY);
            case FLOYD_STEINBERG_DITHER -> new DitheringFilter(toolParameters, ToolCategory.NO_MODIFY);
            case ORDERED_DITHER -> new OrderedDithering(toolParameters, ToolCategory.NO_MODIFY);
            case IMAGE_ROTATOR -> new ImageRotator(toolParameters, ToolCategory.MODIFY);
            case SHRINKER -> new Shrinker(toolParameters, ToolCategory.MODIFY);
            default -> null;
        };
    }
}
