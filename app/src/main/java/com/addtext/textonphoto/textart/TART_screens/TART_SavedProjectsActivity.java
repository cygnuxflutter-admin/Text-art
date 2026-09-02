package com.addtext.textonphoto.textart.TART_screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_utils.TART_FileUtils;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_RecentProjectsAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TART_SavedProjectsActivity extends AppCompatActivity {
    private RecyclerView rvSavedProjects;
    private TextView tvNoProjects;
    private ImageView btnBack;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knack_activity_saved_projects);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        tvNoProjects = findViewById(R.id.tvNoProjects);
        rvSavedProjects = findViewById(R.id.rvSavedProjects);
        rvSavedProjects.setHasFixedSize(true);
        rvSavedProjects.setLayoutManager(new GridLayoutManager(this, 3));

        swipeRefresh = findViewById(R.id.swipeRefresh);
        if (swipeRefresh != null) {
            swipeRefresh.setColorSchemeResources(R.color.brand_orange, R.color.brand_orange_dark);
            swipeRefresh.setOnRefreshListener(() -> {
                loadSavedProjects();
                swipeRefresh.setRefreshing(false);
            });
        }

        loadSavedProjects();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedProjects();
    }

    private void loadSavedProjects() {
        List<TART_RecentProjectsAdapter.ProjectItem> projectList = new ArrayList<>();
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TextOnPhoto");
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            if (files != null && files.length > 0) {
                Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
                for (File file : files) {
                    if (file.exists() && file.length() > 0) {
                        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(file.lastModified(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
                        projectList.add(new TART_RecentProjectsAdapter.ProjectItem(file.getAbsolutePath(), file.getName(), relativeTime.toString()));
                    }
                }
            }
        }

        if (projectList.isEmpty()) {
            rvSavedProjects.setVisibility(View.GONE);
            tvNoProjects.setVisibility(View.VISIBLE);
        } else {
            rvSavedProjects.setVisibility(View.VISIBLE);
            tvNoProjects.setVisibility(View.GONE);
            TART_RecentProjectsAdapter adapter = new TART_RecentProjectsAdapter(this, projectList, true, item -> {
                Intent intent = new Intent(TART_SavedProjectsActivity.this, TART_ShareActivity.class);
                intent.putExtra("path", item.imagePath);
                startActivity(intent);
            });
            rvSavedProjects.setAdapter(adapter);
        }
    }
}
