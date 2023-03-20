package org.example.model.parameters;

public class ParameterFactory {
    public static Parameter create(ParameterName parameterName) {
        return switch (parameterName) {
            case GAMMA -> new Parameter(parameterName, 0.1, 5.0, 10.0);
            case BINARIZATION -> new Parameter(parameterName, 0, 100, 255);
            case GAUSS_FILTER_WINDOW_SIZE -> new Parameter(parameterName, 3, 3, 5);
            case QUANTIZATION_NUMBER_RED, QUANTIZATION_NUMBER_GREEN, QUANTIZATION_NUMBER_BLUE ->
                    new Parameter(parameterName, 2, 2, 128);
            case IMAGE_ANGLE -> new Parameter(parameterName, -180, 0, 180);
            case FRAME_WIDTH -> new Parameter(parameterName, 640, 800, Integer.MAX_VALUE, false);
            case FRAME_HEIGHT -> new Parameter(parameterName, 480, 600, Integer.MAX_VALUE, false);
            default -> null;
        };
    }
}
