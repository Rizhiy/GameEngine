package com.rizhiy.GameEngine;

/**
 * This class is used to display information in absolute place on the screen.
 * Screen space ranges from -1 to 1 on both x and y with 0,0 being in the centre.
 * x goes from left to right, y goes top to bottom. So top left is -1,-1
 */
public class AbsoluteScreenLocation extends Vector2D {
    public AbsoluteScreenLocation(double x, double y) throws ValueOutOfBoundsException {
        super(x, y);
        if (x > 1 || x < -1 || y > 1 || y < -1) throw new ValueOutOfBoundsException();
    }
}
