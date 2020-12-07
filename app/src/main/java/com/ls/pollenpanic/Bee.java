package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class Bee extends GameObject implements IMoveable {

    final int WIDTH = 128;
    final int HEIGHT = 128;

    public Bee(float xPosition, float yPosition, float xDirection, float yDirection, int width, int height, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, width, height, sprite);
        this.width = WIDTH;
        this.height = HEIGHT;
    }


    public void rotationChanged(float xRotation) {

        if (xRotation > 0.0f) {
            xDirection = -10;
        } else if (xRotation < 0.0f) {
            xDirection = 10;
        } else {
            xDirection = 0;
        }
    }

    @Override
    public void move(Canvas canvas) {
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
