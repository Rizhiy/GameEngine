package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;

/**
 * Created by rizhiy on 23/04/16.
 */
public class SpriteSheet {
    private BufferedImage spriteSheet;

    private Vector2D textureSize;

    public SpriteSheet() {

    }

    public void setSpriteSheet(BufferedImage spriteSheet, Vector2D textureSize) {
        this.spriteSheet = spriteSheet;
        this.textureSize = textureSize;
    }

    public BufferedImage getTile(int x, int y, int width, int height) {
        BufferedImage sprite = spriteSheet.getSubimage(x * (int) textureSize.getX(), y * (int) textureSize.getY(), width, height);
        return sprite;
    }
}
