package com.example.assignment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Player implements EntityBase,Collidable {
    private boolean isDone = false;
    private Bitmap kid = null;
    //private Bitmap kid2 = null;
    private SurfaceView view = null;
    private static int width,height;
    private float xPos, yPos;
    private int ScreenWidth,ScreenHeight;
    private boolean touchDown = false;

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
        kid = BitmapFactory.decodeResource(_view.getResources(),R.drawable.kid1);
        //kid2 = BitmapFactory.decodeResource(_view.getResources(),R.drawable.kid2);
        //PlayerSprite = new Sprite(kid,4,4,16);
        //Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        width = kid.getWidth();
        height = kid.getHeight();

        xPos = (float) ScreenWidth * 0.45f;
        yPos = (float) ScreenHeight * 0.85f;
        kid = Bitmap.createScaledBitmap(kid,(int) (width * 0.35),(int) (height * 0.35),false);
        System.out.println(yPos);
        width = kid.getWidth();
        height = kid.getHeight();
    }

    @Override
    public void Update(float _dt){
        //PlayerSprite.Update(_dt);
        if (TouchManager.Instance.IsDown() && !touchDown) {
            //Example of touch on screen in the main game to trigger back to Main menu
            touchDown = true;

        }
        else if (!TouchManager.Instance.IsDown() && touchDown)
        {
            touchDown = false;
            yPos -= 600 * _dt;
        }

    }
    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(kid,xPos,yPos,null);//1st image
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


    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static Player Create(){
        Player result = new Player();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    @Override
    public String GetType()
    {
        return "Player";
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
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType() && _other.GetType() != "Player"){
            SetIsDone(true); // Destroy the item / isDone true means it disappears
        }
    }

}
