package ru.nsu.fit.g20209.ashmarin.ui.parameters;

import ru.nsu.fit.g20209.ashmarin.controllers.ParameterController;

import javax.swing.*;
import java.text.NumberFormat;
import java.text.ParseException;

public abstract class ParameterPanel extends JPanel {
    protected JLabel label;
    protected JSlider slider;
    protected JTextField textField;
    protected JLabel errorLabel;

    public Number getParameterValue() {
        try {
            return NumberFormat.getInstance().parse(textField.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertControllerActions(ParameterController parameterController) {
        if (this instanceof SliderTextPanel) {
            ((SliderTextPanel)this).insertControllerActions(parameterController);
        }
    }
}