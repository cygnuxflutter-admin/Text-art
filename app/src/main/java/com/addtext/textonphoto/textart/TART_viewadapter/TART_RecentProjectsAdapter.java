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
import com.bumptech.glide.Glide;
import java.util.List;

public class TART_RecentProjectsAdapter extends RecyclerView.Adapter<TART_RecentProjectsAdapter.ViewHolder> {
    private final Context context;
    private final List<ProjectItem> projectList;
    private final OnProjectClickListener listener;
    private boolean isGrid = false;

    public interface OnProjectClickListener {
        void onProjectClick(ProjectItem item);
    }

    public static class ProjectItem {
        public String imagePath;
        public int imageRes;
        public String title;
        public String time;

        public ProjectItem(String imagePath, String title, String time) {
            this.imagePath = imagePath;
            this.title = title;
            this.time = time;
        }

        public ProjectItem(int imageRes, String title, String time) {
            this.imageRes = imageRes;
            this.title = title;
            this.time = time;
        }
    }

    public TART_RecentProjectsAdapter(Context context, List<ProjectItem> projectList, OnProjectClickListener listener) {
        this.context = context;
        this.projectList = projectList;
        this.listener = listener;
    }

    public TART_RecentProjectsAdapter(Context context, List<ProjectItem> projectList, boolean isGrid, OnProjectClickListener listener) {
        this.context = context;
        this.projectList = projectList;
        this.isGrid = isGrid;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isGrid) {
            view = LayoutInflater.from(context).inflate(R.layout.knack_item_saved_project_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_recent_project, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectItem item = projectList.get(position);
        
        if (holder.tvTitle != null) {
            holder.tvTitle.setText(item.title);
        }
        if (holder.tvTime != null) {
            holder.tvTime.setText(item.time);
        }

        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            Glide.with(context)
                 .load(item.imagePath)
                 .centerCrop()
                 .into(holder.ivThumb);
        } else {
            Glide.with(context)
                 .load(item.imageRes)
                 .centerCrop()
                 .into(holder.ivThumb);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProjectClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_project_thumb);
            tvTitle = itemView.findViewById(R.id.tv_project_title);
            tvTime = itemView.findViewById(R.id.tv_project_time);
        }
    }
}
