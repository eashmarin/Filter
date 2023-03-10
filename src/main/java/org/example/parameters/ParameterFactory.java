package org.example.parameters;

public class ParameterFactory {
    public static Parameter create(ParameterName parameterName) {
        return switch (parameterName) {
            case GAMMA -> new Parameter(parameterName, 0.1, 5.0, 11.0);
            case BINARIZATION -> new Parameter(parameterName, 1, 2, 11);
            default -> null;
        };
    }
}
