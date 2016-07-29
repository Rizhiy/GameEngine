package com.rizhiy.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by rizhiy on 22/04/16.
 */
public class GameWindow extends JFrame {
    private boolean fullScreen     = false;
    private int     fullScreenMode = 0;
    private int     display        = 0;

    private GraphicsDevice device;
    private Graphics2D     screen;

    private Assets assets = new Assets();

    private GameWindowConfig config;

    public GameWindow(String title, int width, int height, int display) {
        setTitle(title);
        setSize(width, height);

        config = new GameWindowConfig();

        //position in the middle of the screen on start
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.display = display;
        updateScreen();
    }

    public GameWindow(String title, int width, int height) {
        this(title, width, height, 0);
    }

    //Copy constructor
    public GameWindow(GameWindow original) {
        this.fullScreen = original.fullScreen;
        this.fullScreenMode = original.fullScreenMode;
        this.display = original.display;
        this.device = original.device;
        this.screen = original.screen;
        this.assets = original.assets;
        this.config = new GameWindowConfig(original.config);
    }

    private void setFullScreenMode(int fullScreenMode) {
        this.fullScreenMode = fullScreenMode;
        switch (fullScreenMode) {
            case 0:
                System.out.println("No Fullscreen");
                setUndecorated(false);
                break;
            case 1:
                setUndecorated(true);
                setExtendedState(MAXIMIZED_BOTH);
                break;
            case 2:
                setUndecorated(true);
                device.setFullScreenWindow(this);
                break;
            default:

        }
    }

    public void setFullScreen(int mode) {
        fullScreen = true;
        if (mode < 3) {
            setFullScreenMode(mode);
        } else {
            System.err.println("Error " + mode + " is not Supported!");
        }
    }

    private void updateScreen() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[display];
    }


    public void draw(BufferedImage img, Vector2D position, Vector2D size) {
        screen.drawImage(img,
                         (int) ((position.getX() * assets.getTextureSize() + config.getScreenPosition().getX()) * config.getZoomLevel()),
                         (int) ((position.getY() * assets.getTextureSize() + config.getScreenPosition().getY()) * config.getZoomLevel()),
                         (int) (size.getX() * assets.getTextureSize() * config.getZoomLevel()),
                         (int) (size.getY() * assets.getTextureSize() * config.getZoomLevel()), null);
    }

    public void display(Tile b) {
        draw(assets.getImage(b.getType()), b.getPosition(), new Vector2D(b.getWidth(), b.getHeight()));
        draw(assets.getImage(b.getType()), b.getPosition(), new Vector2D(b.getWidth(), b.getHeight()));
    }

    public void display(Actor a, BufferedImage img) {
        draw(img, a.getPosition(), a.getSize());
    }

    public GameWindowConfig getConfig(){
        return config;
    }
    public void setConfig(GameWindowConfig config){
        this.config = config;
    }
}
