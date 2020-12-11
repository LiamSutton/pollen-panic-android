package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Pollution extends GameObject implements IMoveable {

    public Pollution(float xPosition, float yPosition, float xDirection, float yDirection, int width, int height, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, width, height, sprite);
    }

    @Override
    public void move(Canvas canvas) {
        if (sprite != null) {
            yPosition += yDirection;

            sprite.setBounds((int)xPosition, (int)yPosition, (int)(xPosition+width), (int)(yPosition+height));
            sprite.draw(canvas);
        }
    }
}
