package com.addtext.textonphoto.textart.TART_filters;

import android.graphics.Bitmap;

public class TART_FilterModel {

    Boolean isPremium = false;

    Bitmap bitmap;

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public TART_FilterModel(Boolean isPremium, Bitmap bitmap) {
        this.isPremium = isPremium;
        this.bitmap = bitmap;
    }
}
