package com.example.booklistingretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    private String mThumbnail;
    private String mTitle;
    private String mCategory;
    private String mRating;
    private String mAuthors;
    private String mPrice;
    private String mDescription ;
    private String mBuyLink;
    private String mPreviewLink ;


    public Book(String mThumbnail, String mTitle, String mCategory, String mRating, String mAuthors , String mPrice , String mDescription, String mBuyLink ,String mPreviewLink  ) {
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mRating = mRating;
        this.mAuthors = mAuthors;
        this.mPrice = mPrice ;
        this.mDescription = mDescription ;
        this.mBuyLink = mBuyLink;
        this.mPreviewLink = mPreviewLink;

    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(String mAuthors) {
        this.mAuthors = mAuthors;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmBuyLink() {
        return mBuyLink;
    }

    public void setmBuyLink(String mBuyLink) {
        this.mBuyLink = mBuyLink;
    }

    public String getmPreviewLink() {
        return mPreviewLink;
    }

    public void setmPreviewLink(String mPreviewLink) {
        this.mPreviewLink = mPreviewLink;
    }
}
