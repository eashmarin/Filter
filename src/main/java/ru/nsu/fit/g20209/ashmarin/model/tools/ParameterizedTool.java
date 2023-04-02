package ru.nsu.fit.g20209.ashmarin.model.tools;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;

import java.util.List;
import java.util.Objects;

public abstract class ParameterizedTool implements Tool {
    protected List<Parameter> parameters;

    public ParameterizedTool(List<Parameter> parameters) {
        super();
        this.parameters = Objects.requireNonNull(parameters);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
