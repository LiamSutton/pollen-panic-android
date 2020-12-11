package com.ls.pollenpanic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {
    SurfaceHolder surfaceHolder;
    Thread gameThread;
    boolean isRunning = true;
    Paint backgroundPaint;
    Paint textPaint;
    int xRot;

    Bee bee;
    PollenCollection pollenCollection;
    Pollution pollution;

    Random rand;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(42);
        textPaint.setStrokeWidth(2);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.CYAN);
        rand = new Random();

        gameThread = new Thread(this);
        gameThread.start();

        surfaceHolder = getHolder();
        Drawable beeSprite = ContextCompat.getDrawable(context, R.drawable.bee);
        Drawable pollenSprite = ContextCompat.getDrawable(context, R.drawable.pollen);
        Drawable pollutionSprite = ContextCompat.getDrawable(context, R.drawable.pollution);
        bee = new Bee(500, 1500, 0, 0, 128, 128,beeSprite);
        pollution = new Pollution(rand.nextInt(952), 0, 0, 5, 128, 128, pollutionSprite);
        pollenCollection = new PollenCollection(10);
        pollenCollection.setSprite(pollenSprite);
        pollenCollection.initialise();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            DecimalFormat df = new DecimalFormat("0.00");
            String txt = String.format("Current X Rotation = %s", df.format(xRot));
            canvas.drawText(txt, 150, 500, textPaint);
            for (Pollen p : pollenCollection.pollenCollection) {
                boolean collided = checkForCollision(bee, p);
                if (collided) {
                    pollenCollection.movePollenToBack(p);
                }

            }
            bee.move(canvas);
            pollenCollection.render(canvas);
            pollution.move(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void setRotationX(int xRot) {
        this.xRot = xRot;
        bee.rotationChanged(xRot);
    }

    public boolean checkForCollision(Bee bee, Pollen pollen) {
        Rect r1 = new Rect((int)bee.xPosition, (int)bee.yPosition, (int)(bee.xPosition+bee.width), (int)(bee.yPosition + bee.height));
        Rect r2 = new Rect((int)pollen.xPosition, (int)pollen.yPosition, (int)(pollen.xPosition+pollen.width), (int)(pollen.yPosition + pollen.height));
        if (Rect.intersects(r1,r2)) {
            return true;
        }
        else {
            return false;
        }
    }
}
