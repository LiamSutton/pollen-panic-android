package com.ls.pollenpanic;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class Bee extends GameObject {
    public Bee(float xPosition, float yPosition, float xDirection, float yDirection, Drawable sprite) {
        super(xPosition, yPosition, xDirection, yDirection, sprite);
    }
}
