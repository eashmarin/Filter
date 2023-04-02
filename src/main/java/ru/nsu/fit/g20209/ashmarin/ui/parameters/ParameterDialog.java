package ru.nsu.fit.g20209.ashmarin.ui.parameters;

import ru.nsu.fit.g20209.ashmarin.controllers.ParameterController;
import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.parameters.ParameterName;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;
import ru.nsu.fit.g20209.ashmarin.model.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParameterDialog extends JDialog {
    private final Map<ParameterName, ParameterPanel> parametersMap;
    private final List<ParameterPanel> parameterPanels;

    private ParameterController parameterController;

    private final JPanel contentPanel;
    private final JButton okBtn;
    private final JButton cancelBtn;

    public ParameterDialog() {
        parametersMap = new HashMap<>();
        parameterPanels = new LinkedList<>();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");

        Box downBox = new Box(BoxLayout.X_AXIS);
        downBox.add(okBtn);
        downBox.add(Box.createHorizontalGlue());
        downBox.add(cancelBtn);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(contentPanel, BorderLayout.CENTER);
        container.add(downBox, BorderLayout.SOUTH);

        setResizable(false);
        SwingUtils.centerOnParent(this, false);
    }

    public void insertControllerActions(ParameterController parameterController) {
        this.parameterController = parameterController;
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

    public void setParameters(ToolEnum toolEnum, List<Parameter> parameters) {
        setName(toolEnum.toString());
        setTitle(toolEnum.getTitle());

        contentPanel.removeAll();

        parameters.forEach(parameter -> {
            ParameterPanel parameterPanel;
            if (parameter.getName().equals(ParameterName.RESIZE_TYPE)) {
                parameterPanel = new RadioButtonsPanel();
            } else {
                parameterPanel = new SliderTextPanel(
                        parameter,
                        parameter.getSliderToTextOperator(),
                        parameter.getTextToSliderOperator()
                );
            }

            parameterPanels.add(parameterPanel);
            parametersMap.put(parameter.getName(), parameterPanel);

            contentPanel.add(parameterPanel);
        });

        parameterPanels.forEach(parameterPanel -> parameterPanel.insertControllerActions(parameterController));
        pack();
    }

    public Number getValue(ParameterName parameterName) {
        return parametersMap.get(parameterName).getParameterValue();
    }
}
