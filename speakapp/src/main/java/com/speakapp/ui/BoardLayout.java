package com.speakapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import com.speakapp.Interfaces.OnCardClickListener;
import com.speakapp.adapters.BoardAdapter;
import com.speakapp.models.Card;

public class BoardLayout extends GridLayout
{
    private BoardAdapter mBoardAdapter;
    private int mCellHeight;
    private int mCellWidth;
    private OnCardClickListener mOnCardClickListener;


    //region Ctor
    public BoardLayout(Context context)
    {
        super(context);
    }

    public BoardLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BoardLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    //endregion

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec)
    {
        super.onMeasure(widthSpec, heightSpec);
        setRowCount(mBoardAdapter.getBoardSize().getHeight());
        setColumnCount(mBoardAdapter.getBoardSize().getWidth());
        mCellWidth = resolveSize(0, widthSpec) / mBoardAdapter.getBoardSize().getWidth();;
        mCellHeight = resolveSize(0, heightSpec) / mBoardAdapter.getBoardSize().getHeight();
        addEmptyViews();

        addCardsToBoard();
    }

    private void addCardsToBoard()
    {
        mBoardAdapter.setCellSize(mCellWidth, mCellHeight);
        mBoardAdapter.setOnCardClickListener(new OnCardClickListener() {
            @Override
            public void onCardClicked(Card card)
            {
                mOnCardClickListener.onCardClicked(card);
            }
        });
        for(int i = 0 ; i < mBoardAdapter.getCount(); i++)
        {
            addView(mBoardAdapter.getView(i, null, null));
        }
    }

    private void addEmptyViews()
    {
        for(int i=0; i < mBoardAdapter.getBoardSize().getHeight(); i++)
        {
            for(int j=0; j < mBoardAdapter.getBoardSize().getWidth(); j++)
            {
                Spec row = GridLayout.spec(i);
                Spec col = GridLayout.spec(j);

                LayoutParams params = new LayoutParams(row, col);
                params.width = mCellWidth;
                params.height = mCellHeight;
                View v = new View(getContext());
                v.setLayoutParams(params);

                addView(v);
            }
        }
    }

    public void setBoardAdapter(BoardAdapter boardAdapter)
    {
        mBoardAdapter = boardAdapter;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener)
    {
        mOnCardClickListener = onCardClickListener;
    }
}
