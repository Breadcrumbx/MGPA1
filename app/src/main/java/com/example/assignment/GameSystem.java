package com.example.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";


    // Game stuff
    private boolean isPaused = false;
    private boolean isDead = false;
    private boolean isMenu = false;

    //Save game stats
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        //Get shared preferences save file
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);


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


    // Save game states func
    public void SaveEditBegin()
    {
        if(editor !=null)
            return;
        //Start the editing
        editor = sharedPref.edit();
    }


    public void SaveEditEnd()
    {
        // Check if has editor
        if(editor == null)
            return;
        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if(editor == null)
            return;
        editor.putInt(_key,_value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key,0);
    }

}
