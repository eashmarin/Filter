package ru.nsu.fit.g20209.ashmarin;

import ru.nsu.fit.g20209.ashmarin.model.Model;
import ru.nsu.fit.g20209.ashmarin.ui.Frame;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Frame frame = new Frame(model);
    }
}