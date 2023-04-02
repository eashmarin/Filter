package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.parameters.Parameter;
import ru.nsu.fit.g20209.ashmarin.model.tools.ParameterizedTool;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class Resizer extends ParameterizedTool {
    public Resizer(List<Parameter> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int newWidth = parameters.get(0).getValue().intValue();
        int newHeight = parameters.get(1).getValue().intValue();
        int transformType = parameters.get(2).getValue().intValue();

        int width = image.getWidth();
        int height = image.getHeight();

        if (width + 4 == newWidth && height + 4 == newHeight) {
            return image;
        }

        BufferedImage result = new BufferedImage(newWidth, newHeight, image.getType());

        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance((double) newWidth / width, (double) newHeight / height),
                transformType
        );
        op.filter(image, result);

        return result;
    }
}
