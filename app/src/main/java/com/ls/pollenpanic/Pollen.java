package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

// used to represent a pickup which increases the players score
public class Pollen extends GameObject implements IMoveable {

    public Pollen(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, sprite);
    }

    // if the pollen has a valid sprite, move it downwards and render it
    @Override
    public void move(Canvas canvas) {
        if (sprite != null) {
            yPosition += yDirection;

            sprite.setBounds((int)xPosition, (int)yPosition, (int)(xPosition+width), (int)(yPosition+height));
            sprite.draw(canvas);
        }
    }
}
