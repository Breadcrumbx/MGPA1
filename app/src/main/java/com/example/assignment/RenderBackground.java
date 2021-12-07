package com.example.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap Scaledbmp = null; // Scale the bmp based on the screen width & height
    private SurfaceView view = null;
    private float xPos, yPos;
    private int ScreenWidth,ScreenHeight;

    //private Bitmap kid = null;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);
        //Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        Scaledbmp = Bitmap.createScaledBitmap(bmp,(int) (ScreenWidth),(int) (ScreenHeight),true);

        //kid =BitmapFactory.decodeResource(_view.getResources(),R.drawable.schoolkid);
    }
    @Override
    public void Update(float _dt){
        if(GameSystem.Instance.GetIsPaused())
            return;

//        xPos -= _dt * 500; // How fast you want to move the screen
//
//        if(xPos < -ScreenWidth)
//        {
//            xPos = 0;
//        }

    }
    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(Scaledbmp,xPos,(int)(yPos),null);//1st image
        //_canvas.drawBitmap(Scaledbmp,xPos + ScreenWidth, (int) (yPos),null);//2nd image

        Matrix transform = new Matrix();
        //transform.postTranslate(200,200);
        //transform.postScale(10,10);
        transform.postRotate((float)Math.toDegrees(30));
       // _canvas.drawBitmap(kid,transform,null);
        //_canvas.drawBitmap(kid,500,500,null);

    }
    @Override
    public boolean IsInit(){
        return bmp != null;

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
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
