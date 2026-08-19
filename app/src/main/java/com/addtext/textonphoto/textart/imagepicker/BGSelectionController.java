package com.addtext.textonphoto.textart.imagepicker;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_utils.TART_MaterialDialogUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

public class BGSelectionController {

    public final int CAMERA_INTENT = 905;
    public final int GALLERY_INTENT = 907;
    public String mode;
    public File camera_file;

    private final Activity activity;
    private final FragmentManager supportFragmentManager;
    private final TART_PreferenceClass preferenceClass;
    private MaterialDialog materialDialog;

    public BGSelectionController(Activity activity, FragmentManager supportFragmentManager, TART_PreferenceClass preferenceClass, String mode) {
        this.activity = activity;
        this.supportFragmentManager = supportFragmentManager;
        this.preferenceClass = preferenceClass;
        this.mode = mode;
//        loadBgImages();
    }

//    public void loadBgImages() {
//        startMaterialDialog();
//        getBgThumb(preferenceClass.getDataType("field_0"));
//    }

//    private void getBgThumb(String key) {
//        String requestUrl = preferenceClass.getDataType("field_1") + preferenceClass.getDataType("field_34") + preferenceClass.getDataType("field_38");
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, response -> {
//            String text1 = null;
//
//            Log.d("qwertyu", "getBgThumb: "+response);
////            if (preferenceClass.getDecryptionType() == 0) {
////                byte[] octets = Base64.decode(response, Base64.URL_SAFE);
////                String text_base = new String(octets, StandardCharsets.UTF_8);
////                byte[] octets1 = Base64.decode(text_base, Base64.URL_SAFE);
////                text1 = new String(octets1, StandardCharsets.UTF_8);
////            } else if (preferenceClass.getDecryptionType() == 1) {
////                MainSecurity decrypted = MainSecurity.decrypt(preferenceClass.getDataType("main_key"), response);
////                text1 = decrypted.getData();
////            }
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONArray jsonArray = jsonObject.getJSONArray(preferenceClass.getDataType("field_39"));
//                if (jsonArray.length() == 0) {
//                    MaterialDialogUtils.getInstance().errorDialog2(activity, activity.getResources().getString(R.string.something_went_wrong));
//                } else {
//                    new GetBgData(preferenceClass, jsonArray, new GetBgData.OnGetCatDataListener() {
//                        @Override
//                        public void onGetDataComplete(ArrayList<BgModel> posterDataLists) {
//                            setPagerAdapter(posterDataLists);
//                        }
//
//                        @Override
//                        public void onError() {
//                            dismissMaterialDialog();
//                            MaterialDialogUtils.getInstance().errorDialog(activity, activity.getResources().getString(R.string.something_went_wrong));
//                        }
//                    }).execute();
//                }
//            } catch (Exception e) {
//                dismissMaterialDialog();
//                MaterialDialogUtils.getInstance().errorDialog(activity, activity.getResources().getString(R.string.something_went_wrong));
//                e.printStackTrace();
//            }
//        }, error -> {
//            dismissMaterialDialog();
//            MaterialDialogUtils.getInstance().errorDialog(activity, activity.getResources().getString(R.string.something_went_wrong));
//            error.printStackTrace();
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> postMap = new HashMap<>();
//                postMap.put(preferenceClass.getDataType("field_47"), key);
//                postMap.put(preferenceClass.getDataType("field_48"), "1");
//                return postMap;
//            }
//        };
//        Volley.newRequestQueue(activity).add(stringRequest);
//    }

//    private void setPagerAdapter(ArrayList<BgModel> posterDataLists) {
//        PagerSlidingTabStrip tabs = activity.findViewById(R.id.pagerSlidingTabStrip);
//        ViewPager viewPager = activity.findViewById(R.id.viewPager);
//        viewPager.setAdapter(new BackgroundPagerAdapter(supportFragmentManager, posterDataLists));
//        viewPager.setCurrentItem(0);
//        tabs.setViewPager(viewPager);
//        dismissMaterialDialog();
//    }

    public void openPhotoGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    activity.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_GRANTED);
                } else {
                    TART_MaterialDialogUtils.getInstance().PermissionDialog(activity);
                }
                return;
            }
        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(R.string.select_picture)), GALLERY_INTENT);
    }

//    public void openCamera() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (activity.checkSelfPermission("android.permission.CAMERA") != PERMISSION_GRANTED
//                    || activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PERMISSION_GRANTED
//                    || activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    activity.requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_GRANTED);
//                } else {
//                    MaterialDialogUtils.getInstance().PermissionDialog(activity);
//                }
//                return;
//            }
//        }
//
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////        camera_file = new File(Environment.getExternalStorageDirectory(), ".temp.jpg");
//        camera_file = new File(activity.getCacheDir(), ".temp.jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider",
//                        camera_file));
//        activity.startActivityForResult(intent, CAMERA_INTENT);
//    }

//    public void openColorDialog() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
//                    || activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    activity.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_GRANTED);
//                } else {
//                    MaterialDialogUtils.getInstance().PermissionDialog(activity);
//                }
//                return;
//            }
//        }
//        itemColorAdapter();
//    }

