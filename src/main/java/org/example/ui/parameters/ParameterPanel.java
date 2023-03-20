package org.example.ui.parameters;

import javax.swing.*;

public abstract class ParameterPanel extends JPanel {
    protected JLabel label;
    protected JSlider slider;
    protected JTextField textField;
    protected JLabel errorLabel;

    public double getParameterValue() {
        return Double.parseDouble(textField.getText());
    }
}