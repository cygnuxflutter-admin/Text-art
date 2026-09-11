package com.addtext.textonphoto.textart.adManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

public class TART_NativeAdUtil {

    private final Context context;
    private final TART_PreferenceClass preferenceClass;
    private final int width;
    private final int height;
    private NativeAdView adView;
    private NativeAd nativeAd;

    public TART_NativeAdUtil(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.preferenceClass = new TART_PreferenceClass(context);
    }

    public TART_NativeAdUtil(Context context) {
        this.context = context;
        this.width = -1;
        this.height = -1;
        this.preferenceClass = new TART_PreferenceClass(context);
    }

        private static java.util.WeakHashMap<android.view.ViewGroup, NativeAd> activeNativeAds = new java.util.WeakHashMap<>();

    public static void loadNativeAd(android.view.ViewGroup nativeAdContainer, Activity context) {
        loadNativeAd(nativeAdContainer, context, false);
    }

    public static void loadNativeAd(android.view.ViewGroup nativeAdContainer, Activity context, boolean collapseOnFail) {
        if (nativeAdContainer == null || context == null) return;
        try {
            TART_PreferenceClass pref = new TART_PreferenceClass(context);
            String adId = pref.getAdsId("GoogleNativeAd");
            String fbAdId = pref.getAdsId("FbNativeAd");
            if ((adId == null || adId.trim().isEmpty()) && (fbAdId == null || fbAdId.trim().isEmpty())) {
                nativeAdContainer.setVisibility(collapseOnFail ? View.GONE : View.INVISIBLE);
                return;
            }
                        if (activeNativeAds.containsKey(nativeAdContainer)) {
                NativeAd oldAd = activeNativeAds.get(nativeAdContainer);
                if (oldAd != null) {
                    oldAd.destroy();
                }
                activeNativeAds.remove(nativeAdContainer);
            }
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(getLoadingView(context, nativeAdContainer));
            nativeAdContainer.setVisibility(View.VISIBLE);
            TART_NativeAdUtil nativeAdUtil = new TART_NativeAdUtil(context);
            nativeAdUtil.fillAdmobNativeAd(nativeAdContainer, collapseOnFail);
        } catch (Exception e) {
            e.printStackTrace();
            if (nativeAdContainer != null) {
                nativeAdContainer.setVisibility(collapseOnFail ? View.GONE : View.INVISIBLE);
            }
        }
    }

    private static View getLoadingView(Activity context, android.view.ViewGroup parent) {
        View adView = LayoutInflater.from(context).inflate(R.layout.knack_native_ad_layout_loading, parent, false);
        ShimmerFrameLayout shimmerLayout = adView.findViewById(R.id.shimmerLayout);
        if (shimmerLayout != null) {
            shimmerLayout.startShimmer();
            shimmerLayout.setVisibility(View.VISIBLE);
        }
        return adView;
    }

    public void fillAdmobNativeAd(final android.view.ViewGroup nativeAdContainer, final boolean collapseOnFail) {
        try {
            String adUnitId = preferenceClass != null ? preferenceClass.getAdsId("GoogleNativeAd") : null;
            android.util.Log.e("ADMOB_DEBUG_LOG", "=== NATIVE REQUEST with ID: [" + adUnitId + "] ===");
            if (adUnitId == null || adUnitId.trim().isEmpty()) {
                android.util.Log.e("ADMOB_DEBUG_LOG", "Native ID is EMPTY in SharedPreferences! Skipping Google Native.");
                if (nativeAdContainer != null) nativeAdContainer.setVisibility(collapseOnFail ? View.GONE : View.INVISIBLE);
                return;
            }

            AdLoader.Builder builder = new AdLoader.Builder(context, adUnitId);

            builder.forNativeAd(nativeAd -> {
                try {
                    android.util.Log.e("ADMOB_DEBUG_LOG", ">>> NATIVE AD LOADED SUCCESSFULLY!");
                    if (this.nativeAd != null) {
                        this.nativeAd.destroy();
                    }
                    this.nativeAd = nativeAd;
                    adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.knack_native_ad_layout, nativeAdContainer, false);
                    populateUnifiedNativeAdView(nativeAd, adView);
                    if (nativeAdContainer != null) {
                        nativeAdContainer.removeAllViews();
                        nativeAdContainer.addView(adView);
                        nativeAdContainer.setBackgroundColor(Color.TRANSPARENT);
                        nativeAdContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    android.util.Log.e("ADMOB_DEBUG_LOG", ">>> NATIVE AD FAILED TO LOAD!");
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Message: " + loadAdError.getMessage());
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Code: " + loadAdError.getCode() + " (0=Internal, 1=InvalidRequest, 2=Network, 3=NoFill)");
                    android.util.Log.e("ADMOB_DEBUG_LOG", "Error Domain: " + loadAdError.getDomain());
                    // Box ane shimmer loader visible rakho — hide nahi karo
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
            // Box ane shimmer loader visible rakho — hide nahi karo
        }
    }

    public void populateUnifiedNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));

        populateNativeAdView(unifiedNativeAd, unifiedNativeAdView);
    }

    private void populateNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        if (unifiedNativeAd.getIcon() != null) {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        } else {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        }

        TextView headlineView = (TextView) unifiedNativeAdView.getHeadlineView();
        if (headlineView != null) {
            headlineView.setText(unifiedNativeAd.getHeadline());
        }

        View bodyView = unifiedNativeAdView.getBodyView();
        if (unifiedNativeAd.getBody() == null) {
            if (bodyView != null) {
                bodyView.setVisibility(View.INVISIBLE);
            }
        } else {
            if (bodyView != null) {
                bodyView.setVisibility(View.VISIBLE);
                ((TextView) bodyView).setText(unifiedNativeAd.getBody());
            }
        }

        Button callToActionView = (Button) unifiedNativeAdView.getCallToActionView();
        if (callToActionView != null) {
            callToActionView.setText(unifiedNativeAd.getCallToAction());
        }

        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }
}
