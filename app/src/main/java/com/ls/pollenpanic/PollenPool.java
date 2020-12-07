package com.ls.pollenpanic;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class PollenPool {
    ArrayList<Pollen> pollenCollection;
    Random rand;
    Drawable sprite;

    public PollenPool(int size) {
        this.pollenCollection = new ArrayList<Pollen>(size);
        rand = new Random();
    }

    public void initialise() {
        int yPos = 0;
        for(int i = 0; i < 10; i++) {
           pollenCollection.add(new Pollen(rand.nextInt(1080), yPos, 0, 5, 128, 128, sprite));
           yPos -= 500;
        }
    }
    public void render(Canvas canvas) {
        for (Pollen p : pollenCollection) {
            p.move(canvas);
        }
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
