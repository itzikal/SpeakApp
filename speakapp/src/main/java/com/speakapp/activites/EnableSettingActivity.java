package com.speakapp.activites;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.speakapp.speakapp.R;

/**
 * registration screen to go to settings mode.
 */
public class EnableSettingActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_settings);
        findViewById(R.id.cancel_setting_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
