package com.example.assignment.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Attributes;
import com.example.assignment.Collidable;
import com.example.assignment.EntityManager;
import com.example.assignment.LayerConstants;
import com.example.assignment.Primitives.Entity2D;
import com.example.assignment.R;
import com.example.assignment.ResourceManager;
import com.example.assignment.Sprite;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class Cars extends Entity2D {//implements EntityBase,Collidable {
    //Bitmaps
    private Bitmap car = null;
    private Bitmap scale = null;


    private static int width,height;
    private float carSpeed;

    private int ScreenWidth,ScreenHeight;

    private static ArrayList<Integer>checkYSpawn = new ArrayList<>();

    private Sprite carSprite = null;





    @Override
    public void Init(SurfaceView _view) {
        car = ResourceManager.Instance.GetBitmap(R.drawable.carsprite3);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;
        //car = Bitmap.createScaledBitmap(car,(int)(ScreenWidth * 0.146f),(int)(ScreenHeight * 0.128f),false);
        car = Bitmap.createScaledBitmap(car,(int)(ScreenWidth * 0.146f),(int)(ScreenHeight * 0.3f),false);
        carSprite = new Sprite(car,3,1,10);
        width = carSprite.GetWidth();
        height = carSprite.GetHeight();



        float max =2;
        float min =1;
        float range = max - min+1;

        float rand = (float)(Math.random()*range)+min;
        carSpeed = 300 * rand;


        float[] spawnArray = new float[]{

                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f,
                0.5f,0.6f,0.4f,0.7f,0.1f,0.1f,0.2f,0.3f,0.4f


        };

        Random random = new Random(System.currentTimeMillis());
        Pos.y = (random.nextInt(spawnArray.length));
        while (checkYSpawn.contains((Integer)(int)Pos.y)) {
            Pos.y = (random.nextInt(spawnArray.length));
        }
        checkYSpawn.add((Integer)(int)Pos.y);
        Pos.y = spawnArray[(int)Pos.y]*ScreenHeight;

        Pos.x = (int)(random.nextFloat()*ScreenWidth);



    }

    @Override
    public void Update(float _dt) {
        carSprite.Update(_dt);
        Pos.x -= carSpeed*_dt;
        if(Pos.x<-(int)(width*0.8))
        {
            Pos.x = ScreenWidth;
        }

    }

    @Override
    public void Render(Canvas _canvas){
        //_canvas.drawBitmap(car,xPos,yPos,null);//1st image
        carSprite.Render(_canvas,(int) Pos.x,(int)Pos.y);
    }

    @Override
    public boolean IsInit(){
        return car != null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERCAR_LAYER;

    }

    @Override
    public void SetRenderLayer(int _newLayer){
        return;

    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_CAR;
    }

    public static Cars Create(int amt){
        for(int i =0 ; i < amt ; i++)
        {
            Cars result = new Cars();
            EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_CAR);

        }
        return null;
    }


    @Override
    public String GetType()
    {
        return "Cars";
    }

    @Override
    public float GetPosX()
    {

        return Pos.x - (width * 0.5f);
    }

    @Override
    public float GetPosY()
    {
        return Pos.y - (height * 0.5f);
    }

    @Override
    public float GetRadius()
    {
        return 0.f;
    }

    @Override
    public float GetRight() {
        return GetPosX()+width;
    }

    @Override
    public float GetBottom() {
        return GetPosY()+height;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() == "Player" && !Attributes.Instance.getStarPower()){
            SetIsDone(true); // Destroy the item / isDone true means it disappears

        }
    }

}
