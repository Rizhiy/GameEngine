package com.rizhiy.GameEngine;

import java.io.*;
import java.util.Map;

public class GameConfig implements Serializable {

    private final Map<Integer, Action> keyBindings;

    public GameConfig(Map<Integer, Action> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public Map<Integer, Action> getKeyBindings() {
        return keyBindings;
    }

    public static GameConfig loadConfig(File file) throws IOException {
        GameConfig config;

        try {
            FileInputStream   fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                config = (GameConfig) ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                throw new IOException();
            } finally {
                ois.close();
                fis.close();
            }
        } catch (IOException e) {
            System.err.println("Couldn't load Key Bindings");
            throw e;
        }

        return config;
    }

    public void saveConfig(File file) throws IOException {
        FileOutputStream   fos = null;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(file, false);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

    }
}
