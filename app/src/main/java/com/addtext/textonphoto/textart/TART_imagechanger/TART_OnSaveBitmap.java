package com.addtext.textonphoto.textart.TART_imagechanger;

import android.graphics.Bitmap;


public interface TART_OnSaveBitmap {
    void onBitmapReady(Bitmap bitmap);

    void onFailure(Exception exc);
}
