package com.addtext.textonphoto.textart.TART_screens;

import static com.addtext.textonphoto.textart.adManager.TART_NativeAdUtil.loadNativeAd;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;

import com.addtext.textonphoto.textart.TART_base.TART_BaseActivity;
import com.addtext.textonphoto.textart.TART_dialog.TART_RateDialog;
import com.addtext.textonphoto.textart.TART_utils.TART_SharePreferenceUtil;
import com.addtext.textonphoto.textart.R;

import java.io.File;

public class TART_SaveAndShareActivity extends TART_BaseActivity {

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        makeFullScreen();
        setContentView(R.layout.knack_save_and_share_layout);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.save_and_share));
        String string = getIntent().getExtras().getString("path");
        File file = new File(string);
        Glide.with(getApplicationContext()).load(file).into((ImageView) findViewById(R.id.preview));
        findViewById(R.id.preview).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), "com.addtext.textonphoto.textart.provider", file), "image/*");
            startActivity(intent);
        });
        ((TextView) findViewById(R.id.path)).setText(string);

        findViewById(R.id.shareLayout).setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(getApplicationContext(), "com.addtext.textonphoto.textart.fileprovider", file));
            startActivity(Intent.createChooser(intent, "Share"));
        });
        if (!TART_SharePreferenceUtil.isRated(this)) {
            new TART_RateDialog(this, false).show();
        }
      /*  if (Constants.SHOW_ADS) {
            AdmobAds.showFullAds(null);
            AdmobAds.loadNativeAds(this, null);
            return;
        }*/
        RelativeLayout native_banner_ad_container = this.findViewById(R.id.native_banner_ad_container);
        loadNativeAd(native_banner_ad_container, TART_SaveAndShareActivity.this);
        findViewById(R.id.adsContainer).setVisibility(View.GONE);
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }


    public void onResume() {
        super.onResume();
    }
}
