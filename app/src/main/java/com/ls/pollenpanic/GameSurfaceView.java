package com.ls.pollenpanic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {
    SurfaceHolder surfaceHolder;
    Thread gameThread;
    boolean isRunning = true;
    Paint backgroundPaint;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.CYAN);

        gameThread = new Thread(this);
        gameThread.start();

        surfaceHolder = getHolder();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
