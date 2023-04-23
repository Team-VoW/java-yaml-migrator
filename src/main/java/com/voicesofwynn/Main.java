package com.voicesofwynn;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File out = new File("./out");
        File sounds = new File("./sounds");
        SoundsHandler soundsHandler = new SoundsHandler(out);
        Sounds.register(soundsHandler);
    }
}