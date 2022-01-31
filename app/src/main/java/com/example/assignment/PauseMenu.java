package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PauseMenu extends Activity implements View.OnClickListener, StateBase {
    //public static Activity Instance;
    private Button resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Instance = this;
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        resume = (Button)findViewById(R.id.btn_resume);
        resume.setOnClickListener(this);
        StateManager.Instance.AddState(new PauseMenu());
        setContentView(R.layout.activity_pause_menu);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        if (v == resume)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page

        }

        startActivity(intent);

    }

    @Override
    public String GetName() {
        return "PauseMenu";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public void Update(float _dt) {

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}