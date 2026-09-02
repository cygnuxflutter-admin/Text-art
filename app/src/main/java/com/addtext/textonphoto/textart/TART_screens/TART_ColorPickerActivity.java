package com.addtext.textonphoto.textart.TART_screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_sample.TART_BackgroundColorAdapter;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_sample.TART_GenDataBackGround;

public class TART_ColorPickerActivity extends AppCompatActivity {
    private RecyclerView recyclerColors;
    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knack_activity_color_picker);
        com.addtext.textonphoto.textart.TART_utils.TART_BottomNavHelper.setupBottomNav(this, R.id.navCreate);

        android.widget.RelativeLayout rl_banner_color = findViewById(R.id.rl_banner_color);
        if (rl_banner_color != null) {
            com.addtext.textonphoto.textart.adManager.TART_LoadAds.loadAdmobBannerAd(this, rl_banner_color);
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        recyclerColors = findViewById(R.id.recyclerColor);
        recyclerColors.setHasFixedSize(true);
        // Use a Grid layout with 3 columns for colors
        recyclerColors.setLayoutManager(new GridLayoutManager(this, 3));
        
        recyclerColors.setAdapter(new TART_BackgroundColorAdapter(TART_GenDataBackGround.colorList(), this, new TART_ItemClickListener() {
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_ColorPickerActivity.this, () -> sendData(TART_GenDataBackGround.colorList().get(i).getImgSample()));
            }
        }));
    }

    private void sendData(int i) {
        Intent intent = new Intent(this, TART_EditImageActivity.class);
        intent.putExtra("SampleBackground", i);
        startActivity(intent);
    }
}
