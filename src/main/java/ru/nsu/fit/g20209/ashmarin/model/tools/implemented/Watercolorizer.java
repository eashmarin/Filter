package ru.nsu.fit.g20209.ashmarin.model.tools.implemented;

import ru.nsu.fit.g20209.ashmarin.model.tools.Tool;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolFactory;
import ru.nsu.fit.g20209.ashmarin.model.tools.ToolEnum;
import ru.nsu.fit.g20209.ashmarin.model.utils.ColorUtils;
import ru.nsu.fit.g20209.ashmarin.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Watercolorizer implements Tool {
    private final static int WINDOW_SIZE = 5;

    public Watercolorizer() {
        super();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int mediumIndex = (WINDOW_SIZE * WINDOW_SIZE) / 2;
        int width = image.getWidth();
        int height = image.getHeight();

        int n = WINDOW_SIZE / 2;
        int k = n + 1;

        BufferedImage filteredImage = new BufferedImage(width, height, image.getType());

        int[] red = new int[WINDOW_SIZE * WINDOW_SIZE];
        int[] green = new int[WINDOW_SIZE * WINDOW_SIZE];
        int[] blue = new int[WINDOW_SIZE * WINDOW_SIZE];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = 0;
                for (int j = -n; j < k; j++) {
                    for (int i = -n; i < k; i++) {
                        Point coords = ImageUtils.getCoords(x, i, y, j, width, height);

                        int rgb = image.getRGB(coords.x, coords.y);
                        red[index] = ColorUtils.getRed(rgb);
                        green[index] = ColorUtils.getGreen(rgb);
                        blue[index] = ColorUtils.getBlue(rgb);
                        index++;
                    }
                }

                Arrays.sort(red);
                Arrays.sort(green);
                Arrays.sort(blue);

                filteredImage.setRGB(x, y, ColorUtils.getRGB(red[mediumIndex], green[mediumIndex], blue[mediumIndex]));
            }
        }

        Tool sharpener = ToolFactory.create(ToolEnum.SHARPENER, null);
        return sharpener.apply(filteredImage);
    }
}
