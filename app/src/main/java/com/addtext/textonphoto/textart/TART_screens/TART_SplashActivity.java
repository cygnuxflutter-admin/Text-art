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


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack_activity_splash);
        preferenceClass = new TART_PreferenceClass(this);
        MyApplication.isAdsSplash = true;
        if (TART_NetworkUtils.isNetworkAvailable(this)) {
            getData();
        } else {
            TART_MaterialDialogUtils.getInstance().errorDialog(this, getResources().getString(R.string.internet_error));
        }
    }

    public void startToMainActivity() {
        startIntent();
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            @Override // java.lang.Runnable
            public final void run() {

                startToMainActivity();
            }
        }, 3000);
    }

    private void getData() {
        if (TART_NetworkUtils.isNetworkAvailable(this)) {
            database = FirebaseDatabase.getInstance();
            project_data2 = database.getReference("TextArt_Data/Ads_data");
            project_data2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e("TAG", "onDataChange: " + snapshot);

                    try {
                        preferenceClass.setInt("splashscreen", Integer.parseInt(snapshot.child("SplashScreenAdsManage").getValue().toString()));
                        preferenceClass.setInt("UpdateAvailable", Integer.parseInt(snapshot.child("UpdateAvailable").getValue().toString()));
                        preferenceClass.setDataType("UpdateVersionName", Objects.requireNonNull(snapshot.child("UpdateVersionName").getValue()).toString());

                        preferenceClass.setDataType("MainActivityGame", Objects.requireNonNull(snapshot.child("MainActivityGame").getValue()).toString());
                        preferenceClass.setDataType("URL_MainActivityGame", Objects.requireNonNull(snapshot.child("URL_MainActivityGame").getValue()).toString());
                        preferenceClass.setDataType("ImagePickerBanner", Objects.requireNonNull(snapshot.child("ImagePickerBanner").getValue()).toString());
                        preferenceClass.setDataType("URL_ImagePickerBanner ", Objects.requireNonNull(snapshot.child("URL_ImagePickerBanner").getValue()).toString());
                        preferenceClass.setDataType("ShareAcrivityBanner", Objects.requireNonNull(snapshot.child("ShareAcrivityBanner").getValue()).toString());
                        preferenceClass.setDataType("URL_ShareAcrivityBanner", Objects.requireNonNull(snapshot.child("URL_ShareAcrivityBanner").getValue()).toString());
                        preferenceClass.setDataType("BGActivityGame", Objects.requireNonNull(snapshot.child("BGActivityGame").getValue()).toString());
                        preferenceClass.setDataType("URL_BGActivityGame", Objects.requireNonNull(snapshot.child("URL_BGActivityGame").getValue()).toString());
                        preferenceClass.setDataType("BGActivityBanner2", Objects.requireNonNull(snapshot.child("BGActivityBanner2").getValue()).toString());
                        preferenceClass.setDataType("URL_BGActivityBanner2", Objects.requireNonNull(snapshot.child("URL_BGActivityBanner2").getValue()).toString());
                        preferenceClass.setDataType("BGActivityBanner1", Objects.requireNonNull(snapshot.child("BGActivityBanner1").getValue()).toString());
                        preferenceClass.setDataType("URL_BGActivityBanner1", Objects.requireNonNull(snapshot.child("URL_BGActivityBanner1").getValue()).toString());
                        preferenceClass.setDataType("SettingActivityGame", Objects.requireNonNull(snapshot.child("SettingActivityGame").getValue()).toString());
                        preferenceClass.setDataType("URL_SettingActivityGame", Objects.requireNonNull(snapshot.child("URL_SettingActivityGame").getValue()).toString());

                        // ----------------------------------------------- Live ADS -----------------------------------------------
                        preferenceClass.setDataType("GoogleBannerAd", Objects.requireNonNull(snapshot.child("GoogleBannerAd").getValue()).toString());
                        preferenceClass.setDataType("GoogleAppopenAd", Objects.requireNonNull(snapshot.child("GoogleAppopenAd").getValue()).toString());
                        preferenceClass.setDataType("GoogleInterstitialAd", Objects.requireNonNull(snapshot.child("GoogleInterstitialAd").getValue()).toString());
                        preferenceClass.setDataType("GoogleInterstialRewardAd", Objects.requireNonNull(snapshot.child("GoogleInterstialRewardAd").getValue()).toString());
                        preferenceClass.setDataType("GoogleRewardedAd", Objects.requireNonNull(snapshot.child("GoogleRewardedAd").getValue()).toString());
                        preferenceClass.setDataType("GoogleNativeAd", Objects.requireNonNull(snapshot.child("GoogleNativeAd").getValue()).toString());

                        preferenceClass.setDataType("FbNativeAd", Objects.requireNonNull(snapshot.child("FbNativeAd").getValue()).toString());
                        preferenceClass.setDataType("FbInterstitialAd", Objects.requireNonNull(snapshot.child("FbInterstitialAd").getValue()).toString());
                        preferenceClass.setDataType("FbBannerAd", Objects.requireNonNull(snapshot.child("FbBannerAd").getValue()).toString());

                        preferenceClass.setInt("InerstialClickCount", Integer.parseInt(Objects.requireNonNull(snapshot.child("InerstialClickCount").getValue().toString())));
                        preferenceClass.setInt("GoogleAdsTime", Integer.parseInt(Objects.requireNonNull(snapshot.child("GoogleAdsTime").getValue().toString())));
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    try {
                        if (preferenceClass.getInt("UpdateAvailable") == 1 && !preferenceClass.getAdsId("UpdateVersionName").equals(BuildConfig.VERSION_NAME)) {

                            @SuppressLint("ResourceType") Dialog materialDialog = new Dialog(TART_SplashActivity.this, 16974126);
                            materialDialog.requestWindowFeature(1);
                            materialDialog.setContentView(R.layout.knack_reward_dialog);
                            materialDialog.setCancelable(false);


                            if (!materialDialog.isShowing())
                                materialDialog.show();

                            TextView tv_title = materialDialog.findViewById(R.id.title);
                            TextView tv_description = materialDialog.findViewById(R.id.description);

                            TextView button1 = materialDialog.findViewById(R.id.button1);
                            TextView button2 = materialDialog.findViewById(R.id.button2);

                            tv_title.setText("Update is Available");
//                        tv_description.setText(message);
                            button1.setText("Cancal");
                            button2.setText("Update Now");
                            button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                                    } catch (ActivityNotFoundException unused) {
                                        Toast.makeText(TART_SplashActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                                    }

                                    if (materialDialog != null && materialDialog.isShowing()) {
                                        materialDialog.dismiss();
                                        next();
                                    }
                                }
                            });
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (materialDialog != null && materialDialog.isShowing()) {
                                        materialDialog.dismiss();
                                        next();
                                    }
                                }
                            });

                        } else {
                            next();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }

//
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    TART_MaterialDialogUtils.getInstance().errorDialog(TART_SplashActivity.this, getResources().getString(R.string.something_went_wrong));
                }
            });
        } else {
            TART_MaterialDialogUtils.getInstance().errorDialog(this, getResources().getString(R.string.internet_error));

        }
    }

    private void startIntent() {
        callMainActivity();
    }

    public void callStartActivity() {
        callMainActivity();
    }

    public void callMainActivity() {
        MyApplication.isAdsSplash = false;
        ((MyApplication) getApplicationContext()).sendRequest();
        ((MyApplication) getApplicationContext()).loadInterstitialAd();

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

