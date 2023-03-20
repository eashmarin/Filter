package org.example.model;

import org.example.model.tools.*;
import org.example.model.parameters.Parameter;
import org.example.model.parameters.ParameterFactory;
import org.example.model.parameters.ParameterName;
import org.example.model.tools.ParametersToolMapper;

import java.util.*;

public class Model {
    private final List<Parameter> parameterList = new LinkedList<>();
    private final Map<ToolName, Tool> toolMap = new HashMap<>();
    private final Map<ToolName, List<ParameterName>> parametersForTool = ParametersToolMapper.map();
    private final List<Tool> nonParameterizedTools = new LinkedList<>();
    private final List<ParameterizedTool> parameterizedTools = new LinkedList<>();

    private Tool selectedTool;

    public Model() {
        for (ParameterName parameterName : ParameterName.values()) {
            Parameter parameter = ParameterFactory.create(parameterName);
            if (Objects.nonNull(parameter)) {
                parameterList.add(parameter);
            }
        }

        for (ToolName toolName : ToolName.values()) {
            List<Parameter> toolParameters = null;
            List<ParameterName> toolParametersNames = parametersForTool.get(toolName);
            if (Objects.nonNull(toolParametersNames)) {
                toolParameters = parameterList.stream()
                        .filter(parameter -> toolParametersNames.contains(parameter.getName()))
                        .toList();
            }

            Tool newTool = ToolFactory.create(toolName, toolParameters);

            if (newTool instanceof ParameterizedTool) {
                parameterizedTools.add((ParameterizedTool) newTool);
            } else {
                nonParameterizedTools.add(newTool);
            }

            toolMap.put(toolName, newTool);
        }
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public Map<ToolName, Tool> getToolMap() {
        return toolMap;
    }

    public List<Tool> getNonParameterizedTools() {
        return nonParameterizedTools;
    }

    public List<ParameterizedTool> getParameterizedTools() {
        return parameterizedTools;
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }
}
