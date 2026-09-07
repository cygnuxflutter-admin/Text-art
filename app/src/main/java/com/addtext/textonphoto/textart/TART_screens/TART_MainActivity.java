package com.addtext.textonphoto.textart.TART_screens;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity.PICKER_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.AppData;
import com.addtext.textonphoto.textart.DataBaseHelper;
import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_PhotoPicker;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Category;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Quotes;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.addtext.textonphoto.textart.TART_views.CustomTabChrom;
import com.addtext.textonphoto.textart.Utils;
import com.addtext.textonphoto.textart.adManager.TART_NativeAdUtil;
import com.addtext.textonphoto.textart.imagepicker.ImagePickerActivity;
import com.addtext.textonphoto.textart.imagepicker.KSUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.onesignal.OneSignal;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class TART_MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String VERSION_NUMBER = "text_photo";
    private static final String TAG = "MainAcitivty";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    public final int GALLERY_INTENT = 102;
    final private int REQUEST_CAMERA_AND_STORAGE_PERMISSION = 100;
    public String mCurrentPhotoPath;
    public int requestMode = 1;

    View camera;
    DataBaseHelper dbhelper;
    View gallery;
    AppData mAppData;
    SharedPreferences pref;
    View sample;
    View settings;
    View rateus_button;
    boolean mRedirect = false;
    Boolean rateSubmit;
    Activity activity = TART_MainActivity.this;

    private TART_PreferenceClass preferenceClass;

    boolean isPermissionGranted = false;
    private static final int PERMISSION_REQUEST_CODE = 123;

