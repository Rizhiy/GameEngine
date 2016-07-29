package com.rizhiy.GameEngine;

/**
 * Created by rizhiy on 26/07/16.
 */
public class Loop extends Thread {
    protected double time = 0;

    private double dt;
    private double currentTime = System.currentTimeMillis();
    private double accumulator = 0;

    private GameWindowConfig previousWindowConfig;
    private GameWindowConfig currentWindowConfig;

    private GameWindow screen;

    private WorldState previous;
    private WorldState current;

    private boolean done = false;

    public Loop(double dt, WorldState s, GameWindow screen) {
        this.dt = dt;
        this.current = s;
        this.screen = screen;
        this.currentWindowConfig = screen.getConfig();
    }

    @Override
    public void run(){
        while(!done){
            double newTime = System.currentTimeMillis();
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            accumulator += frameTime;

            while(accumulator >= dt){
                previous = new WorldState(current);
                current.update(dt);
                previousWindowConfig = new GameWindowConfig(currentWindowConfig);
                currentWindowConfig.update(dt);
                time += dt;
                accumulator -= dt;
            }

            double alpha = accumulator/dt;
            screen.setConfig(new GameWindowConfig(previousWindowConfig, currentWindowConfig, alpha));
            new WorldState(previous,current,alpha).render(screen);
        }
    }
}
