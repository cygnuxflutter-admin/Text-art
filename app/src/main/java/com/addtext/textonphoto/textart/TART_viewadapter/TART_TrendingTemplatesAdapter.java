package com.addtext.textonphoto.textart.TART_viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;

import java.util.List;

public class TART_TrendingTemplatesAdapter extends RecyclerView.Adapter<TART_TrendingTemplatesAdapter.ViewHolder> {

    private final Context context;
    private final List<TemplateItem> templateList;
    private final OnTemplateClickListener listener;

    public interface OnTemplateClickListener {
        void onTemplateClick(TemplateItem item);
    }

    public static class TemplateItem {
        public int imageRes;
        public String title;

        public TemplateItem(int imageRes, String title) {
            this.imageRes = imageRes;
            this.title = title;
        }
    }

    public TART_TrendingTemplatesAdapter(Context context, List<TemplateItem> templateList, OnTemplateClickListener listener) {
        this.context = context;
        this.templateList = templateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trending_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TemplateItem item = templateList.get(position);
        holder.ivThumb.setImageResource(item.imageRes);
        holder.tvTitle.setText(item.title);

        View.OnClickListener clickListener = v -> {
            if (listener != null) {
                listener.onTemplateClick(item);
            }
        };

        holder.itemView.setOnClickListener(clickListener);
        holder.btnTryNow.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return templateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
        TextView btnTryNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_template_thumb);
            tvTitle = itemView.findViewById(R.id.tv_template_title);
            btnTryNow = itemView.findViewById(R.id.btn_try_now);
        }
    }
}
