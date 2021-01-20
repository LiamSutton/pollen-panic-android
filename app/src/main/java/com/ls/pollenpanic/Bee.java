package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class Bee extends GameObject implements IMoveable {

    // Constructor for Bee Object
    public Bee(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, sprite);
    }


    /**
     * Updates to movement direction of the Bee according to the rotation of the phone
     *
     * @param xRotation the current x rotation of the device reported by the sensors
     */
    public void rotationChanged(float xRotation) {

        if (xRotation > Constants.ROTATION_THRESHOLD) {
            xDirection = Constants.DIRECTION_RIGHT;
        } else if (xRotation < Constants.ROTATION_THRESHOLD) {
            xDirection = Constants.DIRECTION_LEFT;
        } else {
            xDirection = Constants.DIRECTION_NONE;
        }
    }

    /**
     * Prevents the Bee from going out of bounds and draws it to the screen
     *
     * @param canvas a valid canvas to draw on
     */
    @Override
    public void move(Canvas canvas) {
        if (sprite != null) {
            xPosition += xDirection;
            if (xPosition > (canvas.getWidth()-width)) {
                xPosition = canvas.getWidth()-width;
            }

            if (xPosition < 0) {
                xPosition = 0;
            }

            sprite.setBounds((int)xPosition, (int)yPosition, (int)(xPosition+width), (int)(yPosition+height));
            sprite.draw(canvas);
        }
    }
}
