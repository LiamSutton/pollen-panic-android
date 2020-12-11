package com.ls.pollenpanic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {
    SurfaceHolder surfaceHolder;
    Thread gameThread;
    boolean isRunning = true;
    Paint backgroundPaint;
    Paint textPaint;
    int xRot;
    int currentScore;
    Bee bee;
    PollenCollection pollenCollection;
    PollutionCollection pollutionCollection;
    NavController navController;
    ScoreModel scoreModel;

    Random rand;
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int gridSize = Resources.getSystem().getDisplayMetrics().widthPixels / 128;
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(128);
        textPaint.setStrokeWidth(6);
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
        pollutionCollection = new PollutionCollection(10, gridSize);
        pollutionCollection.setSprite(pollutionSprite);
        pollutionCollection.initialize();
        pollenCollection = new PollenCollection(10, gridSize);
        pollenCollection.setSprite(pollenSprite);
        pollenCollection.initialise();

        currentScore = 0;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            for (Pollen p : pollenCollection.pollenCollection) {
                boolean collided = checkForCollision(bee, p);
                if (collided) {
                    pollenCollection.resetPollenPosition(p);
                    currentScore++;
                }

                if (p.yPosition > canvas.getHeight()) {
                    pollenCollection.resetPollenPosition(p);
                }
            }

            for (Pollution p : pollutionCollection.pollutionCollection) {
                boolean collided = checkForCollision(bee, p);
                if (collided) {
                    // Game Over
                    isRunning = false;
                    scoreModel.setScore(currentScore);
                    navController.navigate(R.id.action_gameFragment_to_gameOverFragment);
                }

                if (p.yPosition > canvas.getHeight()) {
                    pollutionCollection.resetPollutionPosition(p);
                }
            }
            bee.move(canvas);
            pollenCollection.render(canvas);
            pollutionCollection.update(canvas);
            canvas.drawText(String.valueOf(currentScore), canvas.getWidth()/2, 200, textPaint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void setRotationX(int xRot) {
        this.xRot = xRot;
        bee.rotationChanged(xRot);
    }

    public boolean checkForCollision(Bee bee, GameObject obj) {
        Rect r1 = new Rect((int)bee.xPosition, (int)bee.yPosition, (int)(bee.xPosition+bee.width), (int)(bee.yPosition + bee.height));
        Rect r2 = new Rect((int)obj.xPosition, (int)obj.yPosition, (int)(obj.xPosition+obj.width), (int)(obj.yPosition + obj.height));

        return Rect.intersects(r1, r2);
    }

    public void setNavController(View v) {
        navController = Navigation.findNavController(v);
    }

    public void setScoreModel(ScoreModel sm) {
        scoreModel = sm;
    }
}
