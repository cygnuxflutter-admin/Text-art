package com.addtext.textonphoto.textart;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {

    public static final String KEY_IMAGE = "unsplash_image";
    public static String PREFS_FILE_NAME = "text_on";
    public static final String feedback_mail = "chirag9983@gmail.com";
    public static final String privacy_policy = "https://cygnux.in/application-privacy-policy/text_art_text_on_photo-editor_privacy_policy.html";

    public static String url = "";
    public static void log(String str) {
    }

    public static void setColorFilter(Drawable drawable, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            drawable.setColorFilter(new BlendModeColorFilter(i, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static Uri getOutputMediaFile(Context context) {
        if (Build.VERSION.SDK_INT <= 28) {
            File file = new File(Environment.getExternalStorageDirectory() + "/TextOnPhoto");
            if (file.exists() || file.mkdirs()) {
                File file2 = new File(file.getPath() + File.separator + "MI_" + new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date()) + ".jpg");
                try {
                    file2.createNewFile();
                    return Uri.fromFile(file2);
                } catch (Exception unused) {
                    return null;
                }
            }
            return null;
        }
        Uri contentUri = MediaStore.Images.Media.getContentUri("external_primary");
        File file3 = new File(Environment.DIRECTORY_PICTURES, "TextOnPhoto");
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()));
        contentValues.put("mime_type", "image/jpeg");
        contentValues.put("date_added", Long.valueOf(currentTimeMillis));
        contentValues.put("date_modified", Long.valueOf(currentTimeMillis));
        contentValues.put("relative_path", file3 + "/");
        contentValues.put("is_pending", (Integer) 1);
        return context.getContentResolver().insert(contentUri, contentValues);
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
            boolean isConnected = networkInfo2 != null ? networkInfo2.isConnected() : false;
            return (isConnected || (networkInfo = connectivityManager.getNetworkInfo(0)) == null) ? isConnected : networkInfo.isConnected();
        }
        return false;
    }
}
