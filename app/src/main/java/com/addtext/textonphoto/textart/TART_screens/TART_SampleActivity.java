package com.addtext.textonphoto.textart.TART_screens;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_views.CustomTabChrom;
import com.addtext.textonphoto.textart.Utils;
import com.addtext.textonphoto.textart.adManager.TART_LoadAds;
import com.addtext.textonphoto.textart.TART_interfaces.TART_ItemClickListener;
import com.addtext.textonphoto.textart.TART_supermodel.TART_Sample;
import com.addtext.textonphoto.textart.TART_utils.TART_NetworkUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_sample.TART_BackgroundColorAdapter;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_sample.TART_BackgroundImageAdapter;
import com.addtext.textonphoto.textart.TART_viewadapter.TART_sample.TART_GenDataBackGround;
import com.bumptech.glide.Glide;

import pl.droidsonroids.gif.GifImageView;


public class TART_SampleActivity extends AppCompatActivity {
    private RecyclerView recyclerColors;
    private RecyclerView recyclerLifeStyle;
    private RecyclerView recyclerLight;
    private RecyclerView recyclerLove;
    private RecyclerView recyclerMacro;
    private RecyclerView recyclerNature;

//    GifImageView iv_game;
    TART_PreferenceClass preferenceClass;
//    ImageView iv_game_banner;
//    ImageView iv_game_banner_1;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack_activity_sample);
        com.addtext.textonphoto.textart.TART_utils.TART_BottomNavHelper.setupBottomNav(this, R.id.navTemplates);
        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (rl_ad != null) {
            com.addtext.textonphoto.textart.adManager.TART_LoadAds.loadAdmobBannerAd(this, rl_ad);
        }
       /* String banner1 = preferenceClass.getDataType("URL_BGActivityGame");
        String banner2 = preferenceClass.getDataType("URL_BGActivityBanner1");
        String banner3 = preferenceClass.getDataType("URL_BGActivityBanner2");
//
        iv_game = findViewById(R.id.iv_game);
        iv_game_banner = findViewById(R.id.iv_game_banner);
        iv_game_banner_1 = findViewById(R.id.iv_game_banner_1);

        Glide.with(TART_SampleActivity.this)
                .load(preferenceClass.getDataType("BGActivityGame"))
                .placeholder(R.drawable.game_gif)
                .into(iv_game);
        Glide.with(TART_SampleActivity.this)
                .load(preferenceClass.getDataType("BGActivityBanner1"))
                .placeholder(R.drawable.game_bg_banner_1)
                .into(iv_game_banner);
        Glide.with(TART_SampleActivity.this)
                .load(preferenceClass.getDataType("BGActivityBanner2"))
                .placeholder(R.drawable.game_bg_banner_2)
                .into(iv_game_banner_1);*/

