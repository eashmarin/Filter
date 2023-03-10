package org.example;

import org.example.parameters.Parameter;
import org.example.parameters.ParameterFactory;
import org.example.parameters.ParameterName;
import org.example.tools.ParametersToolMapper;
import org.example.tools.Tool;
import org.example.tools.ToolFactory;
import org.example.tools.ToolName;
import org.example.ui.Frame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Initializer implements Runnable {
    private final List<Parameter> parameterList = new LinkedList<>();
    private final Map<ToolName, List<ParameterName>> parametersForTool = ParametersToolMapper.map();
    private final List<Tool> toolList = new LinkedList<>();

    @Override
    public void run() {
        for (ParameterName parameterName : ParameterName.values()) {
            Parameter parameter = ParameterFactory.create(parameterName);
            if (Objects.nonNull(parameter)) {
                parameterList.add(parameter);
            }
        }

        for (ToolName toolName : ToolName.values()) {
            List<Parameter> toolParameters = null;
            if (Objects.nonNull(parametersForTool.get(toolName))) {
                toolParameters = parameterList
                        .stream()
                        .filter(p -> parametersForTool.get(toolName).contains(p.getParameterName()))
                        .toList();

            }

            Tool newTool = ToolFactory.create(toolName, toolParameters);
            toolList.add(newTool);
        }

        Frame frame = new Frame("ICGFilter");
    }
}
