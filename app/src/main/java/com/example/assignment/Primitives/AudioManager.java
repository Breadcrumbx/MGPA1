package com.example.assignment.Primitives;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;
public class AudioManager {
    public final static AudioManager Instance = new AudioManager(); // Singleton instance

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();


    private AudioManager(){
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        Release(); // clear the audiomap
    }

    public void PlayAudio(int _id, float _volume)
    {
        if(audioMap.containsKey(_id))
        {
            // Have the clip
            MediaPlayer curr = audioMap.get(_id);
            curr.seekTo(0);
            curr.setVolume(_volume,_volume);
            curr.start();

        }
        else
        {
            MediaPlayer curr = MediaPlayer.create(view.getContext(),_id);
            audioMap.put(_id, curr);
            curr.start();
        }
    }

    public void PlayAudio(int _id, float _volume, boolean loop)
    {
        if(audioMap.containsKey(_id))
        {
            // Have the clip
            MediaPlayer curr = audioMap.get(_id);
            curr.setLooping(loop);
            curr.seekTo(0);
            curr.setVolume(_volume,_volume);
            curr.start();
        }
        else
        {
            MediaPlayer curr = MediaPlayer.create(view.getContext(),_id);
            audioMap.put(_id, curr);
            curr.setLooping(loop);
            curr.start();
        }
    }

    public void ResumeAudio(int _id, float _volume)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.setVolume(_volume,_volume);
        curr.start();
    }


    public void StopAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.stop();
    }

    public void PauseAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.pause();
    }

    //Releases the resource
    public void Release()
    {
        for(HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }

        audioMap.clear();
    }

    private MediaPlayer GetAudio(int _id){
        //Check if the audio is loaded or not
        if(audioMap.containsKey(_id))
            return audioMap.get(_id); //Has the clip then return it

        //Load it if not
        MediaPlayer result = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id,result);
        return result;
    }
}
