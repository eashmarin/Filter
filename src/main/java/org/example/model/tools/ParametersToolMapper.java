package org.example.model.tools;

import org.example.model.parameters.ParameterName;
import org.example.model.tools.ToolName;

import java.util.List;
import java.util.Map;

public class ParametersToolMapper {
    public static Map<ToolName, List<ParameterName>> map() {
        return Map.ofEntries(
                Map.entry(ToolName.GAMMA_CORRECTOR, List.of(ParameterName.GAMMA)),
                Map.entry(ToolName.GAUSS_FILTER, List.of(ParameterName.GAUSS_FILTER_WINDOW_SIZE)),
                Map.entry(ToolName.ROBERTS_OPERATOR, List.of(ParameterName.BINARIZATION)),
                Map.entry(ToolName.SOBEL_OPERATOR, List.of(ParameterName.BINARIZATION)),
                Map.entry(ToolName.FLOYD_STEINBERG_DITHER, List.of(
                        ParameterName.QUANTIZATION_NUMBER_RED,
                        ParameterName.QUANTIZATION_NUMBER_GREEN,
                        ParameterName.QUANTIZATION_NUMBER_BLUE)),
                Map.entry(ToolName.ORDERED_DITHER, List.of(
                        ParameterName.QUANTIZATION_NUMBER_RED,
                        ParameterName.QUANTIZATION_NUMBER_GREEN,
                        ParameterName.QUANTIZATION_NUMBER_BLUE)),
                Map.entry(ToolName.IMAGE_ROTATOR, List.of(ParameterName.IMAGE_ANGLE)),
                Map.entry(ToolName.SHRINKER, List.of(ParameterName.FRAME_WIDTH, ParameterName.FRAME_HEIGHT))
        );
    }
}
