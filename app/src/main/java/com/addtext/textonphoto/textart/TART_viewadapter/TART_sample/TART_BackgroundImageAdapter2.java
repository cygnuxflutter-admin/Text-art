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


public class TART_BackgroundImageAdapter2 extends RecyclerView.Adapter<TART_BackgroundImageAdapter2.ViewHolderImage2> {
    Context context;
    TART_ItemClickListener itemClickListener;
    List<TART_Sample> sampleArrayList;

    public TART_BackgroundImageAdapter2(List<TART_Sample> list, Context context, TART_ItemClickListener itemClickListener) {
        this.sampleArrayList = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolderImage2 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_image_background2, viewGroup, false);
        final ViewHolderImage2 viewHolderImage2 = new ViewHolderImage2(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TART_BackgroundImageAdapter2.this.lambda$onCreateViewHolder$0$BackgroundImageAdapter2(viewHolderImage2, view);
            }
        });
        return viewHolderImage2;
    }

    public  void lambda$onCreateViewHolder$0$BackgroundImageAdapter2(ViewHolderImage2 viewHolderImage2, View view) {
        this.itemClickListener.onItemClick(view, viewHolderImage2.getLayoutPosition());
    }

    @Override
    public void onBindViewHolder(ViewHolderImage2 viewHolderImage2, int i) {
        Glide.with(this.context).load(Integer.valueOf(this.sampleArrayList.get(i).getImgSample())).thumbnail(0.1f).into(viewHolderImage2.roundedImageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.sampleArrayList.size();
    }

    /* loaded from: classes.dex */
    public static class ViewHolderImage2 extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        public ViewHolderImage2(View view) {
            super(view);
            this.roundedImageView = (RoundedImageView) view.findViewById(R.id.img_background_2);
        }
    }
}
