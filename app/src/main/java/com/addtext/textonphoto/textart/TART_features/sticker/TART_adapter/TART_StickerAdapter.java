package com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.addtext.textonphoto.textart.TART_utils.TART_AssetUtils;
import com.addtext.textonphoto.textart.R;

import java.util.List;

public class TART_StickerAdapter extends RecyclerView.Adapter<TART_StickerAdapter.ViewHolder> {

    public Context context;

    public int screenWidth;

    public OnClickStickerListener stickerListener;

    public List<String> stickers;

    public interface OnClickStickerListener {
        void addSticker(Bitmap bitmap);
    }

    public TART_StickerAdapter(Context context2, List<String> list, int i, OnClickStickerListener onClickStickerListener) {
        this.context = context2;
        this.stickers = list;
        this.screenWidth = i;
        this.stickerListener = onClickStickerListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.knack_sticker_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Bitmap bitmap = TART_AssetUtils.loadBitmapFromAssets(this.context, this.stickers.get(i));
        Glide.with(context).load(bitmap).into(viewHolder.sticker);
    }

    public int getItemCount() {
        return this.stickers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView sticker;

        public ViewHolder(View view) {
            super(view);
            this.sticker = view.findViewById(R.id.txt_vp_item_list);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            TART_StickerAdapter.this.stickerListener.addSticker(TART_AssetUtils.loadBitmapFromAssets(TART_StickerAdapter.this.context, (String) TART_StickerAdapter.this.stickers.get(getAdapterPosition())));
        }
    }
}
