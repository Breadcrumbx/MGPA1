package com.example.assignment.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Collidable;
import com.example.assignment.Collision;
import com.example.assignment.EntityManager;
import com.example.assignment.GameSystem;
import com.example.assignment.GameView;
import com.example.assignment.LayerConstants;
import com.example.assignment.Mainmenu;
import com.example.assignment.Primitives.AudioManager;
import com.example.assignment.Primitives.Entity2D;
import com.example.assignment.R;
import com.example.assignment.ResourceManager;
import com.example.assignment.Sprite;
import com.example.assignment.SwipeListener;
import com.example.assignment.TouchManager;

public class InvincibilityPowerUp extends Entity2D {

    private Bitmap StarPower = null;
    private SurfaceView view = null;

    private static int width,height;
    private int ScreenWidth,ScreenHeight;
    private Sprite starSprite = null;

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


        StarPower = ResourceManager.Instance.GetBitmap(R.drawable.starsprite);
        StarPower = Bitmap.createScaledBitmap(StarPower,(int) (ScreenWidth * 0.1),(int) (ScreenHeight * 0.5),false);
        starSprite = new Sprite(StarPower,5,1,16);

        width = starSprite.GetWidth();
        height = starSprite.GetHeight();



        Pos.x = (float) ScreenWidth * 0.5f;
        Pos.y = (float) ScreenHeight * 0.5f;

    }

    @Override
    public void Update(float _dt) {
        starSprite.Update(_dt);
    }




    @Override
    public void Render(Canvas _canvas){
        starSprite.Render(_canvas,(int) Pos.x,(int)Pos.y);
    }
    @Override
    public boolean IsInit(){
        return StarPower != null;
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
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_STARPOWER;
    }

    @Override
    public String GetType()
    {
        return "StarPowerUp";
    }


    @Override
    public float GetPosX() // Gets Left part of the sprite
    {
        return Pos.x - (width * 0.5f);
    }

    @Override
    public float GetPosY() // Gets top part of the sprite
    {
        return Pos.y - (height * 0.5f);
    }

    @Override
    public float GetRight()// Gets the right part of the sprite
    {
        return GetPosX() + width;
    }

    @Override
    public float GetBottom()// Gets the bottom part of the sprite
    {
        return GetPosY() + height;
    }



    @Override
    public float GetRadius()
    {
        return width * 0.5f;
    }


    public static InvincibilityPowerUp Create(){
        InvincibilityPowerUp result = new InvincibilityPowerUp();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_STARPOWER);
        return result;
    }



    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() == "Player"){
            SetIsDone(true); // Destroy the item / isDone true means it disappears
        }
    }
}
