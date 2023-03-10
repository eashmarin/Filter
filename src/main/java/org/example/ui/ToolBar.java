package org.example.ui;

import org.example.tools.ToolName;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JToolBar {
    private final ToolButton monochromeBtn;

    public ToolBar() {
        super();

        monochromeBtn = new ToolButton(ToolName.MONOCHROME_CONVERTER);

        monochromeBtn.setName(ToolName.MONOCHROME_CONVERTER.toString());

        add(monochromeBtn);
        setBackground(Color.WHITE);

        setFloatable(false);
    }

    public void addToolButtonsActionListener(AbstractAction abstractAction) {
        monochromeBtn.addActionListener(abstractAction);
    }
}
