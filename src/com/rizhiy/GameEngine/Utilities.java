package com.rizhiy.GameEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by rizhiy on 27/07/16.
 */
public class Utilities {
    public static HashMap<Integer,Action> loadKeyBindings(Path path) {
        HashMap<Integer, Action> keyBindings = new HashMap<>();

        try {
            FileInputStream   fis = new FileInputStream(path.toFile());
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                keyBindings = (HashMap<Integer, Action>) ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                throw new IOException();
            } finally {
                ois.close();
                fis.close();
            }
        } catch (IOException e) {
            System.err.println("Couldn't load default Key Bindings");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }

        return keyBindings;
    }
}
