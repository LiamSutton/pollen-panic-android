package com.ls.pollenpanic;

import android.graphics.drawable.Drawable;

public class GameObject {

    protected float xPosition;
    protected float yPosition;
    protected float xDirection;
    protected float yDirection;

    Drawable sprite;

    public GameObject(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.sprite = sprite;
    }
}
