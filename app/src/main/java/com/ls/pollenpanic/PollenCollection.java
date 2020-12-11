package com.ls.pollenpanic;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class PollenCollection {
    ArrayList<Pollen> pollenCollection;
    Random rand;
    Drawable sprite;
    int lastCollidedWith = 9;

    public PollenCollection(int size) {
        this.pollenCollection = new ArrayList<Pollen>(size);
        rand = new Random();
    }

    public void initialise() {
        int yPos = 0;
        for(int i = 0; i < 10; i++) {
           pollenCollection.add(new Pollen(rand.nextInt(952), yPos, 0, 5, 128, 128, sprite));
           yPos -= 500;
        }
    }
    public void render(Canvas canvas) {
        for (Pollen p : pollenCollection) {
            p.move(canvas);
        }
    }

    public void movePollenToBack(Pollen p) {
        Pollen back = pollenCollection.get(lastCollidedWith);
        p.xPosition = rand.nextInt(1080);
        p.yPosition = back.yPosition - 500;
        lastCollidedWith = pollenCollection.indexOf(p);
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
