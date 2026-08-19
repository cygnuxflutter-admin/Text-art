package com.addtext.textonphoto.textart.TART_features.TART_draw;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.pavlospt.CircleView;
import com.addtext.textonphoto.textart.TART_utils.TART_ColorUtils;
import com.addtext.textonphoto.textart.R;

import java.util.List;

public class TART_ColorAdapter extends RecyclerView.Adapter<TART_ColorAdapter.ViewHolder> {
    public TART_BrushColorListener brushColorListener;
    public List<String> colors = TART_ColorUtils.lstColorForBrush();
    private Context context;
    public int selectedColorIndex;

    public TART_ColorAdapter(Context context2, TART_BrushColorListener brushColorListener2) {
        this.context = context2;
        this.brushColorListener = brushColorListener2;
    }

    public TART_ColorAdapter(Context context2, TART_BrushColorListener brushColorListener2, boolean z) {
        this.context = context2;
        this.brushColorListener = brushColorListener2;
        this.colors.add(0, "#00000000");
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_color_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.color.setFillColor(Color.parseColor(this.colors.get(i)));
        viewHolder.color.setStrokeColor(Color.parseColor(this.colors.get(i)));
        if (this.selectedColorIndex == i) {
            if (this.colors.get(i).equals("#00000000")) {
                viewHolder.color.setBackgroundColor(Color.parseColor("#00000000"));
                viewHolder.color.setStrokeColor(Color.parseColor("#FF4081"));
                return;
            }
            viewHolder.color.setBackgroundColor(-1);
        } else if (this.colors.get(i).equals("#00000000")) {
            viewHolder.color.setBackground(this.context.getDrawable(R.drawable.knack_none));
            viewHolder.color.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            viewHolder.color.setBackgroundColor(Color.parseColor(this.colors.get(i)));
        }
    }

    public int getItemCount() {
        return this.colors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleView color;

        ViewHolder(View view) {
            super(view);
            this.color = view.findViewById(R.id.color);
            this.color.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TART_ColorAdapter.this.selectedColorIndex = ViewHolder.this.getLayoutPosition();
                    TART_ColorAdapter.this.brushColorListener.onColorChanged(TART_ColorAdapter.this.colors.get(TART_ColorAdapter.this.selectedColorIndex));
                    TART_ColorAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public void setSelectedColorIndex(int i) {
        this.selectedColorIndex = i;
    }
}
