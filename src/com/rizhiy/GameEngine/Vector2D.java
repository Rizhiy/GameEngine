package com.rizhiy.GameEngine;

import java.awt.*;

/**
 * Created by rizhiy on 23/04/16.
 */
public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D original){
        this(original.x,original.y);
    }

    public Vector2D(){
        this(0,0);
    }

    public Vector2D(Vector2D one,Vector2D two, double alpha) {
        x = one.x*(1-alpha)+ two.x*alpha;
        y = one.y*(1-alpha)+ two.y*alpha;
    }
    public static Vector2D zero(){
        return new Vector2D();
    }

    public static Vector2D normalise(Vector2D v) {
        double length = Math.sqrt(v.x * v.x + v.y * v.y);
        double x = 0,y = 0;
        if (length != 0) {
            x = (double) (v.x / length);
            y = (double) (v.y / length);
        }
        return new Vector2D(x,y);
    }

    public static Vector2D add(Vector2D left, Vector2D right){
        return new Vector2D(left.x + right.x, left.y + right.x);
    }

    public Vector2D add(Vector2D other){
        return Vector2D.add(this,other);
    }

    public Vector2D multiply(double a){
        return new Vector2D(x*a,y*a);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public Vector2D changeX(double change){
        return new Vector2D(x + change,y);
    }

    public Vector2D changeY(double change){
        return new Vector2D(x,y + change);
    }

    public static double distance(Vector2D vec1, Vector2D vec2){
        double dx = vec1.getX() - vec2.getX();
        double dy = vec1.getY() - vec2.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0) return false;
        return Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return  "("+ x +","+y+")";
    }

    public double magnitude(){
        return Math.sqrt(x*x+y*x);
    }
}
