package com.example.assignment.Primitives;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import com.example.assignment.PauseButtonEntity;
import com.example.assignment.R;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    public final static AudioManager Instance = new AudioManager(); // Singleton instance

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    private HashMap<Integer, MediaPlayer> wasPlaying = new HashMap<>();
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
            curr.setVolume(_volume,_volume);
            curr.start();
        }
    }
    //Overloaded PlayAudio function that takes in a loop boolean for enabling looping
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

    //Resume all audio that was previously playing
    public void ResumeAll()
    {
        for(HashMap.Entry<Integer, MediaPlayer> entry : wasPlaying.entrySet())
        {
            // Only the background music volume can be edited
            if(entry.getKey() == R.raw.bgmusic)
            {
                entry.getValue().setVolume(PauseButtonEntity.getVolume(),PauseButtonEntity.getVolume());
                entry.getValue().start();
            }
            else
            {
                entry.getValue().start();
            }
        }
        wasPlaying.clear(); // After for loops finishes it clears the wasPlaying hashmap
    }

    //Resumes specific audio
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

    //Pauses specific audio
    public void PauseAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.pause();
    }

    //Pause all audio that is playing
    public void PauseAll()
    {
        for(HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            // Checks only those that are currently playing
            if(entry.getValue().isPlaying())
            {
                entry.getValue().pause(); // Pauses only those that are currently playing
                wasPlaying.put(entry.getKey(), entry.getValue()); // And store it in wasPlaying so when it resumes it can continue where it left off
            }
        }
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

    public MediaPlayer GetAudio(int _id){
        //Check if the audio is loaded or not
        if(audioMap.containsKey(_id))
            return audioMap.get(_id); //Has the clip then return it

        //Load it if not
        MediaPlayer result = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id,result);
        return result;
    }
}
