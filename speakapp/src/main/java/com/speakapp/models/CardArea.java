package com.speakapp.models;

import android.graphics.Rect;

public class CardArea {
    public static Rect getCardArea(Card card) {
        CardPosition pos = card.getPosition();
        CardSize size = card.getSize();
        return new Rect(pos.x,
                pos.y,
                pos.x + size.getWidth(),
                pos.y + size.getHeight());
    }
}
