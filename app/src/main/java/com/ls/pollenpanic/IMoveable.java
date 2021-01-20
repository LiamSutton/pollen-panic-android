package com.ls.pollenpanic;

import android.graphics.Canvas;

// all objects that move in the game implement this interface as it allows for different movement options
public interface IMoveable {
    void move(Canvas canvas);
}
