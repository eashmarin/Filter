package org.example;

import org.example.model.Model;
import org.example.ui.Frame;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Frame frame = new Frame(model);
    }
}