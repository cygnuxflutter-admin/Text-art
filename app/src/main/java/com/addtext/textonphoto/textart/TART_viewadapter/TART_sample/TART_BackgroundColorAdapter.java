package com.addtext.textonphoto.textart.TART_viewadapter.TART_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.TART_supermodel.TART_Sample;
import com.bumptech.glide.Glide;

import java.util.List;


public class TART_BackgroundColorAdapter extends RecyclerView.Adapter<TART_BackgroundColorAdapter.ViewHolderColor> {
    Context context;
    TART_ItemClickListener itemClickListener;
    List<TART_Sample> sampleArrayList;

    public TART_BackgroundColorAdapter(List<TART_Sample> list, Context context, TART_ItemClickListener itemClickListener) {
        this.sampleArrayList = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolderColor onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_color_background, viewGroup, false);
        final ViewHolderColor viewHolderColor = new ViewHolderColor(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.sample.-$$Lambda$BackgroundColorAdapter$kuAR4aoBF_MyaEXfS35_d99By1s
            @Override
            public final void onClick(View view) {
                TART_BackgroundColorAdapter.this.lambda$onCreateViewHolder$0$BackgroundColorAdapter(viewHolderColor, view);
            }
        });
        return viewHolderColor;
    }

    public  void lambda$onCreateViewHolder$0$BackgroundColorAdapter(ViewHolderColor viewHolderColor, View view) {
        int pos = viewHolderColor.getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION) {
            this.itemClickListener.onItemClick(view, pos);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderColor viewHolderColor, int i) {
        viewHolderColor.circleImageView.setImageDrawable(null);
        Glide.with(this.context)
                .load(Integer.valueOf(this.sampleArrayList.get(i).getImgSample()))
                .thumbnail(0.1f)
                .into(viewHolderColor.circleImageView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.sampleArrayList.size();
    }


    public static class ViewHolderColor extends RecyclerView.ViewHolder {
        ImageView circleImageView;

        public ViewHolderColor(View view) {
            super(view);
            this.circleImageView =view.findViewById(R.id.img_background_circle);
        }
    }
}
