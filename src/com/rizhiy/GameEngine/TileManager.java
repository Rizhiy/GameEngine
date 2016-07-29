package com.rizhiy.GameEngine;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by rizhiy on 24/04/16.
 */
public class TileManager {

    private List<Tile> tiles;
    public TileManager(){
        tiles = new ArrayList<>();
    }

    public TileManager(TileManager original){
        this();
        tiles = new ArrayList<>(original.tiles.size());
        for(Tile tile : original.tiles){
            tiles.add(tile.makeCopy());
        }
    }

    public TileManager(TileManager one, TileManager two, double alpha){
        this(one);
    }

    public void update(double deltaTime){
        for(Tile tile : tiles){
            tile.update(deltaTime);
        }
    }

    public void render(GameWindow g){
        for(Tile tile : tiles){
            tile.render(g);
        }
    }

    public List<Tile> getTiles(){
        return tiles;
    }

    public void addTile(Tile b){
        tiles.add(b);
    }
}
