package ru.nsu.fit.g20209.ashmarin.model.parameters;

import java.util.function.UnaryOperator;

public class Parameter {
    private final ParameterName name;

    private final Number minValue;
    private Number value;
    private final Number maxValue;

    private final boolean isControlled;

    private UnaryOperator<Number> sliderToTextOperator = Number::intValue;
    private UnaryOperator<Number> textToSliderOperator = Number::intValue;

    public Parameter(ParameterName name, Number minValue, Number value, Number maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.value = value;
        this.maxValue = maxValue;
        this.isControlled = true;
    }

    public Parameter(ParameterName name,
                     Number minValue,
                     Number value,
                     Number maxValue,
                     UnaryOperator<Number> sliderToTextOperator,
                     UnaryOperator<Number> textToSliderOperator) {
        this.name = name;
        this.minValue = minValue;
        this.value = value;
        this.maxValue = maxValue;
        this.sliderToTextOperator = sliderToTextOperator;
        this.textToSliderOperator = textToSliderOperator;
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

    public UnaryOperator<Number> getSliderToTextOperator() {
        return sliderToTextOperator;
    }

    public UnaryOperator<Number> getTextToSliderOperator() {
        return textToSliderOperator;
    }
}