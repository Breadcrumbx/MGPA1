package com.example.assignment;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Primitives.Entity2D;

public class Player extends Entity2D {//implements EntityBase,Collidable {


    private boolean isDone = false;
    private boolean touchDown = false;
    private Bitmap kid = null;
    //private Bitmap kid2 = null;
    private SurfaceView view = null;

    private static int width,height;
    private static float xPos, yPos;
    private int ScreenWidth,ScreenHeight;

    Attributes attributes = Attributes.Instance;



    private Sprite PlayerSprite = null;

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    //For us to initilise or load resources eg:images
    public void Init(SurfaceView _view){
        //Pos = new Vector2();
        kid = ResourceManager.Instance.GetBitmap(R.drawable.kid1);
        //kid2 = BitmapFactory.decodeResource(_view.getResources(),R.drawable.kid2);
        //PlayerSprite = new Sprite(kid,4,4,16);
        //Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;
        //System.out.println(Position.x);
        Pos.x = (float) ScreenWidth * 0.45f;
        Pos.y = (float) ScreenHeight * 0.85f;
//        xPos = (float) ScreenWidth * 0.45f;
//        yPos = (float) ScreenHeight * 0.85f;

        kid = Bitmap.createScaledBitmap(kid,(int) (ScreenWidth * 0.06f),(int) (ScreenHeight * 0.127f),false);

        width = kid.getWidth();
        height = kid.getHeight();
        // Player's Attributes
        attributes.setScoreValue(0); // Score
        attributes.setHP(3); // Health
        //System.out.println("X: " + xPos + " " + "Y: " + yPos);
        //System.out.println("Right: " + GetRight() + ", Bottom: " + GetBottom());
    }

    @Override
    public void Update(float _dt) {
        //PlayerSprite.Update(_dt);
        if (attributes.getHP() <= 0)
        {
            GamePage.Instance.finish();
            System.exit(0);
            // Here it checks what happens if player's hp drop to zero
            // Nothing for now :_)
        }
        if(Pos.y < 0)
        {
            Pos.y = ScreenHeight;
        }
        if (TouchManager.Instance.IsDown() && !touchDown) {
            //Example of touch on screen in the main game to trigger back to Main menu
            touchDown = true;
            //yPos -= velocity * _dt;
            Pos.y -= 50.0;
            attributes.setScoreValue(attributes.getScoreValue()+1);
            //System.out.println("X: " + xPos + " " + "Y: " + yPos);
            //System.out.println("Right: " + GetRight() + ", Bottom: " + GetBottom());

        }
        else if (!TouchManager.Instance.IsDown() && touchDown)
        {
            touchDown = false;
        }

    }




    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(kid,(float) Pos.x,(float) Pos.y,null);//1st image
        //PlayerSprite.Render(_canvas,(int)xPos,(int)yPos);//1st image
    }
    @Override
    public boolean IsInit(){
        return kid != null;
    }
    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERPLAYER_LAYER;

    }
    @Override
    public void SetRenderLayer(int _newLayer){
        return;

    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_PLAYER;
    }

    @Override
    public String GetType()
    {
        return "Player";
    }


//    @Override
//    public float GetPosX() // Gets Left part of the sprite
//    {
//        return Pos.x;
//    }

    @Override
    public float GetPosY() // Gets top part of the sprite
    {
        return (Pos.y + (Pos.y + height)) * 0.5f;
    }

    @Override
    public float GetRight()// Gets the right part of the sprite
    {
        return Pos.x + width;
    }

    @Override
    public float GetBottom()// Gets the bottom part of the sprite
    {
        return Pos.y + (height - 38);
    }



    @Override
    public float GetRadius()
    {
        return width * 0.5f;
    }

    public float lerp(float yGoal, float yCurrent, float _dt)
    {
        float yDifference = yGoal - yCurrent;

        if(yDifference > _dt)
            return yCurrent + _dt;
        if(yDifference < - _dt)
            return yCurrent - _dt;
        return yGoal;
    }


    public static Player Create(){
        Player result = new Player();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }



    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() == "Cars"){
            SetIsDone(false); // Destroy the item / isDone true means it disappears
            attributes.setHP(attributes.getHP() - 1);
        }
    }

}
