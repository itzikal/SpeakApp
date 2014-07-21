package com.speakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.speakapp.Interfaces.OnCardClickListener;
import com.speakapp.models.Board;
import com.speakapp.models.BoardSize;
import com.speakapp.models.Card;
import com.speakapp.speakapp.R;

public class BoardAdapter extends ArrayAdapter<Card>
{
    private final int mResource;
    private Board mBoard;
    private OnCardClickListener mOnCardClickListener;
    private int mCellWidth;
    private int mCellHight;

    public BoardAdapter(Context context, int resource, Board board)
    {
        super(context, resource, board.getCards());
        mResource = resource;
        mBoard = board;
    }

    public BoardSize getBoardSize()
    {
        return mBoard.getSize();
    }

    @Override
    public View getView(final int position, View cardView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        final Card card = getItem(position);

        GridLayout.Spec row = GridLayout.spec(card.getPosition().x, card.getSize().getWidth());
        GridLayout.Spec col = GridLayout.spec(card.getPosition().y, card.getSize().getHeight());
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(row, col);
        params.height = card.getSize().getHeight() * mCellHight;
        params.width = card.getSize().getWidth() * mCellWidth;
        view.setLayoutParams(params);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mOnCardClickListener.onCardClicked(card);
            }
        });
        ImageView image = (ImageView) view.findViewById(R.id.cardImage);
        image.setImageResource(card.getImage());
        //        image.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view)
        //            {
        //                mOnCardClickListener.onCardClicked(card);
        //            }
        //        });
        ((TextView) view.findViewById(R.id.cardText)).setText(card.getName());


        return view;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener)
    {
        mOnCardClickListener = onCardClickListener;
    }

    public void setCellSize(int cellWidth, int cellHight)
    {
        mCellWidth = cellWidth;
        mCellHight = cellHight;
    }

}
