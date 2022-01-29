package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.ref.WeakReference;

// Created by TanSiewLan2021
// GameView is the SurfaceView

public class GameView extends SurfaceView {
    // Surfaceview has a holder to be used to hold the content.
    private SurfaceHolder holder = null;

    //Thread to be known for its existence
    private UpdateThread updateThread = new UpdateThread(this);

    public static WeakReference<Context> context;



    public static void ChangeActivity(Context curr, Class<?> target) {
        Intent myIntent = new Intent(curr, target);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        curr.startActivity(myIntent);
    }

    public GameView(Context _context)
    {
        super(_context);
        context = new WeakReference<>(_context);

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

