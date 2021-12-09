package com.example.assignment;

import android.app.Activity;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.view.SurfaceView;

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
        Player.Create();
        for (int i = 0; i<5;i++)
        {
            Cars.Create();
        }


        PauseButtonEntity.Create();
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

    }



    @Override
    public void Update(float _dt) {

        if(GameSystem.Instance.GetIsPaused())
            return;

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
            System.out.println("X: " + TouchManager.Instance.GetPosX() + ", Y: " + TouchManager.Instance.GetPosY()); // Debugging purposes
        }

        if (GameSystem.Instance.GetIsPaused()) return;
    }
}



