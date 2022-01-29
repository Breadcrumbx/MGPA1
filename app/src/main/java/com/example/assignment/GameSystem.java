package com.example.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;
    private boolean isDead = false;
    private boolean isMenu = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new PauseMenu());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public void SetIsMenu(boolean _newIsMenu)
    {
        isMenu = _newIsMenu;
    }

    public void SetIsDead(boolean _newIsDead)
    {
        isDead = _newIsDead;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public boolean GetIsDead()
    {
        return isDead;
    }

    public boolean GetIsMenu()
    {
        return isMenu;
    }

}
