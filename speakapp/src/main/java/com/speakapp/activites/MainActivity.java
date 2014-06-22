package com.speakapp.activites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.speakapp.adapters.BoardAdapter;
import com.speakapp.managers.SoundManager;
import com.speakapp.speakapp.R;
import com.speakapp.ui.RecordButton;

import java.util.ArrayList;


public class MainActivity extends Activity
{
    private ArrayList<String> m_activeBoard;
    private BoardAdapter m_boardAdapter;
    private SoundManager mSoundManager;

    private TextView mRecordText;
    private View mDecorView;
    private GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mDecorView = getWindow().getDecorView();

        mDecorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
        {
            public void onSystemUiVisibilityChange(int visibility)
            {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                hideSystemUI();
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                {
                    // TODO: The system bars are visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                    hideSystemUI();
                }
                else
                {
                    // TODO: The system bars are NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                }
            }
        });
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
//        GridView gridview = (GridView) findViewById(R.id.gridView);
//        gridview.setAdapter(m_boardAdapter);

        mRecordText = (TextView)findViewById(R.id.recording_text);

        final RecordButton record = (RecordButton)findViewById(R.id.record_btn);
        record.setSoundManagerEventsListener(new RecordButton.OnAudioRecordListener() {
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

        final Button play = (Button)findViewById(R.id.play_btn);
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
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

    private void init() {
        m_activeBoard = new ArrayList<String>();
        fillDemoValues(); // temp
        m_boardAdapter = new BoardAdapter(this, m_activeBoard);
    }

    private void fillDemoValues() {
        for(int i = 0; i < 20; i++) {
            m_activeBoard.add("card " + i);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        hideSystemUI();

    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            String swipe = "";
            float sensitvity = 50;


//            if((e1.getX() - e2.getX()) > sensitvity){
//                swipe += "Swipe Left\n";
//            }else if((e2.getX() - e1.getX()) > sensitvity){
//                swipe += "Swipe Right\n";
//            }else{
//                swipe += "\n";
//            }
            Log.d("TAG", "onFling, e1: " + e1.getY() + " , e2: " +e2.getY()+ ", bottom: "+mDecorView.getBottom());
            if((e1.getY() - e2.getY()) > sensitvity && e1.getY() > mDecorView.getBottom() - 200)
            {
                swipe += "Swipe Up\n";
                onSwipeBottomUp();
            }
//            }else if((e2.getY() - e1.getY()) > sensitvity){
//                swipe += "Swipe Down\n";
//            }else{
//                swipe += "\n";
//            }TEST



            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void onSwipeBottomUp()
    {
        showSystemUI();
        mRecordText.setText("Swipe successful");
    }
}


