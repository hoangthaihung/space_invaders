package breakout.gameschool.com.spaceinvaders;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class SpaceInvaderActivity extends Activity {

    // spaceInvadersView will be the view of the game
    // it will also hold the logic of the game
    // and respond to screen touches as well
    SpaceInvadersView spaceInvadersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display d = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        d.getSize(size);

        // Initialize gameView and set it as the view
        spaceInvadersView = new SpaceInvadersView(this,size.x,size.y);
        setContentView(spaceInvadersView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // tell the game view pause method to execute
        spaceInvadersView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // tell the game view resume method to execute
        spaceInvadersView.resume();
    }
}
