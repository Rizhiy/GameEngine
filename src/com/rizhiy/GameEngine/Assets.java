package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assets {

    private List<SpriteSheet> spriteSheets;

    //Here until I start encoding size in the texture file
    public final int TextureSize = 16;

    private Map<TileType, BufferedImage> tiles;

    public SpriteSheet addSpriteSheet(File spriteSheet) {
        final int   currentNumber = spriteSheets.size();
        SpriteSheet s             = new SpriteSheet(ImageLoader.loadImageFrom(spriteSheet), new Vector2D(16, 16));
        spriteSheets.add(currentNumber, s);
        return s;
    }

    public int getTextureSize() {
        return TextureSize;
    }

    public BufferedImage getImage(TileType t) {
        return tiles.get(t);
    }

    public Assets() {
        this.spriteSheets = new ArrayList<>();
        this.tiles = new HashMap<>();
    }

    public void addTile(TileType type, BufferedImage img) {
        tiles.put(type, img);
    }
}
