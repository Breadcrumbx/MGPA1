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
    public final static Attributes Instance = new Attributes();
    private int value; // score

    public int getScoreValue(){
        return value;
    }

    public void setScoreValue(int newValue){
        this.value = newValue;
    }

}
