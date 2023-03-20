package org.example.model.tools.implemented;

import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class Shrinker extends ParameterizedTool {
    public Shrinker(List<Parameter> parameters, ToolCategory toolCategory) {
        super(parameters, toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = parameters.get(0).getValue().intValue();
        int height = parameters.get(1).getValue().intValue();
        System.out.println("width = " + width + "; height = " + height);
        return ImageUtils.scaleBufferedImage(image, width, height);
    }
}
