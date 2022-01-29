package com.example.assignment;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener implements View.OnTouchListener
{
    private View view = null;
    public static final SwipeListener Instance = new SwipeListener();
    //Initialize variable
    GestureDetector gestureDetector;


    public void Init(View _view)
    {
        view = _view;
    }

    //Create constructor
    SwipeListener(){
        //Initializing threshold value
        int threshold = 100;
        int thresholdVelocity = 100;
        //Initialize simple gesture listener
        GestureDetector.SimpleOnGestureListener Listener =
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e){
                        return true;
                    }
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
                    {
                        // Get the difference
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
                                        System.out.println("Swiped right");
                                    }
                                    else
                                    {
                                        System.out.println("Swiped left");
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
                                        //When swiped down
                                        System.out.print("Swiped down");
                                    }
                                    else
                                    {
                                        System.out.println("Swiped up");
                                    }
                                    return true;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return false;
                    }
                };
        //Initialize gesture detector
        gestureDetector = new GestureDetector(Listener);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        // Return gesture event
        return gestureDetector.onTouchEvent(motionEvent);
    }


}
