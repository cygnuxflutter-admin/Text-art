package com.addtext.textonphoto.textart.TART_utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_screens.TART_ColorPickerActivity;
import com.addtext.textonphoto.textart.TART_screens.TART_MainActivity;
import com.addtext.textonphoto.textart.TART_screens.TART_SampleActivity;
import com.addtext.textonphoto.textart.TART_screens.TART_SettingsActivity;

public class TART_BottomNavHelper {

    public static void setupBottomNav(Activity activity, int currentTabId) {
        View navHome = activity.findViewById(R.id.navHome);
        View navTemplates = activity.findViewById(R.id.navTemplates);
        View navCreate = activity.findViewById(R.id.navCreate);
        View navGallery = activity.findViewById(R.id.navGallery);
        View navProfile = activity.findViewById(R.id.navProfile);
        
        if (navHome == null) return; // Layout not included

        // Reset all colors
        int colorNormal = ContextCompat.getColor(activity, R.color.text_secondary);
        int colorActive = ContextCompat.getColor(activity, R.color.brand_orange);

        ((ImageView) activity.findViewById(R.id.navHomeIcon)).setColorFilter(colorNormal);
        ((TextView) activity.findViewById(R.id.navHomeText)).setTextColor(colorNormal);
        
        ((ImageView) activity.findViewById(R.id.navTemplatesIcon)).setColorFilter(colorNormal);
        ((TextView) activity.findViewById(R.id.navTemplatesText)).setTextColor(colorNormal);
        
        ((ImageView) activity.findViewById(R.id.navGalleryIcon)).setColorFilter(colorNormal);
        ((TextView) activity.findViewById(R.id.navGalleryText)).setTextColor(colorNormal);
        
        ((ImageView) activity.findViewById(R.id.navProfileIcon)).setColorFilter(colorNormal);
        ((TextView) activity.findViewById(R.id.navProfileText)).setTextColor(colorNormal);

        // Set active tab color
        if (currentTabId == R.id.navHome) {
            ((ImageView) activity.findViewById(R.id.navHomeIcon)).setColorFilter(colorActive);
            ((TextView) activity.findViewById(R.id.navHomeText)).setTextColor(colorActive);
        } else if (currentTabId == R.id.navTemplates) {
            ((ImageView) activity.findViewById(R.id.navTemplatesIcon)).setColorFilter(colorActive);
            ((TextView) activity.findViewById(R.id.navTemplatesText)).setTextColor(colorActive);
        } else if (currentTabId == R.id.navGallery) {
            ((ImageView) activity.findViewById(R.id.navGalleryIcon)).setColorFilter(colorActive);
            ((TextView) activity.findViewById(R.id.navGalleryText)).setTextColor(colorActive);
        } else if (currentTabId == R.id.navProfile) {
            ((ImageView) activity.findViewById(R.id.navProfileIcon)).setColorFilter(colorActive);
            ((TextView) activity.findViewById(R.id.navProfileText)).setTextColor(colorActive);
        }

        // Setup click listeners
        navHome.setOnClickListener(v -> {
            if (currentTabId != R.id.navHome) {
                Intent intent = new Intent(activity, TART_MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                if (!(activity instanceof TART_MainActivity)) {
                    activity.finish();
                }
            }
        });

        navTemplates.setOnClickListener(v -> {
            if (currentTabId != R.id.navTemplates) {
                TART_LoadingDialog loader = new TART_LoadingDialog(activity);
                loader.show();
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    loader.dismiss();
                    Intent intent = new Intent(activity, TART_SampleActivity.class);
                    activity.startActivity(intent);
                    if (!(activity instanceof TART_MainActivity)) {
                        activity.finish();
                    }
                }, 600);
            }
        });

        navCreate.setOnClickListener(v -> {
            TART_LoadingDialog loader = new TART_LoadingDialog(activity);
            loader.show();
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                loader.dismiss();
                Intent intent = new Intent(activity, TART_ColorPickerActivity.class);
                activity.startActivity(intent);
                if (!(activity instanceof TART_MainActivity)) {
                    activity.finish();
                }
            }, 600);
        });

        navGallery.setOnClickListener(v -> {
            com.addtext.textonphoto.textart.imagepicker.KSUtil.fromAlbum = false;
            Intent intent = new Intent(activity, com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity.class);
            intent.putExtra(com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
            intent.putExtra(com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
            activity.startActivityForResult(intent, com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity.PICKER_REQUEST_CODE);
        });

        navProfile.setOnClickListener(v -> {
            if (currentTabId != R.id.navProfile) {
                Intent intent = new Intent(activity, TART_SettingsActivity.class);
                activity.startActivity(intent);
                if (!(activity instanceof TART_MainActivity)) {
                    activity.finish();
                }
            }
        });
    }
}
