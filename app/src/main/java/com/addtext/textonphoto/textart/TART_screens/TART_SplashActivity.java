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
import android.widget.ImageView;
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
    private long splashStartTime;

    private TART_PreferenceClass preferenceClass;
    public FirebaseDatabase database;
    private DatabaseReference project_data2;
    private InterstitialAd interstitial = null;
    public com.facebook.ads.InterstitialAd interstitialFB;
    final private int REQUEST_CAMERA_AND_STORAGE_PERMISSION = 100;


    private boolean isNavigated = false;
    private Handler fallbackHandler = new Handler();
    private Runnable fallbackRunnable = this::callMainActivity;

    private ImageView splashImage;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        splashStartTime = System.currentTimeMillis();

        setContentView(R.layout.knack_activity_splash);

        ImageView splashLogo = findViewById(R.id.splashLogo);
        TextView splashText = findViewById(R.id.splashText);
        TextView splashSubText = findViewById(R.id.splashSubText);
        android.widget.ProgressBar splashLoader = findViewById(R.id.splashLoader);
        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        ImageView star4 = findViewById(R.id.star4);
        View ringInner = findViewById(R.id.ringInner);
        View ringOuter = findViewById(R.id.ringOuter);
        View dot1 = findViewById(R.id.dot1);
        View dot2 = findViewById(R.id.dot2);
        View dot3 = findViewById(R.id.dot3);

        // Set initial states
        if (splashLogo != null) { splashLogo.setScaleX(0.5f); splashLogo.setScaleY(0.5f); }
        if (splashText != null) splashText.setTranslationY(40f);
        if (splashSubText != null) splashSubText.setTranslationY(40f);
        if (star1 != null) { star1.setScaleX(0f); star1.setScaleY(0f); }
        if (star2 != null) { star2.setScaleX(0f); star2.setScaleY(0f); }
        if (star3 != null) { star3.setScaleX(0f); star3.setScaleY(0f); }
        if (star4 != null) { star4.setScaleX(0f); star4.setScaleY(0f); }
        if (ringInner != null) { ringInner.setScaleX(0.3f); ringInner.setScaleY(0.3f); }
        if (ringOuter != null) { ringOuter.setScaleX(0.3f); ringOuter.setScaleY(0.3f); }

        // 1. Logo: scale up with bounce
        if (splashLogo != null) {
            splashLogo.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(900)
                .setInterpolator(new android.view.animation.OvershootInterpolator(1.2f))
                .setStartDelay(100).start();
        }

        // 2. Rings: expand outward with rotation
        if (ringInner != null) {
            ringInner.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(30f)
                .setDuration(1000).setStartDelay(300).start();
        }
        if (ringOuter != null) {
            ringOuter.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(-20f)
                .setDuration(1200).setStartDelay(400).start();
        }

        // 3. Text slides up
        if (splashText != null) {
            splashText.animate().alpha(1f).translationY(0).setDuration(800).setStartDelay(500).start();
        }
        if (splashSubText != null) {
            splashSubText.animate().alpha(1f).translationY(0).setDuration(800).setStartDelay(650).start();
        }

        // 4. Stars pop in
        if (star1 != null) star1.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(180f).setDuration(600).setStartDelay(500).start();
        if (star2 != null) star2.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(180f).setDuration(600).setStartDelay(700).start();
        if (star3 != null) star3.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(180f).setDuration(600).setStartDelay(900).start();
        if (star4 != null) star4.animate().alpha(1f).scaleX(1f).scaleY(1f).rotationBy(180f).setDuration(600).setStartDelay(1100).start();

        // 5. Dots fade in
        if (dot1 != null) dot1.animate().alpha(1f).setDuration(500).setStartDelay(600).start();
        if (dot2 != null) dot2.animate().alpha(1f).setDuration(500).setStartDelay(800).start();
        if (dot3 != null) dot3.animate().alpha(1f).setDuration(500).setStartDelay(1000).start();

        // 7. Loader appears last
        if (splashLoader != null) {
            splashLoader.animate().alpha(1f).setDuration(500).setStartDelay(1000).start();
        }

        preferenceClass = new TART_PreferenceClass(this);
        MyApplication.isAdsSplash = true;

        // Fallback timer: guarantees transition to MainActivity after max 6 seconds if network is slow
        fallbackHandler.postDelayed(fallbackRunnable, 6000);

        if (TART_NetworkUtils.isNetworkAvailable(this)) {
            getData();
        } else {
            next();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fallbackHandler != null) {
            fallbackHandler.removeCallbacks(fallbackRunnable);
        }
    }

    public void startToMainActivity() {
        startIntent();
    }

    public void next() {
        long elapsed = System.currentTimeMillis() - splashStartTime;
        long delay = 2500 - elapsed;
        if (delay < 0) delay = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public final void run() {
                startToMainActivity();
            }
        }, delay);
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
            database = FirebaseDatabase.getInstance("https://poster---text-art-default-rtdb.firebaseio.com");
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

                        preferenceClass.setInt("InerstialClickCount", getIntSafe(snapshot, "InerstialClickCount", 1));
                        preferenceClass.setInt("GoogleAdsTime", getIntSafe(snapshot, "GoogleAdsTime", 10));

                        android.util.Log.e("FIREBASE_ADS_LOG", "==================================================");
                        android.util.Log.e("FIREBASE_ADS_LOG", "=== FIREBASE ADS DATA RECEIVED SUCCESSFULLY ===");
                        android.util.Log.e("FIREBASE_ADS_LOG", "Raw Data: " + snapshot.getValue());
                        android.util.Log.e("FIREBASE_ADS_LOG", "GoogleBannerAd = [" + preferenceClass.getAdsId("GoogleBannerAd") + "]");
                        android.util.Log.e("FIREBASE_ADS_LOG", "GoogleInterstitialAd = [" + preferenceClass.getAdsId("GoogleInterstitialAd") + "]");
                        android.util.Log.e("FIREBASE_ADS_LOG", "GoogleNativeAd = [" + preferenceClass.getAdsId("GoogleNativeAd") + "]");
                        android.util.Log.e("FIREBASE_ADS_LOG", "InerstialClickCount = [" + preferenceClass.getInt("InerstialClickCount", 1) + "]");
                        android.util.Log.e("FIREBASE_ADS_LOG", "==================================================");

                        try {
                            ((MyApplication) getApplication()).getInterstitialAdManager().fetchAdMobAd();
                        } catch (Exception ignored) {}
                    } catch (Exception e) {
                        e.printStackTrace();
                        android.util.Log.e("FIREBASE_ADS_LOG", "Error parsing snapshot: " + e.getMessage());
                    }

                    try {
                        String isUpdateStr = getStringSafe(snapshot, "UpdateAvailable");
                        boolean isUpdate = isUpdateStr.equals("1") || isUpdateStr.equalsIgnoreCase("true");
                        String updateVer = getStringSafe(snapshot, "UpdateVersionName");
                        
                        android.util.Log.e("UPDATE_LOG", "UpdateAvailable (String): " + isUpdateStr + " -> Boolean: " + isUpdate);
                        android.util.Log.e("UPDATE_LOG", "Firebase Version: " + updateVer);
                        android.util.Log.e("UPDATE_LOG", "App Version: " + BuildConfig.VERSION_NAME);
                        
                        if (isUpdate && updateVer != null && !updateVer.isEmpty() && !updateVer.equals(BuildConfig.VERSION_NAME)) {
                            // Stop the 2.5 second fallback timer so the user has time to click!
                            fallbackHandler.removeCallbacks(fallbackRunnable);
                            
                            @SuppressLint("ResourceType") Dialog materialDialog = new Dialog(TART_SplashActivity.this, 16974126);
                            materialDialog.requestWindowFeature(1);
                            materialDialog.setContentView(R.layout.knack_reward_dialog);
                            materialDialog.setCancelable(false);

                            if (!isFinishing() && !materialDialog.isShowing()) {
                                materialDialog.show();
                            }

                            TextView tv_title = materialDialog.findViewById(R.id.title);
                            TextView button1 = materialDialog.findViewById(R.id.button1);
                            TextView button2 = materialDialog.findViewById(R.id.button2);

                            tv_title.setText("Update is Available");
                            button1.setText("Cancel");
                            button2.setText("Update Now");
                            button2.setOnClickListener(v -> {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } catch (ActivityNotFoundException unused) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Toast.makeText(TART_SplashActivity.this, "Unable to open Play Store", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if (materialDialog.isShowing()) {
                                    materialDialog.dismiss();
                                }
                                // Finish the app so it doesn't continue loading in the background
                                finish();
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
                    android.util.Log.e("FIREBASE_ADS_LOG", "==================================================");
                    android.util.Log.e("FIREBASE_ADS_LOG", "=== FIREBASE ON_CANCELLED ERROR ===");
                    android.util.Log.e("FIREBASE_ADS_LOG", "Error Message: " + error.getMessage());
                    android.util.Log.e("FIREBASE_ADS_LOG", "Error Code: " + error.getCode());
                    android.util.Log.e("FIREBASE_ADS_LOG", "Error Details: " + error.getDetails());
                    android.util.Log.e("FIREBASE_ADS_LOG", "==================================================");
                    next();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.e("FIREBASE_ADS_LOG", "Exception in getData(): " + e.getMessage());
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




