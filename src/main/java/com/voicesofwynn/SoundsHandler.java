package com.voicesofwynn;

import java.io.*;
import java.nio.file.Files;

public class SoundsHandler {

    private File soundsFolder;
    private FileWriter configFile;
    private final File out;
    private final File sounds;

    public SoundsHandler(File out, File sounds) {
        this.out = out;
        this.sounds = sounds;
    }

    public void done() {
        if (configFile != null) {
            try {
                configFile.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                configFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean otherStart = false;

    public void state(String name) {
        if (name.equals("Decrepit Sewers")) {
            otherStart = true;
        }

        if (configFile != null) {
            try {
                configFile.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                configFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (otherStart) {
            soundsFolder = new File(out, "other/" + name + "/sounds");
            soundsFolder.mkdirs();
            try {
                configFile = new FileWriter(new File(out, "other/" + name + "/cfg.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            soundsFolder = new File(out, "quests/" + name + "/sounds");
            soundsFolder.mkdirs();
            try {
                configFile = new FileWriter(new File(out, "quests/" + name + "/quest.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            configFile.write("""
                    file-base-dir: "./sounds/"
                    
                    dialogue:
                    """);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer, int fallOff) {
        try {
            if (onPlayer) {
                configFile.write("\t\"" + id + "\":\n");
                configFile.write("\t\tlocation: player\n");
                configFile.write("\t\tfile: \"" + s1 + "\"\n");
                configFile.write("\t\tfallOff: " + fallOff + "\n");
            } else {
                configFile.write("\t\"" + id + "\":\n");
                configFile.write("\t\tfallOff: " + fallOff + "\n");
            }
            try {
                Files.copy(new File(sounds, s1 + ".ogg").toPath(), new File(soundsFolder, s1 + ".ogg").toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1 + ".ogg"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer) {
        try {
            if (onPlayer) {
                configFile.write("\t\"" + id + "\":\n");
                configFile.write("\t\tlocation: player\n");
                configFile.write("\t\tfile: \"" + s1 + "\"\n");

            } else {
                configFile.write("\t\"" + id + "\":\"" + s1 + "\"\n");
            }
            try {
                Files.copy(new File(sounds, s1 + ".ogg").toPath(), new File(soundsFolder, s1 + ".ogg").toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1 + ".ogg"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer, Vector3 pos) {
        try {
            if (onPlayer) {
                configFile.write("\t\"" + id + "\":\n");
                configFile.write("\t\tlocation: player\n");
                configFile.write("\t\tfile: \"" + s1 + "\"\n");
            } else {
                configFile.write("\t\"" + id + "\":\n");
            }
            configFile.write("\t\tlocation:\n");
            configFile.write("\t\t\tx:" + pos.x);
            configFile.write("\t\t\ty:" + pos.y);
            configFile.write("\t\t\tz:" + pos.z);
            try {
                Files.copy(new File(sounds, s1 + ".ogg").toPath(), new File(soundsFolder, s1 + ".ogg").toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1 + ".ogg"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer, Vector3 pos, int fallOff) {
        addSound(id, s1, onPlayer, fallOff);
        try {
            configFile.write("\t\tlocation:\n");
            configFile.write("\t\t\tx:" + pos.x);
            configFile.write("\t\t\ty:" + pos.y);
            configFile.write("\t\t\tz:" + pos.z);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
