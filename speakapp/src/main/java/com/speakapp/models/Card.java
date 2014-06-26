package com.speakapp.models;

/**
 * one card model.
 */
public class Card
{
    int mImage;
    String mName;
    String mDescription;

    //region properties
    public int getImage()
    {
        return mImage;
    }

    public void setImage(int image)
    {
        this.mImage = image;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        this.mName = name;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        this.mDescription = description;
    }
    //endregion

    public Card(String mName, int mImage)
    {
        this.mName = mName;
        this.mImage = mImage;
    }
}
