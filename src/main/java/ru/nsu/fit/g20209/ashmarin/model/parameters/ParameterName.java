package ru.nsu.fit.g20209.ashmarin.model.parameters;

public enum ParameterName {
    GAUSS_FILTER_WINDOW_SIZE("Window size"),
    GAMMA("Gamma power"),
    BINARIZATION("Binarization"),
    IMAGE_ANGLE("Angle"),
    QUANTIZATION_NUMBER_RED("Quantization Red"),
    QUANTIZATION_NUMBER_GREEN("Quantization Green"),
    QUANTIZATION_NUMBER_BLUE("Quantization Blue"),
    FRAME_WIDTH("Width"),
    FRAME_HEIGHT("Height"),
    BLOCK_SIZE("Block size"),
    RESIZE_TYPE("Resize type");

    private final String title;

    ParameterName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}