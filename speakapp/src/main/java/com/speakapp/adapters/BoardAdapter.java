package com.speakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.speakapp.speakapp.R;

import java.util.ArrayList;
import java.util.Random;

public class BoardAdapter extends ArrayAdapter<String>
{
    private Context m_context;
    private ArrayList<String> m_cards;
    private LayoutInflater m_layoutInflater;
    public BoardAdapter(Context context, ArrayList<String> cards) {
        super(context, R.layout.activity_main);
        m_context = context;
        m_cards = cards;
        m_layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return m_cards.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return m_cards.get(position);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }

    @Override
    public View getView(int position, View cardView, ViewGroup parent) {
        if(cardView == null) cardView = m_layoutInflater.inflate(R.layout.card_layout, null);
       // ImageView cardImage = (ImageView) cardView.findViewById(R.id.cardImage);
       // cardImage.setImageResource(R.drawable.ic_luncher);

        TextView cardText = (TextView) cardView.findViewById(R.id.cardText);
        cardText.setText(m_cards.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SoundManager.getInstance().playFormResource(getContext(), R.raw.dog);
                switchBoard(randomCards());
            }
        });
        return cardView;
    }

    public void switchBoard(ArrayList<String> newCards){
        m_cards = newCards;
        notifyDataSetChanged();
    }

    private ArrayList<String> randomCards() {
        ArrayList<String> randomCards = new ArrayList<String>();
        Random a = new Random();
        for(int i = 0; i < a.nextInt(50)+1; i++) {
            randomCards.add("card " + i);
        }
        return randomCards;
    }
}
