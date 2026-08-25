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

public class TART_RecentProjectsAdapter extends RecyclerView.Adapter<TART_RecentProjectsAdapter.ViewHolder> {

    private final Context context;
    private final List<ProjectItem> projectList;
    private final OnProjectClickListener listener;

    public interface OnProjectClickListener {
        void onProjectClick(ProjectItem item);
    }

    public static class ProjectItem {
        public int imageRes;
        public String title;
        public String time;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recent_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectItem item = projectList.get(position);
        holder.ivThumb.setImageResource(item.imageRes);
        holder.tvTitle.setText(item.title);
        holder.tvTime.setText(item.time);

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
