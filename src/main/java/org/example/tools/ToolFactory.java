package org.example.tools;

import org.example.parameters.Parameter;

import java.util.List;

public class ToolFactory {
    public static Tool create(ToolName toolName, List<Parameter> toolParameters) {
        return switch (toolName) {
            case GAMMA_CORRECTOR -> new GammaCorrector(toolParameters);
            default -> null;
        };
    }
}
