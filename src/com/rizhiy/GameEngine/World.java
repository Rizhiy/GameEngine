package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

public class World {
    private final int width;
    private final int height;

    private Vector2D playerStartPosition;

    private TileManager tiles = new TileManager();

    public World(File mapFilePath, Map<Integer, Tile> colourCode) {
        BufferedImage map = ImageLoader.loadImageFrom(mapFilePath);
        this.width = map.getWidth();
        this.height = map.getHeight();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int  colour = map.getRGB(x, y) & 0xFFFFFF;
                Tile tile;
                if (colourCode.get(colour) != null) {
                    tile = colourCode.get(colour).makeCopy();
                } else {
                    continue;
                }
                tile.setPosition(new Vector2D(x, y));
                tile.setHost(this);
                tile.setSize(new Vector2D(1, 1));
                this.tiles.addTile(tile);
            }
        }

        this.playerStartPosition = new Vector2D(this.width / 2, this.height / 2);
    }

    public World(World original) {
        width = original.width;
        height = original.height;
        tiles = new TileManager(original.tiles);
        playerStartPosition = original.playerStartPosition;
    }

    public World(World one, World two, double alpha) {
        this(one);
        tiles = new TileManager(one.tiles, two.tiles, alpha);
    }

    public void update(double deltaTime) {
        tiles.update(deltaTime);
    }

    public void render(GameWindow g) {
        tiles.render(g);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean checkCollision(Actor a) {
        for (Tile tile : tiles.getTiles()) {
            if (tile.isSolid() &&
                Vector2D.distance(a.getPosition(), tile.getPosition()) <
                a.getSize().magnitude() + tile.getSize().magnitude()) {
                for (Vector2D vertex : a.getVertices()) {
                    if (tile.contains(vertex)) return true;
                }
            }
        }
        return false;
    }

    public void setPlayerStartPosition(Vector2D position) {
        playerStartPosition = position;
    }

    public Vector2D getPlayerStartPosition() {
        return playerStartPosition;
    }

    public TileManager getTiles() {
        return tiles;
    }
}
