package com.example.assignment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Player implements EntityBase {
    private boolean isDone = false;
    private Bitmap kid = null;
    //private Bitmap kid2 = null;
    private SurfaceView view = null;
    private int width,height;
    private float xPos, yPos;
    private int ScreenWidth,ScreenHeight;
    private boolean touchDown = false;


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
        if (TouchManager.Instance.IsDown() && !touchDown) {
            //Example of touch on screen in the main game to trigger back to Main menu
            touchDown = true;

        }
        else if (!TouchManager.Instance.IsDown() && touchDown)
        {
            touchDown = false;
            yPos -= 600;

        }

    }
    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(kid,xPos,yPos,null);//1st image

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


    public static void setWidth()
    {

    }

    public static Player Create(){
        Player result = new Player();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }



}
