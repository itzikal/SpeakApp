package com.speakapp.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.speakapp.fragments.AddNewCardFragment;
import com.speakapp.speakapp.R;

import java.util.ArrayList;

/**
 * Created by Itzik on 26/06/2014.
 */
public class SettingsActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ViewPager pager = (ViewPager) findViewById(R.id.settings_view_pager);
        final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new AddNewCardFragment());
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return fragmentList.get(0);

            }

            @Override
            public int getCount()
            {
                return 4;
            }
        });
    }


}

