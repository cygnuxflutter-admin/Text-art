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
import com.google.android.gms.ads.nativead.MediaView;
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

    public static void loadNativeAd(RelativeLayout nativeAdContainer, Activity context) {
        if (nativeAdContainer == null || context == null) return;
        if (com.addtext.textonphoto.textart.BuildConfig.DEBUG) {
            nativeAdContainer.removeAllViews();
            nativeAdContainer.setVisibility(View.GONE);
            return;
        }
        try {
            TART_PreferenceClass pref = new TART_PreferenceClass(context);
            String adId = pref.getAdsId("GoogleNativeAd");
            String fbAdId = pref.getAdsId("FbNativeAd");
            if ((adId == null || adId.trim().isEmpty()) && (fbAdId == null || fbAdId.trim().isEmpty())) {
                nativeAdContainer.setVisibility(View.GONE);
                return;
            }
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(getLoadingView(context));
            nativeAdContainer.setVisibility(View.VISIBLE);
            TART_NativeAdUtil nativeAdUtil = new TART_NativeAdUtil(context);
            nativeAdUtil.fillAdmobNativeAd(nativeAdContainer);
        } catch (Exception e) {
            e.printStackTrace();
            if (nativeAdContainer != null) {
                nativeAdContainer.setVisibility(View.GONE);
            }
        }
    }

    private static View getLoadingView(Activity context) {
        View adView = LayoutInflater.from(context).inflate(R.layout.knack_native_ad_layout_loading, null);
        ShimmerFrameLayout shimmerLayout = adView.findViewById(R.id.shimmerLayout);
        if (shimmerLayout != null) {
            shimmerLayout.startShimmer();
            shimmerLayout.setVisibility(View.VISIBLE);
        }
        return adView;
    }

    public void fillAdmobNativeAd(final RelativeLayout nativeAdContainer) {
        try {
            String adUnitId = preferenceClass != null ? preferenceClass.getAdsId("GoogleNativeAd") : null;
            if (adUnitId == null || adUnitId.trim().isEmpty()) {
                fbNativeAd(nativeAdContainer);
                return;
            }

            AdLoader.Builder builder = new AdLoader.Builder(context, adUnitId);

            builder.forNativeAd(nativeAd -> {
                try {
                    if (this.nativeAd != null) {
                        this.nativeAd.destroy();
                    }
                    this.nativeAd = nativeAd;
                    adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.knack_native_ad_layout, null);
                    populateUnifiedNativeAdView(nativeAd, adView);
                    if (nativeAdContainer != null) {
                        nativeAdContainer.removeAllViews();
                        nativeAdContainer.addView(adView);
                        nativeAdContainer.setBackgroundColor(Color.parseColor("#151515"));
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
                    fbNativeAd(nativeAdContainer);
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
            fbNativeAd(nativeAdContainer);
        }
    }

    private void fbNativeAd(final RelativeLayout nativeAdContainer) {
        try {
            String fbAdId = preferenceClass != null ? preferenceClass.getAdsId("FbNativeAd") : null;
            if (fbAdId == null || fbAdId.trim().isEmpty()) {
                if (nativeAdContainer != null) nativeAdContainer.setVisibility(View.GONE);
                return;
            }

            com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(context, fbAdId);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (nativeAdContainer != null) {
                        nativeAdContainer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    try {
                        if (nativeAd != ad) {
                            return;
                        }
                        if (nativeAdContainer != null) {
                            nativeAdContainer.removeAllViews();
                            View adView = com.facebook.ads.NativeAdView.render(context, nativeAd);
                            nativeAdContainer.addView(adView);
                            nativeAdContainer.setBackgroundColor(Color.parseColor("#151515"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        } catch (Exception e) {
            e.printStackTrace();
            if (nativeAdContainer != null) {
                nativeAdContainer.setVisibility(View.GONE);
            }
        }
    }

    public void populateUnifiedNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        RelativeLayout relativeLayout = unifiedNativeAdView.findViewById(R.id.parentLyt);

        /*if (width != -1 && height != -1) {
            relativeLayout.getLayoutParams().width = width;
            relativeLayout.getLayoutParams().height = 300;
            relativeLayout.invalidate();
        }*/

        MediaView mediaView = unifiedNativeAdView.findViewById(R.id.ad_media);
        unifiedNativeAdView.setMediaView(mediaView);

        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));

        ImageView imageView = unifiedNativeAdView.findViewById(R.id.unified_image_view);

        populateNativeAdView(unifiedNativeAd, unifiedNativeAdView, mediaView, imageView);
    }

    private void populateNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView, MediaView mediaView, ImageView imageView) {
        int i = 0;
       /* MediaContent mediaContent = unifiedNativeAd.getMediaContent();
        if (mediaContent != null) {
            boolean hasVideo = mediaContent.getVideoController().hasVideoContent();
            if (hasVideo) {

                unifiedNativeAdView.setMediaView(mediaView);
                imageView.setVisibility(View.GONE);
            } else {
                unifiedNativeAdView.setImageView(imageView);
                mediaView.setVisibility(View.GONE);
                List<NativeAd.Image> images = unifiedNativeAd.getImages();
                if (images.size() > 0) {
                    while (true) {
                        if (i >= images.size()) {
                            break;
                        }
                        NativeAd.Image image = images.get(i);
                        if (image != null) {
                            Drawable drawable = image.getDrawable();
                            imageView.setImageDrawable(drawable);
                            break;
                        }
                        i++;
                    }
                }
            }
        } else {
            unifiedNativeAdView.setImageView(imageView);
            mediaView.setVisibility(View.GONE);
            List<NativeAd.Image> images = unifiedNativeAd.getImages();
            if (images.size() > 0) {
                while (true) {
                    if (i >= images.size()) {
                        break;
                    }
                    NativeAd.Image image = images.get(i);
                    if (image != null) {
                        Drawable drawable = image.getDrawable();
                        imageView.setImageDrawable(drawable);
                        break;
                    }
                    i++;
                }
            }
        }*/

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
