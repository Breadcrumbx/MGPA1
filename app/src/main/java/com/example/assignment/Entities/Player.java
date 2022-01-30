package com.example.assignment.Entities;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Attributes;
import com.example.assignment.Collidable;
import com.example.assignment.Collision;
import com.example.assignment.EntityManager;
import com.example.assignment.GameSystem;
import com.example.assignment.GameView;
import com.example.assignment.LayerConstants;
import com.example.assignment.Mainmenu;
import com.example.assignment.Primitives.AudioManager;
import com.example.assignment.Primitives.Entity2D;
import com.example.assignment.Primitives.Vector2;
import com.example.assignment.R;
import com.example.assignment.ResourceManager;
import com.example.assignment.Sprite;
import com.example.assignment.StateManager;
import com.example.assignment.SwipeListener;
import com.example.assignment.TouchManager;

import java.util.Random;

public class Player extends Entity2D {//implements EntityBase,Collidable {

    private boolean isDone = false;
    private boolean touchDown = false;
    private boolean isDead = false;
    private Bitmap kid = null;
    public boolean reset= false;
    //private Bitmap kid2 = null;
    private SurfaceView view = null;

    private static int width,height;
    private static float xPos, yPos;
    private int ScreenWidth,ScreenHeight;

    private Bitmap DeathScreen = null;
    private Bitmap MenuButton = null;

    // For power up
    private Random rand = new Random();
    private float iFramesTimer = 5.f;

    private ColorMatrix cm = new ColorMatrix(); // Color for power up

    Attributes attributes = Attributes.Instance;

    private final Vector2 vecUp = new Vector2(0,1);
    private Vector2 vecTest = new Vector2(1,2);
    private Vector2 Diff = vecUp.Substract(vecTest);
    private Sprite PlayerSprite = null;

    private Paint paint = new Paint();


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

        DeathScreen = ResourceManager.Instance.GetBitmap(R.drawable.deathbg);
        MenuButton =  ResourceManager.Instance.GetBitmap(R.drawable.menubutton);
        DeathScreen = Bitmap.createScaledBitmap(DeathScreen, (int) (ScreenWidth), (int)(ScreenHeight), true);
        MenuButton = Bitmap.createScaledBitmap(MenuButton, (int) (ScreenWidth)/3, (int)(ScreenHeight)/5, true);

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
        // Power up
        if(Attributes.Instance.getStarPower())
        {
            iFramesTimer -= _dt;
            int R = rand.nextInt(256);
            int G = rand.nextInt(256);
            int B = rand.nextInt(256);
            cm.set(new float[] {
                    1, 0, 0, 0, R,
                    0, 1, 0, 0, G,
                    0, 0, 1, 0, B,
                    0, 0, 0, 1, 0 }); // Anti Alias
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            if(iFramesTimer < 0)
            {
                paint.setColorFilter(null);
                Attributes.Instance.setStarPower(false);
                iFramesTimer = 5.f;
            }
        }

        //System.out.println("Player's Pos: " + Pos.x + "," +Pos.y);
        if(reset == false)
        {
            Pos.x = (float) ScreenWidth * 0.45f;
            Pos.y = (float) ScreenHeight * 0.85f;
            reset = true;

        }
        if (attributes.getHP() <= 0)
        {
            //isDead = true;
            GameSystem.Instance.SetIsDead(true);
            //GamePage.Instance.finish();

            //System.exit(0);
            // Here it checks what happens if player's hp drop to zero
            // Nothing for now :_)
        }

        if(GameSystem.Instance.GetIsDead())
        {
            if (Collision.AABB(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), TouchManager.Instance.GetPosY(), 0,MenuButton.getWidth(),340,340+MenuButton.getHeight()))
            {
                System.out.println("MainMenu Clicked");

                if(GameSystem.Instance.GetIsMenu()==false)
                {
                    GameSystem.Instance.SetIsMenu(true);
                    GameView.ChangeActivity(GameView.context.get(), Mainmenu.class);
                    Cars.Create(2);


                    //StateManager.Instance.ChangeState("MainMenu");

                }
                reset = false;

//                GamePage.Instance.finish();
//                System.exit(0);

            }
        }
        // Screen wrapping
        if(Pos.y < 0)
        {
            Pos.y = ScreenHeight;
            Cars.Create(1);
            HealthPowerUp.Create();
            InvincibilityPowerUp.Create();
        }

        if(Pos.x > ScreenWidth)
        {
            Pos.x = 0;
        }
        else if (Pos.x < 0)
        {
            Pos.x = ScreenWidth;
        }


        // Movement
        if (TouchManager.Instance.IsDown() && !touchDown)
        {
            //System.out.println("Down");
            touchDown = true;
        }
        else if (!TouchManager.Instance.IsDown() && touchDown)
        {
            //System.out.println("Released");
            AudioManager.Instance.PlayAudio(R.raw.jump, 1.f);
            touchDown = false;
            // Swiping code
            if(SwipeListener.Instance.SwipedRight())
            {
                //System.out.println("Swiped right");
                SwipeListener.Instance.SetStatus(SwipeListener.SwipeState.NONE);
                Pos.x += 100.0;
            }
            else if(SwipeListener.Instance.SwipedLeft())
            {
                //System.out.println("Swiped Left");
                SwipeListener.Instance.SetStatus(SwipeListener.SwipeState.NONE);
                Pos.x -= 100.0;
            }
            else if(SwipeListener.Instance.SwipedDown())
            {
                SwipeListener.Instance.SetStatus(SwipeListener.SwipeState.NONE);
                Attributes.Instance.setScoreValue(attributes.getScoreValue() -1);
                Pos.y += 50.0;
            }
            else
            {
                //System.out.println("None");
                //Example of touch on screen in the main game to trigger back to Main menu

                //yPos -= velocity * _dt;
                Pos.y -= 50.0;
                attributes.setScoreValue(attributes.getScoreValue() + 1);
                //System.out.println("X: " + xPos + " " + "Y: " + yPos);
                //System.out.println("Right: " + GetRight() + ", Bottom: " + GetBottom());
            }
        }

    }




    @Override
    public void Render(Canvas _canvas){

        _canvas.drawBitmap(kid,(float) Pos.x,(float) Pos.y,paint);//1st image
        //PlayerSprite.Render(_canvas,(int)xPos,(int)yPos);//1st image
        if(GameSystem.Instance.GetIsDead())
        {
            _canvas.drawBitmap(DeathScreen,0, 0, null);
            _canvas.drawBitmap(MenuButton,0, 340, null);
        }
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

    public float Lerp(float yGoal, float yCurrent, float _dt)
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
        // Car
        if(_other.GetType() != this.GetType() && _other.GetType() == "Cars" && !Attributes.Instance.getStarPower() )
        {
            AudioManager.Instance.PlayAudio(R.raw.hit, 1.f);
            SetIsDone(false); // Destroy the item / isDone true means it disappears
            attributes.setHP(attributes.getHP() - 1);
        }
        // Star Power Up
        else if(_other.GetType() != this.GetType() && _other.GetType() == "StarPowerUp")
        {
            AudioManager.Instance.PlayAudio(R.raw.starpickup, 1.f);
            Attributes.Instance.setStarPower(true);
        }
        else if(_other.GetType() != this.GetType() && _other.GetType() == "HealthPowerUp")
        {
            AudioManager.Instance.PlayAudio(R.raw.healthpickup, 1.f);
            Attributes.Instance.setHP(attributes.getHP() + 1);
        }
    }

}
