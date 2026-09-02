package com.addtext.textonphoto.textart.TART_features.TART_crop.TART_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.steelkiwi.cropiwa.AspectRatio;

import java.util.Arrays;
import java.util.List;

public class TART_AspectRatioPreviewAdapter extends RecyclerView.Adapter<TART_AspectRatioPreviewAdapter.ViewHolder> {
    public int lastSelectedView;
    public OnNewSelectedListener listener;
    public List<TART_AspectRatioCustom> ratios;
    public TART_AspectRatioCustom selectedRatio;

    public interface OnNewSelectedListener {
        void onNewAspectRatioSelected(AspectRatio aspectRatio);
    }

    public TART_AspectRatioPreviewAdapter() {
        this.ratios = Arrays.asList(new TART_AspectRatioCustom[]{new TART_AspectRatioCustom(10, 10, R.drawable.knack_crop_free, R.drawable.knack_crop_free_click), new TART_AspectRatioCustom(1, 1, R.drawable.knack_ratio_1_1, R.drawable.knack_ratio_1_1_click), new TART_AspectRatioCustom(4, 3, R.drawable.knack_ratio_4_3, R.drawable.knack_ratio_4_3_click), new TART_AspectRatioCustom(3, 4, R.drawable.knack_ratio_3_4, R.drawable.knack_ratio_3_4_click), new TART_AspectRatioCustom(5, 4, R.drawable.knack_ratio_5_4, R.drawable.knack_ratio_5_4_click), new TART_AspectRatioCustom(4, 5, R.drawable.knack_ratio_4_5, R.drawable.knack_ratio_4_5_click), new TART_AspectRatioCustom(3, 2, R.drawable.knack_ratio_3_2, R.drawable.knack_ratio_3_2_click), new TART_AspectRatioCustom(2, 3, R.drawable.knack_ratio_2_3, R.drawable.knack_ratio_2_3_click), new TART_AspectRatioCustom(9, 16, R.drawable.knack_ratio_9_16, R.drawable.knack_ratio_9_16_click), new TART_AspectRatioCustom(16, 9, R.drawable.knack_ratio_16_9, R.drawable.knack_ratio_16_9_click)});
        this.selectedRatio = this.ratios.get(0);
    }

    public TART_AspectRatioPreviewAdapter(boolean z) {
        this.ratios = Arrays.asList(new TART_AspectRatioCustom[]{new TART_AspectRatioCustom(1, 1, R.drawable.knack_ratio_1_1, R.drawable.knack_ratio_1_1_click), new TART_AspectRatioCustom(4, 3, R.drawable.knack_ratio_4_3, R.drawable.knack_ratio_4_3_click), new TART_AspectRatioCustom(3, 4, R.drawable.knack_ratio_3_4, R.drawable.knack_ratio_3_4_click), new TART_AspectRatioCustom(5, 4, R.drawable.knack_ratio_5_4, R.drawable.knack_ratio_5_4_click), new TART_AspectRatioCustom(4, 5, R.drawable.knack_ratio_4_5, R.drawable.knack_ratio_4_5_click), new TART_AspectRatioCustom(3, 2, R.drawable.knack_ratio_3_2, R.drawable.knack_ratio_3_2_click), new TART_AspectRatioCustom(2, 3, R.drawable.knack_ratio_2_3, R.drawable.knack_ratio_2_3_click), new TART_AspectRatioCustom(9, 16, R.drawable.knack_ratio_9_16, R.drawable.knack_ratio_9_16_click), new TART_AspectRatioCustom(16, 9, R.drawable.knack_ratio_16_9, R.drawable.knack_ratio_16_9_click)});
        this.selectedRatio = this.ratios.get(0);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_aspect_ratio, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TART_AspectRatioCustom aspectRatioCustom = this.ratios.get(i);
        if (i == this.lastSelectedView) {
            // સિલેક્ટ થાય ત્યારે ઓરેન્જ બેકગ્રાઉન્ડ અને સફેદ આઇકન
            viewHolder.ratioView.setImageResource(aspectRatioCustom.getUnselectItem());
            viewHolder.ratioView.setBackgroundResource(R.drawable.bg_btn_orange_pill);
            viewHolder.ratioView.setColorFilter(android.graphics.Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
            
            // પેડિંગ સેટ કરો જેથી આઇકન વ્યવસ્થિત દેખાય
            int padding = viewHolder.ratioView.getContext().getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp);
            viewHolder.ratioView.setPadding(padding, padding, padding, padding);
        } else {
            // સિલેક્ટ ન હોય ત્યારે નોર્મલ દેખાવ
            viewHolder.ratioView.setImageResource(aspectRatioCustom.getUnselectItem());
            viewHolder.ratioView.setBackgroundResource(0); // બેકગ્રાઉન્ડ કાઢી નાખો
            viewHolder.ratioView.clearColorFilter();
            viewHolder.ratioView.setPadding(0, 0, 0, 0);
        }
    }


    public void setLastSelectedView(int i) {
        this.lastSelectedView = i;
    }

    public int getItemCount() {
        return this.ratios.size();
    }

    public void setListener(OnNewSelectedListener onNewSelectedListener) {
        this.listener = onNewSelectedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ratioView;

        public ViewHolder(View view) {
            super(view);
            this.ratioView = view.findViewById(R.id.aspect_ratio_preview);
            this.ratioView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (TART_AspectRatioPreviewAdapter.this.lastSelectedView != getAdapterPosition()) {
                TART_AspectRatioPreviewAdapter.this.selectedRatio = TART_AspectRatioPreviewAdapter.this.ratios.get(getAdapterPosition());
                TART_AspectRatioPreviewAdapter.this.lastSelectedView = getAdapterPosition();
                if (TART_AspectRatioPreviewAdapter.this.listener != null) {
                    TART_AspectRatioPreviewAdapter.this.listener.onNewAspectRatioSelected(TART_AspectRatioPreviewAdapter.this.selectedRatio);
                }
                TART_AspectRatioPreviewAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
