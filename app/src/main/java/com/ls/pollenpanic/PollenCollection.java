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
    int baseSpawnYPosition = -500;
    int gridSize;
    int furthestbackIdx;

    public PollenCollection(int size, int gridSize) {
        this.pollenCollection = new ArrayList<Pollen>(size);
        this.gridSize = gridSize;
        rand = new Random();
        furthestbackIdx = size-1;
    }

    public void initialise() {
        int yPos = 0;
        for(int i = 0; i < 10; i++) {
           pollenCollection.add(new Pollen(rand.nextInt(gridSize) * Constants.SPRITE_WIDTH, yPos, 0, 5, 128, 128, sprite));
           yPos -= 500;
        }
    }
    public void render(Canvas canvas) {
        for (Pollen p : pollenCollection) {
            p.move(canvas);
        }
    }
    

    public void resetPollen(Pollen p) {
        Pollen furthestBack = pollenCollection.get(furthestbackIdx); // get reference to the currently furthest back Pollen
        p.xPosition = rand.nextInt(gridSize) * Constants.SPRITE_WIDTH; // Pick a new x position on the grid
        p.yPosition = furthestBack.yPosition - 500; // place the Pollen behind the furthest back pollen
        furthestbackIdx = pollenCollection.indexOf(p); // update the furthest back pollen
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
