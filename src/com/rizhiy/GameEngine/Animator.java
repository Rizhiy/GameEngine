package com.rizhiy.GameEngine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rizhiy on 28/04/16.
 */
public class Animator {

    private List<BufferedImage> frames;
    private boolean running = false;

    private long prevTime, period;
    private int currentFrame = 0;
    private boolean repeat = false;

    public Animator(List<BufferedImage> frames, long period, boolean repeat) {
        this.frames = frames;
        this.period = period;
        this.repeat = repeat;
    }

    public void update(long time) {
        if (running && time - prevTime >= period) {
            if (currentFrame == frames.size()) {
                if (repeat) {
                    currentFrame = 0;
                } else {
                    return;
                }
            }
            currentFrame++;
            prevTime = time;
        }
    }

    public void play() {
        running = true;
        prevTime = 0;
    }

    public void stop() {
        running = false;
        currentFrame = 0;
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void reset(){
        currentFrame = 0;
    }

    public boolean isDoneAnimating() {
        if (!repeat && currentFrame == frames.size()){
            return true;
        } else {
            return false;
        }
    }

    public BufferedImage getSprite(){
        return frames.get(currentFrame);
    }
}
