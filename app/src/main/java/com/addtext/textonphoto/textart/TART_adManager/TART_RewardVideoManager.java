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
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class TART_RewardVideoManager {
    private static TART_PreferenceClass preferenceClass;
    private static String AD_google_Rw;
    private static AlertDialog alertDialog;
    public static RewardedInterstitialAd mRewardedAd;

    static boolean isUserEarnReward = false;
    

    public static void showRewardVideoAd(final Activity context, OnRewardAdLoadInterface onAdLoadInterface) {
        isUserEarnReward = false;
        if (preferenceClass == null) {
            preferenceClass = new TART_PreferenceClass(context);
        }
        AD_google_Rw = preferenceClass.getAdsId("GoogleInterstialRewardAd");//"ca-app-pub-3940256099942544/5354046379" ;//test key
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

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedInterstitialAd.load(context, AD_google_Rw, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(RewardedInterstitialAd ad) {
                mRewardedAd = ad;
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                if (mRewardedAd != null) {
                    mRewardedAd.show(context, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            isUserEarnReward = true;
                        }
                    });
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
//                            isUserEarnReward = false;
//                            onAdLoadInterface.onAdClose(isUserEarnReward);
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
