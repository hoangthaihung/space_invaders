package breakout.gameschool.com.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by dale on 8/8/2016.
 */
public class Invader {
    RectF rect;
    Random generator = new Random();

    // the player ship will be represented by a Bitmap
    private Bitmap bitmap1;
    private Bitmap bitmap2;

    // how long and high our paddle will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // this will hold the pixels per second speed that the paddle will move
    private float shipSpeed;

    public final int LEFT = 1;
    public final int RIGHT = 2;

    // is the ship moving and in which direction
    private int shipMoving = RIGHT;

    boolean isVisible;

    public Invader(Context _context, int _row, int _column, int _screenX, int _screenY) {
        // initialize a blank RectF
        this.rect = new RectF();
        this.length = _screenX / 20;
        this.height = _screenY / 20;
        this.isVisible = true;

        int padding = _screenX / 25;

        this.x = _column * (this.length + padding);
        this.y = _row * (this.length + padding);

        // initialize the bitmap
        this.bitmap1 = BitmapFactory.decodeResource(_context.getResources(), R.drawable.invader1);
        this.bitmap2 = BitmapFactory.decodeResource(_context.getResources(), R.drawable.invader2);

        // stretch the first bitmap to a size appropriate for the screen resolution
        this.bitmap1 = Bitmap.createScaledBitmap(bitmap1, (int) this.length, (int) this.height, false);
        this.bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) this.length, (int) this.height, false);

        // how fast is the invader in pixels per second
        this.shipSpeed = 40;
    }

    public void setInvisible() {
        this.isVisible = false;
    }

    public boolean getVisibility() {
        return this.isVisible;
    }

    public RectF getRect() {
        return this.rect;
    }

    public Bitmap getBitmap() {
        return this.bitmap1;
    }

    public Bitmap getBitmap2() {
        return this.bitmap2;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getLength() {
        return this.length;
    }

    public void update(long fps) {
        if (this.shipMoving == LEFT) {
            this.x = this.x - this.shipSpeed / fps;
        }

        if (this.shipMoving == RIGHT) {
            this.x = this.x + this.shipSpeed / fps;
        }

        // update rect which is used to detect hits
        this.rect.top = this.y;
        this.rect.bottom = this.y + this.height;
        this.rect.left = this.x;
        this.rect.right = this.x + this.length;
    }

    public void dropDownAndReverse() {
        if (this.shipMoving == LEFT) {
            this.shipMoving = RIGHT;
        } else {
            this.shipMoving = LEFT;
        }

        this.y = this.y + this.height;
        this.shipSpeed = this.shipSpeed * 1.18f;
    }

    public boolean takeAim(float playerShipX, float playerShipLength) {
        int ramdomNumber = -1;

        // if near the player
        if ((playerShipX + playerShipLength > this.x && playerShipX + playerShipLength < this.x + this.length) ||
                (playerShipX > this.x && playerShipX < this.x + this.length)) {
            // a 1 in 500 chance to shoot
            ramdomNumber = generator.nextInt();
            if(ramdomNumber == 0){
                return true;
            }
        }

        // if firing randomly (not near the player) a 1 in 5000 chance
        ramdomNumber = generator.nextInt(2000);
        if (ramdomNumber == 0){
            return true;
        }

        return false;
    }


}
