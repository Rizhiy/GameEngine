package com.rizhiy.GameEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by rizhiy on 23/04/16.
 */
public class ImageLoader {

    public static BufferedImage loadImageFrom(Path path) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
