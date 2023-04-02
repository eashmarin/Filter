package ru.nsu.fit.g20209.ashmarin.model;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.parameters.ParameterFactory;
import ru.nsu.fit.g20209.ashmarin.model.parameters.ParameterName;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParametersToolMapper;
import ru.nsu.fit.g20209.ashmarin.model.tools.*;

import java.util.*;

public class Model {
    private final List<Parameter> parameterList = new LinkedList<>();
    private final Map<ToolEnum, Tool> toolMap = new HashMap<>();
    private final Map<ToolEnum, List<ParameterName>> parametersForTool = ParametersToolMapper.map();
    private final List<ParameterizedTool> parameterizedTools = new LinkedList<>();

    private Tool selectedTool;

    public Model() {
        for (ParameterName parameterName : ParameterName.values()) {
            Parameter parameter = ParameterFactory.create(parameterName);
            if (Objects.nonNull(parameter)) {
                parameterList.add(parameter);
            }
        }

        for (ToolEnum toolEnum : ToolEnum.values()) {
            List<Parameter> toolParameters = null;
            List<ParameterName> toolParametersNames = parametersForTool.get(toolEnum);
            if (Objects.nonNull(toolParametersNames)) {
                toolParameters = parameterList.stream()
                        .filter(parameter -> toolParametersNames.contains(parameter.getName()))
                        .toList();
            }

            Tool newTool = ToolFactory.create(toolEnum, toolParameters);

            if (newTool instanceof ParameterizedTool) {
                parameterizedTools.add((ParameterizedTool) newTool);
            }

            toolMap.put(toolEnum, newTool);
        }
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public Map<ToolEnum, Tool> getToolMap() {
        return toolMap;
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