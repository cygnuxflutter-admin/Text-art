package com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.addtext.textonphoto.textart.TART_utils.TART_FontUtils;
import com.addtext.textonphoto.textart.R;
import java.util.List;

public class TART_FontAdapter extends RecyclerView.Adapter<TART_FontAdapter.ViewHolder> {
    private final Context context;
    private List<String> lstFonts;
    public ItemClickListener mClickListener;
    private final LayoutInflater mInflater;
    public int selectedItem = 0;
    private String previewText = "Brew. Sip. Create.";

    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public TART_FontAdapter(Context context2, List<String> list) {
        this.mInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.lstFonts = list;
    }

    public void setPreviewText(String text) {
        if (text != null && !text.trim().isEmpty()) {
            this.previewText = text;
            notifyDataSetChanged();
        }
    }

    public void updateFonts(List<String> fonts) {
        this.lstFonts = fonts;
        this.selectedItem = 0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.knack_font_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String fontPath = this.lstFonts.get(i);
        TART_FontUtils.setFontByName(this.context, viewHolder.font, fontPath);
        viewHolder.font.setText(this.previewText);

        // Format clean font family name from filename
        String fontName = fontPath;
        int lastSlash = fontName.lastIndexOf('/');
        if (lastSlash != -1) {
            fontName = fontName.substring(lastSlash + 1);
        }
        int dotIndex = fontName.lastIndexOf('.');
        if (dotIndex != -1) {
            fontName = fontName.substring(0, dotIndex);
        }
        viewHolder.tvFontName.setText(fontName);

        ConstraintLayout constraintLayout = viewHolder.wrapperFontItems;
        if (this.selectedItem == i) {
            constraintLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_font_item_selected));
            viewHolder.ivChecked.setVisibility(View.VISIBLE);
            viewHolder.tvFontName.setTextColor(ContextCompat.getColor(context, R.color.brand_orange));
        } else {
            constraintLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_font_item_unselected));
            viewHolder.ivChecked.setVisibility(View.GONE);
            viewHolder.tvFontName.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
        }
    }

    @Override
    public int getItemCount() {
        return this.lstFonts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView font;
        public TextView tvFontName;
        public ImageView ivChecked;
        ConstraintLayout wrapperFontItems;

        ViewHolder(View view) {
            super(view);
            this.font = view.findViewById(R.id.font_item);
            this.tvFontName = view.findViewById(R.id.tvFontName);
            this.ivChecked = view.findViewById(R.id.ivFontChecked);
            this.wrapperFontItems = view.findViewById(R.id.wrapper_font_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int oldPos = TART_FontAdapter.this.selectedItem;
            TART_FontAdapter.this.selectedItem = getAdapterPosition();
            notifyItemChanged(oldPos);
            notifyItemChanged(TART_FontAdapter.this.selectedItem);
            if (TART_FontAdapter.this.mClickListener != null) {
                TART_FontAdapter.this.mClickListener.onItemClick(view, TART_FontAdapter.this.selectedItem);
            }
        }
    }

    public void setSelectedItem(int i) {
        this.selectedItem = i;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
