package com.addtext.textonphoto.textart.TART_features.TART_splash;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.adManager.TART_RewardVideoManager;
import com.addtext.textonphoto.textart.TART_utils.TART_MaterialDialogUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.addtext.textonphoto.textart.Constants;
import com.addtext.textonphoto.textart.TART_sticker.TART_SplashSticker;
import com.addtext.textonphoto.textart.TART_utils.TART_AssetUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.List;

public class TART_SplashAdapter extends RecyclerView.Adapter<TART_SplashAdapter.ViewHolder> {
    private int borderWidth;
    private Activity context;
    private String premiumType = "";
    public int selectedSquareIndex;

    public SplashChangeListener splashChangeListener;

    public List<SplashItem> splashList = new ArrayList();

    interface SplashChangeListener {
        void onSelected(TART_SplashSticker splashSticker);
    }

    TART_SplashAdapter(Activity context2, SplashChangeListener splashChangeListener2, boolean z) {
        this.context = context2;
        this.splashChangeListener = splashChangeListener2;
        this.borderWidth = TART_SystemUtil.dpToPx(context2, Constants.BORDER_WIDTH_DP);
        if (z) {
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask1.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame1.webp")), R.drawable.knack_splash01, false));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask2.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame2.webp")), R.drawable.knack_splash02, false));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask3.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame3.webp")), R.drawable.knack_splash03, false));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask4.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame4.webp")), R.drawable.knack_splash04, false));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask5.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame5.webp")), R.drawable.knack_splash05, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask6.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame6.webp")), R.drawable.knack_splash06, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask7.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame7.webp")), R.drawable.knack_splash07, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask8.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame8.webp")), R.drawable.knack_splash08, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask9.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame9.webp")), R.drawable.knack_splash09, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask11.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame11.webp")), R.drawable.knack_splash11, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask12.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame12.webp")), R.drawable.knack_splash12, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask14.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame14.webp")), R.drawable.knack_splash14, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask17.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame17.webp")), R.drawable.knack_splash17, true));
            this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/mask18.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "splash/icons/frame18.webp")), R.drawable.knack_splash18, true));
            return;
        }
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_1_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_1_shadow.webp")), R.drawable.knack_blur_1, false));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_2_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_2_shadow.webp")), R.drawable.knack_blur_2, false));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_3_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_3_shadow.webp")), R.drawable.knack_blur_3, false));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_4_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_4_shadow.webp")), R.drawable.knack_blur_4, true));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_5_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_5_shadow.webp")), R.drawable.knack_blur_5, true));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_7_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_7_shadow.webp")), R.drawable.knack_blur_7, true));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_8_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_8_shadow.webp")), R.drawable.knack_blur_8, true));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_9_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_9_shadow.webp")), R.drawable.knack_blur_9, true));
        this.splashList.add(new SplashItem(new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_10_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(context2, "blur/icons/blur_10_shadow.webp")), R.drawable.knack_blur_10, true));
//        initFilterView();

    }

