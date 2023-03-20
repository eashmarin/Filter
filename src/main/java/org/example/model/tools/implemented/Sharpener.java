package org.example.model.tools.implemented;

import org.example.model.tools.Tool;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sharpener extends Tool {
    public Sharpener(ToolCategory toolCategory) {
        super(toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        double[][] convolutionMatrix = new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        /*BufferedImage scaled = ImageUtils.scaleBufferedImage(image, image.getWidth() / 2, image.getHeight() / 2);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRect(0, 0, scaled.getWidth() - 1, scaled.getHeight() - 1);
        graphics2D.drawImage(scaled, 0, 0 , null);
        graphics2D.dispose();

        image = scaled;*/

        return ImageUtils.applyConvMatrix(image, convolutionMatrix);
    }
}
