package com.addtext.textonphoto.textart.adManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class TART_InterstitialAdManager {

    private final String admobInterstitialAdId;
    private final Context context;
    private final TART_PreferenceClass preferenceClass;
    private InterstitialAd admobInterstitialAd;
    private OnAdLoadInterface onAdLoadInterface;
    private boolean isFailed = false;

    public TART_InterstitialAdManager(Context context) {
        this.context = context;
        preferenceClass = new TART_PreferenceClass(this.context);
        admobInterstitialAdId = preferenceClass.getAdsId("GoogleInterstitialAd");
        Log.e("TAG", "TART_InterstitialAdManager@: "+admobInterstitialAdId );
        // Fetch initial ad based on count logic
        int limit = preferenceClass.getAdsStatus("InerstialClickCount");
        int count = preferenceClass.getInt("getClickCount");
        if (limit > 0 && count >= limit - 1) {
            fetchAdMobAd();
        }
    }

    public void fetchAdMobAd() {

        if (isAdmobAdAvailable()) {
            android.util.Log.e("ADMOB_DEBUG_LOG", "fetchAdMobAd: Ad is already available, skipping fetch.");
            return;
        }

        android.util.Log.e("ADMOB_DEBUG_LOG", "fetchAdMobAd: Requesting new Interstitial Ad...");

        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                admobInterstitialAd = ad;
                android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Ad LOADED successfully!");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isFailed = true;
                android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Ad FAILED to load: " + loadAdError.getMessage());
            }
        };
        AdRequest request = getAdRequest();
        Log.e("TAG", "TART_InterstitialAdManager2: "+admobInterstitialAdId );
        InterstitialAd.load(context, admobInterstitialAdId, request, loadCallback);
    }


    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdmobAdAvailable() {
        return admobInterstitialAd != null;
    }

    

    public boolean willShowAd() {
        int limit = preferenceClass.getAdsStatus("InerstialClickCount");
        if (limit == 0) return false;
        int count = preferenceClass.getInt("getClickCount") + 1;
        if (count < limit) return false;
        return isAdmobAdAvailable();
    }

        public void showAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        int limit = preferenceClass.getAdsStatus("InerstialClickCount");
        if (limit == 0) {
            onAdLoadInterface.onAdClose();
            return;
        }
        int count = preferenceClass.getInt("getClickCount") + 1; // Increment first

        android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Click Count: " + count + " / Limit: " + limit);

        if (count < limit) {
            preferenceClass.setInt("getClickCount", count);
            
            // N-1 Preloading Logic! Load ad just before the click where it's needed
            if (count >= limit - 1) {
                android.util.Log.e("ADMOB_DEBUG_LOG", "N-1 Reached! Preloading Interstitial Ad in background...");
                fetchAdMobAd(); 
            }
            
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        // Target click reached!
        android.util.Log.e("ADMOB_DEBUG_LOG", "Target Click Reached! Attempting to show Interstitial Ad...");
        preferenceClass.setInt("getClickCount", 0);

        if (isAdmobAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Ad FAILED TO SHOW: " + adError.getMessage());
                    admobInterstitialAd = null;
                    isFailed = true;
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }
                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Ad SHOWING to user! (Impression registered)");
                }
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Interstitial Ad DISMISSED by user.");
                    admobInterstitialAd = null;
                    
                    int currentLimit = preferenceClass.getAdsStatus("InerstialClickCount");
                    int currentCount = preferenceClass.getInt("getClickCount");
                    if (currentCount >= currentLimit - 1) {
                        android.util.Log.e("ADMOB_DEBUG_LOG", "Post-Dismiss: N-1 Reached immediately! Fetching next ad...");
                        fetchAdMobAd();
                    }
                    
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }
                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            admobInterstitialAd.show(activity);
        } else {
            if (isFailed || admobInterstitialAd == null) {
                isFailed = false;
                
                int currentLimit = preferenceClass.getAdsStatus("InerstialClickCount");
                int currentCount = preferenceClass.getInt("getClickCount");
                if (currentCount >= currentLimit - 1) {
                    fetchAdMobAd();
                }
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        }
    }
//    public void showAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) { // if google sec code implement open this method and comment above method
//        this.onAdLoadInterface = onAdLoadInterface;
//
//        if (isFailed) {
//            isFailed = false;
//            fetchAdMobAd();
//            fetchFbAd();
//            onAdLoadInterface.onAdClose();
//            return;
//        }
//
//        int interstitalAdStatus = preferenceClass.getAdsStatus("InerstialClickCount");
//        int googleAdsTime =  preferenceClass.getAdsStatus("GoogleAdsTime");//this flag for Google ads stop 20 sec after ones show
//
//        int getClickCount = preferenceClass.getInt("getClickCount");
//        if (getClickCount < interstitalAdStatus) {
//            preferenceClass.setInt("getClickCount", getClickCount + 1);
//            onAdLoadInterface.onAdClose();
//            return;
//        }
//
//        preferenceClass.setInt("getClickCount", 0);
//
//        long currentTime = System.currentTimeMillis();
//        long lastGoogleAdShownTime = preferenceClass.getLong("lastGoogleAdShownTime");
//        long timeDifference = currentTime - lastGoogleAdShownTime;
//
//        if (timeDifference < googleAdsTime * 1000) {


//            } else {
//                onAdLoadInterface.onAdClose();
//            }
//        } else {
//            preferenceClass.setLong("lastGoogleAdShownTime", currentTime);
//
//            if (isAdmobAdAvailable()) {
//                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        super.onAdFailedToShowFullScreenContent(adError);
//                        admobInterstitialAd = null;
//                        isFailed = true;
//                        onAdLoadInterface.onAdClose();
//                    }
//
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                        super.onAdShowedFullScreenContent();
//                    }
//
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        super.onAdDismissedFullScreenContent();
//                        admobInterstitialAd = null;
//                        fetchAdMobAd();
//                        onAdLoadInterface.onAdClose();
//                    }
//
//                    @Override
//                    public void onAdImpression() {
//                        super.onAdImpression();
//                    }
//                };
//                admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
//                admobInterstitialAd.show(activity);


//            } else {
//                onAdLoadInterface.onAdClose();
//            }
//        }
//    }

    public void showInterstitialAd(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        int interstitalAdStatus = preferenceClass.getAdsStatus("InerstialClickCount");

        if (interstitalAdStatus == 0) {
            onAdLoadInterface.onAdClose();
            return;
        }

        int getClickCount = preferenceClass.getInt("getClickCount");
        if (getClickCount < interstitalAdStatus) {
            preferenceClass.setInt("getClickCount", getClickCount + 1);
            onAdLoadInterface.onAdClose();
            return;
        }

        preferenceClass.setInt("getClickCount", 0);

        if (isAdmobAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    isFailed = true;
                    onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            admobInterstitialAd.show(activity);
        }  else {
            if (isFailed || admobInterstitialAd == null) {
                isFailed = false;
                fetchAdMobAd();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        }

    }

    public void showEDitAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        int interstitalAdStatus = preferenceClass.getAdsStatus("EditScreenAdCount");

        int getClickCount = preferenceClass.getInt("getEDitClickCount");
        if (getClickCount < interstitalAdStatus) {
            preferenceClass.setInt("getEDitClickCount", getClickCount + 1);
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        preferenceClass.setInt("getEDitClickCount", 0);

        if (isAdmobAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    isFailed = true;
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            admobInterstitialAd.show(activity);
        }  else {
            if (isFailed || admobInterstitialAd == null) {
                isFailed = false;
                fetchAdMobAd();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        }

    }

    public interface OnAdLoadInterface {
        void onAdClose();
    }

}
