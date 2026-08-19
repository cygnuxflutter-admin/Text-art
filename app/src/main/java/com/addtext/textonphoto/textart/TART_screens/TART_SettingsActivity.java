package com.addtext.textonphoto.textart.TART_screens;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.net.MailTo;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.TART_views.CustomTabChrom;
import com.addtext.textonphoto.textart.Utils;
import com.addtext.textonphoto.textart.adManager.TART_NativeAdUtil;
import com.bumptech.glide.Glide;

import pl.droidsonroids.gif.GifImageView;


public class TART_SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBack;
    LinearLayout ltFeedback;
    LinearLayout ltPrivacy;
    LinearLayout ltRate;
    LinearLayout ltShareApp;
    LinearLayout ltUpdate;

    GifImageView iv_game;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack_activity_settings);
        this.ltRate = (LinearLayout) findViewById(R.id.rate_app);
        this.ltFeedback = (LinearLayout) findViewById(R.id.feed_back);
        this.ltUpdate = (LinearLayout) findViewById(R.id.checkupdates);
        this.ltPrivacy = (LinearLayout) findViewById(R.id.privacy_app);
        this.ltShareApp = (LinearLayout) findViewById(R.id.shareApp);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ltRate.setOnClickListener(this);
        this.ltFeedback.setOnClickListener(this);
        this.ltUpdate.setOnClickListener(this);
        this.ltPrivacy.setOnClickListener(this);
        this.ltShareApp.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        TART_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);

        /*String url = new TART_PreferenceClass(this).getDataType("URL_SettingActivityGame", "");

        iv_game = findViewById(R.id.iv_game);
        Glide.with(TART_SettingsActivity.this)
                .load(new TART_PreferenceClass(this).getDataType("SettingActivityGame"))
                .placeholder(R.drawable.game_gif)
                .into(iv_game);

        iv_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(TART_SettingsActivity.this, R.color.custome_chrom_color));
                CustomTabChrom.openCustomTab(TART_SettingsActivity.this, customIntent.build(), Uri.parse(url));
            }
        });
*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkupdates:
                launchMarket();
                return;
            case R.id.feed_back:
                sendFeedBack();
                return;
            case R.id.ivBack:
                finish();
                return;
            case R.id.privacy_app:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Utils.privacy_policy)));
                return;
            case R.id.rate_app:
                launchMarket();
                return;
            case R.id.shareApp:
                shareApp();
                return;
            default:
                return;
        }
    }

    private void launchMarket() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void shareApp() {
        String string = getString(R.string.app_name);
        String packageName = getPackageName();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", string);
        intent.putExtra("android.intent.extra.TEXT", "Check out the App at: https://play.google.com/store/apps/details?id=" + packageName);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void sendFeedBack() {
        String string = getString(R.string.subject);
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(MailTo.MAILTO_SCHEME));
        intent.putExtra("android.intent.extra.EMAIL", new String[]{Utils.feedback_mail});
        intent.putExtra("android.intent.extra.SUBJECT", string);
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
}
