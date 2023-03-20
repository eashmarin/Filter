package org.example.model.tools;

import org.example.model.parameters.Parameter;
import org.example.model.tools.categories.ToolCategory;

import java.util.List;
import java.util.Objects;

public abstract class ParameterizedTool extends Tool {
    protected List<Parameter> parameters;

    public ParameterizedTool(List<Parameter> parameters, ToolCategory toolCategory) {
        super(toolCategory);
        this.parameters = Objects.requireNonNull(parameters);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
