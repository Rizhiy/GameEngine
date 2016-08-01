package com.rizhiy.GameEngine;

public class Loop extends Thread {
    protected double time = 0;

    private final double dt;
    private final double fps;
    private       double timePerFrame;
    private double currentTime = System.currentTimeMillis();
    private double accumulator = 0;

    private double currentTPS;
    private double currentFPS;
    private double frames;
    private double ticks;
    private double lastUpdateTime;
    private double updatePeriod;

    private GameWindowConfig previousWindowConfig;
    private GameWindowConfig currentWindowConfig;

    private GameWindow window;

    private WorldState previous;
    private WorldState current;

    private boolean done = false;

    public Loop(double dt, double fps, WorldState s, GameWindow window) {
        this.dt = dt;
        this.fps = fps;
        this.timePerFrame = 1.0 / fps;
        this.current = s;
        this.window = window;
        this.currentWindowConfig = window.getConfig();

        currentFPS = 0;
        currentTPS = 0;
        frames = 0;
        ticks = 0;
        lastUpdateTime = (double) System.currentTimeMillis() / 1000.0;
        updatePeriod = 1;
    }

    @Override
    public void run(){
        while(!done){
            double newTime   = (double) System.currentTimeMillis() / 1000.0;
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            if (frameTime <= timePerFrame) frameTime = timePerFrame;

            accumulator += frameTime;

            while(accumulator >= dt){
                previous = new WorldState(current);
                previousWindowConfig = new GameWindowConfig(currentWindowConfig);
                update(dt);
                time += dt;
                accumulator -= dt;
                ticks++;
            }

            if (newTime - lastUpdateTime > updatePeriod) {
                currentTPS = ticks;
                currentFPS = frames;
                lastUpdateTime = newTime;
                ticks = 0;
                frames = 0;
            }

            double alpha = accumulator/dt;
            window.setConfig(new GameWindowConfig(previousWindowConfig, currentWindowConfig, alpha));
            render(alpha);
            frames++;
        }
    }

    public GameWindow getWindow() {
        return window;
    }

    /**
     * @return vector2D where getX() gives current TPS, and getY() gives current FPS
     */
    public Vector2D getInfo() {
        return new Vector2D(currentTPS, currentFPS);
    }

    /**
     * Sets how frequently fps and tps stats should be updated.
     *
     * @param updatePeriod update period in seconds
     */
    public void setUpdatePeriod(double updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    private void update(double deltatime) {
        current.update(deltatime);
        currentWindowConfig.update(deltatime);
    }

    private void render(double alpha) {
        window.clear();
        new WorldState(previous, current, alpha).render(window);
        window.display(getInfo().toString(), new AbsoluteScreenLocation(-0.9, -0.9));
        window.updateScreen();
    }
}
