package com.rizhiy.GameEngine;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rizhiy on 26/07/16.
 */
public class WorldState {
    private World world;

    private Map<Integer, Action> keyBindings;

    public WorldState(Map<Integer, Action> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public WorldState(Path keyBindings) {
        this(Utilities.loadKeyBindings(keyBindings));
    }

    public WorldState(WorldState original) {
        world = new World(original.world);
        keyBindings = new HashMap<>(keyBindings);
    }

    public WorldState(WorldState one, WorldState two, double alpha) {
        this(one);
        world = new World(one.world, two.world, alpha);
    }

    public void update(double deltaTime) {
        world.update(deltaTime);
    }

    public void render(GameWindow window) {
        world.render(window);
    }

    public void setKeyBindings(Map<Integer, Action> bindings) {
        this.keyBindings = bindings;
    }

    public void setKeyBindings(Path path) {
        setKeyBindings(Utilities.loadKeyBindings(path));
    }
}
