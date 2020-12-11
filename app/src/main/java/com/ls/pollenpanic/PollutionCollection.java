package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

public class PollutionCollection {
    ArrayList<Pollution> pollutionCollection;
    Random rand;
    Drawable sprite;
    int furthestBackPollution = 9;

    public PollutionCollection(int size) {
        this.pollutionCollection = new ArrayList<Pollution>(size);
        rand = new Random();
    }

    public void initialize() {
        int yPos = -500;
        for (int i = 0; i < 10; i++) {
            pollutionCollection.add(new Pollution(rand.nextInt(952), yPos, 0, 5, 128, 128, sprite));
            yPos -= 500;
        }
    }

    public void update(Canvas canvas) {
        for (Pollution p : pollutionCollection) {
            p.move(canvas);
        }
    }

    public void movePollutionToBack(Pollution p) {
        Pollution back = pollutionCollection.get(furthestBackPollution);
        p.xPosition = rand.nextInt(1080);
        p.yPosition = back.yPosition - 500;
        furthestBackPollution = pollutionCollection.indexOf(p);
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
