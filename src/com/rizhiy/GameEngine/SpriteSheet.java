package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

/**
 * Created by rizhiy on 23/04/16.
 */
public class SpriteSheet {
    private BufferedImage spriteSheet;

    private Vector2D textureSize;

    public SpriteSheet(BufferedImage spriteSheet, Vector2D textureSize) {
        this.spriteSheet = spriteSheet;
        this.textureSize = textureSize;
    }

    public BufferedImage getTile(int x, int y, int width, int height) {
        BufferedImage sprite;
        try {
            sprite = spriteSheet.getSubimage(x * (int) textureSize.getX(), y * (int) textureSize.getY(),
                                             width * (int) textureSize.getX(), height * (int) textureSize.getY());
        } catch (RasterFormatException e) {
            System.err.println(spriteSheet.getWidth());
            System.err.println(spriteSheet.getHeight());
            throw e;
        }
        return sprite;
    }
}
