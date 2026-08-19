package com.addtext.textonphoto.textart;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.adManager.TART_AppOpenManager;
import com.addtext.textonphoto.textart.adManager.TART_InterstitialAdManager;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;


public class MyApplication extends android.app.Application {

    public static boolean isShowingAppOpen = true, isAdsSplash = true;
    public TART_AppOpenManager appOpenManager;
    private TART_InterstitialAdManager interstitialAdManager;
    public static MyApplication mInstance;

    public static void showInterstitialAd(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showAdIfAvailable(activity, onAdLoadInterface);
    }
    public static void showInterstitialAdWithOutCount(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showInterstitialAd(activity, onAdLoadInterface);
    }
    public static void showFaceBookInterstitial(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showFaceBookInterstitial(activity, onAdLoadInterface);
    }
    public static void showEditInterstitialAd(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showEDitAdIfAvailable(activity, onAdLoadInterface);
    }

    public TART_InterstitialAdManager getInterstitialAdManager() {
        if (interstitialAdManager == null) {
            interstitialAdManager = new TART_InterstitialAdManager(this);
        }
        return interstitialAdManager;
    }

    public void loadInterstitialAd() {
        if (interstitialAdManager == null)
            interstitialAdManager = new TART_InterstitialAdManager(MyApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId("83d4adaf-4ae7-4f59-b91a-d0050698af6a");
//        OneSignal.promptForPushNotifications();
//        OneSignal.sendTag("Apps", "Text Art");

        AudienceNetworkAds.initialize(this);

//        List<String> testDeviceIds = Collections.singletonList("9EB1C89D5458256B2C93F844BAAC93F5");
//        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
//        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(this, initializationStatus -> Log.d(" AD", " poster open ad"));
        appOpenManager = new TART_AppOpenManager(this);

    }

    public static synchronized MyApplication getInstance() {
        MyApplication myApp;
        synchronized (MyApplication.class) {
            myApp = mInstance;
        }
        return myApp;
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenManager.showAdIfSplashAvailable(activity, onShowAdCompleteListener);
    }

    public void showAdIfHomeAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    public void sendRequest() {
        appOpenManager.sendRequest();
    }

    public boolean isAdAvailable() {
        return appOpenManager.isAdAvailable();
    }



}