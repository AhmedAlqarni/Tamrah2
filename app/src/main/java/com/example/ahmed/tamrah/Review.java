package com.example.ahmed.tamrah;

/**
 * Created by Warsh on 3/16/2018.
 */

public class Review {
    private String mRID, mAuthorName, mAuthorUID, mTitle, mContent;
    private double mRate;

    public String getRID() {
        return mRID;
    }

    public void setRID(String RID) {
        this.mRID = RID;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String AuthorName) {
        this.mAuthorName = AuthorName;
    }

    public String getAuthorUID() {
        return mAuthorUID;
    }

    public void setAuthorUID(String AuthorUID) {
        this.mAuthorUID = AuthorUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String Content) {
        this.mContent = Content;
    }

    public double getRate() {
        return mRate;
    }

    public void setRate(double Rate) {
        this.mRate = Rate;
    }
}
