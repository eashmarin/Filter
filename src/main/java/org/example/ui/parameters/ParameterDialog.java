package org.example.ui.parameters;

import org.example.controllers.Controller;
import org.example.controllers.ParameterController;
import org.example.model.parameters.Parameter;
import org.example.model.parameters.ParameterName;
import org.example.model.tools.ToolName;
import org.example.model.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class ParameterDialog extends JDialog {
    private final Map<ParameterName, SliderTextPanel> parametersMap;
    private final List<SliderTextPanel> parameterPanels;

    private final JPanel contentPanel;
    private JButton okBtn;
    private JButton cancelBtn;

    public ParameterDialog() {
        parametersMap = new HashMap<>();
        parameterPanels = new LinkedList<>();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");

        Box downBox = new Box(BoxLayout.X_AXIS);
        downBox.add(okBtn);
        downBox.add(cancelBtn);

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(contentPanel);
        container.add(downBox);

        SwingUtils.centerOnParent(this, false);
    }

    public void insertControllerActions(ParameterController parameterController) {
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parameterController.okBtnClicked(e);
            }
        });
        cancelBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parameterController.cancelBtnClicked(e);
            }
        });
    }

    /*public static void showUp(List<Parameter> parameters) {
        Objects.requireNonNull(parameters);

        instance = new ParameterDialog(parameters);
        instance.setVisible(true);
        instance.pack();
    }*/

    public void setParameters(ToolName toolName, List<Parameter> parameters) {
        setName(toolName.toString());
        setTitle(parameters.get(0).getName().toString());

        contentPanel.removeAll();

        parameters.forEach(parameter -> {
            SliderTextPanel parameterPanel;

            if (parameter.getMinValue().intValue() == parameter.getMinValue().doubleValue()) {
                parameterPanel = new SliderTextPanel(parameter);
            } else {
                Hashtable labelTable = new Hashtable();
                for (double value = parameter.getMinValue().doubleValue(); value < parameter.getMaxValue().intValue(); value += 0.1) {
                    labelTable.put(Integer.valueOf((int) (value)), new JLabel(String.valueOf(value / 10)));
                }
                parameterPanel = new SliderTextPanel(parameter, labelTable);
            }

            parameterPanels.add(parameterPanel);
            parametersMap.put(parameter.getName(), parameterPanel);

            contentPanel.add(parameterPanel);
        });
        pack();
    }

    public Number getValue(ParameterName parameterName) {
        return parametersMap.get(parameterName).getParameterValue();
    }
}
