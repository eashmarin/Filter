package org.example.model.tools.implemented;

import org.example.model.parameters.Parameter;
import org.example.model.tools.ParameterizedTool;
import org.example.model.tools.categories.ToolCategory;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageRotator extends ParameterizedTool {
    public ImageRotator(List<Parameter> parameters, ToolCategory toolCategory) {
        super(parameters, toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int angle = parameters.get(0).getValue().intValue();

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), image.getWidth() / 2.0, image.getHeight() / 2.0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(image, null);

        BufferedImage result = new BufferedImage(rotatedImage.getWidth(), rotatedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        result.createGraphics().drawImage(rotatedImage, 0, 0, null);

        return result;
    }
}
