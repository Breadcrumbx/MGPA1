package com.example.assignment.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Collidable;
import com.example.assignment.EntityBase;
import com.example.assignment.EntityManager;
import com.example.assignment.LayerConstants;
import com.example.assignment.Primitives.Entity2D;
import com.example.assignment.Primitives.Vector2;
import com.example.assignment.R;
import com.example.assignment.ResourceManager;
import com.example.assignment.Sprite;

import java.util.Random;

public class HealthPowerUp extends Entity2D {
    private static Random rand = new Random(System.currentTimeMillis());
    private Bitmap Health = null;
    private Vector2 random = new Vector2();
    private static int width,height;
    private int ScreenWidth,ScreenHeight;

    @Override
    public boolean IsDone(){
        return IsDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        IsDone = _isDone;
    }

    @Override
    //For us to initilise or load resources eg:images
    public void Init(SurfaceView _view){
        //Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;


        Health = ResourceManager.Instance.GetBitmap(R.drawable.health);
        Health = Bitmap.createScaledBitmap(Health,(int) (ScreenWidth * 0.09),(int) (ScreenHeight * 0.1),false);

        width = Health.getWidth();
        height = Health.getHeight();

        random.x = rand.nextFloat();
        random.y = rand.nextFloat();
        System.out.println("Random.x: " + random.x + ", Random.y: " + random.y );


        Pos.x = (float) ScreenWidth * random.x;
        Pos.y = (float) ScreenHeight * random.y;

    }

    @Override
    public void Update(float _dt)
    {
        //Empty
    }


    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(Health,(float) Pos.x,(float) Pos.y,null);//1st image
    }
    @Override
    public boolean IsInit(){
        return Health != null;
    }
    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;

    }
    @Override
    public void SetRenderLayer(int _newLayer){
        return;

    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){
        return EntityBase.ENTITY_TYPE.ENT_HEALTHPOWER;
    }

    @Override
    public String GetType()
    {
        return "HealthPowerUp";
    }


    @Override
    public float GetPosX() // Gets Left part of the sprite
    {
        return Pos.x;
    }

    @Override
    public float GetPosY() // Gets top part of the sprite
    {
        return Pos.y;
    }

    @Override
    public float GetRight()// Gets the right part of the sprite
    {
        return Pos.x + width;
    }

    @Override
    public float GetBottom()// Gets the bottom part of the sprite
    {
        return Pos.y + height;
    }



    @Override
    public float GetRadius()
    {
        return width * 0.5f;
    }


    public static HealthPowerUp Create(){
        int chance = 0;
        if(chance ==0)
        {
            HealthPowerUp result = new HealthPowerUp();
            EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_HEALTHPOWER);
            return result;
        }
        else
        {
            return null;
        }
    }



    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() == "Player"){
            SetIsDone(true); // Destroy the item / isDone true means it disappears
        }
    }
}


