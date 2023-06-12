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
        super_cool_thing();
    }

    private boolean otherStart = false;

    public void state(String name) {
        if (name.equals("Decrepit Sewers")) {
            otherStart = true;
        }

        super_cool_thing();

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

    private void super_cool_thing() {
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

    public void addSound(String id, String s1, boolean onPlayer, int fallOff) {
        id = id.replaceAll("\"", "\\\\\"");
        s1 += ".ogg";
        try {
            if (onPlayer) {
                configFile.write("  \"" + id + "\":\n");
                configFile.write("    location: player\n");
                configFile.write("    file: \"" + s1 + "\"\n");
                configFile.write("    fallOff: " + fallOff + "\n");
            } else {
                configFile.write("  \"" + id + "\":\n");
                configFile.write("    fallOff: " + fallOff + "\n");
                configFile.write("    file: \"" + s1 + "\"\n");
            }
            try {
                Files.copy(new File(sounds, s1).toPath(), new File(soundsFolder, s1).toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer) {
        id = id.replaceAll("\"", "\\\\\"");
        s1 += ".ogg";
        try {
            if (onPlayer) {
                configFile.write("  \"" + id + "\":\n");
                configFile.write("    location: player\n");
                configFile.write("    file: \"" + s1 + "\"\n");

            } else {
                configFile.write("  \"" + id + "\": \"" + s1 + "\"\n");
            }
            try {
                Files.copy(new File(sounds, s1).toPath(), new File(soundsFolder, s1).toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer, Vector3 pos) {
        id = id.replaceAll("\"", "\\\\\"");
        s1 += ".ogg";
        try {
            if (onPlayer) {
                configFile.write("  \"" + id + "\":\n");
                configFile.write("    location: player\n");
            } else {
                configFile.write("  \"" + id + "\":\n");
            }
            configFile.write("    location:\n");
            configFile.write("      x: " + pos.x + "\n");
            configFile.write("      y: " + pos.y + "\n");
            configFile.write("      z: " + pos.z + "\n");
            configFile.write("    file: \"" + s1 + "\"\n");
            try {
                Files.copy(new File(sounds, s1).toPath(), new File(soundsFolder, s1).toPath());
            } catch (Exception e) {
                System.out.println("Missing " + new File(sounds, s1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSound(String id, String s1, boolean onPlayer, Vector3 pos, int fallOff) {
        id = id.replaceAll("\"", "\\\\\"");
        addSound(id, s1, onPlayer, fallOff);
        try {
            configFile.write("    location:\n");
            configFile.write("      x: " + pos.x + "\n");
            configFile.write("      y: " + pos.y + "\n");
            configFile.write("      z: " + pos.z + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
