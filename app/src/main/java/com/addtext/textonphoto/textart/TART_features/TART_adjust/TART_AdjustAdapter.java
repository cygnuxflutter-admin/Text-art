package com.addtext.textonphoto.textart.TART_features.TART_adjust;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.TART_photoeditor.TART_PhotoEditor;
import com.addtext.textonphoto.textart.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TART_AdjustAdapter extends RecyclerView.Adapter<TART_AdjustAdapter.ViewHolder> {
    public String FILTER_CONFIG_TEMPLATE = "@adjust brightness 0 @adjust contrast 1 @adjust saturation 1 @adjust sharpen 0";
    public TART_AdjustListener adjustListener;
    private Context context;
    public List<AdjustModel> lstAdjusts;
    public int selectedFilterIndex = 0;

    public TART_AdjustAdapter(Context context2, TART_AdjustListener adjustListener2) {
        this.context = context2;
        this.adjustListener = adjustListener2;
        init();
    }

    public void setSelectedAdjust(int i) {
        this.adjustListener.onAdjustSelected(this.lstAdjusts.get(i));
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_row_adjust_view, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.toolName.setText(this.lstAdjusts.get(i).name);
        viewHolder.icon.setImageDrawable(this.selectedFilterIndex != i ? this.lstAdjusts.get(i).icon : this.lstAdjusts.get(i).selectedIcon);
        if (this.selectedFilterIndex == i) {
            viewHolder.toolName.setTextColor(ContextCompat.getColor(context, R.color.selected));
        } else {
            viewHolder.toolName.setTextColor(ContextCompat.getColor(context, R.color.unselected_color));
        }
    }

    public int getItemCount() {
        return this.lstAdjusts.size();
    }

    public String getFilterConfig() {
        String str = this.FILTER_CONFIG_TEMPLATE;
        return MessageFormat.format(str, this.lstAdjusts.get(0).originValue + "", this.lstAdjusts.get(1).originValue + "", this.lstAdjusts.get(2).originValue + "", Float.valueOf(this.lstAdjusts.get(3).originValue));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView toolName;

        ViewHolder(View view) {
            super(view);
            this.icon = view.findViewById(R.id.icon);
            this.toolName = view.findViewById(R.id.tool_name);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TART_AdjustAdapter.this.selectedFilterIndex = ViewHolder.this.getLayoutPosition();
                    TART_AdjustAdapter.this.adjustListener.onAdjustSelected(TART_AdjustAdapter.this.lstAdjusts.get(TART_AdjustAdapter.this.selectedFilterIndex));
                    TART_AdjustAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public AdjustModel getCurrentAdjustModel() {
        return this.lstAdjusts.get(this.selectedFilterIndex);
    }

    private void init() {
        this.lstAdjusts = new ArrayList();
        this.lstAdjusts.add(new AdjustModel(0, this.context.getString(R.string.brightness), "brightness", this.context.getDrawable(R.drawable.knack_brightness), this.context.getDrawable(R.drawable.knack_brightness_selected), -1.0f, 0.0f, 1.0f));
        this.lstAdjusts.add(new AdjustModel(1, this.context.getString(R.string.contrast), "contrast", this.context.getDrawable(R.drawable.knack_contrast), this.context.getDrawable(R.drawable.knack_contrast_selected), 0.1f, 1.0f, 3.0f));
        this.lstAdjusts.add(new AdjustModel(2, this.context.getString(R.string.saturation), "saturation", this.context.getDrawable(R.drawable.knack_saturation), this.context.getDrawable(R.drawable.knack_saturation_selected), 0.0f, 1.0f, 3.0f));
        this.lstAdjusts.add(new AdjustModel(3, this.context.getString(R.string.sharpen), "sharpen", this.context.getDrawable(R.drawable.knack_sharpen), this.context.getDrawable(R.drawable.knack_sharpen_selected), -1.0f, 0.0f, 10.0f));
    }

    public class AdjustModel {
        String code;
        Drawable icon;
        public int index;
        public float intensity;
        public float maxValue;
        public float minValue;
        public String name;
        public float originValue;
        Drawable selectedIcon;
        public float slierIntensity = 0.5f;

        AdjustModel(int i, String str, String str2, Drawable drawable, Drawable drawable2, float f, float f2, float f3) {
            this.index = i;
            this.name = str;
            this.code = str2;
            this.icon = drawable;
            this.minValue = f;
            this.originValue = f2;
            this.maxValue = f3;
            this.selectedIcon = drawable2;
        }

        public void setIntensity(TART_PhotoEditor photoEditor, float f, boolean z) {
            if (photoEditor != null) {
                this.slierIntensity = f;
                this.intensity = calcIntensity(f);
                photoEditor.setFilterIntensityForIndex(this.intensity, this.index, z);
            }
        }


        public float calcIntensity(float f) {
            if (f <= 0.0f) {
                return this.minValue;
            }
            if (f >= 1.0f) {
                return this.maxValue;
            }
            if (f <= 0.5f) {
                return this.minValue + ((this.originValue - this.minValue) * f * 2.0f);
            }
            return this.maxValue + ((this.originValue - this.maxValue) * (1.0f - f) * 2.0f);
        }
    }
}
