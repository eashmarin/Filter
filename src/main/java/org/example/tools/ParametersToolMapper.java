package org.example.tools;

import org.example.parameters.ParameterName;

import java.util.List;
import java.util.Map;

public class ParametersToolMapper {
    public static Map<ToolName, List<ParameterName>> map() {
        return Map.ofEntries(
                Map.entry(ToolName.GAMMA_CORRECTOR, List.of(ParameterName.GAMMA)),
                Map.entry(ToolName.GAUSS_FILTER, List.of(ParameterName.GAUSS_FILTER_WINDOW_SIZE))
        );
    }
}
