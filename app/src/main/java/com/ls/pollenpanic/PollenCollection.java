package com.ls.pollenpanic;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class PollenCollection {
    ArrayList<Pollen> pollenCollection; // an aggregation of pollen objects
    Random rand; // used to gerenate a random seed
    Drawable sprite; // sprite used to represent pollen object
    int gridSize; // the size of the current grid
    int furthestbackIdx; // the index of the piece of pollen farthest away from the player

    // constructor
    public PollenCollection(int size, int gridSize) {
        this.pollenCollection = new ArrayList<Pollen>(size);
        this.gridSize = gridSize;
        rand = new Random();
        furthestbackIdx = size-1; // by default the farthest away piece of pollen is the last to be created
    }

    // populate the arraylist
    public void initialise() {
        int yPos = Constants.POLLEN_START_POSITION;

        // for every piece of pollen, place it 500 units behind its predecessor and pick it a random column in the grid
        for(int i = 0; i < 10; i++) {
           pollenCollection.add(new Pollen(rand.nextInt(gridSize) * Constants.SPRITE_WIDTH, yPos, 0, 5, sprite));
           yPos -= Constants.POLLEN_PLACEMENT_DISTANCE;
        }
    }

    // move the pieces of pollen
    public void move(Canvas canvas) {
        for (Pollen p : pollenCollection) {
            p.move(canvas);
        }
    }


    // moves the piece of pollen to the back of the collection
    public void resetPollenPosition(Pollen p) {
        Pollen furthestBack = pollenCollection.get(furthestbackIdx); // get reference to the currently furthest back Pollen
        p.xPosition = rand.nextInt(gridSize) * Constants.SPRITE_WIDTH; // Pick a new x position on the grid
        p.yPosition = furthestBack.yPosition - 500; // place the Pollen behind the furthest back pollen
        furthestbackIdx = pollenCollection.indexOf(p); // update the furthest back pollen
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
