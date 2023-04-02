package ru.nsu.fit.g20209.ashmarin.controllers;

import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolCategory;
import ru.nsu.fit.g20209.ashmarin.ui.Frame;
import ru.nsu.fit.g20209.ashmarin.ui.MainPanel;
import ru.nsu.fit.g20209.ashmarin.ui.parameters.ParameterDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ParameterController extends Controller {
    private Runnable okRunnable;
    private final ParameterDialog parameterDialog;

    public ParameterController(Frame frame, Model model) {
        super(frame, model);
        parameterDialog = frame.getParameterDialog();
    }

    @Override
    public void toolButtonClicked(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();

        ToolEnum toolEnum = ToolEnum.valueOf(button.getName());
        ParameterizedTool parameterizedTool = (ParameterizedTool) model.getToolMap().get(toolEnum);

        List<Parameter> manualParameters = parameterizedTool.getParameters().stream().filter(Parameter::isControlled).toList();

        if (manualParameters.size() > 0) {
            frame.getParameterDialog().setParameters(toolEnum, manualParameters);
            frame.getParameterDialog().setVisible(true);
        } else {
            mainPanel.setCurrentCanvas(parameterizedTool.apply(mainPanel.getClearCanvas()));
            mainPanel.setAppliedCanvas(mainPanel.getCurrentCanvas());
            frame.getMainPanel().repaint();
            model.setSelectedTool(parameterizedTool);

            if (Objects.nonNull(okRunnable)) {
                okRunnable.run();
            }
        }
    }

    public void okBtnClicked(ActionEvent e) {
        ((Component) parameterDialog).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ((Component) frame).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        ToolEnum toolEnum = ToolEnum.valueOf(parameterDialog.getName());
        ParameterizedTool tool = (ParameterizedTool) model.getToolMap().get(toolEnum);

        for (Parameter parameter : model.getParameterList()) {
            if (tool.getParameters().contains(parameter) && parameter.isControlled()) {
                double chosenValue = parameterDialog.getValue(parameter.getName()).doubleValue();
                if (parameter.getMaxValue().doubleValue() < chosenValue || chosenValue < parameter.getMinValue().doubleValue()) {
                    return;
                }
                parameter.setValue(chosenValue);
            }
        }
        BufferedImage inputImage;
        if (toolEnum.getCategory().equals(ToolCategory.MODIFY)) {
            inputImage = mainPanel.getCurrentCanvas();
        } else {
            inputImage = mainPanel.getClearCanvas();
        }

        MainPanel mainPanel = frame.getMainPanel();
        mainPanel.setCurrentCanvas(tool.apply(inputImage));
        mainPanel.setAppliedCanvas(mainPanel.getCurrentCanvas());
        mainPanel.setPreferredSize(new Dimension(mainPanel.getAppliedCanvas().getWidth(), mainPanel.getAppliedCanvas().getHeight()));
        mainPanel.dispatchEvent(new ComponentEvent(mainPanel, ComponentEvent.COMPONENT_RESIZED));
        frame.getMainPanel().repaint();

        ((Component) parameterDialog).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        ((Component) frame).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        model.setSelectedTool(tool);

        parameterDialog.dispose();
        if (toolEnum.getCategory().equals(ToolCategory.MODIFY)) {
            okRunnable.run();
        }
    }

    public void sliderChanged(JSlider slider, JTextField textField,
                              JLabel errorLabel,
                              UnaryOperator<Number> straightOperator) {
        textField.setText(String.valueOf(straightOperator.apply((double) slider.getValue())));
        if (errorLabel.isVisible()) {
            errorLabel.setVisible(false);
            ((JDialog) slider.getTopLevelAncestor()).pack();
        }
    }

    public void textChanged(JSlider slider, JTextField textField, JLabel errorLabel,
                            UnaryOperator<Number> reversedOperator,
                            Parameter parameter) {
        try {
            double reversedValue = reversedOperator.apply(NumberFormat.getInstance().parse(textField.getText())).doubleValue();
            int value = Math.toIntExact(Math.round(reversedValue));
            if (value >= slider.getMinimum() && value <= slider.getMaximum()) {
                slider.setValue(value);
                if (errorLabel.isVisible()) {
                    errorLabel.setVisible(false);
                    ((JDialog) slider.getTopLevelAncestor()).pack();
                }
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException | ParseException ex) {
            showError(errorLabel, parameter);
        }
    }

    private void showError(JLabel errorLabel, Parameter parameter) {
        errorLabel.setText(String.format("Invalid value: out of range (%s, %s)",
                NumberFormat.getInstance().format(parameter.getMinValue()),
                NumberFormat.getInstance().format(parameter.getMaxValue()))
        );
        errorLabel.setVisible(true);
        ((JDialog) errorLabel.getTopLevelAncestor()).pack();
    }

    public void cancelBtnClicked(ActionEvent e) {
        parameterDialog.dispose();
    }

    public void setOkRunnable(Runnable runnable) {
        okRunnable = runnable;
    }
}
