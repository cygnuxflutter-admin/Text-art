package com.addtext.textonphoto.textart.TART_screens;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.addtext.textonphoto.textart.BuildConfig;
import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_utils.RateButtonDialog;
import com.addtext.textonphoto.textart.TART_views.CustomTabChrom;
import com.addtext.textonphoto.textart.Utils;
import com.addtext.textonphoto.textart.adManager.TART_NativeAdUtil;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class TART_ShareActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    LinearLayout nativeAdLayout;
    LinearLayout relativeLayout;
    Uri uri;
    private Boolean firstTime = null;
    Bitmap bitmap = null;

    private void showDialog() {
    }

    private TART_PreferenceClass preferenceClass;
    Boolean rateSubmit = false;
//    ImageView ShareAcrivityBanner;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.knack_activity_share);

        preferenceClass = new TART_PreferenceClass(TART_ShareActivity.this);
        rateSubmit = preferenceClass.getRateSubmited("rateSubmitted");

//        ShareAcrivityBanner = findViewById(R.id.iv_game);
//        Glide.with(TART_ShareActivity.this)
//                .load(preferenceClass.getDataType("ShareAcrivityBanner"))
//                .placeholder(R.drawable.game_shareactivity)
//                .into(ShareAcrivityBanner);

//        String url = preferenceClass.getDataType("URL_ShareAcrivityBanner");

//        reviewPopUp();

//        ShareAcrivityBanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
//                customIntent.setToolbarColor(ContextCompat.getColor(TART_ShareActivity.this, R.color.custome_chrom_color));
//                CustomTabChrom.openCustomTab(TART_ShareActivity.this, customIntent.build(), Uri.parse(url));
//            }
//        });

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        TART_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);

        addControls();

        this.uri = getIntent().getData();
        String imagePath = getIntent().getStringExtra("path");

        Log.d("Uri11", "uri: " + this.uri + " path: " + imagePath);
        
        if (this.uri != null) {
            imageView.setImageURI(this.uri);
        } else if (imagePath != null) {
            this.uri = Uri.fromFile(new File(imagePath));
            Glide.with(this).load(new File(imagePath)).into(imageView);
        }

