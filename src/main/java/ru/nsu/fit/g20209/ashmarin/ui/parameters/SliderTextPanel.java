package ru.nsu.fit.g20209.ashmarin.ui.parameters;

import ru.nsu.fit.g20209.ashmarin.controllers.ParameterController;
import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.UnaryOperator;

public class SliderTextPanel extends ParameterPanel {
    private final Parameter parameter;
    private final UnaryOperator<Number> straightOperator;
    private final UnaryOperator<Number> reversedOperator;

    public SliderTextPanel(Parameter parameter, UnaryOperator<Number> straightOperator, UnaryOperator<Number> reversedOperator) {
        this.parameter = parameter;
        this.straightOperator = straightOperator;
        this.reversedOperator = reversedOperator;

        label = new JLabel(String.format("%s:", parameter.getName().getTitle()));
        slider = new JSlider(
                reversedOperator.apply(parameter.getMinValue().doubleValue()).intValue(),
                reversedOperator.apply(parameter.getMaxValue().doubleValue()).intValue(),
                reversedOperator.apply(parameter.getValue().doubleValue()).intValue()
        );
        textField = new JTextField(String.valueOf(straightOperator.apply((double) slider.getValue())));
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        initComponents();
        addComponents();
    }

    private void initComponents() {
        //label.setMinimumSize(new Dimension(100, 25));
        //label.setPreferredSize(new Dimension(125, 25));
        //label.setMaximumSize(new Dimension(125, 25));
        slider.setMinimumSize(new Dimension(300, 30));
        slider.setPreferredSize(new Dimension(300, 30));
        slider.setMaximumSize(new Dimension(300, 30));
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);

        textField.setMinimumSize(new Dimension(45, 25));
        textField.setPreferredSize(new Dimension(45, 25));
        textField.setMaximumSize(new Dimension(45, 25));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setAlignmentX(Component.RIGHT_ALIGNMENT);

        errorLabel.setPreferredSize(new Dimension(300, 30));
        errorLabel.setVisible(false);
    }

    public void insertControllerActions(ParameterController controller) {
        slider.addChangeListener(e -> controller.sliderChanged(slider, textField, errorLabel, straightOperator));

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                controller.textChanged(slider, textField, errorLabel, reversedOperator, parameter);
            }
        });
    }

    private void addComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setPreferredSize(new Dimension(500, 55));
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(label);
        box.add(slider);
        box.add(Box.createHorizontalGlue());
        box.add(textField);

        Box errorBox = new Box(BoxLayout.X_AXIS);
        errorBox.add(Box.createHorizontalGlue());
        errorBox.add(errorLabel);

        add(box);
        add(errorBox);
    }
}