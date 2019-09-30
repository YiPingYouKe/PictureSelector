package com.jy.framework.pictureselector.basic;

import com.jy.framework.pictureselector.activity.PhotoPickerActivity;

/**
 * Created by JackYang on 2017/7/20.
 * I must face the music and accept responsibility
 */

public enum  PathModel {
    BEAN(PhotoPickerActivity.PATH_BEAN),
    STRING(PhotoPickerActivity.PATH_STRING);

    private int model;

    PathModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return String.valueOf(this.model);
    }
}
