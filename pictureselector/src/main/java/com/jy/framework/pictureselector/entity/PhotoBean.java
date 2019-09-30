package com.jy.framework.pictureselector.entity;

import java.io.Serializable;

/**
 * Created by JackYang on 2017/5/9.
 * I must face the music and accept responsibility
 */

public class PhotoBean implements Serializable{
    private static final long serialVersionUID = 6909264807525573664L;
    String fileName;
    String imgPath;
    int progress;
    boolean isCompleted;
    String objectPath;


    public static PhotoBean createBean(String imgPath)
    {
        PhotoBean bean=new PhotoBean();
        bean.setImgPath(imgPath);
        return  bean;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
