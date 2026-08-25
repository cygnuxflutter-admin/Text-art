package com.addtext.textonphoto.textart.TART_screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.addtext.textonphoto.textart.BuildConfig;
import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterUtils;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterViewAdapter;
import com.addtext.textonphoto.textart.TART_utils.TART_MaterialDialogUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_NetworkUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.adManager.TART_RewardVideoManager;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import io.reactivex.internal.operators.flowable.FlowableGenerate;

public class TART_SplashActivity extends AppCompatActivity {
    private TART_PreferenceClass preferenceClass;
    public FirebaseDatabase database;
    private DatabaseReference project_data2;
    private InterstitialAd interstitial = null;
    public com.facebook.ads.InterstitialAd interstitialFB;
    final private int REQUEST_CAMERA_AND_STORAGE_PERMISSION = 100;


    private boolean isNavigated = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack_activity_splash);
        preferenceClass = new TART_PreferenceClass(this);
        MyApplication.isAdsSplash = true;

        // Fallback timer: guarantees transition to MainActivity after max 4 seconds
        new Handler().postDelayed(this::callMainActivity, 4000);

        if (TART_NetworkUtils.isNetworkAvailable(this)) {
            getData();
        } else {
            next();
        }
    }

    public void startToMainActivity() {
        startIntent();
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                startToMainActivity();
            }
        }, 1500);
    }

    private String getStringSafe(DataSnapshot snapshot, String key) {
        try {
            if (snapshot != null && snapshot.hasChild(key) && snapshot.child(key).getValue() != null) {
                return snapshot.child(key).getValue().toString();
            }
        } catch (Exception ignored) {}
        return "";
    }

    private int getIntSafe(DataSnapshot snapshot, String key, int def) {
        try {
            String val = getStringSafe(snapshot, key);
            if (!val.isEmpty()) {
                return Integer.parseInt(val);
            }
        } catch (Exception ignored) {}
        return def;
    }

    private void getData() {
        if (!TART_NetworkUtils.isNetworkAvailable(this)) {
            next();
            return;
        }

        try {
            database = FirebaseDatabase.getInstance();
            project_data2 = database.getReference("TextArt_Data/Ads_data");
            project_data2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        preferenceClass.setInt("splashscreen", getIntSafe(snapshot, "SplashScreenAdsManage", 0));
                        preferenceClass.setInt("UpdateAvailable", getIntSafe(snapshot, "UpdateAvailable", 0));
                        preferenceClass.setDataType("UpdateVersionName", getStringSafe(snapshot, "UpdateVersionName"));

                        preferenceClass.setDataType("MainActivityGame", getStringSafe(snapshot, "MainActivityGame"));
                        preferenceClass.setDataType("URL_MainActivityGame", getStringSafe(snapshot, "URL_MainActivityGame"));
                        preferenceClass.setDataType("ImagePickerBanner", getStringSafe(snapshot, "ImagePickerBanner"));
                        preferenceClass.setDataType("URL_ImagePickerBanner ", getStringSafe(snapshot, "URL_ImagePickerBanner"));
                        preferenceClass.setDataType("ShareAcrivityBanner", getStringSafe(snapshot, "ShareAcrivityBanner"));
                        preferenceClass.setDataType("URL_ShareAcrivityBanner", getStringSafe(snapshot, "URL_ShareAcrivityBanner"));
                        preferenceClass.setDataType("BGActivityGame", getStringSafe(snapshot, "BGActivityGame"));
                        preferenceClass.setDataType("URL_BGActivityGame", getStringSafe(snapshot, "URL_BGActivityGame"));
                        preferenceClass.setDataType("BGActivityBanner2", getStringSafe(snapshot, "BGActivityBanner2"));
                        preferenceClass.setDataType("URL_BGActivityBanner2", getStringSafe(snapshot, "URL_BGActivityBanner2"));
                        preferenceClass.setDataType("BGActivityBanner1", getStringSafe(snapshot, "BGActivityBanner1"));
                        preferenceClass.setDataType("URL_BGActivityBanner1", getStringSafe(snapshot, "URL_BGActivityBanner1"));
                        preferenceClass.setDataType("SettingActivityGame", getStringSafe(snapshot, "SettingActivityGame"));
                        preferenceClass.setDataType("URL_SettingActivityGame", getStringSafe(snapshot, "URL_SettingActivityGame"));

                        // Live ADS
                        preferenceClass.setDataType("GoogleBannerAd", getStringSafe(snapshot, "GoogleBannerAd"));
                        preferenceClass.setDataType("GoogleAppopenAd", getStringSafe(snapshot, "GoogleAppopenAd"));
                        preferenceClass.setDataType("GoogleInterstitialAd", getStringSafe(snapshot, "GoogleInterstitialAd"));
                        preferenceClass.setDataType("GoogleInterstialRewardAd", getStringSafe(snapshot, "GoogleInterstialRewardAd"));
                        preferenceClass.setDataType("GoogleRewardedAd", getStringSafe(snapshot, "GoogleRewardedAd"));
                        preferenceClass.setDataType("GoogleNativeAd", getStringSafe(snapshot, "GoogleNativeAd"));

                        preferenceClass.setDataType("FbNativeAd", getStringSafe(snapshot, "FbNativeAd"));
                        preferenceClass.setDataType("FbInterstitialAd", getStringSafe(snapshot, "FbInterstitialAd"));
                        preferenceClass.setDataType("FbBannerAd", getStringSafe(snapshot, "FbBannerAd"));

                        preferenceClass.setInt("InerstialClickCount", getIntSafe(snapshot, "InerstialClickCount", 3));
                        preferenceClass.setInt("GoogleAdsTime", getIntSafe(snapshot, "GoogleAdsTime", 10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        String updateVer = preferenceClass.getAdsId("UpdateVersionName");
                        if (preferenceClass.getInt("UpdateAvailable") == 1 && updateVer != null && !updateVer.isEmpty() && !updateVer.equals(BuildConfig.VERSION_NAME)) {
                            @SuppressLint("ResourceType") Dialog materialDialog = new Dialog(TART_SplashActivity.this, 16974126);
                            materialDialog.requestWindowFeature(1);
                            materialDialog.setContentView(R.layout.knack_reward_dialog);
                            materialDialog.setCancelable(false);

                            if (!materialDialog.isShowing()) materialDialog.show();

                            TextView tv_title = materialDialog.findViewById(R.id.title);
                            TextView button1 = materialDialog.findViewById(R.id.button1);
                            TextView button2 = materialDialog.findViewById(R.id.button2);

                            tv_title.setText("Update is Available");
                            button1.setText("Cancel");
                            button2.setText("Update Now");
                            button2.setOnClickListener(v -> {
                                try {
                                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                                } catch (ActivityNotFoundException unused) {
                                    Toast.makeText(TART_SplashActivity.this, "unable to find market app", Toast.LENGTH_LONG).show();
                                }
                                if (materialDialog.isShowing()) {
                                    materialDialog.dismiss();
                                    next();
                                }
                            });
                            button1.setOnClickListener(v -> {
                                if (materialDialog.isShowing()) {
                                    materialDialog.dismiss();
                                    next();
                                }
                            });
                        } else {
                            next();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        next();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    next();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            next();
        }
    }

    private void startIntent() {
        callMainActivity();
    }

    public void callStartActivity() {
        callMainActivity();
    }

    public synchronized void callMainActivity() {
        if (isNavigated) return;
        isNavigated = true;

        try {
            MyApplication.isAdsSplash = false;
            ((MyApplication) getApplicationContext()).sendRequest();
            ((MyApplication) getApplicationContext()).loadInterstitialAd();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), TART_MainActivity.class);
        startActivity(intent);
        finish();
    }


//    private void save_token() {
//        TART_MaterialDialogUtils.getInstance().rewardDialog(TART_SplashActivity.this,
//                "Update Now", "Update Your App", materialDialog -> {
//
//                    TART_PreferenceClass preferenceClass = new TART_PreferenceClass(TART_SplashActivity.this);
//                    // if(preferenceClass.getDataType("PremiumAdType")!=null && preferenceClass.getDataType("PremiumAdType").equals("Reward")) {
//
//                    if (materialDialog != null && materialDialog.isShowing())
//                        materialDialog.dismiss();
//                }, materialDialog -> {
//                    if (materialDialog != null && materialDialog.isShowing())
//                        materialDialog.dismiss();
//                });
//    }


}

