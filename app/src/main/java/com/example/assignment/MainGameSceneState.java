package com.example.assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.example.assignment.Entities.Cars;
import com.example.assignment.Entities.InvincibilityPowerUp;
import com.example.assignment.Entities.Player;
import com.example.assignment.Primitives.AudioManager;
import com.example.assignment.Primitives.Entity2D;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create(); // This is the entity
        RenderTextEntity.Create();
        AudioManager.Instance.PlayAudio(R.raw.bgmusic,1.f,true);
        Cars.Create(3);
        PauseButtonEntity.Create();
        Player.Create();

        //Player.Create();
        //NPC.Create();
        //PauseButtonEntity.Create();
        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();

    }

    @Override
    public void Render(Canvas _canvas)
    {

        EntityManager.Instance.Render(_canvas);

        if(!GameSystem.Instance.GetIsPaused())
        {
            String scoreText = String.format("HIGH SCORE : %d", GameSystem.Instance.GetIntFromSave("HighScore"));

            Paint paint = new Paint();
            paint.setColor(Color.CYAN);

            paint.setTextSize(64);
            _canvas.drawText(scoreText, 30 , 330,paint);
        }
    }



    @Override
    public void Update(float _dt) {

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
            //System.out.println("X: " + TouchManager.Instance.GetPosX() + ", Y: " + TouchManager.Instance.GetPosY()); // Debugging purposes
        }

    }


}



