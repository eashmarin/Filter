package org.example.ui;

import org.example.parameters.Parameter;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class ParameterDialog extends JDialog {
    private static ParameterDialog instance;

    private ParameterDialog(List<Parameter> parameters) {
        super();

        setTitle(parameters.get(0).getParameterName().toString());
    }

    public static void showUp(List<Parameter> parameters) {
        Objects.requireNonNull(parameters);

        instance = new ParameterDialog(parameters);
        instance.setVisible(true);
        instance.pack();
    }
}
