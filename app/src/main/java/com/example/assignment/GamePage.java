package com.example.assignment;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class GamePage extends AppCompatActivity {

    public static GamePage Instance = null;

    private GestureDetectorCompat gestureDetectorCompat = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        Instance = GamePage.this;

        SwipeListener.Instance.setGamePage(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, SwipeListener.Instance);

        setContentView(new GameView(getApplicationContext())); // Surfaceview = GameView

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();
        gestureDetectorCompat.onTouchEvent(event);
        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(GameSystem.Instance.GetIsMenu() == true)
        {

            finish();
        }
        GameSystem.Instance.SetIsMenu(false);
        GameSystem.Instance.SetIsDead(false);
        GameSystem.Instance.SetIsPaused(false);
        Attributes.Instance.setHP(3);
        Attributes.Instance.setScoreValue(0);


    }
}

