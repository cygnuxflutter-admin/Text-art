package com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_AddTextProperties;
import com.addtext.textonphoto.textart.R;
import java.util.List;

public class TART_ShadowAdapter extends RecyclerView.Adapter<TART_ShadowAdapter.ViewHolder> {
    private Context context;
    private List<TART_AddTextProperties.TextShadow> lstTextShadows;
    public ShadowItemClickListener mClickListener;
    private LayoutInflater mInflater;
    public int selectedItem = 0;

    public interface ShadowItemClickListener {
        void onShadowItemClick(View view, int i);
    }

    public int getItemViewType(int i) {
        return i;
    }

    public TART_ShadowAdapter(Context context2, List<TART_AddTextProperties.TextShadow> list) {
        this.mInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.lstTextShadows = list;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.knack_shadow_adapter, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int i2;
        TART_AddTextProperties.TextShadow textShadow = this.lstTextShadows.get(i);
        viewHolder.textShadow.setShadowLayer((float) textShadow.getRadius(), (float) textShadow.getDx(), (float) textShadow.getDy(), textShadow.getColorShadow());
        ConstraintLayout constraintLayout = viewHolder.wrapperFontItems;
        if (this.selectedItem != i) {
            i2 = R.drawable.knack_border_black_view;
        } else {
            i2 = R.drawable.knack_border_view;
        }
        constraintLayout.setBackground(ContextCompat.getDrawable(context, i2));
    }

    public int getItemCount() {
        return this.lstTextShadows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textShadow;
        ConstraintLayout wrapperFontItems;

        ViewHolder(View view) {
            super(view);
            this.textShadow = view.findViewById(R.id.font_item);
            this.wrapperFontItems = view.findViewById(R.id.wrapper_font_item);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (TART_ShadowAdapter.this.mClickListener != null) {
                TART_ShadowAdapter.this.mClickListener.onShadowItemClick(view, getAdapterPosition());
            }
            TART_ShadowAdapter.this.selectedItem = getAdapterPosition();
            TART_ShadowAdapter.this.notifyDataSetChanged();
        }
    }

    public void setSelectedItem(int i) {
        this.selectedItem = i;
    }

    public void setClickListener(ShadowItemClickListener shadowItemClickListener) {
        this.mClickListener = shadowItemClickListener;
    }
}
