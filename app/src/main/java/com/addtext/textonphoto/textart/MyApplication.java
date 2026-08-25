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
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showAdIfAvailable(activity, onAdLoadInterface);
    }
    public static void showInterstitialAdWithOutCount(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showInterstitialAd(activity, onAdLoadInterface);
    }
    public static void showFaceBookInterstitial(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showFaceBookInterstitial(activity, onAdLoadInterface);
    }
    public static void showEditInterstitialAd(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showEDitAdIfAvailable(activity, onAdLoadInterface);
    }

    public TART_InterstitialAdManager getInterstitialAdManager() {
        if (interstitialAdManager == null) {
            interstitialAdManager = new TART_InterstitialAdManager(this);
        }
        return interstitialAdManager;
    }

    public void loadInterstitialAd() {
        if (BuildConfig.DEBUG) return;
        if (interstitialAdManager == null)
            interstitialAdManager = new TART_InterstitialAdManager(MyApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        if (!BuildConfig.DEBUG) {
            AudienceNetworkAds.initialize(this);
            MobileAds.initialize(this, initializationStatus -> Log.d(" AD", " poster open ad"));
            appOpenManager = new TART_AppOpenManager(this);
        }
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
        if (BuildConfig.DEBUG || appOpenManager == null) {
            if (onShowAdCompleteListener != null) onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        appOpenManager.showAdIfSplashAvailable(activity, onShowAdCompleteListener);
    }

    public void showAdIfHomeAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (BuildConfig.DEBUG || appOpenManager == null) {
            if (onShowAdCompleteListener != null) onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        appOpenManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    public void sendRequest() {
        if (BuildConfig.DEBUG || appOpenManager == null) return;
        appOpenManager.sendRequest();
    }

    public boolean isAdAvailable() {
        if (BuildConfig.DEBUG || appOpenManager == null) return false;
        return appOpenManager.isAdAvailable();
    }



}