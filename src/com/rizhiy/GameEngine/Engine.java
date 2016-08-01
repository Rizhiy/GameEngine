package com.rizhiy.GameEngine;

import java.awt.*;

public class Engine {
    private final WorldState s;
    private final Loop       l;
    private static int selectedMonitor = 0;

    private static GraphicsDevice gd     = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[selectedMonitor];
    private static int            width  = gd.getDisplayMode().getWidth();
    private static int            height = gd.getDisplayMode().getHeight();

    public Engine(WorldState s, Loop l) {
        this.s = s;
        this.l = l;

        this.l.getWindow().addKeyListener(s.getPlayer());
        this.l.getWindow().addKeyListener(this.l.getWindow().getConfig());
        this.l.getWindow().setVisible(true);
    }

    public Engine(String title, WorldState s) {
        this(s, new Loop(0.1, 60, s, new GameWindow(title,
                                                    GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getWidth(),
                                                    GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getHeight())));
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setMonitor(int m) {
        selectedMonitor = m;
        resetParameters();
    }

    public static void resetParameters() {
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[selectedMonitor];
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
    }
}
