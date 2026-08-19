package com.addtext.textonphoto.textart.adManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class TART_InterstitialAdManager {

    private final String admobInterstitialAdId, fbInterstitialAdId;
    private final Context context;
    private final TART_PreferenceClass preferenceClass;
    private InterstitialAd admobInterstitialAd;
    private com.facebook.ads.InterstitialAd fbInterstitialAd;
    private OnAdLoadInterface onAdLoadInterface;
    private boolean isFailed = false;

    public TART_InterstitialAdManager(Context context) {
        this.context = context;
        preferenceClass = new TART_PreferenceClass(this.context);
        admobInterstitialAdId = preferenceClass.getAdsId("GoogleInterstitialAd");
        fbInterstitialAdId = preferenceClass.getAdsId("FbInterstitialAd");
        Log.e("TAG", "TART_InterstitialAdManager@: "+admobInterstitialAdId );
        Log.e("TAG", "TART_InterstitialAdManager@: "+fbInterstitialAdId );
        Log.e("TAG", "TART_InterstitialAdManager@@: "+preferenceClass.getDataType("GoogleInterstitialAd"));
        Log.e("TAG", "TART_InterstitialAdManager@@: "+preferenceClass.getDataType("FbInterstitialAd"));
        fetchAdMobAd();
     //   fetchFbAd();
    }

    private void fetchFbAd() {

        fbInterstitialAd = new com.facebook.ads.InterstitialAd(context, fbInterstitialAdId);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                fetchAdMobAd();
                fetchFbAd();
                if (onAdLoadInterface != null) {
                    onAdLoadInterface.onAdClose();
                }
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                isFailed = true;
            }

            @Override
            public void onAdLoaded(Ad ad) {
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        fbInterstitialAd.loadAd(fbInterstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());

    }

    public void fetchAdMobAd() {

        if (isAdmobAdAvailable()) {
            return;
        }

        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                admobInterstitialAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                fetchFbAd();
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

    public boolean isFbAdAvailable() {
        return fbInterstitialAd != null && fbInterstitialAd.isAdLoaded() && !fbInterstitialAd.isAdInvalidated();
    }

        public void showAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        if (isFailed) {
            isFailed = false;
            fetchAdMobAd();
            onAdLoadInterface.onAdClose();
            return;
        }

        int interstitalAdStatus = preferenceClass.getAdsStatus("InerstialClickCount");

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
        } else if(isFbAdAvailable()) {
            fbInterstitialAd.show();
        } else {
            onAdLoadInterface.onAdClose();
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
//            if (isFbAdAvailable()) {
//                fbInterstitialAd.show();
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
//            } else if (isFbAdAvailable()) {
//                fbInterstitialAd.show();
//            } else {
//                onAdLoadInterface.onAdClose();
//            }
//        }
//    }

    public void showInterstitialAd(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        if (isFailed) {
            isFailed = false;
            fetchAdMobAd();
            onAdLoadInterface.onAdClose();
            return;
        }
        int interstitalAdStatus = preferenceClass.getAdsStatus("InerstialClickCount");

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
        } else if (isFbAdAvailable()) {
            fbInterstitialAd.show();
        } else {
            onAdLoadInterface.onAdClose();
        }

    }

    public void showFaceBookInterstitial(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;
        if (isFbAdAvailable()) {
            fbInterstitialAd.show();
        } else {
            onAdLoadInterface.onAdClose();
        }

    }

    public void showEDitAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        if (isFailed) {
            isFailed = false;
            fetchAdMobAd();
            onAdLoadInterface.onAdClose();
            return;
        }

        int interstitalAdStatus = preferenceClass.getAdsStatus("EditScreenAdCount");

        int getClickCount = preferenceClass.getInt("getEDitClickCount");
        if (getClickCount < interstitalAdStatus) {
            preferenceClass.setInt("getEDitClickCount", getClickCount + 1);
            onAdLoadInterface.onAdClose();
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
        } else if (isFbAdAvailable()) {
            fbInterstitialAd.show();
        } else {
            onAdLoadInterface.onAdClose();
        }

    }

    public interface OnAdLoadInterface {
        void onAdClose();
    }

}
