package org.example.ui.parameters;

import org.example.model.parameters.Parameter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class SliderTextPanel extends ParameterPanel {
    private final Parameter parameter;

    public SliderTextPanel(Parameter parameter) {
        this.parameter = parameter;

        label = new JLabel(String.format("%s:", parameter.getName().toString()));
        slider = new JSlider(parameter.getMinValue().intValue(), parameter.getMaxValue().intValue(), parameter.getValue().intValue());
        textField = new JTextField(String.valueOf(slider.getValue()));
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        initComponents();
        connectSliderText();
        addComponents();
    }

    public SliderTextPanel(Parameter parameter, Hashtable labelTable) {
        this.parameter = parameter;

        slider = new JSlider((int) (parameter.getMinValue().doubleValue() * 10), (int) (parameter.getMaxValue().doubleValue() * 10), (int) (parameter.getValue().doubleValue() * 10));
        slider.setLabelTable(labelTable);
        label = new JLabel(String.format("%s:", parameter.getName().toString()));
        textField = new JTextField(String.valueOf((double) slider.getValue() / 10));
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        initComponents();

        slider.addChangeListener(e -> {
            textField.setText(String.valueOf((double) slider.getValue() / 10));
            if (errorLabel.isVisible()) {
                errorLabel.setVisible(false);
            }
            System.out.println(slider.getValue());
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    int value = (int) Double.parseDouble(textField.getText()) * 10;
                    if (value >= slider.getMinimum() && value <= slider.getMaximum()) {
                        slider.setValue(value);
                        if (errorLabel.isVisible()) {
                            errorLabel.setVisible(false);
                        }
                    } else {
                        showRealError();
                    }
                } catch (NumberFormatException ex) {
                    showRealError();
                }
            }
        });

        addComponents();
    }

    private void initComponents() {
        label.setMinimumSize(new Dimension(50, 25));
        label.setMaximumSize(new Dimension(100, 25));
        slider.setMinimumSize(new Dimension(300, 30));
        slider.setPreferredSize(new Dimension(300, 30));
        slider.setMaximumSize(new Dimension(300, 30));
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);

        textField.setMinimumSize(new Dimension(45, 25));
        textField.setPreferredSize(new Dimension(45, 25));
        textField.setMaximumSize(new Dimension(45, 25));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setAlignmentX(Component.RIGHT_ALIGNMENT);

        errorLabel.setVisible(false);
    }

    private void connectSliderText() {
        slider.addChangeListener(e -> {
            textField.setText(String.valueOf(slider.getValue()));
            if (errorLabel.isVisible()) {
                errorLabel.setVisible(false);
            }
            System.out.println(slider.getValue());
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    int value =  Integer.parseInt(textField.getText());
                    if (value >= slider.getMinimum() && value <= slider.getMaximum()) {
                        slider.setValue(value);
                        if (errorLabel.isVisible()) {
                            errorLabel.setVisible(false);
                        }
                    } else {
                        showError();
                    }
                } catch (NumberFormatException ex) {
                    showError();
                }
            }
        });
    }

    private void showRealError() {
        errorLabel.setText(String.format("Invalid value: out of range (%f, %f)", parameter.getMinValue().doubleValue(), parameter.getMaxValue().doubleValue()));
        errorLabel.setVisible(true);
    }

    private void showError() {
        errorLabel.setText(String.format("Invalid value: out of range (%d, %d)", slider.getMinimum(), slider.getMaximum()));
        errorLabel.setVisible(true);
    }

    private void addComponents() {
        setPreferredSize(new Dimension(400, 55));

        Box box = new Box(BoxLayout.X_AXIS);
        box.add(label);
        box.add(slider);
        box.add(Box.createHorizontalStrut(10));
        box.add(textField);

        Box errorBox = new Box(BoxLayout.X_AXIS);
        errorBox.add(Box.createHorizontalGlue());
        errorBox.add(errorLabel);

        add(box);
        add(errorBox);
    }
}