//        findViewById(R.id.img_final_card).setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.screens.-$$Lambda$ShareActivity$nh81uAJ90lOf3cnSZiLMBv5kyN4
//            @Override // android.view.View.OnClickListener
//            public final void onClick(View view) {
//                openImage();
//            }
//        });

    }


    private void rate_buttonNext() {
        rateSubmit = preferenceClass.getRateSubmited("rateSubmitted");
        if (rateSubmit) {
            return;
        }
        // startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        /*  Drawable icon = getResources().getDrawable(R.drawable.dialog_icon);*/
        RateButtonDialog ratingDialog = new RateButtonDialog(TART_ShareActivity.this, "Rate the App", "Please rate the app and provide your feedback.", new RateButtonDialog.onRatingDialogListener() {
            @Override
            public void onRatingSelected(float rating) {
            }

            @Override
            public void onDialogCancelled() {
            }
        }, uri);

        ratingDialog.Show();
    }

    private boolean isFirstTime() {
        if (this.firstTime == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("first_time", 0);
            this.firstTime = Boolean.valueOf(sharedPreferences.getBoolean("firstTime", true));
            if (Boolean.TRUE.equals(this.firstTime)) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("firstTime", false);
                edit.apply();
            }
        }
        return this.firstTime.booleanValue();
    }

    private void addControls() {
        this.imageView = (ImageView) findViewById(R.id.img_final);
        findViewById(R.id.ltWall).setOnClickListener(this);
        findViewById(R.id.ltShare).setOnClickListener(this);
        this.relativeLayout = (LinearLayout) findViewById(R.id.ltSaveShare);
        findViewById(R.id.btnBackShare).setOnClickListener(this);
        findViewById(R.id.btn_new).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            int id = view.getId();
            if (id == R.id.btnBackShare) {
                super.onBackPressed();
                return;
            } else if (id == R.id.btn_new) {
                MyApplication.showInterstitialAd(TART_ShareActivity.this, () -> goToHome());
                return;
            } else if (id == R.id.ltShare) {
                Uri createCacheFile = uri;
                if (createCacheFile != null) {
                    Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(createCacheFile.getPath()));
                    Intent intent2 = new Intent();
                    intent2.setAction(Intent.ACTION_SEND);
                    intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent2.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                    intent2.putExtra(Intent.EXTRA_STREAM, contentUri);
                    startActivity(Intent.createChooser(intent2, "Choose an app"));
                    rate_buttonNext();
                    return;
                }
                Toast.makeText(this, "Fail to share", Toast.LENGTH_SHORT).show();
                return;
            } else if (id == R.id.ltWall) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Set Wallpaper")
                            .setMessage("Are you sure you want to set this image as your device wallpaper?")
                            .setPositiveButton("OK", (dialog, which) -> {
                                MyApplication.showInterstitialAd(TART_ShareActivity.this, () -> {
                                    setAsWallpaper();
                                    rate_buttonNext();
                                });
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                return;
            }
        }
    }

    /*    public void setAsWallpaper() {
            Uri createcachefile2 = createcachefile();
            if (createcachefile2 != null) {
                Intent intent3 = new Intent("android.intent.action.ATTACH_DATA");
                intent3.setDataAndType(createcachefile2, getContentResolver().getType(createcachefile2));
                intent3.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent3, "Use as"));
                return;
            }
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAsWallpaper() {
        Uri imageUri = uri;
        if (imageUri != null) {
            InputStream inputStream = null;
            try {
                // Open InputStream to get Bitmap
                inputStream = getContentResolver().openInputStream(imageUri);
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.e("TAG", "setAsWallpaper bitmap: "+bitmap);
                    if (bitmap != null) {
                        // Get WallpaperManager instance
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        // Set the bitmap as wallpaper
                        wallpaperManager.setBitmap(bitmap);
                        Toast.makeText(this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to decode bitmap", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Error: InputStream is null", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Failed to create cache file", Toast.LENGTH_SHORT).show();
        }
    }


    public void goToHome() {
        Intent intent = new Intent(this, TART_MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void sharePhoto(String str) {
        if (isPackageInstalled(this, str)) {
            Uri createcachefile = createcachefile();
            if (createcachefile != null) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(createcachefile, getContentResolver().getType(createcachefile));
                intent.putExtra("android.intent.extra.STREAM", createcachefile);
                intent.setPackage(str);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "Fail to sharing", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Can't find this App, please download and try it again", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setData(Uri.parse("market://details?id=" + str));
        startActivity(intent2);
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private Uri createcachefile() {
        try {
            File file = new File(getCacheDir(), "images");
            boolean mkdirs = file.mkdirs();
            Log.d("f", mkdirs + "");
            FileOutputStream fileOutputStream = new FileOutputStream(file + "/image.png");
            this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            Log.e("NULL", String.valueOf(e2));
        }
        return FileProvider.getUriForFile(this, "com.addtext.textonphoto.textart.fileprovider", new File(new File(getCacheDir(), "images"), "image.png"));
    }

//    public void openImage() {
//        Uri fromFile;
//        if (Build.VERSION.SDK_INT <= 28) {
//            File file = new File((String) Objects.requireNonNull(this.uri.getPath()));
//            Intent intent = new Intent("android.intent.action.VIEW");
//            if (Build.VERSION.SDK_INT >= 24) {
//                fromFile = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
//            } else {
//                fromFile = Uri.fromFile(file);
//            }
//            startActivity(intent.setDataAndType(fromFile, "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
//            return;
//        }
//        startActivity(new Intent("android.intent.action.VIEW", this.uri));
//    }
}
