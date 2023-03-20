package org.example.model.tools;

import org.example.model.tools.categories.ToolCategory;

import java.awt.image.BufferedImage;

public abstract class Tool {
    protected final ToolCategory toolCategory;

    public Tool(ToolCategory toolCategory) {
        this.toolCategory = toolCategory;
    }

    public abstract BufferedImage apply(BufferedImage image);

    public ToolCategory getToolCategory() {
        return toolCategory;
    }
}
