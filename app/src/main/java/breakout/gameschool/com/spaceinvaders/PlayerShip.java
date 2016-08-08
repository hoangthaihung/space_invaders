package breakout.gameschool.com.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by dale on 8/8/2016.
 */
public class PlayerShip {
    RectF rect;

    // the player ship will be represeted by a Bitmap
    private Bitmap bitmap;

    // how long and high our paddle will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // this will hold the pixels per second speed that the paddle will move
    private float shipSpeed;

    // which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // is the ship moving and in which direction
    private int shipMoving = STOPPED;

    // constructor
    public PlayerShip(Context _context, int _screenX, int _screenY) {
        // initialze a blank RectF
        this.rect = new RectF();

        this.length = _screenX / 10;
        this.height = _screenY / 10;

        // start ship in roughly the centre
        this.x = _screenX / 2;
        this.y = _screenY - 20;

        // initialize the bitmap
        bitmap = BitmapFactory.decodeResource(_context.getResources(), R.drawable.playership);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) length, (int) height, false);

        // how fast is the spaceship in pixels per second
        shipSpeed = 350;

    }

    public RectF getRect() {
        return this.rect;
    }

    // this is a getter method to make the rectangle that defines our paddle available in BreakoutView class
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public float getX() {
        return this.x;
    }

    public float getLength() {
        return this.length;
    }

    // this method will be used to change/set if the paddle is going left, right or nowhere
    public void setMovementState(int state) {
        this.shipMoving = state;
    }

    // this update method will be called from update in SpaceInvadersView
    // it determines if the player ship needs to move and changes the coordinates
    // contained in x if necessary
    public void update(long fps) {
        if (shipMoving == LEFT) {
            this.x = this.x - shipSpeed / fps;
        } else if (shipMoving == RIGHT) {
            x = x + shipSpeed / fps;
        }

        // update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }


}
