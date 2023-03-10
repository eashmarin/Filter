package org.example.tools;

import org.example.parameters.Parameter;

import java.util.List;
import java.util.Objects;

public abstract class ParameterizedTool extends Tool {
    protected List<Parameter> parameters;

    public ParameterizedTool(List<Parameter> parameters) {
        this.parameters = Objects.requireNonNull(parameters);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
