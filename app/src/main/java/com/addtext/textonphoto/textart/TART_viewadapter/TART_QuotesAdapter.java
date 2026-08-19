package com.addtext.textonphoto.textart.TART_viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Quotes;

import java.util.List;

/* loaded from: classes.dex */
public class TART_QuotesAdapter extends RecyclerView.Adapter<TART_QuotesAdapter.QuotesViewHolder> {
    Context context;
    TART_ItemClickListener itemClickListener;
    List<TART_Quotes> list;

    public TART_QuotesAdapter(List<TART_Quotes> list, Context context, TART_ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public QuotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_quotes, viewGroup, false);
        final QuotesViewHolder quotesViewHolder = new QuotesViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.-$$Lambda$QuotesAdapter$IhGQ46k-dm6V0Ii_qU2er5mx55c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TART_QuotesAdapter.this.lambda$onCreateViewHolder$0$QuotesAdapter(quotesViewHolder, view);
            }
        });
        return quotesViewHolder;
    }

    public  void lambda$onCreateViewHolder$0$QuotesAdapter(QuotesViewHolder quotesViewHolder, View view) {
        this.itemClickListener.onItemClick(view, quotesViewHolder.getLayoutPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(QuotesViewHolder quotesViewHolder, int i) {
        quotesViewHolder.tvQuote.setText(this.list.get(i).getQuote());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes.dex */
    public static class QuotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuote;

        public QuotesViewHolder(View view) {
            super(view);
            this.tvQuote = (TextView) view.findViewById(R.id.tvQuote);
        }
    }
}