//    public void itemColorAdapter() {
//        Dialog dialogColor = new Dialog(activity, R.style.DialogTheme);
//        dialogColor.setContentView(R.layout.color_pallete_layout);
//
//        Objects.requireNonNull(dialogColor.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation_2;
//
//        dialogColor.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//        ImageView tvBack = dialogColor.findViewById(R.id.tv_back);
//
//        tvBack.setOnClickListener(v -> dialogColor.dismiss());
//
//        ImageView tvCustom = dialogColor.findViewById(R.id.tv_custom);
//        tvCustom.setOnClickListener(v -> {
//            dialogColor.dismiss();
//            openColorPicker();
//        });
//
//        RecyclerView rvSubList = dialogColor.findViewById(R.id.rv_sub_list);
//        rvSubList.setLayoutManager(new GridLayoutManager(activity, 3));
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        float screenDensity = activity.getResources().getDisplayMetrics().density;
//        int screenWidth = (displayMetrics).widthPixels;
//        int cellWidth = (int) (screenWidth - (42 * screenDensity)) / 3;
//        int cellHeight = (700 * cellWidth) / 507;
//
//        ColorPelleteAdapter colorListAdapter = new ColorPelleteAdapter(activity, cellWidth, cellHeight, (colorCode) -> {
//            Bitmap bitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
//            bitmap.eraseColor(Color.parseColor(colorCode));
//            new SaveBitmapTask(bitmap, new File(/*Environment.getExternalStorageDirectory()*/activity.getCacheDir(), ".temp.jpg").getPath(), new SaveBitmapTask.OnColorBitmapListener() {
//                @Override
//                public void onDownloadComplete() {
//                    if (mode.equals("user")) {
//                        Intent intent = new Intent();
//                        intent.putExtra("local", false);
//                        intent.putExtra("bg_image", new File(activity.getCacheDir(), ".temp.jpg").getPath());
////                        intent.putExtra("bg_image", new File(Environment.getExternalStorageDirectory(), ".temp.jpg").getPath());
//                        activity.setResult(RESULT_OK, intent);
//                        activity.finish();
//                    } else {
//                        startCrop(Uri.fromFile(new File(activity.getCacheDir(), ".temp.jpg")));
////                        startCrop(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), ".temp.jpg")));
//                    }
//                }
//
//                @Override
//                public void onError() {
//                    //Toast.makeText(activity, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//                }
//            }).execute();
//        });
//
//        rvSubList.setAdapter(colorListAdapter);
//
//        dialogColor.show();
//    }

//    private void openColorPicker() {
//        new AmbilWarnaDialog(activity, Color.CYAN, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//            public void onOk(AmbilWarnaDialog dialog, int color) {
//                Bitmap bitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
//                bitmap.eraseColor(color);
//                new SaveBitmapTask(bitmap, new File(/*Environment.getExternalStorageDirectory()*/activity.getCacheDir(), ".temp.jpg").getPath(), new SaveBitmapTask.OnColorBitmapListener() {
//                    @Override
//                    public void onDownloadComplete() {
//                        if (mode.equals("user")) {
//                            Intent intent = new Intent();
//                            intent.putExtra("local", false);
////                            intent.putExtra("bg_image", new File(Environment.getExternalStorageDirectory(), ".temp.jpg").getPath());
//                            intent.putExtra("bg_image", new File(activity.getCacheDir(), ".temp.jpg").getPath());
//                            activity.setResult(RESULT_OK, intent);
//                            activity.finish();
//                        } else {
////                            startCrop(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), ".temp.jpg")));
//                            startCrop(Uri.fromFile(new File(activity.getCacheDir(), ".temp.jpg")));
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//                        // Toast.makeText(activity, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//                    }
//                }).execute();
//            }
//
//            public void onCancel(AmbilWarnaDialog dialog) {
//            }
//        }).show();
//    }

//    public void startCrop(@NonNull Uri uri) {
//        String destinationFileName = "SampleCropImage.png";
////        File file=new File(activity.getCacheDir(), destinationFileName);
////        if (file.exists()){
////            file.delete();
////        }
////        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), destinationFileName)));
//        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), destinationFileName)));
//        uCrop = advancedConfig(uCrop);
//        uCrop.start(activity);
//    }


//    private UCrop advancedConfig(@NonNull UCrop uCrop) {
//        UCrop.Options options = new UCrop.Options();
//        options.setToolbarColor(ContextCompat.getColor(activity, R.color.purple_700));
//        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.purple_700));
//        options.setRootViewBackgroundColor(ContextCompat.getColor(activity, R.color.purple_200));
//        options.setAspectRatioOptions(1,
//                new AspectRatio("1:1", 1, 1),
//                new AspectRatio("3:2", 3, 2),
//                new AspectRatio("2:3", 2, 3),
//                new AspectRatio("4:3", 4, 3),
//                new AspectRatio("3:4", 3, 4),
//                new AspectRatio("16:9", 16, 9),
//                new AspectRatio("5:4", 5, 4),
//                new AspectRatio("4:5", 4, 5));
//        return uCrop.withOptions(options);
//    }

//    public void startMaterialDialog() {
//        materialDialog = MaterialDialogUtils.getInstance().createAnimationDialog(activity);
//        Objects.requireNonNull(materialDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        materialDialog.setCancelable(false);
//        materialDialog.show();
//    }

    public void dismissMaterialDialog() {
        if (materialDialog != null && materialDialog.isShowing())
            materialDialog.dismiss();
    }

}
