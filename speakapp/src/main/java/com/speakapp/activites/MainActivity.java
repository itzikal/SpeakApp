package com.speakapp.activites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.speakapp.adapters.CardsAdapter;
import com.speakapp.models.Card;
import com.speakapp.speakapp.R;

import java.util.ArrayList;


public class MainActivity extends Activity
{
    private static final int GET_NEW_CARD = 1;
    private ArrayList<Card> mActiveBoard;
    private CardsAdapter mAdapter;
    private View mDecorView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        final TextView textLine = (TextView) findViewById(R.id.text_line);
        final GridView gridview = (GridView) findViewById(R.id.board_gird_view);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Card card = (Card) gridview.getAdapter().getItem(i);
                textLine.append(card.getName());
            }
        });
        findViewById(R.id.clear_text_line).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                textLine.setText("");
            }
        });
        //        findViewById(R.id.add_new_card_btn).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view)
        //            {
        //                startActivityForResult(new Intent(MainActivity.this, CreateCardActivity.class), GET_NEW_CARD);
        //            }
        //        });
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }


    private void init()
    {
        mActiveBoard = new ArrayList<Card>();
        fillDemoValues(); // temp
        mAdapter = new CardsAdapter(this, R.layout.card_layout, mActiveBoard);
    }

    private void fillDemoValues()
    {
        mActiveBoard.add(new Card("כלב", R.drawable.dog));
        mActiveBoard.add(new Card("חתול", R.drawable.cat));
        mActiveBoard.add(new Card("איציק", R.drawable.mouse));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        hideSystemUI();

    }

    // This snippet hides the system bars.
    private void hideSystemUI()
    {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI()
    {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            String swipe = "";
            float sensitvity = 50;


            //            if((e1.getX() - e2.getX()) > sensitvity){
            //                swipe += "Swipe Left\n";
            //            }else if((e2.getX() - e1.getX()) > sensitvity){
            //                swipe += "Swipe Right\n";
            //            }else{
            //                swipe += "\n";
            //            }
            // Aviv was here
            Log.d("TAG", "onFling, e1: " + e1.getY() + " , e2: " + e2.getY() + ", bottom: " + mDecorView.getBottom());
            if ((e1.getY() - e2.getY()) > sensitvity && e1.getY() > mDecorView.getBottom() - 200)
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
    }
}


