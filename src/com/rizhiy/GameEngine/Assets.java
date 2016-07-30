package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    private Map<Integer,SpriteSheet> spriteSheets;

    //Here until I start encoding size in the texture file
    public final int TextureSize = 16;

    private Map<TileType, BufferedImage> tiles = new HashMap<>();

    public int addSpriteSheet(String path){
        final int currentNumber = spriteSheets.size();
        Path spriteSheetPath = Paths.get(path);
        SpriteSheet s = new SpriteSheet();
        s.setSpriteSheet(ImageLoader.loadImageFrom(spriteSheetPath), new Vector2D(16,16));
        spriteSheets.put(currentNumber,s);
        return currentNumber;
    }

    public int getTextureSize(){
        return TextureSize;
    }

    public BufferedImage getImage(TileType t) {
        return tiles.get(t);
    }

}
