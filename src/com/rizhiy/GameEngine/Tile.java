package com.rizhiy.GameEngine;

import java.util.ArrayList;

/**
 * Created by rizhiy on 24/04/16.
 */
public abstract class Tile {
    public enum TileType {BLANK, ACTOR}

    private   TileType type;
    protected Vector2D position;
    protected Vector2D size;
    private   boolean  solid;

    private World host;


    public Tile(Vector2D pos, Vector2D size, boolean solid, TileType type, World host) {
        this.type = type;
        this.position = pos;
        this.size = size;
        this.solid = solid;
        this.host = host;
    }

    public Tile(Vector2D pos, boolean solid, TileType type, World host) {
        this(pos, new Vector2D(), solid, type, host);
    }

    public Tile(Vector2D pos, boolean solid, World host) {
        this(pos, solid, TileType.BLANK, host);
    }

    public Tile(Vector2D pos, World host) {
        this(pos, true, host);
    }

    public Tile(World host) {
        this(new Vector2D(), host);
    }

    public Tile(Tile original) {
        this(original.position, original.size, original.solid, original.type, original.host);
    }

    public abstract void update(double deltaTime);

    public void render(GameWindow window) {
        window.display(this);
    }

    public boolean isSolid() {
        return solid;
    }

    public Vector2D getPosition() {
        return position;
    }

    public abstract Tile makeCopy();

    public TileType getType() {
        return type;
    }

    public Vector2D getSize() {
        return size;
    }

    public ArrayList<Vector2D> getVertices() {
        ArrayList<Vector2D> result = new ArrayList<>();
        result.add(position);
        result.add(new Vector2D(position.getX() + size.getX(), position.getY()));
        result.add(new Vector2D(position.getX(), position.getY() + size.getY()));
        result.add(position.add(size));
        return result;
    }

    public World getHost() {
        return host;
    }

    public boolean contains(Vector2D v) {
        if (v.getX() > position.getX() && v.getX() < position.getX() + size.getX() &&
            v.getY() > position.getY() && v.getY() < position.getY() + size.getY()) {
            return true;
        }
        return false;
    }

    public double getWidth(){
        return size.getX();
    }

    public double getHeight(){
        return size.getY();
    }
}
