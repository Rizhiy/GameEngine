package com.rizhiy.GameEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;

public class gameConfig implements Serializable {

    private final Map<Integer, Action> keyBindings;

    public gameConfig(Map<Integer, Action> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public Map<Integer, Action> getKeyBindings() {
        return keyBindings;
    }

    public static gameConfig loadKeyBindings(Path path) throws IOException {
        gameConfig config;

        try {
            FileInputStream   fis = new FileInputStream(path.toFile());
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                config = (gameConfig) ois.readObject();
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
}
