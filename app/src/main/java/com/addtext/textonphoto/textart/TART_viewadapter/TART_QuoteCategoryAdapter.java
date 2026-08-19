package com.addtext.textonphoto.textart.TART_viewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Category;

import java.util.ArrayList;


public class TART_QuoteCategoryAdapter extends RecyclerView.Adapter<TART_QuoteCategoryAdapter.ViewHolder> {
    TypedArray category_images;
    TART_ItemClickListener itemClickListener;
    ArrayList<TART_Category> mCategoryList;
    Context mContext;
    int mPosition;

    public TART_QuoteCategoryAdapter(ArrayList<TART_Category> arrayList, Context context, TART_ItemClickListener itemClickListener) {
        this.mCategoryList = arrayList;
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.QuoteCategoryAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_view_category, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.-$$Lambda$QuoteCategoryAdapter$Q8_z_k8i1OunUVPhQE7H93NE4M0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TART_QuoteCategoryAdapter.this.lambda$onCreateViewHolder$0$QuoteCategoryAdapter(viewHolder, view);
            }
        });
        return viewHolder;
    }

    public  void lambda$onCreateViewHolder$0$QuoteCategoryAdapter(ViewHolder viewHolder, View view) {
        this.itemClickListener.onItemClick(view, viewHolder.getLayoutPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        this.mPosition = i;
        viewHolder.tvTitle.setText(this.mCategoryList.get(i).getName());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<TART_Category> arrayList = this.mCategoryList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
