package com.ls.pollenpanic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.IOException;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class GameSurfaceView extends SurfaceView implements Runnable {

    SurfaceHolder surfaceHolder; // allows exclusive access to the canvas
    Thread gameThread; // the game logic runs on a separate thread than the UI to prevent slowdown
    boolean isRunning = true; // whether the game should continue running

    Paint backgroundPaint; // paint used for the background
    Paint grassPaint; // paint used for the grass
    Paint textPaint; // paint used for game text

    int xRot; // current rotation of device
    int currentScore; // the users current score

    Bee bee; // Bee (player character) object

    PollenCollection pollenCollection; // represents an aggregation of Pollen Objects
    PollutionCollection pollutionCollection; // represents an aggregation of Pollution Objects
    NavController navController; // used to facilitate navigation
    ScoreModel scoreModel; // contains information on the current game's score

    AudioAttributes audioAttributes; // configuration settings for game audio
    SoundPool gameSoundPool; // used to play sfx
    MediaPlayer backgroundMusicMediaPlayer; // used to play looping background music

    int pollenPickupSfx; // used to store reference to game sfx
    int gameOverSfx; // used to store reference to game sfx

    Random rand; // used to generate a random seed

    // constructor
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // sets the grid size that obstacles will be placed on
        int gridSize = Resources.getSystem().getDisplayMetrics().widthPixels / 128;

        // initialise Paint's
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(128);
        textPaint.setStrokeWidth(6);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(92, 167, 204));
        grassPaint = new Paint();
        grassPaint.setColor(Color.rgb(47, 142, 59));

        // set a random seed
        rand = new Random();

        // initialise objects used to play sounds
        audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        gameSoundPool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build();
        backgroundMusicMediaPlayer = MediaPlayer.create(context, R.raw.loopingbackgroundmusic);
        backgroundMusicMediaPlayer.setLooping(true);

        // load the sfx into the sound pool
        pollenPickupSfx = gameSoundPool.load(context, R.raw.pollenpickupsfx, 1);
        gameOverSfx = gameSoundPool.load(context, R.raw.gameoversfx, 1);

        // begin the game
        gameThread = new Thread(this);
        gameThread.start();
        surfaceHolder = getHolder();

        // initialise variables storing sprites used in the game
        Drawable beeSprite = ContextCompat.getDrawable(context, R.drawable.bee);
        Drawable pollenSprite = ContextCompat.getDrawable(context, R.drawable.pollen);
        Drawable pollutionSprite = ContextCompat.getDrawable(context, R.drawable.pollution);

        // initialise player character and populate obstacles
        bee = new Bee(500, 1500, 0, 0, beeSprite);

        pollutionCollection = new PollutionCollection(10, gridSize);
        pollutionCollection.setSprite(pollutionSprite);
        pollutionCollection.initialize();

        pollenCollection = new PollenCollection(10, gridSize);
        pollenCollection.setSprite(pollenSprite);
        pollenCollection.initialise();

        currentScore = 0; // score begins at 0 as its a new game
    }

    @Override
    public void run() {

        backgroundMusicMediaPlayer.start(); // begin playing the background music

        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            // get exclusive access to the canvas
            Canvas canvas = surfaceHolder.lockCanvas();

            // draw the water and grass
            drawBackground(canvas);

            // check for any relevant collisions
            checkAllCollisions(canvas);

            // move and render all objects
            bee.move(canvas);
            pollenCollection.move(canvas);
            pollutionCollection.move(canvas);

            // draw the current score to the screen
            canvas.drawText(String.valueOf(currentScore), canvas.getWidth() / 2, 200, textPaint);

            // unlock the canvas and render a frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }

    // draws the water and grass background
    void drawBackground(Canvas canvas) {
        canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
        canvas.drawRect(0, 0, 275, canvas.getHeight(), grassPaint);
        canvas.drawRect(canvas.getWidth()-275, 0,canvas.getWidth() ,canvas.getHeight(), grassPaint);
    }

    // stops the game from running, stores the final score and navigates to the game over screen
    public void gameOver() {

        backgroundMusicMediaPlayer.stop();
        backgroundMusicMediaPlayer.release();
        scoreModel.setScore(currentScore);
        isRunning = false;

        navController.navigate(R.id.action_gameFragment_to_gameOverFragment);
    }

    // check's for all relevant collisions
    public void checkAllCollisions(Canvas canvas) {
        for (Pollen p : pollenCollection.pollenCollection) {
            boolean collided = checkForCollision(bee, p);
            if (collided) {
                pollenCollection.resetPollenPosition(p); // pick a new position for the pollen object
                currentScore++; // increment the players score
                gameSoundPool.play(pollenPickupSfx, 1.0f, 1.0f, 1, 0, 1);
            }

            // if pollen goes of the screen reset its position to the top
            if (p.yPosition > canvas.getHeight()) {
                pollenCollection.resetPollenPosition(p);
            }
        }
        for (Pollution p : pollutionCollection.pollutionCollection) {
            boolean collided = checkForCollision(bee, p);
            if (collided) {
                // player hit pollution, trigger game over
                gameSoundPool.play(gameOverSfx, 1.0f, 1.0f, 1, 0, 1);
                gameOver();

            }

            // if pollution goes of the screen reset its position to the top
            if (p.yPosition > canvas.getHeight()) {
                pollutionCollection.resetPollutionPosition(p);
            }
        }
    }

    // pass the current rotation of the device to the bee object to change its direction
    public void setRotationX(int xRot) {
        this.xRot = xRot;
        bee.rotationChanged(xRot);
    }

    // draws rectangles around both objects and if they intersect, they have collided
    public boolean checkForCollision(Bee bee, GameObject obj) {
        Rect r1 = new Rect((int) bee.xPosition, (int) bee.yPosition, (int) (bee.xPosition + bee.width), (int) (bee.yPosition + bee.height));
        Rect r2 = new Rect((int) obj.xPosition, (int) obj.yPosition, (int) (obj.xPosition + obj.width), (int) (obj.yPosition + obj.height));

        return Rect.intersects(r1, r2);
    }

    public void setNavController(View v) {
        navController = Navigation.findNavController(v);
    }

    public void setScoreModel(ScoreModel sm) {
        scoreModel = sm;
    }
}
