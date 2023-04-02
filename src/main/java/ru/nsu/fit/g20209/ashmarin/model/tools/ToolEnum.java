package ru.nsu.fit.g20209.ashmarin.model.tools;

public enum ToolEnum {
    GRAYSCALE("Grayscale", ToolCategory.NO_MODIFY),
    NEGATIVE("Negative", ToolCategory.NO_MODIFY),
    GAUSS_FILTER("Gauss filter", ToolCategory.NO_MODIFY),
    SHARPENER("Sharpness", ToolCategory.NO_MODIFY),
    EMBOSSER("Emboss filter", ToolCategory.NO_MODIFY),
    GAMMA_CORRECTOR("Gamma correction", ToolCategory.NO_MODIFY),
    ROBERTS_OPERATOR("Roberts operator", ToolCategory.NO_MODIFY),
    SOBEL_OPERATOR("Sobel operator", ToolCategory.NO_MODIFY),
    FLOYD_STEINBERG_DITHER("Floyd-Steinberg dithering", ToolCategory.NO_MODIFY),
    ORDERED_DITHER("Ordered dithering", ToolCategory.NO_MODIFY),
    WATERCOLORIZER("Watercolorize", ToolCategory.NO_MODIFY),
    PIXELATE("Pixelate filter", ToolCategory.NO_MODIFY),
    ROTATOR("Rotation", ToolCategory.NO_MODIFY),
    RESIZER("Resize", ToolCategory.MODIFY);
    private final String title;
    private final ToolCategory category;

    ToolEnum(String title, ToolCategory category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public ToolCategory getCategory() {
        return category;
    }
}
