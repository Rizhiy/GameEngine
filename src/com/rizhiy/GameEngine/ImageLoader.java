package com.rizhiy.GameEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rizhiy on 23/04/16.
 */
public class ImageLoader {

    public static BufferedImage loadImageFrom(File file) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
