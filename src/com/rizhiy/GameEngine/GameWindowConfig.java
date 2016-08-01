package com.rizhiy.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class GameWindowConfig implements KeyListener {
    private double  zoomLevel;
    private double  zoomChangeRate;
    private boolean increaseZoom, decreaseZoom;

    private Vector2D screenPosition;
    private boolean  screenFollow;
    private Tile     followTarget;
    private double   screenSpeed;

    private Map<Integer, Action> keyBindings;

    private boolean up, right, down, left;

    public GameWindowConfig() {
        zoomLevel = 1.5;
        zoomChangeRate = 1.1;
        increaseZoom = false;
        decreaseZoom = false;
        screenPosition = new Vector2D();
        screenFollow = false;
        followTarget = null;
        screenSpeed = 3;
    }

    public GameWindowConfig(GameWindowConfig original) {
        zoomLevel = original.zoomLevel;
        zoomChangeRate = original.zoomChangeRate;
        increaseZoom = original.increaseZoom;
        decreaseZoom = original.decreaseZoom;
        screenPosition = original.screenPosition;
        screenFollow = original.screenFollow;
        followTarget = original.followTarget;
        keyBindings = original.keyBindings;
    }

    public GameWindowConfig(GameWindowConfig one, GameWindowConfig two, double alpha) {
        this(one);
        zoomLevel = one.zoomLevel * (1 - alpha) + two.zoomLevel * alpha;
        screenPosition = new Vector2D(one.screenPosition, two.screenPosition, alpha);
    }

    public void updateScreenPosition(double dt) {
        if (screenFollow && followTarget != null) {
            screenPosition = followTarget.getPosition();
        } else {
            if (up) {
                screenPosition = new Vector2D(screenPosition.getX(), screenPosition.getY() - screenSpeed * dt);
            }
            if (down) {
                screenPosition = new Vector2D(screenPosition.getX(), screenPosition.getY() + screenSpeed * dt);
            }
            if (left) {
                screenPosition = new Vector2D(screenPosition.getX() - screenSpeed * dt, screenPosition.getY());
            }
            if (right) {
                screenPosition = new Vector2D(screenPosition.getX() + screenSpeed * dt, screenPosition.getY());
            }
        }
    }

    public void update(double deltatime) {
        if (increaseZoom) zoomLevel *= (zoomChangeRate - 1) * deltatime + 1;
        if (decreaseZoom) zoomLevel /= (zoomChangeRate - 1) * deltatime + 1;
        updateScreenPosition(deltatime);
        System.out.println(screenPosition);
    }

    public void setKeyBindings(Map<Integer, Action> bindings) {
        this.keyBindings = bindings;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        switch (keyBindings.getOrDefault(keyEvent.getKeyCode(), Action.UNBOUND)) {
            case CAMERA_FOLLOW:
                screenFollow = !screenFollow;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyBindings.getOrDefault(keyEvent.getKeyCode(), Action.UNBOUND)) {
            case INCREASE_ZOOM:
                increaseZoom = true;
                break;
            case DECREASE_ZOOM:
                decreaseZoom = true;
                break;
            case CAMERA_MOVE_UP:
                up = true;
                break;
            case CAMERA_MOVE_DOWN:
                down = true;
                break;
            case CAMERA_MOVE_LEFT:
                left = true;
                break;
            case CAMERA_MOVE_RIGHT:
                right = true;
                break;
            case EXIT:
                System.exit(0);
                break;
            case CAMERA_FOLLOW:
                screenFollow = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyBindings.getOrDefault(keyEvent.getKeyCode(), Action.UNBOUND)) {
            case INCREASE_ZOOM:
                increaseZoom = false;
                break;
            case DECREASE_ZOOM:
                decreaseZoom = false;
                break;
            case CAMERA_MOVE_UP:
                up = false;
                break;
            case CAMERA_MOVE_DOWN:
                down = false;
                break;
            case CAMERA_MOVE_LEFT:
                left = false;
                break;
            case CAMERA_MOVE_RIGHT:
                right = false;
                break;
            case CAMERA_FOLLOW:
                screenFollow = false;
                break;
        }
    }

    public Vector2D getScreenPosition() {
        return screenPosition;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public void setFollowTarget(Tile target) {
        this.followTarget = target;
    }
}