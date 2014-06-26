package com.speakapp.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.speakapp.managers.SoundManager;
import com.speakapp.speakapp.R;
import com.speakapp.ui.RecordButton;

/**
 * Created by Itzik on 24/06/2014.
 */
public class CreateCardActivity extends Activity
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;
    private SoundManager mSoundManager;
    private TextView mRecordText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        mImageView = (ImageView)findViewById(R.id.card_image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dispatchTakePictureIntent();
            }
        });

        mRecordText = (TextView) findViewById(R.id.recording_text);

        final RecordButton record = (RecordButton) findViewById(R.id.record_btn);
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

        final Button play = (Button) findViewById(R.id.play_btn);
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
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

}
