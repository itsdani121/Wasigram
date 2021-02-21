package com.example.wasigram;

public class newsFeedModel {
    String image;
    String[] viewImg;
    String titleName;
    String[] video;
    String like;
    public newsFeedModel(String image, String[] viewImg, String titleName, String[] video, String like) {
        this.image = image;
        this.viewImg = viewImg;
        this.titleName = titleName;
        this.video = video;
        this.like = like;
    }
    public newsFeedModel() {
    }

    public String[] getViewImg() {
        return viewImg;
    }

    public void setViewImg(String[] viewImg) {
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


    public String[] getVideo() {
        return video;
    }

    public void setVideo(String[] video) {
        this.video = video;
    }
}
