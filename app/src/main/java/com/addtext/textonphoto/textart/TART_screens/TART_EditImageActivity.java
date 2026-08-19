package com.addtext.textonphoto.textart.TART_screens;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.addtext.textonphoto.textart.MyApplication;
import com.addtext.textonphoto.textart.Utils;
import com.addtext.textonphoto.textart.adManager.TART_LoadAds;
import com.addtext.textonphoto.textart.adManager.TART_RewardVideoManager;
import com.addtext.textonphoto.textart.TART_utils.TART_MaterialDialogUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_NetworkUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PreferenceClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hold1.keyboardheightprovider.KeyboardHeightProvider;
import com.addtext.textonphoto.textart.R;

import com.addtext.textonphoto.textart.TART_base.TART_BaseActivity;
import com.addtext.textonphoto.textart.TART_features.TART_ColorSplashDialog;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_AddTextProperties;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_TextEditorDialogFragment;
import com.addtext.textonphoto.textart.TART_features.TART_adjust.TART_AdjustAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_adjust.TART_AdjustListener;
import com.addtext.textonphoto.textart.TART_features.TART_beauty.TART_BeautyDialog;
import com.addtext.textonphoto.textart.TART_features.TART_crop.TART_CropDialogFragment;
import com.addtext.textonphoto.textart.TART_features.TART_draw.TART_BrushColorListener;
import com.addtext.textonphoto.textart.TART_features.TART_draw.TART_BrushMagicListener;
import com.addtext.textonphoto.textart.TART_features.TART_draw.TART_ColorAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_draw.TART_MagicBrushAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_insta.TART_InstaDialog;
import com.addtext.textonphoto.textart.TART_features.TART_mosaic.TART_MosaicDialog;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_PhotoPicker;
import com.addtext.textonphoto.textart.TART_features.TART_splash.TART_SplashDialog;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TART_RecyclerTabLayout;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TART_StickerAdapter;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TART_TopTabEditAdapter;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterListener;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterUtils;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterViewAdapter;
import com.addtext.textonphoto.textart.TART_photoeditor.TART_DrawBitmapModel;
import com.addtext.textonphoto.textart.TART_photoeditor.TART_OnPhotoEditorListener;
import com.addtext.textonphoto.textart.TART_photoeditor.TART_PhotoEditor;
import com.addtext.textonphoto.textart.TART_photoeditor.TART_PhotoEditorView;
import com.addtext.textonphoto.textart.TART_photoeditor.TART_ViewType;
import com.addtext.textonphoto.textart.TART_sticker.TART_BitmapStickerIcon;
import com.addtext.textonphoto.textart.TART_sticker.TART_DrawableSticker;
import com.addtext.textonphoto.textart.TART_sticker.Sticker;
import com.addtext.textonphoto.textart.TART_sticker.TART_StickerView;
import com.addtext.textonphoto.textart.TART_sticker.TART_TextSticker;
import com.addtext.textonphoto.textart.TART_sticker.TART_event.TART_AlignHorizontallyEvent;
import com.addtext.textonphoto.textart.TART_sticker.TART_event.TART_DeleteIconEvent;
import com.addtext.textonphoto.textart.TART_sticker.TART_event.TART_EditTextIconEvent;
import com.addtext.textonphoto.textart.TART_sticker.TART_event.TART_FlipHorizontallyEvent;
import com.addtext.textonphoto.textart.TART_sticker.TART_event.TART_ZoomIconEvent;
import com.addtext.textonphoto.textart.TART_tools.TART_EditingToolsAdapter;
import com.addtext.textonphoto.textart.TART_tools.TART_ToolType;
import com.addtext.textonphoto.textart.TART_utils.TART_AssetUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_FileUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_SharePreferenceUtil;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;

