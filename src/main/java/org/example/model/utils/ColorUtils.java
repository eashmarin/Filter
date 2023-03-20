package org.example.model.utils;

public class ColorUtils {

    public static int getRed(int rgb) {
        return (rgb >> 16) & 255;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 255;
    }

    public static int getBlue(int rgb) {
        return rgb & 255;
    }

    public static int getRGB(int r, int g, int b) {
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }

    public static int correctRange(int value) {
        return Math.min(Math.max(value, 0), 255);
    }
}
