package com.example.wasigram;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class newsFeedModel {
    String image;
    String viewImg;
    String titleName;
    String video;
    String viewMore;
    String discriptionLength;

    public String getViewMore() {
        return viewMore;
    }

    public void setViewMore(String viewMore) {
        this.viewMore = viewMore;
    }

    String like;
    String mediaType;
    String Description; 
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public newsFeedModel(String image, String viewImg, String titleName, String video, String like, String mediaType, String description) {
        this.image = image;
        this.viewImg = viewImg;
        this.titleName = titleName;
        this.video = video;
        this.like = like;
        this.mediaType = mediaType;
        Description = description;
    }

    public newsFeedModel() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getViewImg() {
        return viewImg;
    }

    public void setViewImg(String viewImg) {
        this.viewImg = viewImg;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
