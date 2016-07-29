package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by rizhiy on 26/07/16.
 */
public class World {
    private final int width;
    private final int height;

    private Actor player;

    private TileManager tiles = new TileManager();

    public World(Path mapFilePath, Map<Integer, Tile> colourCode) {
        BufferedImage map = ImageLoader.loadImageFrom(mapFilePath);
        this.width = map.getWidth();
        this.height = map.getHeight();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int colour = map.getRGB(x, y);
                tiles.addTile(colourCode.get(colour));
            }
        }

        this.player = new Actor(this);
    }

    public World(World original) {
        width = original.width;
        height = original.height;
        player = new Actor(original.player);
        tiles = new TileManager(tiles);
    }

    public World(World one, World two, double alpha) {
        this(one);
        player = new Actor(one.player, two.player, alpha);
        tiles = new TileManager(one.tiles, two.tiles, alpha);
    }

    public void update(double deltaTime) {
        tiles.update(deltaTime);
        player.update(deltaTime);
    }

    public void render(GameWindow g) {
        tiles.render(g);
        player.render(g);
    }

    public Actor getPlayer() {
        return player;
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
}
