package com.speakapp.models;

import android.graphics.Rect;

/**
 * Created by itzik on 01-Jul-14.
 */
public class CardsOverlapVerifier {

    public static boolean doesCardsOverlap(Card first, Card second) {
        return CardArea.getCardArea(first).intersect(CardArea.getCardArea(second));
    }

}
