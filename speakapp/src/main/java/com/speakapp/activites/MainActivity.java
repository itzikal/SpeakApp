package com.speakapp.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.speakapp.Interfaces.OnCardClickListener;
import com.speakapp.adapters.BoardAdapter;
import com.speakapp.models.Board;
import com.speakapp.models.BoardSize;
import com.speakapp.models.Card;
import com.speakapp.models.CardPosition;
import com.speakapp.models.CardSize;
import com.speakapp.speakapp.R;
import com.speakapp.ui.BoardLayout;


public class MainActivity extends Activity
{
    private BoardAdapter mAdapter;
    private View mDecorView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        final TextView textLine = (TextView) findViewById(R.id.text_line);
        final BoardLayout b= (BoardLayout)findViewById(R.id.board_layout);
        b.setOnCardClickListener(new OnCardClickListener()
        {
            @Override
            public void onCardClicked(Card card)
            {
                if (card == null) return;
                textLine.append(card.getName()+ " ");
            }
        });

        mAdapter = new BoardAdapter(this, R.layout.card_layout, fillDemoValues());
        b.setBoardAdapter(mAdapter);

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

               findViewById(R.id.clear_text_line).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                textLine.setText("");
            }
        });
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        if (item.getItemId() == R.id.action_settings)
        {
            startSettingActivity();
        }
        return super.onMenuItemSelected(featureId, item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startSettingActivity()
    {
        startActivity(new Intent(this, EnableSettingActivity.class));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }



    private Board fillDemoValues()
    {
        Board board = new Board(new BoardSize(6,6));
        Card dog = new Card("כלב", R.drawable.dog);
        dog.setSize(new CardSize(3,3));
        dog.setPosition(new CardPosition(0,0));

        Card cat = new Card("חתול", R.drawable.cat);
        cat.setSize(new CardSize(1,1));
        cat.setPosition(new CardPosition(0,4));

        Card mouse = new Card("עכבר", R.drawable.mouse);
        mouse.setSize(new CardSize(2,2));
        mouse.setPosition(new CardPosition(4,4));

        board.addCard(dog);
        board.addCard(cat);
        board.addCard(mouse);

        return board;
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


