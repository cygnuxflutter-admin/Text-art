package com.addtext.textonphoto.textart.TART_screens;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_EndlessRecyclerViewScrollListener;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_Photo;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_PhotosAdapter;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_SearchResults;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_UnsplashClient;
import com.addtext.textonphoto.textart.TART_UnsplashImage.TART_UnsplashInterface;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TART_UnsplashActivitiy extends AppCompatActivity {

    FrameLayout searchLayout;
    AppBarLayout appBarLayout;
    EditText searchBar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TART_PhotosAdapter adapter;

    TART_UnsplashInterface dataService;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knack_activity_unsplash_activitiy);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.app_bar_layout);
        searchLayout = findViewById(R.id.searchLayout);
        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        dataService = TART_UnsplashClient.getUnsplashClient().create(TART_UnsplashInterface.class);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    search(searchBar.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });

        loadPhotos();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TART_PhotosAdapter(new ArrayList<TART_Photo>(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new TART_EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPhotos();
            }
        });

    }


    private void loadPhotos() {
        progressBar.setVisibility(View.VISIBLE);

        try {
            dataService.getPhotos(page, null, "latest")
                    .enqueue(new Callback<List<TART_Photo>>() {
                        @Override
                        public void onResponse(Call<List<TART_Photo>> call, Response<List<TART_Photo>> response) {
                            List<TART_Photo> photos = response.body();
                            Log.d("Photos", "Photos Fetched " + photos.size());
                            //add to adapter
                            page++;
                            adapter.addPhotos(photos);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<List<TART_Photo>> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);

                        }
                    });
        }catch (Exception e){
            Log.e("TAG", "loadPhotos: "+e.getMessage() );
        }
    }

    public void search(String query) {
        if (query != null && !query.equals("")) {
            progressBar.setVisibility(View.VISIBLE);

            dataService.searchPhotos(query, null, null, null)
                    .enqueue(new Callback<TART_SearchResults>() {
                        @Override
                        public void onResponse(Call<TART_SearchResults> call, Response<TART_SearchResults> response) {
                            TART_SearchResults results = response.body();
                            Log.d("Photos", "Total Results Found " + results.getTotal());
                            List<TART_Photo> photos = results.getResults();
                            adapter = new TART_PhotosAdapter(photos, TART_UnsplashActivitiy.this);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<TART_SearchResults> call, Throwable t) {
                            Log.d("Unsplash", t.getLocalizedMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });

        } else {
            loadPhotos();
        }
    }

    private void showSearchBar() {
        appBarLayout.setVisibility(View.GONE);
        searchLayout.setVisibility(View.VISIBLE);
        searchBar.requestFocus();
    }

    public void hideSearchBar(View view) {
        searchLayout.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.VISIBLE);
        searchBar.clearFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.knack_menu_unsplash_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Log.d("picker", "Search bar open");
            showSearchBar();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchLayout.getVisibility() == View.VISIBLE) {
            Log.d("picker", "Search bar visible");
            hideSearchBar(null);
            return;
        }
        super.onBackPressed();
    }


}