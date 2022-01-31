package com.example.assignment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.assignment.Primitives.AudioManager;
import com.example.assignment.Primitives.Vector2;


public class PauseButtonEntity implements EntityBase {
    private Bitmap bmpP = null;
    private Bitmap bmpUP = null;

    private Bitmap ScaledbmpP = null;
    private Bitmap ScaledbmpUP = null;


    //Volume buttons
    private Bitmap volumeUp = null;
    private Bitmap volumeDown = null;


    private Bitmap PauseScreen = null;
    private Bitmap QuitButton = null;


    private float xPos = 0;
    private float yPos = 0;

    private float xPos1 = 0;
    private float yPos1 = 0;



    private boolean isDone = false;
    private boolean isInit = false;
    private boolean Paused = false;
    private boolean Quitted = false;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

    private static float volume = 1.f;
    private Vector2 volumeUpPos =null;
    private Vector2 volumeDownPos = null;
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
        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pause1);
        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.resume);
        //Volume buttons
        volumeDown = ResourceManager.Instance.GetBitmap(R.drawable.voldown);
        volumeUp = ResourceManager.Instance.GetBitmap(R.drawable.volup);




        //Pause background
        PauseScreen = ResourceManager.Instance.GetBitmap(R.drawable.pausebg);

        //Quit button
        QuitButton = ResourceManager.Instance.GetBitmap(R.drawable.quitbutton);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;


        volumeUpPos = new Vector2((float) (ScreenWidth * 0.14),(float) (ScreenHeight*0.28));
        volumeDownPos = new Vector2(0,(float) (ScreenHeight * 0.28));

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP, (int) (ScreenWidth)/12, (int)(ScreenWidth)/7, true);
        ScaledbmpUP = Bitmap.createScaledBitmap(bmpUP, (int) (ScreenWidth)/12, (int)(ScreenWidth)/7, true);

        volumeDown = Bitmap.createScaledBitmap(volumeDown, (int) (ScreenWidth)/12, (int)(ScreenWidth)/7, true);
        volumeUp = Bitmap.createScaledBitmap(volumeUp, (int) (ScreenWidth)/12, (int)(ScreenWidth)/7, true);


        PauseScreen = Bitmap.createScaledBitmap(PauseScreen, (int) (ScreenWidth), (int)(ScreenHeight), true);
        QuitButton = Bitmap.createScaledBitmap(QuitButton, (int) (ScreenWidth)/3, (int)(ScreenHeight)/5, true);

        xPos = ScreenWidth - 150;
        yPos = 150;

        xPos1 = ScreenWidth;
        yPos1 = 150;

        isInit = true;
    }
    @Override
    public void Update(float _dt){
        //System.out.println("Pause: " + Paused);
        buttonDelay += _dt;
        // Pause Button
        if(TouchManager.Instance.HasTouch()) {

            if(TouchManager.Instance.IsDown())
            {
                float imgRadius = ScaledbmpP.getHeight() * 0.5f;
                float volumeRadius = volumeUp.getHeight() * 0.5f;
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.f, xPos, yPos, imgRadius) && buttonDelay >=0.25) {
                    Paused = !Paused;
                    // Pause Music
                    if(Paused)
                    {
                        System.out.println("Paused!");
                        //AudioManager.Instance.PauseAudio(R.raw.bgmusic);
                        AudioManager.Instance.PauseAll();
                    }
                    // Resume music
                    else
                    {
                        System.out.println("Resume!");
                        //AudioManager.Instance.ResumeAudio(R.raw.bgmusic,volume);
                        AudioManager.Instance.ResumeAll();
                    }
                    //Intent intent = new Intent();
                    //intent.setClass(GamePage.Instance, PauseMenu.class);
                    //StateManager.Instance.ChangeState("PauseMenu");
                    //GamePage.Instance.startActivity(intent);
                    //GameView.ChangeActivity(PauseMenu.class);
                    //return;
                    buttonDelay = 0;
                    GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
                //Volume control
                if(Paused)
                {
                    // Volume Down
                    if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.f, volumeDownPos.x + volumeRadius,  volumeDownPos.y + volumeRadius, volumeRadius) && buttonDelay >=0.25)
                    {
                        volume -= .1f;
                        if(volume <0)
                            volume =0.f; //Limit
                        System.out.println("Volume: " + volume);

                    }
                    // Volume Up
                    else if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.f, volumeUpPos.x + volumeRadius,  volumeUpPos.y + volumeRadius, volumeRadius) && buttonDelay >=0.25)
                    {
                        volume += .1f;
                        if (volume > 1.f)
                            volume = 1.f; // Limit
                        System.out.println("Volume: " + volume);
                    }
                    buttonDelay =0;
                }
            }

        }


        // Quit button
        if(TouchManager.Instance.IsDown() && GameSystem.Instance.GetIsPaused()) {

            if (Collision.AABB(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), TouchManager.Instance.GetPosY(), 0, QuitButton.getWidth(), 40, 40 + QuitButton.getHeight())) {
                System.out.println("quit");
                Quitted = true;
                //Intent intent = new Intent();
                //intent.setClass(GamePage.Instance, PauseMenu.class);
                //StateManager.Instance.ChangeState("PauseMenu");
                //GamePage.Instance.startActivity(intent);
                //GameView.ChangeActivity(PauseMenu.class);
                //return;
                if (QuitConfirmDialogFragment.IsShown)
                    return;
                QuitConfirmDialogFragment QuitConfirm = new QuitConfirmDialogFragment();
                QuitConfirm.show(GamePage.Instance.getSupportFragmentManager(), "QuitConfirm");
                buttonDelay = 0;
                //finish();

            }

        }
        else
            {
            Quitted = false;
        }
    }
    @Override
    public void Render(Canvas _canvas) {
        if (!GameSystem.Instance.GetIsPaused())
            _canvas.drawBitmap(ScaledbmpP,xPos - ScaledbmpP.getWidth() * 0.5f, yPos - ScaledbmpP.getHeight() * 0.5f, null);
        else
        {
            _canvas.drawBitmap(PauseScreen,0, 0, null);
            _canvas.drawBitmap(QuitButton,0, 40, null);
            _canvas.drawBitmap(ScaledbmpUP,xPos - ScaledbmpUP.getWidth() * 0.5f, yPos - ScaledbmpUP.getHeight() * 0.5f, null);

            _canvas.drawBitmap(volumeUp,(int) (ScreenWidth * 0.14),(int) (ScreenHeight*0.28),null);
            _canvas.drawBitmap(volumeDown,0,(int) (ScreenHeight*0.28),null);
        }

    }
    @Override
    public boolean IsInit(){
        return isInit;

    }
    @Override
    public int GetRenderLayer(){
        return LayerConstants.PAUSEBUTTONENTITY;

    }
    @Override
    public void SetRenderLayer(int _newLayer){
        return;

    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_PAUSE;
    }

    public static PauseButtonEntity Create()
    {
        PauseButtonEntity result = new PauseButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }

    public static float getVolume()
    {
        return volume;
    }
}
