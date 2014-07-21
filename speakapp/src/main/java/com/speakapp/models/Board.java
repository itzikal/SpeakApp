package com.speakapp.models;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by itzik on 01-Jul-14.
 */
public class Board
{
    private BoardSize mSize;
    private ArrayList<Card> mCards;

    public ArrayList<Card> getCards()
    {
        return mCards;
    }

    public Board(BoardSize size)
    {
        mSize = size;
        mCards = new ArrayList<Card>();
    }

    public void addCard(Card card)
    {
        if (validateCardPosition(card))
        {
            mCards.add(card);
        }
    }

    public void removeCard(Card card)
    {
        mCards.remove(card);
    }

    private boolean validateCardPosition(Card card)
    {
        if (!isInsideOfBorder(card))
        {
            return false;
        }
        for (Card existingCard : mCards)
        {
            if (CardsOverlapVerifier.doesCardsOverlap(card, existingCard))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isInsideOfBorder(Card card)
    {
        Rect cardArea = CardArea.getCardArea(card);
        Rect boardArea = new Rect(0, 0, mSize.getWidth(), mSize.getHeight());
        return boardArea.contains(cardArea);
    }

    public BoardSize getSize()
    {
        return mSize;
    }
}