//    GifImageView mainActivityGame;
    private ConsentInformation consentInformation;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        /*        getWindow().setFlags(67108864, 67108864);*/
        setContentView(R.layout.knack_activity_main);
        // Check if the required permissions are granted

        checkSelfPermission();


        preferenceClass = new TART_PreferenceClass(this);

        initViews();

        try {
            ConsentRequestParameters params = new ConsentRequestParameters
                    .Builder()
                    .build();

            consentInformation = UserMessagingPlatform.getConsentInformation(this);
            consentInformation.requestConsentInfoUpdate(
                    this,
                    params,
                    (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
                        UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                                this,
                                (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                    if (loadAndShowError != null) {
                                        Log.w("TAG52451", String.format("%s: %s",
                                                loadAndShowError.getErrorCode(),
                                                loadAndShowError.getMessage()));
                                    }
                                }
                        );
                    },
                    (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
                        Log.w("TAG54697", String.format("%s: %s",
                                requestConsentError.getErrorCode(),
                                requestConsentError.getMessage()));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        rateSubmit = preferenceClass.getRateSubmited("rateSubmitted");

        this.mAppData = AppData.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(VERSION_NUMBER, 0);
        this.pref = sharedPreferences;
        this.mRedirect = sharedPreferences.getBoolean("suspended", false);
        if (getVersioncode() != this.pref.getInt("pref_version", 0)) {
            Log.d("version_update", "need update");
            File databasePath = getDatabasePath("tpquotes.sqlite");
            if (databasePath.exists()) {
                databasePath.delete();
            }
            Log.d("version_update", "is deleted ");
            SharedPreferences.Editor edit = this.pref.edit();
            edit.putInt("pref_version", getVersioncode());
            edit.apply();
        } else {
            Log.d("version_update", "no update " + this.pref.getInt("pref_version", 0));
            Log.d("version", "equal" + getVersioncode());
            SharedPreferences.Editor edit2 = this.pref.edit();
            edit2.putInt("pref_version", getVersioncode());
            edit2.apply();
        }


        this.dbhelper = new DataBaseHelper(this);
        new Async(this, this.dbhelper).execute(new Void[0]);

        try {
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            OneSignal.initWithContext(TART_MainActivity.this);
            OneSignal.setAppId("83d4adaf-4ae7-4f59-b91a-d0050698af6a");
            OneSignal.promptForPushNotifications();
            OneSignal.sendTag("Apps", "Text Art");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
//        String packageName = "com.android.chrome";
//        if (packageName != null) {
//            customTabsIntent.intent.setPackage(packageName);
//            customTabsIntent.launchUrl(activity, uri);
//        } else {
//            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
//        }
//    }
    private int getVersioncode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        if (!this.mRedirect) {
            switch (view.getId()) {
                case R.id.btCamera:
                    MyApplication.showInterstitialAd(TART_MainActivity.this, () -> dispatchTakePictureIntent());
                    //  dispatchTakePictureIntent();
                    return;
                case R.id.btGallery:
                    MyApplication.showInterstitialAd(TART_MainActivity.this, () -> pickFromGalery());
                    //  pickFromGalery();
                    return;
                case R.id.btSample:
                    MyApplication.showInterstitialAd(TART_MainActivity.this, () -> btSampleOnclickNext());
                    return;
                case R.id.btnNotification:
                    startActivity(new Intent(TART_MainActivity.this, com.addtext.textonphoto.textart.TART_notifications.TART_NotificationActivity.class));
                    return;
                case R.id.btrateButton:
/*//                    MyApplication.showInterstitialAdWithOutCount(MainActivity.this, () ->    SettingsNext());
                    //   if (!rateSubmit) {
//                    rate_buttonNext();
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                    customIntent.setToolbarColor(ContextCompat.getColor(TART_MainActivity.this, R.color.custome_chrom_color));
                    CustomTabChrom.openCustomTab(TART_MainActivity.this, customIntent.build(), Uri.parse("market://details?id=" + getPackageName()));
                  *//*  } else {
                        Toast.makeText(this, "rating submitted allready", Toast.LENGTH_SHORT).show();
                    }*//*
//                       launchPromoLink("https://play.google.com/store/apps/details?id=" + getPackageName());
//                      rate_buttonNext();*/

                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(myAppLinkToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                    }
                    return;

                default:
                    return;
            }
        }
    }


    private void SettingsNext() {
        startActivity(new Intent(TART_MainActivity.this, TART_SettingsActivity.class));
    }

    private void rate_buttonNext() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
        }


    }





    private void btSampleOnclickNext() {
        if (checkSelfPermission()) {
            TART_MainActivity.this.startActivity(new Intent(TART_MainActivity.this, TART_SampleActivity.class));
        } else {
            showToast(getString(R.string.permission_denied));
        }

    }


    public File createImageFile() throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File createTempFile = File.createTempFile("JPEG_" + format + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        mCurrentPhotoPath = createTempFile.getAbsolutePath();
        return createTempFile;
    }

    private void initViews() {
        sample = findViewById(R.id.btSample);
        camera = findViewById(R.id.btCamera);
        gallery = findViewById(R.id.btGallery);
        settings = findViewById(R.id.btnNotification);
        rateus_button = findViewById(R.id.btrateButton);

        if (camera != null) camera.setOnClickListener(TART_MainActivity.this);
        if (gallery != null) gallery.setOnClickListener(TART_MainActivity.this);
        if (sample != null) sample.setOnClickListener(TART_MainActivity.this);
        if (settings != null) settings.setOnClickListener(TART_MainActivity.this);
        if (rateus_button != null) rateus_button.setOnClickListener(TART_MainActivity.this);

        androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(() -> {
                setupRecentProjects();
                swipeRefresh.setRefreshing(false);
            });
        }

        setupBottomNav();
        setupCategoryChips();
        setupRecentProjects();
        setupTrendingTemplates();

        androidx.cardview.widget.CardView nativeAdContainer = findViewById(R.id.cvNativeAdContainerHome);
        if (nativeAdContainer != null) {
            TART_NativeAdUtil.loadNativeAd(nativeAdContainer, this, true);
        }
    }

    private void setupBottomNav() {
        View navHome = findViewById(R.id.navHome);
        View navTemplates = findViewById(R.id.navTemplates);
        View navCreate = findViewById(R.id.navCreate);
        View navGallery = findViewById(R.id.navGallery);
        View navProfile = findViewById(R.id.navProfile);

        if (navTemplates != null) {
            navTemplates.setOnClickListener(v -> MyApplication.showInterstitialAd(TART_MainActivity.this, this::btSampleOnclickNext));
        }
        if (navCreate != null) {
            navCreate.setOnClickListener(v -> MyApplication.showInterstitialAd(TART_MainActivity.this, () -> {
                startActivity(new Intent(TART_MainActivity.this, TART_ColorPickerActivity.class));
            }));
        }
        if (navGallery != null) {
            navGallery.setOnClickListener(v -> MyApplication.showInterstitialAd(TART_MainActivity.this, this::pickFromGalery));
        }
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> SettingsNext());
        }
    }

    private void setupCategoryChips() {
        // Feature removed as per user request
    }

    private void setupRecentProjects() {
        View layoutRecentHeader = findViewById(R.id.layoutRecentHeader);
        TextView tvSeeAllProjects = findViewById(R.id.tvSeeAllProjects);
        if (tvSeeAllProjects != null) {
            tvSeeAllProjects.setOnClickListener(v -> startActivity(new Intent(TART_MainActivity.this, TART_SavedProjectsActivity.class)));
        }

        androidx.recyclerview.widget.RecyclerView rvRecent = findViewById(R.id.rvRecentProjects);
        if (rvRecent != null) {
            List<com.addtext.textonphoto.textart.TART_viewadapter.TART_RecentProjectsAdapter.ProjectItem> list = new ArrayList<>();
            java.io.File dir = new java.io.File(android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES), "TextOnPhoto");
            if (dir.exists() && dir.isDirectory()) {
                java.io.File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
                if (files != null && files.length > 0) {
                    java.util.Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
                    int count = Math.min(files.length, 5);
                    for (int i = 0; i < count; i++) {
                        java.io.File file = files[i];
                        if (file.exists() && file.length() > 0) {
                            CharSequence relativeTime = android.text.format.DateUtils.getRelativeTimeSpanString(file.lastModified(), System.currentTimeMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS);
                            list.add(new com.addtext.textonphoto.textart.TART_viewadapter.TART_RecentProjectsAdapter.ProjectItem(file.getAbsolutePath(), file.getName(), relativeTime.toString()));
                        }
                    }
                }
            }

            View layoutNoRecent = findViewById(R.id.layoutNoRecentProjects);

            if (list.isEmpty()) {
                rvRecent.setVisibility(View.GONE);
                if (layoutNoRecent != null) layoutNoRecent.setVisibility(View.VISIBLE);
            } else {
                rvRecent.setVisibility(View.VISIBLE);
                if (layoutNoRecent != null) layoutNoRecent.setVisibility(View.GONE);
                com.addtext.textonphoto.textart.TART_viewadapter.TART_RecentProjectsAdapter adapter =
                        new com.addtext.textonphoto.textart.TART_viewadapter.TART_RecentProjectsAdapter(this, list, false, item -> {
                            Intent intent = new Intent(TART_MainActivity.this, TART_ShareActivity.class);
                            intent.putExtra("path", item.imagePath);
                            startActivity(intent);
                        });
                rvRecent.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));
                rvRecent.setAdapter(adapter);
            }
        }
    }

    private void setupTrendingTemplates() {
        androidx.recyclerview.widget.RecyclerView rvTemplates = findViewById(R.id.rvTrendingTemplates);
        if (rvTemplates != null) {
            List<com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter.TemplateItem> list = new ArrayList<>();
            list.add(new com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter.TemplateItem(R.drawable.knack_tt_nature13, "Magazine Cover Layout"));
            list.add(new com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter.TemplateItem(R.drawable.knack_tt_nature9, "Social Media Story Frame"));
            list.add(new com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter.TemplateItem(R.drawable.knack_lov3, "Holiday Greeting Card"));
            list.add(new com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter.TemplateItem(R.drawable.knack_tt_nature6, "Quote graphic with sianve"));

            com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter adapter =
                    new com.addtext.textonphoto.textart.TART_viewadapter.TART_TrendingTemplatesAdapter(this, list, item -> {
                        btSampleOnclickNext();
                    });
            rvTemplates.setLayoutManager(new GridLayoutManager(this, 2));
            rvTemplates.setAdapter(adapter);
        }
    }

    private void dispatchTakePictureIntent() {
        if (checkSelfPermission()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            Log.d("Camera111", "getPackageManager not null");
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file != null && TART_MainActivity.this.mCurrentPhotoPath != null) {
                intent.putExtra("output", FileProvider.getUriForFile(TART_MainActivity.this, "com.addtext.textonphoto.textart.fileprovider", file));
                TART_MainActivity.this.startActivityForResult(intent, 111);
            }
        } else {
            showToast(getString(R.string.permission_denied));
        }
    }

    private void pickFromGalery() {
//        Matisse matisse = new Matisse(
//                getMaxSelectable(),
//                getSupportedMimeTypes(),
//                getCaptureStrategy());
//
//        imagePickerLauncher.launch(matisse);
        KSUtil.fromAlbum = false;
        Intent mIntent = new Intent(TART_MainActivity.this, ImagePickerActivity.class);
        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
        startActivityForResult(mIntent, PICKER_REQUEST_CODE);


    }
