package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// GameView is the SurfaceView

public class GameView extends SurfaceView {
    // Surfaceview has a holder to be used to hold the content.
    private SurfaceHolder holder = null;

    //Thread to be known for its existence
    private UpdateThread updateThread = new UpdateThread(this);

    public static Context context;



    public static void ChangeActivity(Class<?> nameofclass){
        Intent i = new Intent(context, nameofclass);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public GameView(Context _context)
    {
        super(_context);
        context = _context;

        holder = getHolder();

        if (holder != null)
        {
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    //Setup some stuff to indicate whether thread is running and initialized
                    if (!updateThread.IsRunning())
                        updateThread.Initialize();

                    if (!updateThread.isAlive())
                        updateThread.start();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    // Nothing to type here cos it will be handle by the thread
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    // Done then thread should not run too.
                    updateThread.Terminate();
                }
            });
        }

    }
}

