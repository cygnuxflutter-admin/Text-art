package com.addtext.textonphoto.textart;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

public class PhotoApp extends Application {
    private static PhotoApp sPhotoApp;

    public void onCreate() {
        super.onCreate();
        sPhotoApp = this;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke( null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return sPhotoApp.getContext();
    }
}