//
//    private List<MimeType> getSupportedMimeTypes() {
//        return Matisse.Companion.ofImage(true);
//    }
//
//    private int getMaxSelectable() {
//        return 1;
//    }
//
//    private CaptureStrategy getCaptureStrategy() {
//        return new MediaStoreCaptureStrategy();
//    }


    private void shareApp() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "My application name");
            intent.putExtra("android.intent.extra.TEXT", "\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, "Choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCropError(Intent intent) {
        Throwable error = UCrop.getError(intent);
        if (error != null) {
            Log.e(TAG, "handleCropError: ", error);
            Toast.makeText(TART_MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(TART_MainActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
    }

    private void handleCropResult(Intent intent) {

        Uri output = UCrop.getOutput(intent);
        if (output != null) {
//            Intent intent2 = new Intent(MainActivity.this, EditImageActivity.class);
//            intent2.setData(output);
//            startActivityForResult(intent2, 1);

            String realPath = null;
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(output, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPath = cursor.getString(columnIndex);
                cursor.close();
            }

            if (realPath != null) {
                Intent intent2 = new Intent(TART_MainActivity.this, TART_EditImageActivity.class);
//            intent2.putExtra(PhotoPicker.KEY_SELECTED_PHOTOS, output /*photo.getPath()*/);
                intent2.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, realPath/*"/storage/emulated/0/Pictures/2bc5c4dc.jpg"*/);
                startActivity(intent2);

            } else {
                Intent intent2 = new Intent(TART_MainActivity.this, TART_EditImageActivity.class);
//              intent2.putExtra(PhotoPicker.KEY_SELECTED_PHOTOS, output /*photo.getPath()*/);
                intent2.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, output/*"/storage/emulated/0/Pictures/2bc5c4dc.jpg"*/);
                startActivity(intent2);

            }


            return;
        }
        Toast.makeText(TART_MainActivity.this, "Cannot retrieve cropped image", Toast.LENGTH_SHORT).show();
    }

    private void startCrop(Uri uri) {
        UCrop of = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), "SampleCropImage")));
        of.useSourceImageAspectRatio();
        of.useSourceImageAspectRatio();
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setFreeStyleCropEnabled(true);
        of.withOptions(options);
        of.start(TART_MainActivity.this);
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);

        StringBuilder sb = new StringBuilder();
        sb.append("onActivityResult ");
        sb.append(i2);
        sb.append(" ");
        sb.append(i);
        sb.append(" ");
        sb.append(intent != null);
        Log.d("XXXXXX", sb.toString());
        if (i2 == -1) {
            if (i == PICKER_REQUEST_CODE) {
                KSUtil.videoPathList.clear();
                KSUtil.videoPathList = intent.getExtras().getStringArrayList(ImagePickerActivity.KEY_DATA_RESULT);

                Log.e(TAG, "onActivityResult: " + KSUtil.videoPathList);
                if (KSUtil.videoPathList != null && !KSUtil.videoPathList.isEmpty()) {
                    Intent intent2 = new Intent(TART_MainActivity.this, TART_EditImageActivity.class);
                    intent2.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, KSUtil.videoPathList.get(0) /*"/storage/emulated/0/Pictures/2bc5c4dc.jpg"*/);
                    startActivity(intent2);
                    // startActivityForResult(intent2, 0);


//                imageList.clear();
//                imageList.addAll(data);

//                    startCrop(data);

//                    MyApplication.showInterstitialAd(TART_MainActivity.this, () -> startIntentGallery(getRealPathFromURI(data)));
                } else {
                    Toast.makeText(TART_MainActivity.this, "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }

            } else if (i == 111) {
                Uri fromFile = Uri.fromFile(new File(mCurrentPhotoPath));
                if (fromFile != null) {
//                    startCrop(fromFile);
                    MyApplication.showInterstitialAd(TART_MainActivity.this, () -> startIntentCamera());

                } else {
                    Toast.makeText(TART_MainActivity.this, "Cannot capture picture", Toast.LENGTH_SHORT).show();
                }
            } else if (i == 69) {
                if (intent == null) {
                    return;
                }
                handleCropResult(intent);
//                MyApplication.showInterstitialAdWithOutCount(MainActivity.this, () ->handleCropResult(intent) );
                //handleCropResult(intent);
            }
        }
        if (i2 == 96 && intent != null) {
            handleCropError(intent);
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onBackPressed() {
        ShowExitDialouge();
    }

    public void startIntentGallery(String path) {
        Intent intent2 = new Intent(TART_MainActivity.this, TART_EditImageActivity.class);
//            intent2.putExtra(PhotoPicker.KEY_SELECTED_PHOTOS, output /*photo.getPath()*/);
        intent2.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, path /*"/storage/emulated/0/Pictures/2bc5c4dc.jpg"*/);
        startActivity(intent2);
    }

    public void startIntentCamera() {
        Intent intent2 = new Intent(TART_MainActivity.this, TART_EditImageActivity.class);
        intent2.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, mCurrentPhotoPath);
        startActivity(intent2);
    }

    public boolean checkSelfPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            }
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA},
                    REQUEST_CAMERA_AND_STORAGE_PERMISSION);
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // You can request the permission here or show an explanation why the permission is needed
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_AND_STORAGE_PERMISSION);
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CAMERA_AND_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted
                    // You can now use the camera and storage
