package com.speakapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.speakapp.managers.SoundManager;
import com.speakapp.speakapp.R;
import com.speakapp.ui.RecordButton;

/**
 * Created by Itzik on 30/06/2014.
 */
public class AddNewCardFragment extends Fragment
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;
    private SoundManager mSoundManager;
    private TextView mRecordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_create_new_card, container, false);


        mImageView = (ImageView)view.findViewById(R.id.card_image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dispatchTakePictureIntent();
            }
        });

        mRecordText = (TextView) view.findViewById(R.id.recording_text);

        final RecordButton record = (RecordButton)view.findViewById(R.id.record_btn);
        record.setSoundManagerEventsListener(new RecordButton.OnAudioRecordListener()
        {
            @Override
            public void onStartRecording()
            {
                mRecordText.setText("Recording...");
            }

            @Override
            public void onStopRecording()
            {
                mRecordText.setText("Recording ended");
            }
        });

        final Button play = (Button) view.findViewById(R.id.play_btn);
        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (getSoundManager().isPlaying())
                {
                    getSoundManager().stopPlaying();
                }
                else
                {
                    getSoundManager().startPlaying();
                }
            }
        });

        return view;
    }

    private SoundManager getSoundManager()
    {
        if(mSoundManager == null)
        {
            mSoundManager = new SoundManager(new SoundManager.SoundManagerEventsListener() {
                @Override
                public void onStartRecording()
                {
                    mRecordText.setText("Recording...");
                }

                @Override
                public void onStopRecording()
                {
                    mRecordText.setText("Recording ended");
                }

                @Override
                public void onStartPlaying()
                {
                    mRecordText.setText("Playing sound started");
                }

                @Override
                public void onStopPlaying()
                {
                    mRecordText.setText("Playing sound ended");
                }
            });
        }

        return mSoundManager;
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
