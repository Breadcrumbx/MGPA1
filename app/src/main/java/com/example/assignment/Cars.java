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

public class Cars implements EntityBase,Collidable {

    private boolean isDone = false;
    private Bitmap car = null;
    private Bitmap scale = null;
    private SurfaceView view = null;
    private static int width,height;
    private float xPos, yPos;
    private float carSpeed;
    private double xPos1;
    private int ScreenWidth,ScreenHeight;
    private static ArrayList<Integer>checkYSpawn = new ArrayList<>();



    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        car = ResourceManager.Instance.GetBitmap(R.drawable.carsprite);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;
        car = Bitmap.createScaledBitmap(car,(int)(ScreenWidth * 0.146f),(int)(ScreenHeight * 0.128f),false);

        width = car.getWidth();
        height = car.getHeight();

        xPos1 = Math.random();

        float max =2;
        float min =1;
        float range = max - min+1;

        float rand = (float)(Math.random()*range)+min;
        carSpeed = 300 * rand;

        float notbottom = ScreenHeight*0.8f;

        float[] spawnArray = new float[]{0.5f,0.6f,0.4f,0.7f,0.1f};
        //for(int i = 0;i<spawnArray.length;i++)
        //{
          //  yPos = (float) notbottom * (float)(Math.random()) + spawnArray[i];
        //}
        Random random = new Random(System.currentTimeMillis());
        yPos = (random.nextInt(spawnArray.length));
        while (checkYSpawn.contains((Integer)(int)yPos)) {
            yPos = (random.nextInt(spawnArray.length));
        }
        checkYSpawn.add((Integer)(int)yPos);
        yPos = spawnArray[(int)yPos]*ScreenHeight;

        xPos = (int)(random.nextFloat()*ScreenWidth);



    }

    @Override
    public void Update(float _dt) {
        //CarSprite.Update(_dt);
        //if (GameSystem.Instance.GetIsPaused()) return;
        xPos -= carSpeed*_dt;
        if(xPos<-(int)(width*0.8))
        {
            xPos = ScreenWidth;
        }

    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(car,xPos,yPos,null);//1st image

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

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static Cars Create(){
        Cars result = new Cars();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_CAR);
        return result;
    }


    @Override
    public String GetType()
    {
        return "Cars";
    }

    @Override
    public float GetPosX()
    {
        return xPos;
    }

    @Override
    public float GetPosY()
    {
        return yPos;
    }

    @Override
    public float GetRadius()
    {
        return 0.f;
    }

    @Override
    public float GetRight() {
        return xPos+width;
    }

    @Override
    public float GetBottom() {
        return yPos+height;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() == "Player"){
            SetIsDone(true); // Destroy the item / isDone true means it disappears
        }
    }

}
