package com.ls.pollenpanic;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;


public class PollutionCollection {
    ArrayList<Pollution> pollutionCollection; // an aggregation of pollution objects
    Random rand; // used to generate a random seed
    Drawable sprite; // sprite used to represent pollution object
    int furthestBackIdx; // index of the piece of pollution farthest away from the player
    int gridSize; // the size of the current grid

    // constructor
    public PollutionCollection(int size, int gridSize) {
        this.pollutionCollection = new ArrayList<Pollution>(size);
        this.gridSize = gridSize;
        rand = new Random();
        furthestBackIdx = size-1; // by default the farthest away piece of pollution is the last one created
    }

    // populate the arraylist
    public void initialize() {
        int yPos = Constants.POLLUTION_START_POSITION;

        // for every piece of pollution, place it 500 units behind its predecessor and pick it a random column in the grid
        for (int i = 0; i < 10; i++) {
            pollutionCollection.add(new Pollution(rand.nextInt(gridSize) * Constants.SPRITE_WIDTH, yPos, 0, 5, sprite));
            yPos -= Constants.POLLUTION_PLACEMENT_DISTANCE;
        }
    }

    // move the pieces of pollution
    public void move(Canvas canvas) {
        for (Pollution p : pollutionCollection) {
            p.move(canvas);
        }
    }

    // moves the piece of pollution to the back of the collection
    public void resetPollutionPosition(Pollution p) {
        Pollution furthestBack = pollutionCollection.get(furthestBackIdx);
        p.xPosition = rand.nextInt(gridSize) * Constants.SPRITE_WIDTH;
        p.yPosition = furthestBack.yPosition - 750;
        furthestBackIdx = pollutionCollection.indexOf(p);
    }

    public void setSprite(Drawable drawable) {
        this.sprite = drawable;
    }
}
