package com.ls.pollenpanic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

public class GameSurfaceView extends SurfaceView implements Runnable {
    SurfaceHolder surfaceHolder;
    Thread gameThread;
    boolean isRunning = true;
    Paint backgroundPaint;

    Bee bee;
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.CYAN);

        gameThread = new Thread(this);
        gameThread.start();

        surfaceHolder = getHolder();

        Drawable beeSprite = ContextCompat.getDrawable(context, R.drawable.bee);
        bee = new Bee(250, 100, 0, 0, beeSprite);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            bee.render(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
