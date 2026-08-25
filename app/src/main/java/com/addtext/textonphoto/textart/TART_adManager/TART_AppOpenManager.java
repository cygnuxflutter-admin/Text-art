package com.addtext.textonphoto.textart.adManager;

import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static com.google.android.gms.ads.appopen.AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT;
import static com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;
import static com.google.android.gms.ads.appopen.AppOpenAd.load;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.TART_screens.TART_SplashActivity;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class TART_AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private AppOpenAdLoadCallback loadCallback;
    private final MyApplication myApplication;
    private static boolean isShowingAd = false;
    private Activity currentActivity;
    private long loadTime = 0;
    private static TART_PreferenceClass preferenceClass;
    private String AD_UNIT_ID1;

    /**
     * Constructor
     */
    public TART_AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        if (com.addtext.textonphoto.textart.BuildConfig.DEBUG) {
            return;
        }
        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                TART_AppOpenManager.this.appOpenAd = ad;
                TART_AppOpenManager.this.loadTime = (new Date()).getTime();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                TART_AppOpenManager.this.appOpenAd = null;
            }
        };

        if (preferenceClass == null) {
            preferenceClass = new TART_PreferenceClass(myApplication);
        }
        AD_UNIT_ID1 = preferenceClass.getAdsId("GoogleAppopenAd");

        if (AD_UNIT_ID1 != null && !AD_UNIT_ID1.isEmpty()) {
            AdRequest request = getAdRequest();
            load(myApplication, AD_UNIT_ID1, request, APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
        }
    }

    public void fetchAdX() {
        fetchAd();
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        if (com.addtext.textonphoto.textart.BuildConfig.DEBUG) {
            return false;
        }
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void sendRequest() {
        fetchAd();
    }

    /**
     * Shows the ad if one isn't already showing and returning from background.
     */
    public void showAdIfAvailable() {
        if (com.addtext.textonphoto.textart.BuildConfig.DEBUG) {
            return;
        }
        if (!isShowingAd && isAdAvailable()) {
            if (MyApplication.isShowingAppOpen && currentActivity != null && !(currentActivity instanceof TART_SplashActivity)) {
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        TART_AppOpenManager.this.appOpenAd = null;
                        isShowingAd = false;
                        fetchAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        TART_AppOpenManager.this.appOpenAd = null;
                        isShowingAd = false;
                        fetchAd();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        isShowingAd = true;
                    }
                };

                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAd.show(currentActivity);
            }
        } else {
            fetchAd();
        }
    }

    public void showAdIfSplashAvailable(@NonNull final Activity activity, @NonNull MyApplication.OnShowAdCompleteListener onShowAdCompleteListener) {
        if (onShowAdCompleteListener != null) {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    public void showAdIfAvailable(@NonNull final Activity activity, @NonNull MyApplication.OnShowAdCompleteListener onShowAdCompleteListener) {
        if (onShowAdCompleteListener != null) {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    /**
     * ActivityLifecycleCallback methods
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (!MyApplication.isAdsSplash && currentActivity != null && !(currentActivity instanceof TART_SplashActivity)) {
            showAdIfAvailable();
        }
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }
}