import org.jetbrains.annotations.NotNull;
import org.wysaid.myUtils.MsgUtil;
import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class TART_EditImageActivity extends TART_BaseActivity implements TART_OnPhotoEditorListener, View.OnClickListener, TART_StickerAdapter.OnClickStickerListener, TART_CropDialogFragment.OnCropPhoto, TART_BrushColorListener, TART_BrushMagicListener, TART_InstaDialog.InstaSaveListener, TART_SplashDialog.SplashDialogListener, TART_BeautyDialog.OnBeautySave, TART_MosaicDialog.MosaicDialogListener, TART_EditingToolsAdapter.OnItemSelected, TART_FilterListener, TART_AdjustListener {
    private static final String TAG = "EditImageActivity";
    public ImageView addNewSticker;
    private ImageView addNewText;
    private ConstraintLayout adjustLayout;
    private SeekBar adjustSeekBar;
    private TextView brush;
    private TextView brushBlur;
    private ConstraintLayout brushLayout;
    private SeekBar brushSize;
    private ImageView compareAdjust;
    public ImageView compareFilter;
    public ImageView compareOverlay;
    public TART_ToolType currentMode = TART_ToolType.NONE;
    private ImageView erase;
    private SeekBar eraseSize;
    public SeekBar filterIntensity;
    public ConstraintLayout filterLayout;
    private KeyboardHeightProvider keyboardHeightProvider;
    private RelativeLayout loadingView;

    public ArrayList lstBitmapWithFilter = new ArrayList<>();

    public List<Bitmap> lstBitmapWithOverlay = new ArrayList<>();

    public TART_AdjustAdapter mAdjustAdapter;

    private RecyclerView mColorBush;
    private final TART_EditingToolsAdapter mEditingToolsAdapter = new TART_EditingToolsAdapter(this);
    public CGENativeLibrary.LoadImageCallback mLoadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String str, Object obj) {
            try {
                return BitmapFactory.decodeStream(TART_EditImageActivity.this.getAssets().open(str));
            } catch (IOException io) {
                return null;
            }
        }

        public void loadImageOK(Bitmap bitmap, Object obj) {
            bitmap.recycle();
        }
    };
    private RecyclerView mMagicBrush;
    public TART_PhotoEditor mPhotoEditor;
    public TART_PhotoEditorView mPhotoEditorView;
    private String rewardType = "";
    public RelativeLayout rl_watermark;
    public ImageView btn_watermark, btn_watermark_remove;
    private ConstraintLayout mRootView;
    private RecyclerView mRvAdjust;
    public RecyclerView mRvFilters;

    public RecyclerView mRvOverlays;

    public RecyclerView mRvTools;
    private TextView magicBrush;
    View.OnTouchListener onCompareTouchListener = (view, motionEvent) -> {
        switch (motionEvent.getAction()) {
            case 0:
                TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(0.0f);
                return true;
            case 1:
                TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(1.0f);
                return false;
            default:
                return true;
        }
    };

    public SeekBar overlayIntensity;

    public ConstraintLayout overlayLayout;
    private ImageView redo;
    private ConstraintLayout saveControl;

    public SeekBar stickerAlpha;
    private ConstraintLayout stickerLayout;

    public TART_TextEditorDialogFragment.TextEditor textEditor;

    public TART_TextEditorDialogFragment textEditorDialogFragment;
    private ConstraintLayout textLayout;
    private ImageView undo;

    public RelativeLayout wrapPhotoView;

    public LinearLayout wrapStickerList;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        makeFullScreen();
        setContentView(R.layout.knack_activity_edit_image);
       /* if (Constants.SHOW_ADS) {
            AdmobAds.loadBanner(this);
        } else {
            findViewById(R.id.adsContainer).setVisibility(View.GONE);
        }*/

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (TART_NetworkUtils.isNetworkAvailable(this)) {
            TART_LoadAds.loadAdmobBannerAd(this, rl_ad);
        }

        initViews();
        CGENativeLibrary.setLoadImageCallback(this.mLoadImageCallback, null);
        if (Build.VERSION.SDK_INT < 26) {
            getWindow().setSoftInputMode(48);
        }
        this.mRvTools.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mRvTools.setAdapter(this.mEditingToolsAdapter);
        this.mRvFilters.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mRvFilters.setHasFixedSize(true);
        this.mRvOverlays.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mRvOverlays.setHasFixedSize(true);
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        this.mRvAdjust.setLayoutManager(new GridLayoutManager(this, 4));
        this.mRvAdjust.setHasFixedSize(true);
        this.mAdjustAdapter = new TART_AdjustAdapter(getApplicationContext(), this);
        this.mRvAdjust.setAdapter(this.mAdjustAdapter);
        this.mColorBush.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mColorBush.setHasFixedSize(true);
        this.mColorBush.setAdapter(new TART_ColorAdapter(getApplicationContext(), this));
        this.mMagicBrush.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mMagicBrush.setHasFixedSize(true);
        this.mMagicBrush.setAdapter(new TART_MagicBrushAdapter(getApplicationContext(), this));
        this.mPhotoEditor = new TART_PhotoEditor.Builder(this, this.mPhotoEditorView).setPinchTextScalable(true).build();
        this.mPhotoEditor.setOnPhotoEditorListener(this);
        toogleDrawBottomToolbar(false);
        this.brushLayout.setAlpha(0.0f);
        this.adjustLayout.setAlpha(0.0f);
        this.filterLayout.setAlpha(0.0f);
        this.stickerLayout.setAlpha(0.0f);
        this.textLayout.setAlpha(0.0f);
        this.overlayLayout.setAlpha(0.0f);
        findViewById(R.id.activitylayout).post(() -> {
            slideDown(brushLayout);
            slideDown(adjustLayout);
            slideDown(filterLayout);
            slideDown(stickerLayout);
            slideDown(textLayout);
            slideDown(overlayLayout);
        });
        new Handler().postDelayed(() -> {
            brushLayout.setAlpha(1.0f);
            adjustLayout.setAlpha(1.0f);
            filterLayout.setAlpha(1.0f);
            stickerLayout.setAlpha(1.0f);
            textLayout.setAlpha(1.0f);
            overlayLayout.setAlpha(1.0f);
        }, 1000);
        TART_SharePreferenceUtil.setHeightOfKeyboard(getApplicationContext(), 0);
        this.keyboardHeightProvider = new KeyboardHeightProvider(this);
        this.keyboardHeightProvider.addKeyboardListener(i -> {
            if (i <= 0) {
                TART_SharePreferenceUtil.setHeightOfNotch(getApplicationContext(), -i);
            } else if (textEditorDialogFragment != null) {
                textEditorDialogFragment.updateAddTextBottomToolbarHeight(TART_SharePreferenceUtil.getHeightOfNotch(getApplicationContext()) + i);
                TART_SharePreferenceUtil.setHeightOfKeyboard(getApplicationContext(), i + TART_SharePreferenceUtil.getHeightOfNotch(getApplicationContext()));
            }
        });
        if (TART_SharePreferenceUtil.isPurchased(getApplicationContext())) {
            ((ConstraintLayout.LayoutParams) this.wrapPhotoView.getLayoutParams()).topMargin = TART_SystemUtil.dpToPx(getApplicationContext(), 5);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent intent = getIntent();
            int intExtra = intent.getIntExtra("SampleBackground", 0);
            String intExtra2 = intent.getStringExtra(Utils.KEY_IMAGE);
            if (intExtra != 0) {
                Drawable d = getResources().getDrawable(intExtra);
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                TART_EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
                TART_EditImageActivity.this.updateLayout();
            } else if (intExtra2 != null && !intExtra2.isEmpty()) {
                try {
                    Glide.with(this)
                            .asBitmap()
                            .load(intExtra2)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                                    // The bitmap has been loaded successfully
                                    TART_EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
                                    TART_EditImageActivity.this.updateLayout();
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    // Failed to load the bitmap
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    // Called when the image is being loaded or cleared
                                }
                            });


                  /*  URL url = new URL(intExtra2);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    EditImageActivity.this.mPhotoEditorView.setImageSource(image);
                    EditImageActivity.this.updateLayout();*/
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                new OnLoadBitmapFromUri().execute(extras.getString(TART_PhotoPicker.KEY_SELECTED_PHOTOS));
            }
        }
    }


    private void toogleDrawBottomToolbar(boolean z) {
        int i = !z ? View.GONE : View.VISIBLE;
        this.brush.setVisibility(i);
        this.magicBrush.setVisibility(i);
        this.brushBlur.setVisibility(i);
        this.erase.setVisibility(i);
        this.undo.setVisibility(i);
        this.redo.setVisibility(i);
    }


    public void showEraseBrush() {
        this.brushSize.setVisibility(View.GONE);
        this.mColorBush.setVisibility(View.GONE);
        this.eraseSize.setVisibility(View.VISIBLE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.erase.setImageResource(R.drawable.knack_erase_selected);
        this.mPhotoEditor.brushEraser();
        this.eraseSize.setProgress(20);
    }


    public void showColorBlurBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.VISIBLE);
        TART_ColorAdapter colorAdapter = (TART_ColorAdapter) this.mColorBush.getAdapter();
        if (colorAdapter != null) {
            colorAdapter.setSelectedColorIndex(0);
        }
        this.mColorBush.scrollToPosition(0);
        if (colorAdapter != null) {
            colorAdapter.notifyDataSetChanged();
        }
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.erase.setImageResource(R.drawable.knack_erase);
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.brushBlur.setBackground(ContextCompat.getDrawable(this, R.drawable.knack_border_bottom));
        this.brushBlur.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.mPhotoEditor.setBrushMode(2);
        this.mPhotoEditor.setBrushDrawingMode(true);
        this.brushSize.setProgress(20);
    }


    public void showColorBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.VISIBLE);
        this.mColorBush.scrollToPosition(0);
        TART_ColorAdapter colorAdapter = (TART_ColorAdapter) this.mColorBush.getAdapter();
        if (colorAdapter != null) {
            colorAdapter.setSelectedColorIndex(0);
        }
        if (colorAdapter != null) {
            colorAdapter.notifyDataSetChanged();
        }
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.GONE);
        this.erase.setImageResource(R.drawable.knack_erase);
        this.magicBrush.setBackgroundResource(0);
        this.magicBrush.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.brush.setBackground(ContextCompat.getDrawable(this, R.drawable.knack_border_bottom));
        this.brush.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.mPhotoEditor.setBrushMode(1);
        this.mPhotoEditor.setBrushDrawingMode(true);
        this.brushSize.setProgress(20);
    }


    public void showMagicBrush() {
        this.brushSize.setVisibility(View.VISIBLE);
        this.mColorBush.setVisibility(View.GONE);
        this.eraseSize.setVisibility(View.GONE);
        this.mMagicBrush.setVisibility(View.VISIBLE);
        this.erase.setImageResource(R.drawable.knack_erase);
        this.magicBrush.setBackground(ContextCompat.getDrawable(this, R.drawable.knack_border_bottom));
        this.magicBrush.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.brush.setBackgroundResource(0);
        this.brush.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.brushBlur.setBackgroundResource(0);
        this.brushBlur.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.mPhotoEditor.setBrushMagic(TART_MagicBrushAdapter.lstDrawBitmapModel(getApplicationContext()).get(0));
        this.mPhotoEditor.setBrushMode(3);
        this.mPhotoEditor.setBrushDrawingMode(true);
        TART_MagicBrushAdapter magicBrushAdapter = (TART_MagicBrushAdapter) this.mMagicBrush.getAdapter();
        if (magicBrushAdapter != null) {
            magicBrushAdapter.setSelectedColorIndex(0);
        }
        this.mMagicBrush.scrollToPosition(0);
        if (magicBrushAdapter != null) {
            magicBrushAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        this.wrapStickerList = findViewById(R.id.wrapStickerList);
        this.mPhotoEditorView = findViewById(R.id.photoEditorView);
        this.mPhotoEditorView.setVisibility(View.INVISIBLE);
        this.mRvTools = findViewById(R.id.rvConstraintTools);
        this.mRvFilters = findViewById(R.id.rvFilterView);
        this.mRvOverlays = findViewById(R.id.rvOverlayView);
        this.mRvAdjust = findViewById(R.id.rvAdjustView);
        this.mRootView = findViewById(R.id.rootView);
        this.filterLayout = findViewById(R.id.filterLayout);
        this.overlayLayout = findViewById(R.id.overlayLayout);
        this.adjustLayout = findViewById(R.id.adjustLayout);
        this.stickerLayout = findViewById(R.id.stickerLayout);
        this.textLayout = findViewById(R.id.textControl);
        ViewPager viewPager = findViewById(R.id.sticker_viewpaper);
        this.filterIntensity = findViewById(R.id.filterIntensity);
        this.overlayIntensity = findViewById(R.id.overlayIntensity);
        this.stickerAlpha = findViewById(R.id.stickerAlpha);
        this.stickerAlpha.setVisibility(View.GONE);
        this.brushLayout = findViewById(R.id.brushLayout);
        this.mColorBush = findViewById(R.id.rvColorBush);
        this.mMagicBrush = findViewById(R.id.rvMagicBush);
        this.wrapPhotoView = findViewById(R.id.wrap_photo_view);
        this.brush = findViewById(R.id.draw);
        this.magicBrush = findViewById(R.id.brush_magic);
        this.erase = findViewById(R.id.erase);
        this.undo = findViewById(R.id.undo);
        this.undo.setVisibility(View.GONE);
        this.redo = findViewById(R.id.redo);
        this.redo.setVisibility(View.GONE);
        this.brushBlur = findViewById(R.id.brush_blur);
        this.brushSize = findViewById(R.id.brushSize);
        this.eraseSize = findViewById(R.id.eraseSize);
        this.loadingView = findViewById(R.id.loadingView);
        this.loadingView.setVisibility(View.VISIBLE);
        TextView saveBitmap = findViewById(R.id.save);
        this.saveControl = findViewById(R.id.saveControl);
        this.rl_watermark = (RelativeLayout) findViewById(R.id.rl_watermark);
        this.btn_watermark = (ImageView) findViewById(R.id.btn_watermark);
        this.btn_watermark_remove = (ImageView) findViewById(R.id.btn_watermark_remove);

        final float[] widgetDX = {0f};
        final float[] widgetDY = {0f};
        this.rl_watermark.setOnTouchListener(new View.OnTouchListener() {

            private final GestureDetector gestureDetector = new GestureDetector(TART_EditImageActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
//                    MaterialDialogUtils.getInstance().rewardDialog(PosterEditActivity.this,
//                            "Remove Watermark(One Time)", "Remove Watermark by watching Ads", materialDialog -> {
//                        rewardType = "remWatermark";
//                                AppOpenManager.loadRewardVideoAd(PosterEditActivity.this,() -> btn_watermark.setVisibility(View.GONE));
////                                MyApplication.showInterstitialAd(PosterEditActivity.this,() -> btn_watermark.setVisibility(View.GONE));
//                        if (materialDialog != null && materialDialog.isShowing())
//                            materialDialog.dismiss();
//                    }, materialDialog -> {
//                        if (materialDialog != null && materialDialog.isShowing())
//                            materialDialog.dismiss();
//                    });
                    return super.onDoubleTap(e);
                }
            });

            private static final long CLICK_TIME_THRESHOLD = 200; // Adjust as needed
            private float startX, startY;
            private long touchStartTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                float parentHeight = mPhotoEditorView.getHeight();
                float parentWidth = mPhotoEditorView.getWidth();
                float xMax = parentWidth - v.getWidth();
                float yMax = parentHeight - v.getHeight();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        touchStartTime = System.currentTimeMillis();
                        widgetDX[0] = v.getX() - event.getRawX();
                        widgetDY[0] = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + widgetDX[0];
                        newX = Math.max(0F, newX);
                        newX = Math.min(xMax, newX);
                        v.setX(newX);

                        float newY = event.getRawY() + widgetDY[0];
                        newY = Math.max(0F, newY);
                        newY = Math.min(yMax, newY);
                        v.setY(newY);
                        //hideAllControls();
                        // removeScroll();
                        break;

                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        long touchDuration = System.currentTimeMillis() - touchStartTime;

                        // Check for click event
                        if (Math.abs(endX - startX) < 10 && Math.abs(endY - startY) < 10 && touchDuration < CLICK_TIME_THRESHOLD) {

                            // Handle click event here
                            // Add your desired code logic for handling the click event
                            TART_MaterialDialogUtils.getInstance().rewardDialog(TART_EditImageActivity.this,
                                    "Remove Watermark(One Time)", "Remove Watermark by watching Ads", materialDialog -> {
                                        rewardType = "remWatermark";
                                        TART_PreferenceClass preferenceClass = new TART_PreferenceClass(TART_EditImageActivity.this);
                                        // if(preferenceClass.getDataType("PremiumAdType")!=null && preferenceClass.getDataType("PremiumAdType").equals("Reward")) {
                                        TART_RewardVideoManager.showRewardVideoAd(TART_EditImageActivity.this, new TART_RewardVideoManager.OnRewardAdLoadInterface() {
                                            @Override
                                            public void onAdClose(boolean isWithReward) {
                                                if (isWithReward) {
                                                    rl_watermark.setVisibility(View.GONE);
                                                }
                                            }

                                            @Override
                                            public void onAdFail() {
                                                MyApplication.showFaceBookInterstitial(TART_EditImageActivity.this, () -> rl_watermark.setVisibility(View.GONE));
                                            }
                                        });
                                       /* } else {
                                            MyApplication.showInterstitialAd(EditImageActivity.this, () -> rl_watermark.setVisibility(View.GONE));
                                        }*/
//                                MyApplication.showInterstitialAd(PosterEditActivity.this,() -> btn_watermark.setVisibility(View.GONE));
                                        if (materialDialog != null && materialDialog.isShowing())
                                            materialDialog.dismiss();
                                    }, materialDialog -> {
                                        if (materialDialog != null && materialDialog.isShowing())
                                            materialDialog.dismiss();
                                    });
                        }
                        break;

                    default:
                        break;
                }

                return true;
            }
        });
        saveBitmap.setOnClickListener(view -> {
            this.btn_watermark_remove.setVisibility(View.GONE);
//            if (PermissionsUtils.checkWriteStoragePermission(EditImageActivity.this)) {
            new SaveBitmapAsFile().execute();
//            }


        });
        this.compareAdjust = findViewById(R.id.compareAdjust);
        this.compareAdjust.setOnTouchListener(this.onCompareTouchListener);
        this.compareAdjust.setVisibility(View.GONE);

        this.compareFilter = findViewById(R.id.compareFilter);
        this.compareFilter.setOnTouchListener(this.onCompareTouchListener);
        this.compareFilter.setVisibility(View.GONE);

        this.compareOverlay = findViewById(R.id.compareOverlay);
        this.compareOverlay.setOnTouchListener(this.onCompareTouchListener);
        this.compareOverlay.setVisibility(View.GONE);
        findViewById(R.id.exitEditMode).setOnClickListener(view -> TART_EditImageActivity.this.onBackPressed());
        this.erase.setOnClickListener(view -> TART_EditImageActivity.this.showEraseBrush());
        this.brush.setOnClickListener(view -> TART_EditImageActivity.this.showColorBrush());
        this.magicBrush.setOnClickListener(view -> TART_EditImageActivity.this.showMagicBrush());
        this.brushBlur.setOnClickListener(view -> TART_EditImageActivity.this.showColorBlurBrush());
        this.eraseSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_EditImageActivity.this.mPhotoEditor.setBrushEraserSize((float) i);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                TART_EditImageActivity.this.mPhotoEditor.brushEraser();
            }
        });
        this.brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_EditImageActivity.this.mPhotoEditor.setBrushSize((float) (i + 10));
            }
        });
        this.stickerAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = TART_EditImageActivity.this.mPhotoEditorView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.addNewSticker = findViewById(R.id.addNewSticker);
        this.addNewSticker.setVisibility(View.GONE);
        this.addNewSticker.setOnClickListener(view -> {
            TART_EditImageActivity.this.addNewSticker.setVisibility(View.GONE);
            TART_EditImageActivity.this.slideUp(TART_EditImageActivity.this.wrapStickerList);
        });
        this.addNewText = findViewById(R.id.addNewText);
        this.addNewText.setVisibility(View.GONE);
        this.addNewText.setOnClickListener(view -> {
            TART_EditImageActivity.this.mPhotoEditorView.setHandlingSticker(null);
            TART_EditImageActivity.this.openTextFragment();
        });
        this.adjustSeekBar = findViewById(R.id.adjustLevel);
        this.adjustSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_EditImageActivity.this.mAdjustAdapter.getCurrentAdjustModel().setIntensity(TART_EditImageActivity.this.mPhotoEditor, ((float) i) / ((float) seekBar.getMax()), true);
            }
        });
        TART_BitmapStickerIcon bitmapStickerIcon = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_sticker_ic_close_white_18dp), 0, TART_BitmapStickerIcon.REMOVE);
        bitmapStickerIcon.setIconEvent(new TART_DeleteIconEvent());
        TART_BitmapStickerIcon bitmapStickerIcon2 = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_ic_sticker_ic_scale_black_18dp), 3, TART_BitmapStickerIcon.ZOOM);
        bitmapStickerIcon2.setIconEvent(new TART_ZoomIconEvent());
        TART_BitmapStickerIcon bitmapStickerIcon3 = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_ic_sticker_ic_flip_black_18dp), 1, TART_BitmapStickerIcon.FLIP);
        bitmapStickerIcon3.setIconEvent(new TART_FlipHorizontallyEvent());
        TART_BitmapStickerIcon bitmapStickerIcon4 = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_ic_rotate_black_18dp), 3, TART_BitmapStickerIcon.ROTATE);
        bitmapStickerIcon4.setIconEvent(new TART_ZoomIconEvent());
        TART_BitmapStickerIcon bitmapStickerIcon5 = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_ic_edit_black_18dp), 1, TART_BitmapStickerIcon.EDIT);
        bitmapStickerIcon5.setIconEvent(new TART_EditTextIconEvent());
        TART_BitmapStickerIcon bitmapStickerIcon6 = new TART_BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.knack_ic_center_black_18dp), 2, TART_BitmapStickerIcon.ALIGN_HORIZONTALLY);
        bitmapStickerIcon6.setIconEvent(new TART_AlignHorizontallyEvent());
        this.mPhotoEditorView.setIcons(Arrays.asList(bitmapStickerIcon, bitmapStickerIcon2, bitmapStickerIcon3, bitmapStickerIcon5, bitmapStickerIcon4, bitmapStickerIcon6));
        this.mPhotoEditorView.setBackgroundColor(-16777216);
        this.mPhotoEditorView.setLocked(false);
        this.mPhotoEditorView.setConstrained(true);
        this.mPhotoEditorView.setOnStickerOperationListener(new TART_StickerView.OnStickerOperationListener() {
            public void onStickerDragFinished(@NonNull Sticker sticker) {
            }

            public void onStickerFlipped(@NonNull Sticker sticker) {
            }

            public void onStickerTouchedDown(@NonNull Sticker sticker) {
            }

            public void onStickerZoomFinished(@NonNull Sticker sticker) {
            }

            public void onTouchDownForBeauty(float f, float f2) {
            }

            public void onTouchDragForBeauty(float f, float f2) {
            }

            public void onTouchUpForBeauty(float f, float f2) {
            }

            public void onStickerAdded(@NonNull Sticker sticker) {
                TART_EditImageActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
                TART_EditImageActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
            }

            @SuppressLint("RestrictedApi")
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TART_TextSticker) {
                    ((TART_TextSticker) sticker).setTextColor(SupportMenu.CATEGORY_MASK);
                    TART_EditImageActivity.this.mPhotoEditorView.replace(sticker);
                    TART_EditImageActivity.this.mPhotoEditorView.invalidate();
                }
                TART_EditImageActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
                TART_EditImageActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
            }

            public void onStickerDeleted(@NonNull Sticker sticker) {
                TART_EditImageActivity.this.stickerAlpha.setVisibility(View.GONE);
            }

            public void onStickerTouchOutside() {
                TART_EditImageActivity.this.stickerAlpha.setVisibility(View.GONE);
            }

            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                if (sticker instanceof TART_TextSticker) {
                    sticker.setShow(false);
                    TART_EditImageActivity.this.mPhotoEditorView.setHandlingSticker(null);
                    TART_EditImageActivity.this.textEditorDialogFragment = TART_TextEditorDialogFragment.show(TART_EditImageActivity.this, ((TART_TextSticker) sticker).getAddTextProperties());
                    TART_EditImageActivity.this.textEditor = new TART_TextEditorDialogFragment.TextEditor() {
                        public void onDone(TART_AddTextProperties addTextProperties) {
                            TART_EditImageActivity.this.mPhotoEditorView.getStickers().remove(TART_EditImageActivity.this.mPhotoEditorView.getLastHandlingSticker());
                            TART_EditImageActivity.this.mPhotoEditorView.addSticker(new TART_TextSticker(TART_EditImageActivity.this, addTextProperties));
                        }

                        public void onBackButton() {
                            TART_EditImageActivity.this.mPhotoEditorView.showLastHandlingSticker();
                        }
                    };
                    TART_EditImageActivity.this.textEditorDialogFragment.setOnTextEditorListener(TART_EditImageActivity.this.textEditor);
                }
            }
        });
        this.filterIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_EditImageActivity.this.mPhotoEditorView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
        this.overlayIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_EditImageActivity.this.mPhotoEditorView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        viewPager.setAdapter(new PagerAdapter() {
            public int getCount() {
                return 13;
            }

            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view.equals(obj);
            }


            @Override
            public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
                (container).removeView((View) object);
            }

            @NonNull
            public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(TART_EditImageActivity.this.getBaseContext()).inflate(R.layout.knack_sticker_items, null, false);
                RecyclerView recyclerView = inflate.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(TART_EditImageActivity.this.getApplicationContext(), 4));
                switch (i) {
                    case 0:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstCatFaces(), i, TART_EditImageActivity.this));
                        break;
                    case 1:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstCkeeks(), i, TART_EditImageActivity.this));
                        break;
                    case 2:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstDiadems(), i, TART_EditImageActivity.this));
                        break;
                    case 3:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstEyes(), i, TART_EditImageActivity.this));
                        break;
                    case 4:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstGiddy(), i, TART_EditImageActivity.this));
                        break;
                    case 5:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstGlasses(), i, TART_EditImageActivity.this));
                        break;
                    case 6:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstTies(), i, TART_EditImageActivity.this));
                        break;
                    case 7:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstHeardes(), i, TART_EditImageActivity.this));
                        break;
                    case 8:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstEmoj(), i, TART_EditImageActivity.this));
                        break;
                    case 9:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstTexts(), i, TART_EditImageActivity.this));
                        break;
                    case 10:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstOthers(), i, TART_EditImageActivity.this));
                        break;
                    case 11:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstMuscle(), i, TART_EditImageActivity.this));
                        break;
                    case 12:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_EditImageActivity.this.getApplicationContext(), TART_AssetUtils.lstTatoos(), i, TART_EditImageActivity.this));
                        break;
                }
                viewGroup.addView(inflate);
                return inflate;
            }
        });
        TART_RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new TART_TopTabEditAdapter(viewPager, getApplicationContext()));
        recyclerTabLayout.setPositionThreshold(0.5f);
        recyclerTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.loadingView.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.loadingView.setVisibility(View.GONE);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void onAddViewListener(TART_ViewType viewType, int i) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(int i) {
        Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(TART_ViewType viewType, int i) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onStartViewChangeListener(TART_ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onStopViewChangeListener(TART_ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCloseAdjust:
            case R.id.imgCloseBrush:
            case R.id.imgCloseFilter:
            case R.id.imgCloseOverlay:
            case R.id.imgCloseSticker:
            case R.id.imgCloseText:
                slideDownSaveView();
                onBackPressed();
                return;
            case R.id.imgSaveAdjust:
                new SaveFilterAsBitmap().execute();
                this.compareAdjust.setVisibility(View.GONE);
                slideDown(this.adjustLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.imgSaveBrush:
                showLoading(true);
                runOnUiThread(() -> {
                    mPhotoEditor.setBrushDrawingMode(false);
                    undo.setVisibility(View.GONE);
                    redo.setVisibility(View.GONE);
                    erase.setVisibility(View.GONE);
                    slideDown(brushLayout);
                    slideUp(mRvTools);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(mRootView);
                    if (!TART_SharePreferenceUtil.isPurchased(getApplicationContext())) {
                        constraintSet.connect(wrapPhotoView.getId(), 3, mRootView.getId(), 3, TART_SystemUtil.dpToPx(getApplicationContext(), 50));
                    } else {
                        constraintSet.connect(wrapPhotoView.getId(), 3, mRootView.getId(), 3, 0);
                    }
                    constraintSet.connect(wrapPhotoView.getId(), 1, mRootView.getId(), 1, 0);
                    constraintSet.connect(wrapPhotoView.getId(), 4, mRvTools.getId(), 3, 0);
                    constraintSet.connect(wrapPhotoView.getId(), 2, mRootView.getId(), 2, 0);
                    constraintSet.applyTo(mRootView);
                    mPhotoEditorView.setImageSource(mPhotoEditor.getBrushDrawingView().getDrawBitmap(mPhotoEditorView.getCurrentBitmap()));
                    mPhotoEditor.clearBrushAllViews();
                    showLoading(false);
                    updateLayout();
                });
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.imgSaveFilter:
                new SaveFilterAsBitmap().execute();
                this.compareFilter.setVisibility(View.GONE);
                slideDown(this.filterLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.imgSaveOverlay:
                new SaveFilterAsBitmap().execute();
                slideDown(this.overlayLayout);
                slideUp(this.mRvTools);
                this.compareOverlay.setVisibility(View.GONE);
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.imgSaveSticker:
                this.mPhotoEditorView.setHandlingSticker(null);
                this.mPhotoEditorView.setLocked(true);
                this.stickerAlpha.setVisibility(View.GONE);
                this.addNewSticker.setVisibility(View.GONE);
                if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                    new SaveStickerAsBitmap().execute();
                }
                slideUp(this.wrapStickerList);
                slideDown(this.stickerLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.imgSaveText:
                this.mPhotoEditorView.setHandlingSticker(null);
                this.mPhotoEditorView.setLocked(true);
                this.addNewText.setVisibility(View.GONE);
                if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                    new SaveStickerAsBitmap().execute();
                }
                slideDown(this.textLayout);
                slideUp(this.mRvTools);
                slideDownSaveView();
                this.currentMode = TART_ToolType.NONE;
                return;
            case R.id.redo:
                this.mPhotoEditor.redoBrush();
                return;
            case R.id.undo:
                this.mPhotoEditor.undoBrush();
                return;
            default:
        }
    }


    public void onPause() {
        super.onPause();
        this.keyboardHeightProvider.onPause();
    }

    public void onResume() {
        super.onResume();
        this.keyboardHeightProvider.onResume();
    }

    public void isPermissionGranted(boolean z, String str) {
       /* if (z) {
            new SaveBitmapAsFile().execute();
        }*/
    }

    public void onFilterSelected(String str) {
        this.mPhotoEditor.setFilterEffect(str);
        this.filterIntensity.setProgress(100);
        this.overlayIntensity.setProgress(70);
        if (this.currentMode == TART_ToolType.OVERLAY) {
            this.mPhotoEditorView.getGLSurfaceView().setFilterIntensity(0.7f);
        }
    }


    public void openTextFragment() {
        this.textEditorDialogFragment = TART_TextEditorDialogFragment.show(this);
        this.textEditor = new TART_TextEditorDialogFragment.TextEditor() {
            public void onDone(TART_AddTextProperties addTextProperties) {
                TART_EditImageActivity.this.mPhotoEditorView.addSticker(new TART_TextSticker(TART_EditImageActivity.this.getApplicationContext(), addTextProperties));
            }

            public void onBackButton() {
                if (TART_EditImageActivity.this.mPhotoEditorView.getStickers().isEmpty()) {
                    TART_EditImageActivity.this.onBackPressed();
                }
            }
        };
        this.textEditorDialogFragment.setOnTextEditorListener(this.textEditor);
    }

    public void onToolSelected(TART_ToolType toolType) {
        this.currentMode = toolType;
        switch (toolType) {
            case BRUSH:
                showColorBrush();
                this.mPhotoEditor.setBrushDrawingMode(true);
                slideDown(this.mRvTools);
                slideUp(this.brushLayout);
                slideUpSaveControl();
                toogleDrawBottomToolbar(true);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(this.mRootView);
                if (!TART_SharePreferenceUtil.isPurchased(getApplicationContext())) {
                    constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, TART_SystemUtil.dpToPx(getApplicationContext(), 50));
                } else {
                    constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, 0);
                }
                constraintSet.connect(this.wrapPhotoView.getId(), 1, this.mRootView.getId(), 1, 0);
                constraintSet.connect(this.wrapPhotoView.getId(), 4, this.brushLayout.getId(), 3, 0);
                constraintSet.connect(this.wrapPhotoView.getId(), 2, this.mRootView.getId(), 2, 0);
                constraintSet.applyTo(this.mRootView);
                this.mPhotoEditor.setBrushMode(1);
                updateLayout();
                break;
            case TEXT:
                slideUpSaveView();
                this.mPhotoEditorView.setLocked(false);
                openTextFragment();
                slideDown(this.mRvTools);
                slideUp(this.textLayout);
                this.addNewText.setVisibility(View.VISIBLE);
                break;
            case ADJUST:
                slideUpSaveView();
                this.compareAdjust.setVisibility(View.VISIBLE);
                this.mAdjustAdapter = new TART_AdjustAdapter(getApplicationContext(), this);
                this.mRvAdjust.setAdapter(this.mAdjustAdapter);
                this.mAdjustAdapter.setSelectedAdjust(0);
                this.mPhotoEditor.setAdjustFilter(this.mAdjustAdapter.getFilterConfig());
                slideUp(this.adjustLayout);
                slideDown(this.mRvTools);
                break;
            case FILTER:
                slideUpSaveView();
                new LoadFilterBitmap().execute();
                break;
            case STICKER:
                slideUpSaveView();
                this.mPhotoEditorView.setLocked(false);
                slideDown(this.mRvTools);
                slideUp(this.stickerLayout);
                break;
            case OVERLAY:
                slideUpSaveView();
                new LoadOverlayBitmap().execute();
                break;
            case INSTA:
                new ShowInstaDialog().execute();
                break;
            case SPLASH:
                new ShowSplashDialog(true).execute();
                break;
            case BLUR:
                new ShowSplashDialog(false).execute();
                break;
            case MOSAIC:
                new ShowMosaicDialog().execute();
                break;
            case COLOR:
                TART_ColorSplashDialog.show(this, this.mPhotoEditorView.getCurrentBitmap());
                break;
            case CROP:
                TART_CropDialogFragment.show(this, this, this.mPhotoEditorView.getCurrentBitmap());
                break;
            case BEAUTY:
                TART_BeautyDialog.show(this, this.mPhotoEditorView.getCurrentBitmap(), this);
                break;
        }
        this.mPhotoEditorView.setHandlingSticker(null);
    }

    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", (float) view.getHeight(), 0.0f).start();
    }

    public void slideUpSaveView() {
        this.saveControl.setVisibility(View.GONE);
    }

    public void slideUpSaveControl() {
        this.saveControl.setVisibility(View.GONE);
    }

    public void slideDownSaveControl() {
        this.saveControl.setVisibility(View.VISIBLE);
    }

    public void slideDownSaveView() {
        this.saveControl.setVisibility(View.VISIBLE);
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", 0.0f, (float) view.getHeight()).start();
    }

    public void onBackPressed() {
        if (this.currentMode != null) {
            try {
                switch (this.currentMode) {
                    case BRUSH:
                        slideDown(this.brushLayout);
                        slideUp(this.mRvTools);
                        slideDownSaveControl();
                        this.undo.setVisibility(View.GONE);
                        this.redo.setVisibility(View.GONE);
                        this.erase.setVisibility(View.GONE);
                        this.mPhotoEditor.setBrushDrawingMode(false);
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(this.mRootView);
                        if (!TART_SharePreferenceUtil.isPurchased(getApplicationContext())) {
                            constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, TART_SystemUtil.dpToPx(getApplicationContext(), 50));
                        } else {
                            constraintSet.connect(this.wrapPhotoView.getId(), 3, this.mRootView.getId(), 3, 0);
                        }
                        constraintSet.connect(this.wrapPhotoView.getId(), 1, this.mRootView.getId(), 1, 0);
                        constraintSet.connect(this.wrapPhotoView.getId(), 4, this.mRvTools.getId(), 3, 0);
                        constraintSet.connect(this.wrapPhotoView.getId(), 2, this.mRootView.getId(), 2, 0);
                        constraintSet.applyTo(this.mRootView);
                        this.mPhotoEditor.clearBrushAllViews();
                        slideDownSaveView();
                        this.currentMode = TART_ToolType.NONE;
                        updateLayout();
                        return;
                    case TEXT:
                        if (!this.mPhotoEditorView.getStickers().isEmpty()) {
                            this.mPhotoEditorView.getStickers().clear();
                            this.mPhotoEditorView.setHandlingSticker(null);
                        }
                        slideDown(this.textLayout);
                        this.addNewText.setVisibility(View.GONE);
                        this.mPhotoEditorView.setHandlingSticker(null);
                        slideUp(this.mRvTools);
                        this.mPhotoEditorView.setLocked(true);
                        slideDownSaveView();
                        this.currentMode = TART_ToolType.NONE;
                        return;
                    case ADJUST:
                        this.mPhotoEditor.setFilterEffect("");
                        this.compareAdjust.setVisibility(View.GONE);
                        slideDown(this.adjustLayout);
                        slideUp(this.mRvTools);
                        slideDownSaveView();
                        this.currentMode = TART_ToolType.NONE;
                        return;
                    case FILTER:
                        slideDown(this.filterLayout);
                        slideUp(this.mRvTools);
                        slideDownSaveView();
                        this.mPhotoEditor.setFilterEffect("");
                        this.compareFilter.setVisibility(View.GONE);
                        this.lstBitmapWithFilter.clear();
                        if (this.mRvFilters.getAdapter() != null) {
                            this.mRvFilters.getAdapter().notifyDataSetChanged();
                        }
                        this.currentMode = TART_ToolType.NONE;
                        return;
                    case STICKER:
                        if (this.mPhotoEditorView.getStickers().size() <= 0) {
                            slideUp(this.wrapStickerList);
                            slideDown(this.stickerLayout);
                            this.addNewSticker.setVisibility(View.GONE);
                            this.mPhotoEditorView.setHandlingSticker(null);
                            slideUp(this.mRvTools);
                            this.mPhotoEditorView.setLocked(true);
                            this.currentMode = TART_ToolType.NONE;
                        } else if (this.addNewSticker.getVisibility() == View.VISIBLE) {
                            this.mPhotoEditorView.getStickers().clear();
                            this.addNewSticker.setVisibility(View.GONE);
                            this.mPhotoEditorView.setHandlingSticker(null);
                            slideUp(this.wrapStickerList);
                            slideDown(this.stickerLayout);
                            slideUp(this.mRvTools);
                            this.currentMode = TART_ToolType.NONE;
                        } else {
                            slideDown(this.wrapStickerList);
                            this.addNewSticker.setVisibility(View.VISIBLE);
                        }
                        slideDownSaveView();
                        return;
                    case OVERLAY:
                        this.mPhotoEditor.setFilterEffect("");
                        this.compareOverlay.setVisibility(View.GONE);
                        this.lstBitmapWithOverlay.clear();
                        slideUp(this.mRvTools);
                        slideDown(this.overlayLayout);
                        slideDownSaveView();
                        this.mRvOverlays.getAdapter().notifyDataSetChanged();
                        this.currentMode = TART_ToolType.NONE;
                        return;
                    case SPLASH:
                    case BLUR:
                    case MOSAIC:
                    case CROP:
                    case BEAUTY:
                        showDiscardDialog();
                        return;
                    case NONE:
                        showDiscardDialog();
                        return;
                    default:
                        super.onBackPressed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showDiscardDialog() {
        new AlertDialog.Builder(this).setMessage(R.string.dialog_discard_title).setPositiveButton(R.string.discard, (dialogInterface, i) -> {
            TART_EditImageActivity.this.currentMode = null;
            TART_EditImageActivity.this.finish();
        }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onAdjustSelected(TART_AdjustAdapter.AdjustModel adjustModel) {
        Log.d("XXXXXXXX", "onAdjustSelected " + adjustModel.slierIntensity + " " + this.adjustSeekBar.getMax());
        this.adjustSeekBar.setProgress((int) (adjustModel.slierIntensity * ((float) this.adjustSeekBar.getMax())));
    }

    public void addSticker(Bitmap bitmap) {
        this.mPhotoEditorView.addSticker(new TART_DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        slideDown(this.wrapStickerList);
        this.addNewSticker.setVisibility(View.VISIBLE);
    }

    public void finishCrop(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = TART_ToolType.NONE;
        updateLayout();
    }

    public void onColorChanged(String str) {
        this.mPhotoEditor.setBrushColor(Color.parseColor(str));
    }

    public void instaSavedBitmap(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = TART_ToolType.NONE;
        updateLayout();
    }

    public void onMagicChanged(TART_DrawBitmapModel drawBitmapModel) {
        this.mPhotoEditor.setBrushMagic(drawBitmapModel);
    }

    public void onSaveSplash(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = TART_ToolType.NONE;
    }

    public void onSaveMosaic(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = TART_ToolType.NONE;
    }

    public void onBeautySave(Bitmap bitmap) {
        this.mPhotoEditorView.setImageSource(bitmap);
        this.currentMode = TART_ToolType.NONE;
    }

    class LoadFilterBitmap extends AsyncTask<Void, Void, Void> {
        LoadFilterBitmap() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public Void doInBackground(Void... voidArr) {
            TART_EditImageActivity.this.lstBitmapWithFilter.clear();
            TART_EditImageActivity.this.lstBitmapWithFilter.addAll(TART_FilterUtils.getLstBitmapWithFilter(ThumbnailUtils.extractThumbnail(TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "LoadFilterBitmap " + TART_EditImageActivity.this.lstBitmapWithFilter.size());
            return null;
        }


        public void onPostExecute(Void voidR) {
            TART_EditImageActivity.this.mRvFilters.setAdapter(new TART_FilterViewAdapter(TART_EditImageActivity.this.lstBitmapWithFilter, TART_EditImageActivity.this, TART_EditImageActivity.this, Arrays.asList(TART_FilterUtils.EFFECT_CONFIGS)));
            TART_EditImageActivity.this.slideDown(TART_EditImageActivity.this.mRvTools);
            TART_EditImageActivity.this.slideUp(TART_EditImageActivity.this.filterLayout);
            TART_EditImageActivity.this.compareFilter.setVisibility(View.VISIBLE);
            TART_EditImageActivity.this.filterIntensity.setProgress(100);
            TART_EditImageActivity.this.showLoading(false);
        }
    }

    class ShowInstaDialog extends AsyncTask<Void, Bitmap, Bitmap> {
        ShowInstaDialog() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            return TART_FilterUtils.getBlurImageFromBitmap(TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 5.0f);
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_EditImageActivity.this.showLoading(false);
            TART_InstaDialog.show(TART_EditImageActivity.this, TART_EditImageActivity.this, TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), bitmap);
        }
    }

    class ShowMosaicDialog extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        ShowMosaicDialog() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public List<Bitmap> doInBackground(Void... voidArr) {
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(TART_FilterUtils.cloneBitmap(TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap()));
            arrayList.add(TART_FilterUtils.getBlurImageFromBitmap(TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 8.0f));
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            TART_EditImageActivity.this.showLoading(false);
            TART_MosaicDialog.show(TART_EditImageActivity.this, list.get(0), list.get(1), TART_EditImageActivity.this);
        }
    }

    class LoadOverlayBitmap extends AsyncTask<Void, Void, Void> {
        LoadOverlayBitmap() {

        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public Void doInBackground(Void... voidArr) {
            try {
                TART_EditImageActivity.this.lstBitmapWithOverlay.clear();
                TART_EditImageActivity.this.lstBitmapWithOverlay.addAll(TART_FilterUtils.getLstBitmapWithOverlay(ThumbnailUtils.extractThumbnail(TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap(), 100, 100)));
            } catch (Exception e) {
                Log.e("#CATCH_OVERLAY", e.getMessage());
            }
            return null;
        }


        public void onPostExecute(Void voidR) {
            TART_EditImageActivity.this.mRvOverlays.setAdapter(new TART_FilterViewAdapter(TART_EditImageActivity.this.lstBitmapWithOverlay, TART_EditImageActivity.this, TART_EditImageActivity.this, Arrays.asList(TART_FilterUtils.OVERLAY_CONFIG)));
            TART_EditImageActivity.this.slideDown(TART_EditImageActivity.this.mRvTools);
            TART_EditImageActivity.this.slideUp(TART_EditImageActivity.this.overlayLayout);
            TART_EditImageActivity.this.compareOverlay.setVisibility(View.VISIBLE);
            TART_EditImageActivity.this.overlayIntensity.setProgress(100);
            TART_EditImageActivity.this.showLoading(false);
        }
    }

    class ShowSplashDialog extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplash;

        public ShowSplashDialog(boolean z) {
            this.isSplash = z;
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public List<Bitmap> doInBackground(Void... voidArr) {
            Bitmap currentBitmap = TART_EditImageActivity.this.mPhotoEditorView.getCurrentBitmap();
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(currentBitmap);
            if (this.isSplash) {
                arrayList.add(TART_FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            } else {
                arrayList.add(TART_FilterUtils.getBlurImageFromBitmap(currentBitmap, 3.0f));
            }
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplash) {
                TART_SplashDialog.show(TART_EditImageActivity.this, list.get(0), null, list.get(1), TART_EditImageActivity.this, true);
            } else {
                TART_SplashDialog.show(TART_EditImageActivity.this, list.get(0), list.get(1), null, TART_EditImageActivity.this, false);
            }
            TART_EditImageActivity.this.showLoading(false);
        }
    }

    class SaveFilterAsBitmap extends AsyncTask<Void, Void, Bitmap> {
        SaveFilterAsBitmap() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            final Bitmap[] bitmapArr = {null};
            TART_EditImageActivity.this.mPhotoEditorView.saveGLSurfaceViewAsBitmap(bitmap -> bitmapArr[0] = bitmap);
            while (bitmapArr[0] == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bitmapArr[0];
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            TART_EditImageActivity.this.mPhotoEditorView.setFilterEffect("");
            TART_EditImageActivity.this.showLoading(false);
        }
    }

    class SaveStickerAsBitmap extends AsyncTask<Void, Void, Bitmap> {
        SaveStickerAsBitmap() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(0.0f);
            TART_EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            final Bitmap[] bitmapArr = {null};
            while (bitmapArr[0] == null) {
                try {
                    TART_EditImageActivity.this.mPhotoEditor.saveStickerAsBitmap(bitmap -> bitmapArr[0] = bitmap);
                    while (bitmapArr[0] == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            }
            return bitmapArr[0];
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            TART_EditImageActivity.this.mPhotoEditorView.getStickers().clear();
            TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().setAlpha(1.0f);
            TART_EditImageActivity.this.showLoading(false);
            TART_EditImageActivity.this.updateLayout();
        }
    }


    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 123) {
            if (i2 == -1) {
                try {
                    InputStream openInputStream = getContentResolver().openInputStream(intent.getData());
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                    float width = (float) decodeStream.getWidth();
                    float height = (float) decodeStream.getHeight();
                    float max = Math.max(width / 1280.0f, height / 1280.0f);
                    if (max > 1.0f) {
                        decodeStream = Bitmap.createScaledBitmap(decodeStream, (int) (width / max), (int) (height / max), false);
                    }
                    if (TART_SystemUtil.rotateBitmap(decodeStream, new ExifInterface(openInputStream).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) != decodeStream) {
                        decodeStream.recycle();
                        decodeStream = null;
                    }
                    this.mPhotoEditorView.setImageSource(decodeStream);
                    updateLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgUtil.toastMsg(this, "Error: Can not open image");
                }
            } else {
                finish();
            }
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(TART_EditImageActivity.this.getContentResolver(), fromFile);
                float width = (float) bitmap.getWidth();
                float height = (float) bitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                if (max > 1.0f) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                }
                Bitmap rotateBitmap = TART_SystemUtil.rotateBitmap(bitmap, new ExifInterface(TART_EditImageActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
                if (rotateBitmap != bitmap) {
                    bitmap.recycle();
                }
                return rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_EditImageActivity.this.mPhotoEditorView.setImageSource(bitmap);
            TART_EditImageActivity.this.updateLayout();
        }
    }

    public void updateLayout() {
        this.mPhotoEditorView.postDelayed(() -> {
            try {
                Display defaultDisplay = TART_EditImageActivity.this.getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                int i = point.x;
                int height = TART_EditImageActivity.this.wrapPhotoView.getHeight();
                int i2 = TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().getRenderViewport().width;
                float f = (float) TART_EditImageActivity.this.mPhotoEditorView.getGLSurfaceView().getRenderViewport().height;
                float f2 = (float) i2;
                if (((int) ((((float) i) * f) / f2)) <= height) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
                    layoutParams.addRule(13);
                    TART_EditImageActivity.this.mPhotoEditorView.setLayoutParams(layoutParams);
                    TART_EditImageActivity.this.mPhotoEditorView.setVisibility(View.VISIBLE);
                } else {
                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) ((((float) height) * f2) / f), -1);
                    layoutParams2.addRule(13);
                    TART_EditImageActivity.this.mPhotoEditorView.setLayoutParams(layoutParams2);
                    TART_EditImageActivity.this.mPhotoEditorView.setVisibility(View.VISIBLE);
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            TART_EditImageActivity.this.showLoading(false);
        }, 300);
    }

    class SaveBitmapAsFile extends AsyncTask<Void, String, String> {


        SaveBitmapAsFile() {
        }


        public void onPreExecute() {
            TART_EditImageActivity.this.showLoading(true);
        }


        public String doInBackground(Void... voidArr) {
            @SuppressLint("WrongThread") File saveBitmapAsFile = TART_FileUtils.saveBitmapAsFile(TART_EditImageActivity.this, TART_EditImageActivity.this.mPhotoEditorView.getSaveImg());
            try {
                MediaScannerConnection.scanFile(TART_EditImageActivity.this.getApplicationContext(), new String[]{saveBitmapAsFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

                return saveBitmapAsFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(String str) {
            TART_EditImageActivity.this.showLoading(false);
            if (str == null) {
                Toast.makeText(TART_EditImageActivity.this.getApplicationContext(), "Oop! Something went wrong", Toast.LENGTH_LONG).show();
                return;
            }
            MyApplication.showInterstitialAd(TART_EditImageActivity.this, () -> startIntent(str));


//            Intent intent = new Intent(EditImageActivity.this, SaveAndShareActivity.class);
//            intent.putExtra("path", str);
//            EditImageActivity.this.startActivity(intent);
        }
    }

    public void startIntent(String str){
        Intent intent = new Intent(TART_EditImageActivity.this, TART_ShareActivity.class);
        intent.setData(Uri.fromFile(new File(str)));
        TART_EditImageActivity.this.startActivity(intent);
    }
}
