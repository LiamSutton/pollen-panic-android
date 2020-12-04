package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class Bee extends GameObject {
    int width=128;
    int height=128;
    public Bee(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, sprite);
    }

    public void render(Canvas canvas) {
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

    public void rotationChanged(float xRotation) {

        if (xRotation > 0.0f) xDirection = -10;
        if (xRotation < 0.0f) xDirection = 10;
    }
}
