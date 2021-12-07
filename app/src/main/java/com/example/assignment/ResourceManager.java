package com.example.assignment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import java.util.HashMap;

public class ResourceManager {
    public final static ResourceManager Instance = new ResourceManager();

    private Resources res = null; // object name to resource which is called res.

    private HashMap<Integer, Bitmap> resMap = new HashMap<Integer, Bitmap>();

    private ResourceManager() {}

    public void Init (SurfaceView _view)
    {
        res = _view.getResources(); // _view is the surfaceview and where we get the images
    }

    public Bitmap GetBitmap(int _id){
        if(resMap.containsKey(_id)) // Use by key
            return resMap.get(_id); //Does this just skip the decoderresources part?
        //This allows the images to be loaded.
        // ==== Bitmap bmp
        // Every image used there is always an id tied to it.
        // if image is null program will crash
        // Image size to big will also clash the progam.
        //Check for null

        Bitmap results = BitmapFactory.decodeResource(res, _id);
        resMap.put(_id, results);
        return results;
    }

}
