package org.example.model.parameters;

public class Parameter {
    private final ParameterName name;
    private final Number minValue;
    private Number value;
    private final Number maxValue;
    private final boolean isControlled;

    public Parameter(ParameterName name, Number minValue, Number value, Number maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.value = value;
        this.maxValue = maxValue;
        this.isControlled = true;
    }

    public Parameter(ParameterName name, Number minValue, Number value, Number maxValue, boolean isControlled) {
        this.name = name;
        this.minValue = minValue;
        this.value = value;
        this.maxValue = maxValue;
        this.isControlled = isControlled;
    }

    public ParameterName getName() {
        return name;
    }

    public Number getMinValue() {
        return minValue;
    }

    public Number getValue() {
        return value;
    }

    public Number getMaxValue() {
        return maxValue;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public boolean isControlled() {
        return isControlled;
    }
}