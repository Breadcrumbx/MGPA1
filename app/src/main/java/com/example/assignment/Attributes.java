package com.example.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class Attributes {
    public final static Attributes Instance = new Attributes(); // Singleton
    private int value; // score
    private int hp;
    private boolean starPowerActive = false;

    public boolean getStarPower()
    {
        return starPowerActive;
    }
    public int getScoreValue(){
        return value;
    }
    public int getHP(){ return hp;}

    public void setScoreValue(int newValue){
        this.value = newValue;
    }
    public void setHP(int hp) { this.hp = hp;}
    public void setStarPower(boolean value)
    {
        starPowerActive = value;
    }
}
