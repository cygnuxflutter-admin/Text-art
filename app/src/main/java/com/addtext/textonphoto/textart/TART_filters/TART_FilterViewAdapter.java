package com.addtext.textonphoto.textart.TART_filters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.Constants;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.adManager.TART_RewardVideoManager;
import com.addtext.textonphoto.textart.TART_utils.TART_MaterialDialogUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class TART_FilterViewAdapter extends RecyclerView.Adapter<TART_FilterViewAdapter.ViewHolder> {
    private List<Bitmap> bitmaps;
    private List<TART_FilterModel> filterModels;
    private String premiumType = "";
    private int borderWidth;
    private Activity context;

    public List<TART_FilterUtils.FilterBean> filterEffects;

    public TART_FilterListener mFilterListener;

    public int selectedFilterIndex = 0;

    public TART_FilterViewAdapter(List<Bitmap> list, TART_FilterListener filterListener, Activity context2, List<TART_FilterUtils.FilterBean> list2) {
        this.mFilterListener = filterListener;
        this.bitmaps = list;
        this.context = context2;
        this.filterEffects = list2;
        this.borderWidth = TART_SystemUtil.dpToPx(context2, Constants.BORDER_WIDTH_DP);
        initFilterView();
    }

    private void initFilterView() {
        filterModels = new ArrayList<>();
        for (int i = 0; i < bitmaps.size(); i++) {
            if (i >= 4) {
                filterModels.add(new TART_FilterModel(true, bitmaps.get(i)));
            } else {
                filterModels.add(new TART_FilterModel(false, bitmaps.get(i)));
            }
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_row_filter_view, viewGroup, false));
    }

    public void reset() {
        this.selectedFilterIndex = 0;
        notifyDataSetChanged();
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = this.filterEffects.get(i).getName();
        if (name != null && !name.trim().isEmpty()) {
            viewHolder.mTxtFilterName.setText(name);
            viewHolder.mTxtFilterName.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTxtFilterName.setVisibility(View.GONE);
        }
        viewHolder.mImageFilterView.setImageBitmap(this.filterModels.get(i).getBitmap());
        if (filterModels.get(i).isPremium) {
            viewHolder.iv_premium.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_premium.setVisibility(View.GONE);
        }
        if (this.selectedFilterIndex == i) {
            viewHolder.mImageFilterView.setBorderColor(ContextCompat.getColor(context, R.color.brand_orange));
            viewHolder.mImageFilterView.setBorderWidth((float) this.borderWidth);
            viewHolder.mTxtFilterName.setTextColor(ContextCompat.getColor(context, R.color.brand_orange));
        } else {
            viewHolder.mImageFilterView.setBorderColor(0);
            viewHolder.mImageFilterView.setBorderWidth(0.0f);
            viewHolder.mTxtFilterName.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
        }
    }

    public int getItemCount() {
        return this.filterModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mImageFilterView;
        TextView mTxtFilterName;
        ConstraintLayout wrapFilterItem;
        ImageView iv_premium;

        ViewHolder(View view) {
            super(view);
            this.mImageFilterView = view.findViewById(R.id.imgFilterView);
            this.mTxtFilterName = view.findViewById(R.id.txtFilterName);
            this.wrapFilterItem = view.findViewById(R.id.wrapFilterItem);
            this.iv_premium = view.findViewById(R.id.iv_premium);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (filterModels.get(ViewHolder.this.getLayoutPosition()).isPremium) {
                        // Handle click event here
                        // Add your desired code logic for handling the click event
                        TART_MaterialDialogUtils.getInstance().rewardDialog(context,
                                "Remove Premium(One Time)", "Remove Premium by watching Ads", materialDialog -> {
                                    premiumType = "remPremium";
                                    TART_PreferenceClass preferenceClass = new TART_PreferenceClass(context);
                                    // if(preferenceClass.getDataType("PremiumAdType")!=null && preferenceClass.getDataType("PremiumAdType").equals("Reward")) {
                                    TART_RewardVideoManager.showRewardVideoAd(context, new TART_RewardVideoManager.OnRewardAdLoadInterface() {
                                        @Override
                                        public void onAdClose(boolean isWithReward) {
                                            if (isWithReward) {
                                                filterModels.get(ViewHolder.this.getLayoutPosition()).isPremium = false;
                                                TART_FilterViewAdapter.this.selectedFilterIndex = ViewHolder.this.getLayoutPosition();
                                                TART_FilterViewAdapter.this.mFilterListener.onFilterSelected(((TART_FilterUtils.FilterBean) TART_FilterViewAdapter.this.filterEffects.get(TART_FilterViewAdapter.this.selectedFilterIndex)).getConfig());
                                                TART_FilterViewAdapter.this.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onAdFail() {
                                            TART_FilterViewAdapter.this.selectedFilterIndex = ViewHolder.this.getLayoutPosition();
                                            TART_FilterViewAdapter.this.mFilterListener.onFilterSelected(((TART_FilterUtils.FilterBean) TART_FilterViewAdapter.this.filterEffects.get(TART_FilterViewAdapter.this.selectedFilterIndex)).getConfig());
                                            TART_FilterViewAdapter.this.notifyDataSetChanged();
                                        }
                                    });



                                    if (materialDialog != null && materialDialog.isShowing())
                                        materialDialog.dismiss();
                                }, materialDialog -> {
                                    if (materialDialog != null && materialDialog.isShowing())
                                        materialDialog.dismiss();
                                });

                    } else {
                        TART_FilterViewAdapter.this.selectedFilterIndex = ViewHolder.this.getLayoutPosition();
                        TART_FilterViewAdapter.this.mFilterListener.onFilterSelected(((TART_FilterUtils.FilterBean) TART_FilterViewAdapter.this.filterEffects.get(TART_FilterViewAdapter.this.selectedFilterIndex)).getConfig());
                        TART_FilterViewAdapter.this.notifyDataSetChanged();
                    }

                }
            });
        }
    }
}
