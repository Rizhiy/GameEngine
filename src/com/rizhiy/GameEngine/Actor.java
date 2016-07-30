package com.rizhiy.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Actor extends Tile implements KeyListener {

    public enum ActorType implements TileType {ACTOR}

    //ratio by which maxSpeed we be reached. Higher values lead to faster acc/decelerations.
    private double acceleration = 1;
    private double deceleration = 3;

    //TODO: look into replacing this with a map of moveDirection and mutable boolean object
    private boolean up, right, down, left;
    private double maxSpeed = 5;

    private Map<MoveDirection,Animator> sprites = new HashMap<>();

    private Map<Integer,Action> keyBindings;
    /**
     * UP,DOWN,LEFT,RIGHT
     */
    private double[] speeds = {0, 0, 0, 0};

    //TODO: choose a better name
    public enum MoveDirection {
        UP(0), DOWN(1), LEFT(2), RIGHT(3),IDLE(5);

        private int code;

        MoveDirection(int c) {
            code = c;
        }

        public int getCode() {
            return code;
        }
    }

    public Actor(World host, Vector2D position, Vector2D size){
        super(position, size, true, ActorType.ACTOR, host);
    }

    public Actor(World host, Vector2D position){
        this(host,position,new Vector2D(1,1));
    }

    public Actor(World host) {
        this(host,new Vector2D());
    }

    public Actor(Actor original){
        super(original);
        acceleration = original.acceleration;
        deceleration = original.deceleration;
        up = original.up;
        right = original.right;
        down = original.down;
        left = original.left;
        maxSpeed = original.maxSpeed;
        sprites = new HashMap<>(original.sprites);
        System.arraycopy(original.speeds,0,speeds,0,original.speeds.length);
    }

    public Actor(Actor one, Actor two, double alpha){
        this(one);
        position = new Vector2D(one.position,two.position,alpha);
        size = one.size.multiply(1-alpha).add(two.size.multiply(alpha));
        for(int i = 0; i < speeds.length; i++){
            speeds[i] = one.speeds[i]*(1-alpha)+two.speeds[i]*alpha;
        }
    }

    //Increase indicates whether to accelerate or decelerate actor, true for acc, false for dec
    private void updateSpeed(boolean increase, MoveDirection direction, double deltaTime) {
        double change;
        if (increase) {
            change = maxSpeed * acceleration * deltaTime;
            if (speeds[direction.getCode()] + change < maxSpeed) speeds[direction.getCode()] += change;
            else speeds[direction.getCode()] = maxSpeed;
        } else {
            change = maxSpeed * deceleration * deltaTime;
            if (speeds[direction.getCode()] - change > 0) speeds[direction.getCode()] -= change;
            else speeds[direction.getCode()] = 0;
        }
    }

    private void updatePosition(double deltaTime) {
        updateSpeed(up, MoveDirection.UP, deltaTime);
        updateSpeed(down, MoveDirection.DOWN, deltaTime);
        updateSpeed(left, MoveDirection.LEFT, deltaTime);
        updateSpeed(right, MoveDirection.RIGHT, deltaTime);

        double changeU = speeds[MoveDirection.UP.getCode()] * deltaTime;
        double changeD = speeds[MoveDirection.DOWN.getCode()] * deltaTime;
        double changeL = speeds[MoveDirection.LEFT.getCode()] * deltaTime;
        double changeR = speeds[MoveDirection.RIGHT.getCode()] * deltaTime;

        Vector2D oldPosition = position;
        position = position.changeY(changeD-changeU);
        if(getHost().checkCollision(this)) {
            position = oldPosition;
            speeds[0] = 0;
            speeds[1] = 0;
        }
        oldPosition = position;
        position = position.changeX(changeR-changeL);
        if(getHost().checkCollision(this)) {
            position = oldPosition;
            speeds[2] = 0;
            speeds[3] = 0;
        }
    }

    public void update(double deltaTime) {
        updatePosition(deltaTime);
        for(HashMap.Entry<MoveDirection,Animator> animator : sprites.entrySet()){
            animator.getValue().update(System.currentTimeMillis());
        }
    }

    public void render(GameWindow window) {
        BufferedImage img;
        if(up){
            img = sprites.get(MoveDirection.UP).getSprite();
        } else if(down){
            img = sprites.get(MoveDirection.DOWN).getSprite();
        } else if(left){
            img = sprites.get(MoveDirection.LEFT).getSprite();
        } else if(right) {
            img = sprites.get(MoveDirection.RIGHT).getSprite();
        } else{
            img = sprites.get(MoveDirection.IDLE).getSprite();
        }

        window.display(this,img);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyBindings.getOrDefault(keyEvent.getKeyCode(),Action.UNBOUND)) {
            case MOVE_UP:
                up = true;
                sprites.get(MoveDirection.UP).play();
                break;
            case MOVE_DOWN:
                down = true;
                sprites.get(MoveDirection.DOWN).play();
                break;
            case MOVE_LEFT:
                left = true;
                sprites.get(MoveDirection.LEFT).play();
                break;
            case MOVE_RIGHT:
                right = true;
                sprites.get(MoveDirection.RIGHT).play();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyBindings.getOrDefault(keyEvent.getKeyCode(),Action.UNBOUND)) {
            case MOVE_UP:
                up = false;
                sprites.get(MoveDirection.UP).stop();
                break;
            case MOVE_DOWN:
                down = false;
                sprites.get(MoveDirection.UP).stop();
                break;
            case MOVE_LEFT:
                left = false;
                sprites.get(MoveDirection.LEFT).stop();
                break;
            case MOVE_RIGHT:
                right = false;
                sprites.get(MoveDirection.RIGHT).stop();
                break;
        }

    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getSize() {
        return size;
    }

    @Override
    public Actor makeCopy(){
        return new Actor(this);
    }

    public void addAnimation(MoveDirection direction, Animator animator) {
        sprites.put(direction, animator);
    }

    public void setAnimations(Map<MoveDirection, Animator> animations) {
        sprites = animations;
    }

    public void setKeyBindings(Map<Integer, Action> bindings) {
        this.keyBindings = bindings;
    }

    public void setKeyBindings(Path path) {
        setKeyBindings(Utilities.loadKeyBindings(path));
    }
}
