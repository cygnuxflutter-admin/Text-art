package com.addtext.textonphoto.textart.TART_notifications;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import java.util.List;

public class TART_NotificationAdapter extends RecyclerView.Adapter<TART_NotificationAdapter.ViewHolder> {

    private Context context;
    private List<TART_NotificationDB.NotificationModel> list;

    public TART_NotificationAdapter(Context context, List<TART_NotificationDB.NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.knack_item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TART_NotificationDB.NotificationModel model = list.get(position);
        holder.tvTitle.setText(model.title);
        holder.tvBody.setText(model.body);
        
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(model.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.tvTime.setText(timeAgo);
        
        if (model.isRead) {
            holder.unreadDot.setVisibility(View.GONE);
        } else {
            holder.unreadDot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody, tvTime;
        View unreadDot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTime = itemView.findViewById(R.id.tvTime);
            unreadDot = itemView.findViewById(R.id.unreadDot);
        }
    }
}
