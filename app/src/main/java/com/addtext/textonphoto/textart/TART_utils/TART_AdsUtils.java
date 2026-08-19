package com.addtext.textonphoto.textart.TART_utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class TART_AdsUtils {
    public static boolean isNetworkAvailabel(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