        /*iv_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(TART_SampleActivity.this, R.color.custome_chrom_color));
                CustomTabChrom.openCustomTab(TART_SampleActivity.this, customIntent.build(), Uri.parse(banner1));
            }
        });

        iv_game_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(TART_SampleActivity.this, R.color.custome_chrom_color));
                CustomTabChrom.openCustomTab(TART_SampleActivity.this, customIntent.build(), Uri.parse(banner2));
            }
        });

        iv_game_banner_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(TART_SampleActivity.this, R.color.custome_chrom_color));
                CustomTabChrom.openCustomTab(TART_SampleActivity.this, customIntent.build(), Uri.parse(banner3));
            }
        });*/

//        ((Button) findViewById(R.id.btUnsplash)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public final void onClick(View view) {
//                if (!isNetworkConnected(TART_SampleActivity.this)) {
//                    Toast.makeText(TART_SampleActivity.this, (int) R.string.internet, Toast.LENGTH_LONG).show();
//                } else {
//                    MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> gotoUnsplash());
//
//                    //    SplashPicker.open(SampleActivity.this, "gdijPZCBDAunI25RDiT3aimBm1N2-deDbo-HTKDdk-I");
//                }
//            }
//        });
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                finish();
            }
        });
        this.recyclerNature = (RecyclerView) findViewById(R.id.recyclerNature);
        this.recyclerNature.setItemViewCacheSize(0);
        setRecyclerNature();
        this.recyclerLove = (RecyclerView) findViewById(R.id.recyclerLove);
        this.recyclerLove.setItemViewCacheSize(0);
        setRecyclerLove();
        this.recyclerLifeStyle = (RecyclerView) findViewById(R.id.recyclerLifeStyle);
        this.recyclerLifeStyle.setItemViewCacheSize(0);
        setRecyclerLifeStyle();
        this.recyclerMacro = (RecyclerView) findViewById(R.id.recyclerMacro);
        this.recyclerMacro.setItemViewCacheSize(0);
        setRecyclerMacro();
        this.recyclerLight = (RecyclerView) findViewById(R.id.recyclerLight);
        this.recyclerLight.setItemViewCacheSize(0);
        setRecyclerLight();

        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        if (swipeRefresh != null) {
            swipeRefresh.setColorSchemeResources(R.color.brand_orange, R.color.brand_orange_dark);
            swipeRefresh.setOnRefreshListener(() -> {
                setRecyclerNature();
                setRecyclerLove();
                setRecyclerLifeStyle();
                setRecyclerMacro();
                setRecyclerLight();
                swipeRefresh.setRefreshing(false);
            });
        }

        


    }

    private void setRecyclerLifeStyle() {
        this.recyclerLifeStyle.setHasFixedSize(true);
        this.recyclerLifeStyle.setLayoutManager(new GridLayoutManager(this, 3));
        this.recyclerLifeStyle.setAdapter(new TART_BackgroundImageAdapter(TART_GenDataBackGround.lifeStyle(), this, new TART_ItemClickListener() { // from class: com.addtext.textonphoto.textart.screens.-$$Lambda$SampleActivity$ruEAAvXkPvDTo7yVr7gjpTZ8ufg
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> sendData(TART_GenDataBackGround.lifeStyle().get(i)));
//                sendData(TART_GenDataBackGround.lifeStyle().get(i));
            }
        }));
    }

    private void setRecyclerLove() {
        this.recyclerLove.setHasFixedSize(true);
        this.recyclerLove.setLayoutManager(new GridLayoutManager(this, 3));
        this.recyclerLove.setAdapter(new TART_BackgroundImageAdapter(TART_GenDataBackGround.LoveList(), this, new TART_ItemClickListener() {
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> sendData(TART_GenDataBackGround.LoveList().get(i)));
//                sendData(TART_GenDataBackGround.LoveList().get(i));
            }
        }));
    }


    private void setRecyclerNature() {
        this.recyclerNature.setHasFixedSize(true);
        this.recyclerNature.setLayoutManager(new GridLayoutManager(this, 3));
        this.recyclerNature.setAdapter(new TART_BackgroundImageAdapter(TART_GenDataBackGround.nativeList(), this, new TART_ItemClickListener() {
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> sendData(TART_GenDataBackGround.nativeList().get(i)));
//                sendData(TART_GenDataBackGround.nativeList().get(i));
            }
        }));
    }


    private void setRecyclerMacro() {
        this.recyclerMacro.setHasFixedSize(true);
        this.recyclerMacro.setLayoutManager(new GridLayoutManager(this, 3));
        this.recyclerMacro.setAdapter(new TART_BackgroundImageAdapter(TART_GenDataBackGround.macroList(), this, new TART_ItemClickListener() {
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> sendData(TART_GenDataBackGround.macroList().get(i)));
//                sendData(TART_GenDataBackGround.macroList().get(i));
            }
        }));
    }


    private void setRecyclerLight() {
        this.recyclerLight.setHasFixedSize(true);
        this.recyclerLight.setLayoutManager(new GridLayoutManager(this, 3));
        this.recyclerLight.setAdapter(new TART_BackgroundImageAdapter(TART_GenDataBackGround.LightList(), this, new TART_ItemClickListener() { // from class: com.addtext.textonphoto.textart.screens.-$$Lambda$SampleActivity$g13u2bWUjm3lm2ByRAe7_uryxkM
            @Override
            public final void onItemClick(View view, int i) {
                MyApplication.showInterstitialAd(TART_SampleActivity.this, () -> sendData(TART_GenDataBackGround.LightList().get(i)));
//                sendData(TART_GenDataBackGround.LightList().get(i));
            }
        }));
    }




    public void sendData(TART_Sample sample) {
        Intent intent = new Intent(TART_SampleActivity.this, TART_EditImageActivity.class);
        intent.putExtra("SampleBackground", sample.getImgSample());
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void gotoUnsplash() {
        startActivity(new Intent(TART_SampleActivity.this, TART_UnsplashActivitiy.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.knack_sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        super.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


  /*  @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        String str;
        String str2;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 100) {
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_PHOTOS);
            String str3 = null;
            if (parcelableArrayListExtra.size() >= 0) {
                str3 = ((UnsplashPhoto) parcelableArrayListExtra.get(0)).getUrls().getFull();
                str2 = ((UnsplashPhoto) parcelableArrayListExtra.get(0)).getUser().getName();
                str = ((UnsplashPhoto) parcelableArrayListExtra.get(0)).getUser().getUsername();
            } else {
                str = null;
                str2 = null;
            }
            Intent intent2 = new Intent(this, UnsplashPhotoActivity.class);
            intent2.putExtra("url", str3);
            intent2.putExtra("name", str2);
            intent2.putExtra("user_id", str);
            startActivity(intent2);
        }
    }*/

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] allNetworks = connectivityManager.getAllNetworks();
        if (allNetworks.length > 0) {
            boolean z = false;
            for (Network network : allNetworks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }
}
