package com.speakapp.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        final EditText enableSettingTextView = (EditText)findViewById(R.id.enable_setting_text_view);
        findViewById(R.id.cancel_setting_mode).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        findViewById(R.id.go_to_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(enableSettingTextView.getText().toString().toLowerCase().equals("settings"))
                {
                    startActivity(new Intent(EnableSettingActivity.this, SettingsActivity.class));
                    finish();
                }
            }
        });

    }
}
