package com.example.wasigram;

public class newsFeedModel {
    String image;
    String viewImg;
    String titleName;
    String video;
    String like;
    String Descrption;

    public newsFeedModel(String image, String titleName, String video, String like, String descrption) {
        this.image = image;
        this.titleName = titleName;
        this.video = video;
        this.like = like;
        Descrption = descrption;
    }

    public newsFeedModel() {
    }

    public String getDescription() {
        return Descrption;
    }

    public void setDescrption(String descrption) {
        Descrption = descrption;
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
