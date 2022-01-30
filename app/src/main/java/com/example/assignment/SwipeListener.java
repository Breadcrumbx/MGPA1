package com.example.assignment;


import android.gesture.Gesture;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener extends GestureDetector.SimpleOnGestureListener {
    private static int Min_Swipe_Dist_X = 100;
    private static int Min_Swipe_Dist_Y = 100;

    private static int Max_Swipe_Dist_X = 1000;
    private static int Max_Swipe_Dist_Y = 1000;

    private static int threshold = 100;
    private static int thresholdVelocity = 1000;

    public final static SwipeListener Instance = new SwipeListener();
    public enum SwipeState
    {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private SwipeState state = SwipeState.NONE;

    private GamePage gamePage = null;

    public GamePage getGamePage()
    {
        return gamePage;
    }

    public void setGamePage(GamePage gamePage)
    {
        this.gamePage = gamePage;
    }

    public boolean SwipedLeft()
    {
        return state == SwipeState.LEFT;
    }

    public boolean SwipedRight()
    {
        return state == SwipeState.RIGHT;
    }

    public boolean SwipedUp()
    {
        return state == SwipeState.UP;
    }

    public boolean SwipedDown()
    {
        return state == SwipeState.DOWN;
    }

    public void SetStatus(SwipeState state)
    {
        this.state = state;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        float xDiff = e2.getX() - e1.getX();
        float yDiff = e2.getY() - e1.getY();

        try{
            if(Math.abs(xDiff) > Math.abs(yDiff)){
                //checks when x is greater than y

                if(Math.abs(xDiff) > threshold && Math.abs(velocityX) > thresholdVelocity){
                    // When the difference of x is greater than the threshold
                    // When velocity of x is greater than threshold Velocity
                    if(xDiff > 0)
                    {
                        //Swiped right
                        state = SwipeState.RIGHT;
                    }
                    else
                    {
                        //Swiped left
                        state = SwipeState.LEFT;
                    }
                    return true;
                }
            }
            else
            {
                //Checks when y is greater than x
                if(Math.abs(yDiff) > threshold && Math.abs(velocityY) > thresholdVelocity)
                {
                    //When y difference is greater than threshold
                    // When velocity of y is greater than threshold Velocity
                    if (yDiff >0)
                    {
                        //Swiped down
                        state = SwipeState.DOWN;
                    }
                    else
                    {
                        //Swiped Up
                        state = SwipeState.UP;
                    }
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
