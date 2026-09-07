package com.addtext.textonphoto.textart;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.adManager.TART_AppOpenManager;
import com.addtext.textonphoto.textart.adManager.TART_InterstitialAdManager;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;


public class MyApplication extends android.app.Application {

    public static boolean isShowingAppOpen = true, isAdsSplash = true;
    public TART_AppOpenManager appOpenManager;
    private TART_InterstitialAdManager interstitialAdManager;
    public static MyApplication mInstance;

    public static void showInterstitialAd(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        com.addtext.textonphoto.textart.TART_utils.TART_LoadingDialog dialog = new com.addtext.textonphoto.textart.TART_utils.TART_LoadingDialog(activity);
        dialog.show();
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                ((MyApplication) activity.getApplication()).getInterstitialAdManager().showAdIfAvailable(activity, onAdLoadInterface);
            }
        }, 800);
    }
    public static void showInterstitialAdWithOutCount(Activity activity, TART_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showInterstitialAd(activity, onAdLoadInterface);
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

        try {
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            OneSignal.initWithContext(this);
            OneSignal.setAppId("abd91acb-cb03-410a-80c3-3ab7f58e7734");
            OneSignal.promptForPushNotifications();
            OneSignal.sendTag("Apps", "Text Art");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MobileAds.initialize(this, initializationStatus -> Log.d(" AD", " Google MobileAds initialized"));
        appOpenManager = new TART_AppOpenManager(this);
        startNetworkCallback();
        registerActivityLifecycleCallbacks(new android.app.Application.ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(@NonNull Activity activity, android.os.Bundle savedInstanceState) {}
            @Override public void onActivityStarted(@NonNull Activity activity) {}
            
            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                currentActivity = activity;
                if (!(activity instanceof com.addtext.textonphoto.textart.TART_screens.TART_NoInternetActivity)
                        && !(activity instanceof com.addtext.textonphoto.textart.TART_screens.TART_SplashActivity)) {
                    if (!com.addtext.textonphoto.textart.TART_utils.TART_NetworkUtils.isNetworkAvailable(activity)) {
                        if (!com.addtext.textonphoto.textart.TART_screens.TART_NoInternetActivity.isVisible) {
                            android.content.Intent intent = new android.content.Intent(activity, com.addtext.textonphoto.textart.TART_screens.TART_NoInternetActivity.class);
                            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            activity.startActivity(intent);
                        }
                    }
                }
            }
            
            @Override public void onActivityPaused(@NonNull Activity activity) {
                if (currentActivity == activity) currentActivity = null;
            }
            @Override public void onActivityStopped(@NonNull Activity activity) {}
            @Override public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull android.os.Bundle outState) {}
            @Override public void onActivityDestroyed(@NonNull Activity activity) {}
        });
    }
    private static Activity currentActivity = null;

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    private void startNetworkCallback() {
        android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            android.net.NetworkRequest.Builder builder = new android.net.NetworkRequest.Builder();
            connectivityManager.registerNetworkCallback(builder.build(), new android.net.ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull android.net.Network network) {
                    super.onAvailable(network);
                    android.content.Intent intent = new android.content.Intent("com.addtext.textonphoto.textart.NETWORK_RESTORED");
                    intent.setPackage(getPackageName());
                    sendBroadcast(intent);
                }

                @Override
                public void onLost(@NonNull android.net.Network network) {
                    super.onLost(network);
                    Activity curr = currentActivity;
                    if (curr instanceof com.addtext.textonphoto.textart.TART_screens.TART_SplashActivity) return;
                    if (!com.addtext.textonphoto.textart.TART_screens.TART_NoInternetActivity.isVisible) {
                        android.content.Intent intent = new android.content.Intent(MyApplication.this, com.addtext.textonphoto.textart.TART_screens.TART_NoInternetActivity.class);
                        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }
            });
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
        if (appOpenManager == null) {
            if (onShowAdCompleteListener != null) onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        appOpenManager.showAdIfSplashAvailable(activity, onShowAdCompleteListener);
    }

    public void showAdIfHomeAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (appOpenManager == null) {
            if (onShowAdCompleteListener != null) onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        appOpenManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    public void sendRequest() {
        if (appOpenManager == null) return;
        appOpenManager.sendRequest();
    }

    public boolean isAdAvailable() {
        if (appOpenManager == null) return false;
        return appOpenManager.isAdAvailable();
    }



}