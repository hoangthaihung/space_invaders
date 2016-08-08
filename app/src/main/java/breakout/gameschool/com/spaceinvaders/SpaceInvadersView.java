package breakout.gameschool.com.spaceinvaders;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by dale on 8/8/2016.
 */
public class SpaceInvadersView extends SurfaceView implements Runnable {

    Context context;

    // this is our thread
    private Thread gameThread = null;

    // our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset when the game is running or not.
    private volatile boolean playing;

    // Game is paused at the start
    private boolean paused = true;

    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;

    // This tracks the game frame rate
    private long fps;

    // this is used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    private int screenX;
    private int screenY;

    // the player ship
    private PlayerShip playerShip;

    // the player's bullet
    private Bullet bullet;

    // the invaders bullets
    private Bullet[] invadersBullets = new Bullet[200];
    private int nextBullet;
    private int maxInvadersBullets = 10;

    // Up to 60 invaders
    Invader[] invaders = new Invader[60];
    int numInvaders = 0;

    // The player's shelters are built from bricks
    private DefenseBrick[] bricks = new DefenseBrick[400];
    private int numBricks;

    // For sound FX
    private SoundPool soundPool;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int shootID = -1;
    private int damageShelterID = -1;
    private int uhID = -1;
    private int ohID = -1;

    // the score
    int score = 0;
    // lives
    private int lives = 3;

    // how menacing should the sound be?
    private long menaceInterval = 1000;
    // which menace sound should play next
    private boolean uhOrOh;
    // when did we last play a menacing sound
    private long lastMenaceTime = System.currentTimeMillis();


    // when we initialize a game view
    public SpaceInvadersView(Context _context, int _x, int _y) {
        super(_context);

        // make a globally available copy of the context so we can use it in another method.
        this.context = _context;

        // initialize ourHolder and paint objects.
        ourHolder = getHolder();
        paint = new Paint();

        this.screenX = _x;
        this.screenY = _y;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        try {
            // create objects of the 2 required classes
            AssetManager assetManager = this.context.getAssets();
            AssetFileDescriptor descriptor;

            // load our fx in memory ready for use
            descriptor = assetManager.openFd("shoot.ogg");
            shootID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("invaderexplode.ogg");
            invaderExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("damageshelter.ogg");
            damageShelterID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("playerexplode.ogg");
            playerExplodeID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("uh.ogg");
            uhID = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("oh.ogg");
            ohID = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            Log.e("SpaceInvaderView:", "falied to load sound files.");
        }

        prepareLevel();

    }

    private void prepareLevel() {
        // here we will initialize all the game objects

        // make a new player space ship
        playerShip = new PlayerShip(this.context, this.screenX, this.screenY);

        // prepare the players bullet
        bullet = new Bullet(this.screenY);

        // initialize the invadersBullets array
        for (int i = 0; i < invadersBullets.length; i++) {
            invadersBullets[i] = new Bullet(screenY);
        }

        // build an army of invaders

        // build the shelters
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();

            // update the frame
            if (!paused) {
                update();
            }

            // draw the frame
            draw();

            // calculate the fps this frame, we can then use the result to time animations and more
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }

        // do somethiing
    }

    private void update() {
        // did an invader bump into the side of the screen
        boolean bumped = false;

        // has the player lost
        boolean lost = false;

        // move the player's ship
        playerShip.update(fps);

        // update the invaders if visible

        // update all the invaders bullets if active

        // did an invader bump into the edge of the screen

        if (lost) {
            prepareLevel();
        }

        // update the players bullet
        if (bullet.getStatus()) {
            bullet.update(fps);
        }

        // update all the invaders bullets if active
        for (int i = 0; i < invadersBullets.length; i++) {
            if (invadersBullets[i].getStatus()) {
                invadersBullets[i].update(fps);
            }
        }

        // has the player's bullet hit the top of the screen

        // has an invaders bullet hhit the bottom of the screen

        // has the player's bullet hit an invader

        // has an alien bullet hit a shelter brick

        // has a player bullet hit a shelter brick

        // has an invader bullet hit the player ship 

    }

    private void draw() {
        // make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            //Log.e("GameView: ", "draw()");
            // lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // draw the player spaceship
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX(), this.screenY - 50, paint);

            // draw the invaders

            // draw the bricks if visible

            // draw the players bullet if active

            // draw all the invader's bullets if active
            for (int i = 0; i < invadersBullets.length; i++) {
                if (invadersBullets[i].getStatus()) {
                    canvas.drawRect(invadersBullets[i].getRect(), paint);
                }
            }

            // draw the score and remaining lives
            // change the brush color
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + " Lives: " + lives, 10, 50, paint);

            // draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    // If SpaceInvadersActivity is paused/stopped --> shutdown our thread
    public void pause() {
        playing = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("SpaceInvadersView:", "joining thread failed.");
        }
    }

    // If spaceInvadersActivity is started, then start our thread
    public void resume() {
        playing = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // player has touched the screen
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if (event.getY() > this.screenY - this.screenY/8){
                    if (event.getX() > screenX/2){
                        playerShip.setMovementState(playerShip.RIGHT);
                    } else {
                        playerShip.setMovementState(playerShip.LEFT);
                    }
                }

                if (event.getY() < this.screenY - this.screenY/8){
                    playerShip.setMovementState(playerShip.STOPPED);
                }

                break;

            // player has removed finger from screen
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
