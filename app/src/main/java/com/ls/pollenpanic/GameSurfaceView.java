package com.ls.pollenpanic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
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
    Pollen pollen;
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
        bee = new Bee(500, 1500, 0, 0, 128, 128,beeSprite);
        pollen = new Pollen(rand.nextInt(1080), 0, 0, 5, 128, 128,pollenSprite);
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
            bee.move(canvas);
            pollen.move(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void setRotationX(int xRot) {
        this.xRot = xRot;
        bee.rotationChanged(xRot);
    }
}
