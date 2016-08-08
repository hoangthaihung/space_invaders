package breakout.gameschool.com.spaceinvaders;

import android.graphics.RectF;

/**
 * Created by dale on 8/8/2016.
 */
public class Bullet {
    private float x;
    private float y;
    private RectF rect;

    // which way is it shooting
    public final int UP = 0;
    public final int DOWN = 1;

    // going nowhere
    int heading = -1;
    float speed = 350;

    private int width = 1;
    private int height;

    private boolean isActive;

    public Bullet(int screenY) {
        this.height = screenY / 20;
        this.isActive = false;
        this.rect = new RectF();
    }

    public RectF getRect() {
        return this.rect;
    }

    public boolean getStatus() {
        return this.isActive;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public float getImpactPointY() {
        if (this.heading == DOWN) {
            return this.y + this.height;
        } else {
            return this.y;
        }
    }

    public boolean shoot(float startX, float startY, int direction) {
        if (!isActive) {
            this.x = startX;
            this.y = startY;
            this.heading = direction;
            this.isActive = true;
            return true;
        }

        // bullet already active
        return false;
    }

    public void update(long fps) {
        // just move up or down
        if (this.heading == UP) {
            this.y = this.y - this.speed / fps;
        } else {
            this.y = this.y + this.speed / fps;
        }

        // update rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;
    }


}
