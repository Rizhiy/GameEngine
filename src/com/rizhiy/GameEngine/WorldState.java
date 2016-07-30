package com.rizhiy.GameEngine;

public class WorldState {
    private World world;
    private Actor player;

    public WorldState(World world, Actor actor) {
        this.world = world;
        this.player = actor;
    }

    public WorldState(World world) {
        this(world, new Actor(world));
    }

    public WorldState(WorldState original) {
        world = new World(original.world);
        player = new Actor(original.player);
    }

    public WorldState(WorldState one, WorldState two, double alpha) {
        this(one);
        world = new World(one.world, two.world, alpha);
        player = new Actor(one.player, two.player, alpha);
    }

    public void update(double deltaTime) {
        world.update(deltaTime);
        player.update(deltaTime);
    }

    public void render(GameWindow window) {
        world.render(window);
        player.render(window);
    }

    public World getWorld() {
        return world;
    }

    public Actor getPlayer() {
        return player;
    }
}
