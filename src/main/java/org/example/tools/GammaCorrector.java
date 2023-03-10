package org.example.tools;

import org.example.parameters.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class GammaCorrector extends ParameterizedTool {
    public GammaCorrector(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public void apply(BufferedImage image) {

    }
}
