package com.speakapp.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.speakapp.managers.SoundManager;

/**
 * Created by Itzik on 15/06/2014.
 */
public class RecordButton extends Button
{
    private SoundManager mSoundManager;
    private OnAudioRecordListener mOnAudioRecordListener;

    //region Constructors
    public RecordButton(Context context)
    {
        super(context);
    }

    public RecordButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    //endregion


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if(!getSoundManager().isRecording())
                {
                    getSoundManager().startRecording();
                }
                return true;
            case MotionEvent.ACTION_UP:
                if(getSoundManager().isRecording())
                {
                    getSoundManager().stopRecording();
                }
                return true;
        }
        return false;
    }

    private SoundManager getSoundManager()
    {
        if(mSoundManager == null)
        {
            mSoundManager = new SoundManager(new SoundManager.SoundManagerEventsListener() {
                @Override
                public void onStartRecording()
                {
                    mOnAudioRecordListener.onStartRecording();
                }

                @Override
                public void onStopRecording()
                {
                    mOnAudioRecordListener.onStopRecording();
                }

                @Override
                public void onStartPlaying()
                {
                }

                @Override
                public void onStopPlaying()
                {
                }
            });
        }

        return mSoundManager;
    }

    public void setSoundManagerEventsListener(OnAudioRecordListener onAudioRecordListener)
    {
        mOnAudioRecordListener = onAudioRecordListener;
    }

    public interface OnAudioRecordListener
    {
        void onStartRecording();
        void onStopRecording();
    }
}
