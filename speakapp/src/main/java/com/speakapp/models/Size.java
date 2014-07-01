package com.speakapp.models;

public class Size {

    private int mWidth;
    private int mHeight;

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public Size(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }
}

