package com.addtext.textonphoto.textart.TART_viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;

import java.util.List;


public class TART_SuggestAdapter extends RecyclerView.Adapter<TART_SuggestAdapter.ViewHolderSuggest> {
    Context context;
    TART_ItemClickListener itemClickListener;
    List<String> suggestList;

    public TART_SuggestAdapter(List<String> list, Context context, TART_ItemClickListener itemClickListener) {
        this.suggestList = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolderSuggest onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_suggest, viewGroup, false);
        final ViewHolderSuggest viewHolderSuggest = new ViewHolderSuggest(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.-$$Lambda$SuggestAdapter$IsOeFY4Gqi2-XFQvT1Ocog1xc4Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TART_SuggestAdapter.this.lambda$onCreateViewHolder$0$SuggestAdapter(viewHolderSuggest, view);
            }
        });
        return viewHolderSuggest;
    }

    public  void lambda$onCreateViewHolder$0$SuggestAdapter(ViewHolderSuggest viewHolderSuggest, View view) {
        this.itemClickListener.onItemClick(view, viewHolderSuggest.getLayoutPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolderSuggest viewHolderSuggest, int i) {
        viewHolderSuggest.tvSuggest.setText(this.suggestList.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.suggestList.size();
    }

    /* loaded from: classes.dex */
    public static class ViewHolderSuggest extends RecyclerView.ViewHolder {
        TextView tvSuggest;

        public ViewHolderSuggest(View view) {
            super(view);
            this.tvSuggest = (TextView) view.findViewById(R.id.tvSuggest);
        }
    }
}
