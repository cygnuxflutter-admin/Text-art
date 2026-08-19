package com.addtext.textonphoto.textart.TART_screens;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.addtext.textonphoto.textart.R;

import com.addtext.textonphoto.textart.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class TART_UnsplashPhotoActivity extends AppCompatActivity {

    ImageView ivBack;
    ImageView ivImage;
    ProgressBar progressBar;
    TextView tvSelect;
    TextView tvUserID;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack_activity_unsplash_photo);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.ivImage = (ImageView) findViewById(R.id.ivUnsplash);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.tvSelect = (TextView) findViewById(R.id.tvSelect);

        String image = getIntent().getStringExtra("image");
        Log.e("TAG", "onCreate: "+image );

        if (image != null) {
            this.progressBar.setVisibility(0);
            Glide.with((FragmentActivity) this).load(image).apply((BaseRequestOptions<?>) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    progressBar.setVisibility(8);
                    return false;
                }
            }).into(this.ivImage);
        }

        this.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TART_UnsplashPhotoActivity.this, TART_EditImageActivity.class);
                intent.putExtra(Utils.KEY_IMAGE, image);
                TART_UnsplashPhotoActivity.this.startActivity(intent);
            }
        });

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TART_UnsplashPhotoActivity.this.finish();
            }
        });

    }
}
