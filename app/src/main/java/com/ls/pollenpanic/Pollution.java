package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

// used to represent a harmful obstacle for the Bee and ends the game if collided with
public class Pollution extends GameObject implements IMoveable {

    public Pollution(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, sprite);
    }

    // if the object has a valid sprite, move it down the screen and render it
    @Override
    public void move(Canvas canvas) {
        if (sprite != null) {
            yPosition += yDirection;

            sprite.setBounds((int)xPosition, (int)yPosition, (int)(xPosition+width), (int)(yPosition+height));
            sprite.draw(canvas);
        }
    }
}