//    private void initFilterView() {
//        filterModels = new ArrayList<>();
//        for (int i = 0; i < splashList.size(); i++) {
//            if (i >= 4) {
//                filterModels.add(new splashModel(true, splashList.get(i));
//            } else {
//                filterModels.add(new splashModel(false, bitmaps.get(i)));
//            }
//        }
//    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_splash_view, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.splash.setImageResource(this.splashList.get(i).drawableId);
        if (splashList.get(i).isPremium) {
            viewHolder.iv_premium.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_premium.setVisibility(View.GONE);
        }

        if (this.selectedSquareIndex == i) {
            viewHolder.splash.setBorderColor(ContextCompat.getColor(context, R.color.colorAccent));
            viewHolder.splash.setBorderWidth(this.borderWidth);
            return;
        }
        viewHolder.splash.setBorderColor(0);
        viewHolder.splash.setBorderWidth(this.borderWidth);
    }

    public int getItemCount() {
        return this.splashList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoundedImageView splash;
        public ImageView iv_premium;

        public ViewHolder(View view) {
            super(view);
            this.splash = view.findViewById(R.id.splash);
            this.iv_premium = view.findViewById(R.id.iv_premium);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {

            if (splashList.get(ViewHolder.this.getLayoutPosition()).isPremium) {

                TART_MaterialDialogUtils.getInstance().rewardDialog(context,
                        "Remove Premium(One Time)", "Remove Premium by watching Ads", materialDialog -> {
                            premiumType = "remPremium";
                            TART_PreferenceClass preferenceClass = new TART_PreferenceClass(context);
                            // if(preferenceClass.getDataType("PremiumAdType")!=null && preferenceClass.getDataType("PremiumAdType").equals("Reward")) {
                            TART_RewardVideoManager.showRewardVideoAd(context, new TART_RewardVideoManager.OnRewardAdLoadInterface() {
                                @Override
                                public void onAdClose(boolean isWithReward) {
                                    if (isWithReward) {
                                        splashList.get(ViewHolder.this.getLayoutPosition()).isPremium = false;
                                        TART_SplashAdapter.this.selectedSquareIndex = getAdapterPosition();
                                        if (TART_SplashAdapter.this.selectedSquareIndex < 0) {
                                            TART_SplashAdapter.this.selectedSquareIndex = 0;
                                        }
                                        if (TART_SplashAdapter.this.selectedSquareIndex >= TART_SplashAdapter.this.splashList.size()) {
                                            TART_SplashAdapter.this.selectedSquareIndex = TART_SplashAdapter.this.splashList.size() - 1;
                                        }
                                        TART_SplashAdapter.this.splashChangeListener.onSelected((TART_SplashAdapter.this.splashList.get(TART_SplashAdapter.this.selectedSquareIndex)).splashSticker);
                                        TART_SplashAdapter.this.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onAdFail() {
                                    TART_SplashAdapter.this.selectedSquareIndex = getAdapterPosition();
                                    if (TART_SplashAdapter.this.selectedSquareIndex < 0) {
                                        TART_SplashAdapter.this.selectedSquareIndex = 0;
                                    }
                                    if (TART_SplashAdapter.this.selectedSquareIndex >= TART_SplashAdapter.this.splashList.size()) {
                                        TART_SplashAdapter.this.selectedSquareIndex = TART_SplashAdapter.this.splashList.size() - 1;
                                    }
                                    TART_SplashAdapter.this.splashChangeListener.onSelected((TART_SplashAdapter.this.splashList.get(TART_SplashAdapter.this.selectedSquareIndex)).splashSticker);
                                    TART_SplashAdapter.this.notifyDataSetChanged();
                                }
                            });

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                        }, materialDialog -> {
                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                        });

            } else {
                TART_SplashAdapter.this.selectedSquareIndex = getAdapterPosition();
                if (TART_SplashAdapter.this.selectedSquareIndex < 0) {
                    TART_SplashAdapter.this.selectedSquareIndex = 0;
                }
                if (TART_SplashAdapter.this.selectedSquareIndex >= TART_SplashAdapter.this.splashList.size()) {
                    TART_SplashAdapter.this.selectedSquareIndex = TART_SplashAdapter.this.splashList.size() - 1;
                }
                TART_SplashAdapter.this.splashChangeListener.onSelected((TART_SplashAdapter.this.splashList.get(TART_SplashAdapter.this.selectedSquareIndex)).splashSticker);
                TART_SplashAdapter.this.notifyDataSetChanged();
            }
        }
    }

    class SplashItem {
        int drawableId;
        TART_SplashSticker splashSticker;
        Boolean isPremium;

        SplashItem(TART_SplashSticker splashSticker2, int i, Boolean isPremium) {
            this.splashSticker = splashSticker2;
            this.drawableId = i;
            this.isPremium = isPremium;
        }
    }
}
