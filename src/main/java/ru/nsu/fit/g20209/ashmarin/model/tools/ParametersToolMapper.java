package ru.nsu.fit.g20209.ashmarin.model.tools;

import ru.nsu.fit.g20209.ashmarin.model.parameters.ParameterName;

import java.util.List;
import java.util.Map;

public class ParametersToolMapper {
    public static Map<ToolEnum, List<ParameterName>> map() {
        return Map.ofEntries(
                Map.entry(ToolEnum.GAMMA_CORRECTOR, List.of(ParameterName.GAMMA)),
                Map.entry(ToolEnum.GAUSS_FILTER, List.of(ParameterName.GAUSS_FILTER_WINDOW_SIZE)),
                Map.entry(ToolEnum.ROBERTS_OPERATOR, List.of(ParameterName.BINARIZATION)),
                Map.entry(ToolEnum.SOBEL_OPERATOR, List.of(ParameterName.BINARIZATION)),
                Map.entry(ToolEnum.FLOYD_STEINBERG_DITHER, List.of(
                        ParameterName.QUANTIZATION_NUMBER_RED,
                        ParameterName.QUANTIZATION_NUMBER_GREEN,
                        ParameterName.QUANTIZATION_NUMBER_BLUE)),
                Map.entry(ToolEnum.ORDERED_DITHER, List.of(
                        ParameterName.QUANTIZATION_NUMBER_RED,
                        ParameterName.QUANTIZATION_NUMBER_GREEN,
                        ParameterName.QUANTIZATION_NUMBER_BLUE)),
                Map.entry(ToolEnum.ROTATOR, List.of(ParameterName.IMAGE_ANGLE)),
                Map.entry(ToolEnum.RESIZER, List.of(ParameterName.FRAME_WIDTH, ParameterName.FRAME_HEIGHT, ParameterName.RESIZE_TYPE)),
                Map.entry(ToolEnum.PIXELATE, List.of(ParameterName.BLOCK_SIZE))
        );
    }
}
