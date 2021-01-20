package com.ls.pollenpanic;

import android.graphics.drawable.Drawable;

// The base class from which all GameObjects derive
public class GameObject {

    protected float xPosition; // current xPosition
    protected float yPosition; // current yPosition
    protected float xDirection; // current xDirection
    protected float yDirection; // current yDirection
    protected int width; // width of the GameObjects sprite
    protected int height; // height of the GameObjects sprite

    Drawable sprite; // used to store an image representing the GameObject

    // Constructor
    public GameObject(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.width = Constants.SPRITE_WIDTH;
        this.height = Constants.SPRITE_HEIGHT;
        this.sprite = sprite;
    }
}
