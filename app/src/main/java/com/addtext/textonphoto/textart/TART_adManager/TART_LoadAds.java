package com.addtext.textonphoto.textart.adManager;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class TART_LoadAds {

    public static void loadAdmobBannerAd(Activity activity, RelativeLayout mainLayout) {
        if (mainLayout == null || activity == null) return;
        mainLayout.removeAllViews();

        String bannerAdunitID = new TART_PreferenceClass(activity).getAdsId("GoogleBannerAd");
        android.util.Log.e("ADMOB_DEBUG_LOG", "=== BANNER REQUEST in " + activity.getClass().getSimpleName() + " with ID: [" + bannerAdunitID + "] ===");
        if (bannerAdunitID == null || bannerAdunitID.trim().isEmpty()) {
            android.util.Log.e("ADMOB_DEBUG_LOG", "Banner ID is EMPTY in SharedPreferences! Skipping Google Banner.");
            loadFBBannerAd(activity, mainLayout);
            return;
        }

        try {
            AdView adView = new AdView(activity);
            AdSize adSize = getAdSize(activity);
            adView.setAdSize(adSize);
            adView.setAdUnitId(bannerAdunitID);

            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    android.util.Log.e("ADMOB_DEBUG_LOG", ">>> BANNER FAILED TO LOAD in " + activity.getClass().getSimpleName() + "!");
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Message: " + loadAdError.getMessage());
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Code: " + loadAdError.getCode() + " (0=Internal, 1=InvalidRequest, 2=Network, 3=NoFill)");
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Domain: " + loadAdError.getDomain());
                    loadFBBannerAd(activity, mainLayout);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    android.util.Log.e("ADMOB_DEBUG_LOG", ">>> BANNER LOADED SUCCESSFULLY in " + activity.getClass().getSimpleName() + "!");
                    mainLayout.removeAllViews();
                    mainLayout.setVisibility(View.VISIBLE);
                    mainLayout.addView(adView, bannerParameters);
                }
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
            loadFBBannerAd(activity, mainLayout);
        }
    }

    private static void loadFBBannerAd(Activity activity, RelativeLayout mainLayout) {
        if (mainLayout == null || activity == null) return;
        String fbBannerAdunitID = new TART_PreferenceClass(activity).getAdsId("FbBannerAd");
        if (fbBannerAdunitID == null || fbBannerAdunitID.trim().isEmpty()) {
            mainLayout.removeAllViews();
            mainLayout.setVisibility(View.GONE);
            return;
        }

        try {
            com.facebook.ads.AdView fbBannerView = new com.facebook.ads.AdView(activity, fbBannerAdunitID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            mainLayout.setGravity(Gravity.CENTER);

            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    mainLayout.removeAllViews();
                    mainLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    mainLayout.removeAllViews();
                    mainLayout.setVisibility(View.VISIBLE);
                    mainLayout.addView(fbBannerView);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            fbBannerView.loadAd(fbBannerView.buildLoadAdConfig().withAdListener(adListener).build());
        } catch (Exception e) {
            e.printStackTrace();
            mainLayout.removeAllViews();
            mainLayout.setVisibility(View.GONE);
        }
    }

    private static AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }
}
