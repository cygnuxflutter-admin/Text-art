package com.addtext.textonphoto.textart.TART_notifications;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;

import java.util.List;

public class TART_NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerNotifications;
    private View tvNoNotifications;
    private ImageView btnBack;
    private TART_NotificationDB db;
    private TART_NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knack_activity_notifications);

        recyclerNotifications = findViewById(R.id.recyclerNotifications);
        tvNoNotifications = findViewById(R.id.tvNoNotifications);
        btnBack = findViewById(R.id.btnBack);
        
        btnBack.setOnClickListener(v -> onBackPressed());
        
        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));
        
        db = new TART_NotificationDB(this);
        List<TART_NotificationDB.NotificationModel> list = db.getAllNotifications();
        
        if (list.isEmpty()) {
            tvNoNotifications.setVisibility(View.VISIBLE);
            recyclerNotifications.setVisibility(View.GONE);
        } else {
            tvNoNotifications.setVisibility(View.GONE);
            recyclerNotifications.setVisibility(View.VISIBLE);
            adapter = new TART_NotificationAdapter(this, list);
            recyclerNotifications.setAdapter(adapter);
        }
        
        // Mark all as read when user opens the screen
        db.markAllAsRead();
    }
}