//                    Toast.makeText(activity, "permission", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission is denied
                    // You can disable the camera and storage features or show a message to the user

                }
                break;
        }
    }

    private void showToast(String message) {
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowExitDialouge() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.knack_dialouge_logout, null);
        dialogBuilder.setView(dialogView);
        TextView cancel = dialogView.findViewById(R.id.cancel);
        TextView exit = dialogView.findViewById(R.id.exit);
        RelativeLayout native_banner_ad_container = dialogView.findViewById(R.id.native_banner_ad_container);
        TART_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(800, 600);

        alertDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                alertDialog.dismiss();
            }
        });
    }

    public class Async extends AsyncTask<Void, Void, ArrayList<TART_Category>> {
        Context context;
        DataBaseHelper db;

        public Async(Context context, DataBaseHelper dataBaseHelper) {
            this.db = dataBaseHelper;
            this.context = context;
        }


        @Override
        public ArrayList<TART_Category> doInBackground(Void... voidArr) {
            ArrayList<TART_Category> category = this.db.getCategory();
            ArrayList<TART_Quotes> favouriteList = this.db.getFavouriteList();
            TART_MainActivity.this.mAppData.categoryArrayList = category;
            TART_MainActivity.this.mAppData.favouriteArrayList = favouriteList;
            for (int i = 0; i < category.size(); i++) {
                TART_MainActivity.this.mAppData.quote_map.put(category.get(i).getId(), this.db.getQuotes(category.get(i).getId()));
            }
            return category;
        }


        @Override
        public void onPostExecute(ArrayList<TART_Category> arrayList) {
            super.onPostExecute(arrayList);
        }
    }
}
