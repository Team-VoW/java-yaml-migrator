package com.voicesofwynn;

import java.io.File;

public class Main {

    public static void recursivelyDelete(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                recursivelyDelete(f);
            }
        }
        dir.delete();
    }

    public static void main(String[] args) {
        File out = new File("./out");
        recursivelyDelete(out);
        File sounds = new File("./sounds");
        SoundsHandler soundsHandler = new SoundsHandler(out, sounds);
        Sounds.register(soundsHandler);

        soundsHandler.done();

    }
}