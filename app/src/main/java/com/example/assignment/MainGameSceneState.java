package com.example.assignment;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.assignment.Entities.Cars;
import com.example.assignment.Entities.InvincibilityPowerUp;
import com.example.assignment.Entities.Player;

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

        Cars.Create(5);
        PauseButtonEntity.Create();
        Player.Create();
        InvincibilityPowerUp.Create();
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

        EntityManager.Instance.Update(_dt);

        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
            //System.out.println("X: " + TouchManager.Instance.GetPosX() + ", Y: " + TouchManager.Instance.GetPosY()); // Debugging purposes
        }

    }


}



