package org.example.model.tools.implemented;

import org.example.model.tools.Tool;
import org.example.model.tools.ToolFactory;
import org.example.model.tools.ToolName;
import org.example.model.tools.categories.ToolCategory;
import org.example.model.utils.ColorUtils;
import org.example.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.*;

public class Watercolorizer extends Tool {
    private final static int WINDOW_SIZE = 5;

    public Watercolorizer(ToolCategory toolCategory) {
        super(toolCategory);
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int mediumIndex = (WINDOW_SIZE * WINDOW_SIZE) / 2 + 1;
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage filteredImage = ImageUtils.copyOf(image);

        int[] red = new int[WINDOW_SIZE * WINDOW_SIZE];
        int[] green = new int[WINDOW_SIZE * WINDOW_SIZE];
        int[] blue = new int[WINDOW_SIZE * WINDOW_SIZE];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = 0;
                for (int j = 0; j < WINDOW_SIZE; j++) {
                    for (int i = 0; i < WINDOW_SIZE; i++) {
                        int rgb = image.getRGB(x + i - ((x + i) / width) * (x + i + 1) % width, y + j - ((y + j) / height) * (y + j + 1) % height);
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

        Tool sharpener = ToolFactory.create(ToolName.SHARPENER, null);
        return sharpener.apply(filteredImage);
    }
}
