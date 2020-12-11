package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

public class PollutionCollection {
    ArrayList<Pollution> pollutionCollection;
    Random rand;
    Drawable sprite;
    int furthestBackIdx;
    int gridSize;

    public PollutionCollection(int size, int gridSize) {
        this.pollutionCollection = new ArrayList<Pollution>(size);
        this.gridSize = gridSize;
        rand = new Random();
        furthestBackIdx = size-1;
    }

    public void initialize() {
        int yPos = -500;
        for (int i = 0; i < 10; i++) {
            pollutionCollection.add(new Pollution(rand.nextInt(gridSize) * Constants.SPRITE_WIDTH, yPos, 0, 5, 128, 128, sprite));
            yPos -= 500;
        }
    }

    public void update(Canvas canvas) {
        for (Pollution p : pollutionCollection) {
            p.move(canvas);
        }
    }

    public void resetPollutionPosition(Pollution p) {
        Pollution furthestBack = pollutionCollection.get(furthestBackIdx);
        p.xPosition = rand.nextInt(gridSize) * Constants.SPRITE_WIDTH;
        p.yPosition = furthestBack.yPosition - 500;
        furthestBackIdx = pollutionCollection.indexOf(p);
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
