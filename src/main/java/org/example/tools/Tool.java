package org.example.tools;

import java.awt.image.BufferedImage;

public abstract class Tool {
    protected ToolName toolName;

    public abstract void apply(BufferedImage image);

    public ToolName getToolName() {
        return toolName;
    }
}
