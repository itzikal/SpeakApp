package com.speakapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.speakapp.models.Card;
import com.speakapp.speakapp.R;

import java.util.List;

public class CardsAdapter extends ArrayAdapter<Card>
{
    private final int mResource;

    public CardsAdapter(Context context, int resource, List<Card> objects)
    {
        super(context, resource, objects);
        mResource = resource;
    }

    @Override
    public View getView(int position, View cardView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        Card card = getItem(position);
        ((ImageView) view.findViewById(R.id.cardImage)).setImageResource(card.getImage());
        ((TextView) view.findViewById(R.id.cardText)).setText(card.getName());

        return view;
    }
}
