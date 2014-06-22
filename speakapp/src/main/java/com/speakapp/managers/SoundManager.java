package com.speakapp.managers;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Itzik on 08/06/14.
 */
public class SoundManager
{
    private static final String TAG = SoundManager.class.getSimpleName();

    private String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
    private static SoundManager msInstance = null;
//    private Context mContext = null;
    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;
    private SoundManagerEventsListener mSoundManagerEventsListener;
    private boolean mIsRecording = false;

    public SoundManager(SoundManagerEventsListener soundManagerEventsListener)
    {
        mSoundManagerEventsListener = soundManagerEventsListener;
    }

    public void playFormResource(Context context, int resource, MediaPlayer.OnCompletionListener onPlayCompletedListener)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, resource);
        mediaPlayer.setOnCompletionListener(onPlayCompletedListener);
        mediaPlayer.start();
    }

    public void startPlaying()
    {
        stopPreviousUsageOfSoundManager();
        mPlayer = new MediaPlayer();
        try
        {
            //todo: File existens checking!!
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            mSoundManagerEventsListener.onStartPlaying();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    stopPlaying();
                }
            });
        }
        catch (IOException e)
        {
            Log.e(TAG, "prepare() failed");
            stopPlaying();
        }
    }

    public void stopPlaying()
    {
        if(mPlayer !=null)
        {
            try
            {
                mPlayer.release();
            }
            finally
            {
                mPlayer = null;
                mSoundManagerEventsListener.onStopPlaying();
            }
        }
    }

    public void startRecording()
    {
        try
        {
            stopPreviousUsageOfSoundManager();

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);//todo: File existens checking!!
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
            mRecorder.start();
            mIsRecording = true;
            mSoundManagerEventsListener.onStartRecording();
        }
        catch (IOException e)
        {
            Log.e(TAG, "prepare() failed");
            stopRecording();
            return;
        }
    }

    private void stopPreviousUsageOfSoundManager()
    {
        stopPlaying();
        stopRecording();
    }

    public void stopRecording()
    {
        if (mRecorder != null)
        {
            try
            {
                if(mIsRecording)
                {
                    mRecorder.stop();
                }
            }
            catch (Exception e)
            {
                Log.d(TAG, "stopRecording " + e.getMessage());
            }
            finally
            {
                mIsRecording = false;
                mRecorder.release();
                mRecorder = null;
                mSoundManagerEventsListener.onStopRecording();
            }
        }
    }


    //Todo: Make sure it called.
    public void dispose()
    {
        if (mRecorder != null)
        {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null)
        {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public boolean isRecording()
    {
        return mIsRecording;
    }

    public boolean isPlaying()
    {
        return mPlayer != null;
    }

    public interface SoundManagerEventsListener
    {
        void onStartRecording();
        void onStopRecording();
        void onStartPlaying();
        void onStopPlaying();
    }
}