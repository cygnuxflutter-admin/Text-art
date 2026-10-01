package com.addtext.textonphoto.textart.adManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.addtext.textonphoto.textart.R;

import com.addtext.textonphoto.textart.TART_screens.TART_SplashActivity;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;


import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class TART_RewardVideoManager {
    private static TART_PreferenceClass preferenceClass;
    private static String AD_google_Rw;
    private static AlertDialog alertDialog;
    public static RewardedAd mRewardedAd;

    static boolean isUserEarnReward = false;
    

    public static void showRewardVideoAd(final Activity context, OnRewardAdLoadInterface onAdLoadInterface) {
        isUserEarnReward = false;
        if (preferenceClass == null) {
            preferenceClass = new TART_PreferenceClass(context);
        }
        AD_google_Rw = preferenceClass.getAdsId("GoogleRewardedAd");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.knack_lottie_anim_dialog, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (!((Activity) context).isFinishing()) {
            try {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.d("AdmobReward", "Loading Reward Ad with ID: " + AD_google_Rw);
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, AD_google_Rw, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                mRewardedAd = ad;
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                if (mRewardedAd != null) {
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            onAdLoadInterface.onAdClose(isUserEarnReward);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            onAdLoadInterface.onAdFail();
                        }
                    });

                    mRewardedAd.show(context, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            isUserEarnReward = true;
                        }
                    });
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                onAdLoadInterface.onAdFail();
            }
        });

    }

    public interface OnRewardAdLoadInterface {
        void onAdClose(boolean isWithReward);

        void onAdFail();
    }
}
