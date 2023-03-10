package org.example.parameters;

public class Parameter {
    private final ParameterName parameterName;
    private final Number minValue;
    private final Number currValue;
    private final Number maxValue;

    public Parameter(ParameterName parameterName, Number minValue, Number currValue, Number maxValue) {
        this.parameterName = parameterName;
        this.minValue = minValue;
        this.currValue = currValue;
        this.maxValue = maxValue;
    }

    public ParameterName getParameterName() {
        return parameterName;
    }

    public Number getMinValue() {
        return minValue;
    }

    public Number getCurrValue() {
        return currValue;
    }

    public Number getMaxValue() {
        return maxValue;
    }
}