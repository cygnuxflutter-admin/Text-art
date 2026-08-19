package com.addtext.textonphoto.textart.TART_viewadapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;

import java.util.List;


public class TART_FontAdapter extends RecyclerView.Adapter<TART_FontAdapter.FontViewHolder> {
    Context context;
    FontAdapterClickListener fontAdapterClickListener;
    TART_ItemClickListener itemClickListener;
    List<String> list;


    public interface FontAdapterClickListener {
        void onFontItemSelected(String str);
    }

    public TART_FontAdapter(List<String> list, Context context, TART_ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.list = list;
    }

    @Override
    public FontViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.knack_item_font, viewGroup, false);
        final FontViewHolder fontViewHolder = new FontViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                TART_FontAdapter.this.lambda$onCreateViewHolder$0$FontAdapter(fontViewHolder, view);
            }
        });
        return fontViewHolder;
    }

    public  void lambda$onCreateViewHolder$0$FontAdapter(FontViewHolder fontViewHolder, View view) {
        this.itemClickListener.onItemClick(view, fontViewHolder.getLayoutPosition());
    }

    @Override
    public void onBindViewHolder(FontViewHolder fontViewHolder, int i) {
        AssetManager assets = this.context.getAssets();
        TextView textView = fontViewHolder.txtFontDemo;
        textView.setTypeface(Typeface.createFromAsset(assets, "font/" + this.list.get(i)));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public static class FontViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fontSection;
        TextView txtFontDemo;

        public FontViewHolder(View view) {
            super(view);
            this.txtFontDemo = (TextView) view.findViewById(R.id.txt_font_demo);
            this.fontSection = (FrameLayout) view.findViewById(R.id.font_section);
        }
    }
}
