package com.ls.pollenpanic;

import android.graphics.drawable.Drawable;

public class GameObject {

    protected float xPosition;
    protected float yPosition;
    protected float xDirection;
    protected float yDirection;
    protected int width;
    protected int height;

    Drawable sprite;

    public GameObject(float xPosition, float yPosition, float xDirection, float yDirection, int width, int height, Drawable sprite) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
}
