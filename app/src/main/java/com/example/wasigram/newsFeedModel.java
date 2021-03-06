package com.example.wasigram;

public class newsFeedModel {
    String image;
    String userLike;
    String viewImg;
    String dateSnap;
    String titleName, userName, userComments;
    String video;
    String userId;
    String viewAllComments;
    String like;
    String mediaType;
    String Description;
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

    public String getDateSnap() {
        return dateSnap;
    }

    public void setDateSnap(String dateSnap) {
        this.dateSnap = dateSnap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public String getViewAllComments() {
        return viewAllComments;
    }

    public void setViewAllComments(String viewAllComments) {
        this.viewAllComments = viewAllComments;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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
