package com.addtext.textonphoto.textart.TART_viewadapter.TART_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.TART_supermodel.TART_Sample;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/* loaded from: classes.dex */
public class TART_BackgroundImageAdapter extends RecyclerView.Adapter<TART_BackgroundImageAdapter.ViewHolderImage> {
    Context context;
    TART_ItemClickListener itemClickListener;
    List<TART_Sample> sampleArrayList;

    public TART_BackgroundImageAdapter(List<TART_Sample> list, Context context, TART_ItemClickListener itemClickListener) {
        this.sampleArrayList = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolderImage onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_image_background, viewGroup, false);
        final ViewHolderImage viewHolderImage = new ViewHolderImage(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.sample.-$$Lambda$BackgroundImageAdapter$HylbINNjBBOTu67GhI-7uIARhHY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TART_BackgroundImageAdapter.this.lambda$onCreateViewHolder$0$BackgroundImageAdapter(viewHolderImage, view);
            }
        });
        return viewHolderImage;
    }

    public  void lambda$onCreateViewHolder$0$BackgroundImageAdapter(ViewHolderImage viewHolderImage, View view) {
        this.itemClickListener.onItemClick(view, viewHolderImage.getLayoutPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolderImage viewHolderImage, int i) {
        Glide.with(this.context).load(Integer.valueOf(this.sampleArrayList.get(i).getImgSample())).thumbnail(0.1f).into(viewHolderImage.roundedImageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.sampleArrayList.size();
    }

    /* loaded from: classes.dex */
    public static class ViewHolderImage extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        public ViewHolderImage(View view) {
            super(view);
            this.roundedImageView = (RoundedImageView) view.findViewById(R.id.img_background_rectange);
        }
    }
}
