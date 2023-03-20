package org.example.model.tools;

public enum ToolName {
    GRAYSCALE("Grayscale"),
    NEGATIVE("Negative"),
    GAUSS_FILTER("Gauss"),
    SHARPENER("Sharpness"),
    EMBOSSER("Emboss"),
    GAMMA_CORRECTOR("Gamma"),
    ROBERTS_OPERATOR("Roberts"),
    SOBEL_OPERATOR("Sobel"),
    FLOYD_STEINBERG_DITHER("Floyd-Steinberg"),
    ORDERED_DITHER("Ordered"),
    WATERCOLORIZER("Watercolorize"),
    IMAGE_ROTATOR("Rotate"),
    SHRINKER("Shrink");

    private final String title;

    ToolName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
