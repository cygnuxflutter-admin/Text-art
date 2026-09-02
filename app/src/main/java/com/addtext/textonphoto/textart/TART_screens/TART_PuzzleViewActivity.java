package com.addtext.textonphoto.textart.TART_screens;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hold1.keyboardheightprovider.KeyboardHeightProvider;

import com.addtext.textonphoto.textart.TART_base.TART_BaseActivity;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_AddTextProperties;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_TextEditorDialogFragment;
import com.addtext.textonphoto.textart.TART_features.TART_crop.TART_CropDialogFragment;
import com.addtext.textonphoto.textart.TART_features.TART_crop.TART_adapter.TART_AspectRatioPreviewAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_PhotoPicker;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_PermissionsUtils;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayoutParser;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzlePiece;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleView;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_adapter.TART_PuzzleAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_adapter.TART_PuzzleBackgroundAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_photopicker.TART_activity.TART_PickImageActivity;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TART_RecyclerTabLayout;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TART_StickerAdapter;
import com.addtext.textonphoto.textart.TART_features.sticker.TART_adapter.TopTabAdapter;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterDialogFragment;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterListener;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterUtils;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterViewAdapter;
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
import com.addtext.textonphoto.textart.TART_tools.TART_TextToolsAdapter;
import com.addtext.textonphoto.textart.TART_tools.TART_ToolType;
import com.addtext.textonphoto.textart.TART_utils.TART_AssetUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_FileUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_PuzzleUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_SharePreferenceUtil;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.addtext.textonphoto.textart.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.steelkiwi.cropiwa.AspectRatio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.wysaid.nativePort.CGENativeLibrary;

@SuppressLint("StaticFieldLeak")
public class TART_PuzzleViewActivity extends TART_BaseActivity implements TART_EditingToolsAdapter.OnItemSelected, TART_AspectRatioPreviewAdapter.OnNewSelectedListener, TART_StickerAdapter.OnClickStickerListener, TART_PuzzleBackgroundAdapter.BackgroundChangeListener, TART_FilterListener, TART_CropDialogFragment.OnCropPhoto, TART_FilterDialogFragment.OnFilterSavePhoto, TART_TextToolsAdapter.OnPieceFuncItemSelected, TART_PuzzleAdapter.OnItemClickListener {
    private static TART_PuzzleViewActivity instance;
    public static TART_PuzzleViewActivity puzzle;
    public ImageView addNewSticker;
    public ImageView addNewText;
    private View adsContainer;
    public ConstraintLayout changeBackgroundLayout;
    private LinearLayout changeBorder;
    public ConstraintLayout changeLayoutLayout;
    public AspectRatio currentAspect;
    public TART_PuzzleBackgroundAdapter.SquareView currentBackgroundState;
    public TART_ToolType currentMode;
    private int deviceHeight = 0;
    public int deviceWidth = 0;
    public ConstraintLayout filterLayout;
    private KeyboardHeightProvider keyboardHeightProvider;
    private RelativeLayout loadingView;
    public List<Bitmap> lstBitmapWithFilter = new ArrayList<>();
    public List<Drawable> lstOriginalDrawable = new ArrayList<>();
    public List<String> lstPaths;
    private TART_EditingToolsAdapter mEditingToolsAdapter = new TART_EditingToolsAdapter(this, true);
    public CGENativeLibrary.LoadImageCallback mLoadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String str, Object obj) {
            try {
                return BitmapFactory.decodeStream(TART_PuzzleViewActivity.this.getAssets().open(str));
            } catch (IOException io) {
                return null;
            }
        }

        public void loadImageOK(Bitmap bitmap, Object obj) {
            bitmap.recycle();
        }
    };

    public RecyclerView mRvTools;
    private ConstraintLayout mainActivity;
    public View.OnClickListener onClickListener = view -> {
        int id = view.getId();
        if (id == R.id.imgCloseBackground || id == R.id.imgCloseFilter || id == R.id.imgCloseLayout || id == R.id.imgCloseSticker || id == R.id.imgCloseText) {
            TART_PuzzleViewActivity.this.slideDownSaveView();
            TART_PuzzleViewActivity.this.onBackPressed();
            return;
        } else if (id == R.id.imgSaveBackground) {
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.changeBackgroundLayout);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideDownSaveView();
            TART_PuzzleViewActivity.this.showDownFunction();
            TART_PuzzleViewActivity.this.puzzleView.setLocked(true);
            TART_PuzzleViewActivity.this.puzzleView.setTouchEnable(true);
            if (TART_PuzzleViewActivity.this.puzzleView.getBackgroundResourceMode() == 0) {
                TART_PuzzleViewActivity.this.currentBackgroundState.isColor = true;
                TART_PuzzleViewActivity.this.currentBackgroundState.isBitmap = false;
                TART_PuzzleViewActivity.this.currentBackgroundState.drawableId = ((ColorDrawable) TART_PuzzleViewActivity.this.puzzleView.getBackground()).getColor();
                TART_PuzzleViewActivity.this.currentBackgroundState.drawable = null;
            } else if (TART_PuzzleViewActivity.this.puzzleView.getBackgroundResourceMode() == 1) {
                TART_PuzzleViewActivity.this.currentBackgroundState.isColor = false;
                TART_PuzzleViewActivity.this.currentBackgroundState.isBitmap = false;
                TART_PuzzleViewActivity.this.currentBackgroundState.drawable = TART_PuzzleViewActivity.this.puzzleView.getBackground();
            } else {
                TART_PuzzleViewActivity.this.currentBackgroundState.isColor = false;
                TART_PuzzleViewActivity.this.currentBackgroundState.isBitmap = true;
                TART_PuzzleViewActivity.this.currentBackgroundState.drawable = TART_PuzzleViewActivity.this.puzzleView.getBackground();
            }
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.NONE;
            return;
        } else if (id == R.id.imgSaveFilter) {
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.filterLayout);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.NONE;
            return;
        } else if (id == R.id.imgSaveLayout) {
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.changeLayoutLayout);
            TART_PuzzleViewActivity.this.slideDownSaveView();
            TART_PuzzleViewActivity.this.showDownFunction();
            TART_PuzzleViewActivity.this.puzzleLayout = TART_PuzzleViewActivity.this.puzzleView.getPuzzleLayout();
            TART_PuzzleViewActivity.this.pieceBorderRadius = TART_PuzzleViewActivity.this.puzzleView.getPieceRadian();
            TART_PuzzleViewActivity.this.piecePadding = TART_PuzzleViewActivity.this.puzzleView.getPiecePadding();
            TART_PuzzleViewActivity.this.puzzleView.setLocked(true);
            TART_PuzzleViewActivity.this.puzzleView.setTouchEnable(true);
            TART_PuzzleViewActivity.this.currentAspect = TART_PuzzleViewActivity.this.puzzleView.getAspectRatio();
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.NONE;
            return;
        } else if (id == R.id.imgSaveSticker) {
            TART_PuzzleViewActivity.this.puzzleView.setHandlingSticker(null);
            TART_PuzzleViewActivity.this.stickerAlpha.setVisibility(View.GONE);
            TART_PuzzleViewActivity.this.addNewSticker.setVisibility(View.GONE);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.wrapStickerList);
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.stickerLayout);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideDownSaveView();
            TART_PuzzleViewActivity.this.puzzleView.setLocked(true);
            TART_PuzzleViewActivity.this.puzzleView.setTouchEnable(true);
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.NONE;
            return;
        } else if (id == R.id.imgSaveText) {
            TART_PuzzleViewActivity.this.puzzleView.setHandlingSticker(null);
            TART_PuzzleViewActivity.this.puzzleView.setLocked(true);
            TART_PuzzleViewActivity.this.addNewText.setVisibility(View.GONE);
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.textLayout);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideDownSaveView();
            TART_PuzzleViewActivity.this.puzzleView.setLocked(true);
            TART_PuzzleViewActivity.this.puzzleView.setTouchEnable(true);
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.NONE;
            return;
        } else if (id == R.id.tv_blur) {
            TART_PuzzleViewActivity.this.selectBackgroundBlur();
            return;
        } else if (id == R.id.tv_change_border) {
            TART_PuzzleViewActivity.this.selectBorderTool();
            return;
        } else if (id == R.id.tv_change_layout) {
            TART_PuzzleViewActivity.this.selectLayoutTool();
            return;
        } else if (id == R.id.tv_change_ratio) {
            TART_PuzzleViewActivity.this.selectRadiusTool();
            return;
        } else if (id == R.id.tv_color) {
            TART_PuzzleViewActivity.this.selectBackgroundColorTab();
            return;
        } else if (id == R.id.tv_radian) {
            TART_PuzzleViewActivity.this.selectBackgroundGradientTab();
            return;
        }
    };
    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int id = seekBar.getId();
            if (id == R.id.sk_border) {
                TART_PuzzleViewActivity.this.puzzleView.setPiecePadding((float) i);
            } else if (id == R.id.sk_border_radius) {
                TART_PuzzleViewActivity.this.puzzleView.setPieceRadian((float) i);
            }
            TART_PuzzleViewActivity.this.puzzleView.invalidate();
        }
    };
    TART_StickerView.OnStickerOperationListener onStickerOperationListener = new TART_StickerView.OnStickerOperationListener() {
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
            TART_PuzzleViewActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
            TART_PuzzleViewActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
        }

        public void onStickerClicked(@NonNull Sticker sticker) {
            TART_PuzzleViewActivity.this.stickerAlpha.setVisibility(View.VISIBLE);
            TART_PuzzleViewActivity.this.stickerAlpha.setProgress(sticker.getAlpha());
        }

        public void onStickerDeleted(@NonNull Sticker sticker) {
            TART_PuzzleViewActivity.this.stickerAlpha.setVisibility(View.GONE);
        }

        public void onStickerTouchOutside() {
            TART_PuzzleViewActivity.this.stickerAlpha.setVisibility(View.GONE);
        }

        public void onStickerDoubleTapped(@NonNull Sticker sticker) {
            if (sticker instanceof TART_TextSticker) {
                sticker.setShow(false);
                TART_PuzzleViewActivity.this.puzzleView.setHandlingSticker(null);
                TART_PuzzleViewActivity.this.textEditorDialogFragment = TART_TextEditorDialogFragment.show(TART_PuzzleViewActivity.this, ((TART_TextSticker) sticker).getAddTextProperties());
                TART_PuzzleViewActivity.this.textEditor = new TART_TextEditorDialogFragment.TextEditor() {
                    public void onDone(TART_AddTextProperties addTextProperties) {
                        TART_PuzzleViewActivity.this.puzzleView.getStickers().remove(TART_PuzzleViewActivity.this.puzzleView.getLastHandlingSticker());
                        TART_PuzzleViewActivity.this.puzzleView.addSticker(new TART_TextSticker(TART_PuzzleViewActivity.this, addTextProperties));
                    }

                    public void onBackButton() {
                        TART_PuzzleViewActivity.this.puzzleView.showLastHandlingSticker();
                    }
                };
                TART_PuzzleViewActivity.this.textEditorDialogFragment.setOnTextEditorListener(TART_PuzzleViewActivity.this.textEditor);
            }
        }
    };

    public float pieceBorderRadius;

    public float piecePadding;
    private TART_TextToolsAdapter pieceToolsAdapter = new TART_TextToolsAdapter(this);

    public TART_PuzzleLayout puzzleLayout;
    private RecyclerView puzzleList;

    public TART_PuzzleView puzzleView;
    private RecyclerView radiusLayout;
    private RecyclerView rvBackgroundBlur;
    private RecyclerView rvBackgroundColor;
    private RecyclerView rvBackgroundGradient;

    public RecyclerView rvFilterView;

    public RecyclerView rvPieceControl;
    private TextView saveBitmap;
    private ConstraintLayout saveControl;
    private SeekBar sbChangeBorderRadius;
    private SeekBar sbChangeBorderSize;

    public SeekBar stickerAlpha;

    public ConstraintLayout stickerLayout;

    public List<Target> targets = new ArrayList();

    public TART_TextEditorDialogFragment.TextEditor textEditor;

    public TART_TextEditorDialogFragment textEditorDialogFragment;

    public ConstraintLayout textLayout;
    private TextView tvChangeBackgroundBlur;
    private TextView tvChangeBackgroundColor;
    private TextView tvChangeBackgroundGradient;
    private TextView tvChangeBorder;
    private TextView tvChangeLayout;
    private TextView tvChangeRatio;
    private ConstraintLayout wrapPuzzleView;

    public LinearLayout wrapStickerList;

    public static TART_PuzzleViewActivity getInstance() {
        return instance;
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.knack_puzzle_layout);
        if (Build.VERSION.SDK_INT < 26) {
            getWindow().setSoftInputMode(48);
        }
        this.adsContainer = findViewById(R.id.adsContainer);
        this.deviceWidth = getResources().getDisplayMetrics().widthPixels;
        this.deviceHeight = getResources().getDisplayMetrics().heightPixels;
    /*    if (SharePreferenceUtil.isPurchased(getApplicationContext())) {
            AdmobAds.loadBanner(this);
        }*/
        findViewById(R.id.exitEditMode).setOnClickListener(view -> TART_PuzzleViewActivity.this.onBackPressed());
        this.loadingView = findViewById(R.id.loadingView);
        this.puzzleView = findViewById(R.id.puzzle_view);
        this.wrapPuzzleView = findViewById(R.id.wrapPuzzleView);
        this.filterLayout = findViewById(R.id.filterLayout);
        this.rvFilterView = findViewById(R.id.rvFilterView);
        this.mRvTools = findViewById(R.id.rvConstraintTools);
        this.mRvTools.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.mRvTools.setAdapter(this.mEditingToolsAdapter);
        this.rvPieceControl = findViewById(R.id.rvPieceControl);
        this.rvPieceControl.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.rvPieceControl.setAdapter(this.pieceToolsAdapter);
        this.sbChangeBorderSize = findViewById(R.id.sk_border);
        this.sbChangeBorderSize.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.sbChangeBorderRadius = findViewById(R.id.sk_border_radius);
        this.sbChangeBorderRadius.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.lstPaths = getIntent().getStringArrayListExtra(TART_PickImageActivity.KEY_DATA_RESULT);
        this.puzzleLayout = TART_PuzzleUtils.getPuzzleLayouts(this.lstPaths.size()).get(0);
        this.puzzleView.setPuzzleLayout(this.puzzleLayout);
        this.puzzleView.setTouchEnable(true);
        this.puzzleView.setNeedDrawLine(false);
        this.puzzleView.setNeedDrawOuterLine(false);
        this.puzzleView.setLineSize(4);
        this.puzzleView.setPiecePadding(6.0f);
        this.puzzleView.setPieceRadian(15.0f);
        this.puzzleView.setLineColor(ContextCompat.getColor(this, R.color.white));
        this.puzzleView.setSelectedLineColor(ContextCompat.getColor(this, R.color.colorAccent));
        this.puzzleView.setHandleBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        this.puzzleView.setAnimateDuration(300);
        this.puzzleView.setOnPieceSelectedListener((puzzlePiece, i) -> {
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.rvPieceControl);
            TART_PuzzleViewActivity.this.slideUpSaveView();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) TART_PuzzleViewActivity.this.rvPieceControl.getLayoutParams();
            layoutParams.bottomMargin = TART_SystemUtil.dpToPx(TART_PuzzleViewActivity.this.getApplicationContext(), 10);
            TART_PuzzleViewActivity.this.rvPieceControl.setLayoutParams(layoutParams);
            TART_PuzzleViewActivity.this.currentMode = TART_ToolType.PIECE;
        });
        this.puzzleView.setOnPieceUnSelectedListener(() -> {
            slideDown(rvPieceControl);
            slideUp(mRvTools);
            slideDownSaveView();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) rvPieceControl.getLayoutParams();
            layoutParams.bottomMargin = 0;
            rvPieceControl.setLayoutParams(layoutParams);
            currentMode = TART_ToolType.NONE;
        });
        this.saveControl = findViewById(R.id.saveControl);
        this.puzzleView.post(() -> TART_PuzzleViewActivity.this.loadPhoto());
        findViewById(R.id.imgCloseLayout).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgSaveLayout).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgCloseSticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgCloseFilter).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgCloseBackground).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgSaveSticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgCloseText).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgSaveText).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgSaveFilter).setOnClickListener(this.onClickListener);
        findViewById(R.id.imgSaveBackground).setOnClickListener(this.onClickListener);
        this.changeLayoutLayout = findViewById(R.id.changeLayoutLayout);
        this.changeBorder = findViewById(R.id.change_border);
        this.tvChangeLayout = findViewById(R.id.tv_change_layout);
        this.tvChangeLayout.setOnClickListener(this.onClickListener);
        this.tvChangeBorder = findViewById(R.id.tv_change_border);
        this.tvChangeBorder.setOnClickListener(this.onClickListener);
        this.tvChangeRatio = findViewById(R.id.tv_change_ratio);
        this.tvChangeRatio.setOnClickListener(this.onClickListener);
        this.tvChangeBackgroundColor = findViewById(R.id.tv_color);
        this.tvChangeBackgroundColor.setOnClickListener(this.onClickListener);
        this.tvChangeBackgroundGradient = findViewById(R.id.tv_radian);
        this.tvChangeBackgroundGradient.setOnClickListener(this.onClickListener);
        this.tvChangeBackgroundBlur = findViewById(R.id.tv_blur);
        this.tvChangeBackgroundBlur.setOnClickListener(this.onClickListener);
        TART_PuzzleAdapter puzzleAdapter = new TART_PuzzleAdapter();
        this.puzzleList = findViewById(R.id.puzzleList);
        this.puzzleList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.puzzleList.setAdapter(puzzleAdapter);
        puzzleAdapter.refreshData(TART_PuzzleUtils.getPuzzleLayouts(this.lstPaths.size()), null);
        puzzleAdapter.setOnItemClickListener(this);
        TART_AspectRatioPreviewAdapter aspectRatioPreviewAdapter = new TART_AspectRatioPreviewAdapter(true);
        aspectRatioPreviewAdapter.setListener(this);
        this.radiusLayout = findViewById(R.id.radioLayout);
        this.radiusLayout.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.radiusLayout.setAdapter(aspectRatioPreviewAdapter);
        this.textLayout = findViewById(R.id.textControl);
        this.addNewText = findViewById(R.id.addNewText);
        this.addNewText.setVisibility(View.GONE);
        this.addNewText.setOnClickListener(view -> {
            puzzleView.setHandlingSticker(null);
            openTextFragment();
        });
        this.wrapStickerList = findViewById(R.id.wrapStickerList);
        ViewPager viewPager = findViewById(R.id.sticker_viewpaper);
        this.stickerLayout = findViewById(R.id.stickerLayout);
        this.stickerAlpha = findViewById(R.id.stickerAlpha);
        this.stickerAlpha.setVisibility(View.GONE);
        this.stickerAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = TART_PuzzleViewActivity.this.puzzleView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.saveBitmap = findViewById(R.id.save);
        this.saveBitmap.setOnClickListener(view -> {
            if (TART_PermissionsUtils.checkWriteStoragePermission( TART_PuzzleViewActivity.this)) {
                Bitmap createBitmap = TART_FileUtils.createBitmap(TART_PuzzleViewActivity.this.puzzleView, 1920);
                Bitmap createBitmap2 = TART_PuzzleViewActivity.this.puzzleView.createBitmap();
                new SavePuzzleAsFile().execute(new Bitmap[]{createBitmap, createBitmap2});
            }
        });
        this.addNewSticker = findViewById(R.id.addNewSticker);
        this.addNewSticker.setVisibility(View.GONE);
        this.addNewSticker.setOnClickListener(view -> {
            addNewSticker.setVisibility(View.GONE);
            slideUp(wrapStickerList);
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
        this.puzzleView.setIcons(Arrays.asList(new TART_BitmapStickerIcon[]{bitmapStickerIcon, bitmapStickerIcon2, bitmapStickerIcon3, bitmapStickerIcon5, bitmapStickerIcon4, bitmapStickerIcon6}));
        this.puzzleView.setConstrained(true);
        this.puzzleView.setOnStickerOperationListener(this.onStickerOperationListener);
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
                View inflate = LayoutInflater.from(TART_PuzzleViewActivity.this.getBaseContext()).inflate(R.layout.knack_sticker_items, null, false);
                RecyclerView recyclerView = inflate.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(TART_PuzzleViewActivity.this.getApplicationContext(), 4));
                switch (i) {
                    case 0:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstHeardes(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 1:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstEmoj(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 2:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstTexts(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 3:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstOthers(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 4:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstGiddy(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 5:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstGlasses(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 6:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstTies(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 7:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstCatFaces(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 8:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstCkeeks(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 9:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstDiadems(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 10:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstEyes(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 11:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstMuscle(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                    case 12:
                        recyclerView.setAdapter(new TART_StickerAdapter(TART_PuzzleViewActivity.this.getApplicationContext(), TART_AssetUtils.lstTatoos(), TART_PuzzleViewActivity.this.deviceWidth, TART_PuzzleViewActivity.this));
                        break;
                }
                viewGroup.addView(inflate);
                return inflate;
            }
        });
        TART_RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new TopTabAdapter(viewPager, getApplicationContext()));
        recyclerTabLayout.setPositionThreshold(0.5f);
        recyclerTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey_more));
        this.changeBackgroundLayout = findViewById(R.id.changeBackgroundLayout);
        this.mainActivity = findViewById(R.id.puzzle_layout);
        this.changeLayoutLayout.setAlpha(0.0f);
        this.stickerLayout.setAlpha(0.0f);
        this.textLayout.setAlpha(0.0f);
        this.filterLayout.setAlpha(0.0f);
        this.changeBackgroundLayout.setAlpha(0.0f);
        this.rvPieceControl.setAlpha(0.0f);
        this.mainActivity.post(() -> {
            slideDown(changeLayoutLayout);
            slideDown(stickerLayout);
            slideDown(textLayout);
            slideDown(changeBackgroundLayout);
            slideDown(filterLayout);
            slideDown(rvPieceControl);
        });
        new Handler().postDelayed(() -> {
            changeLayoutLayout.setAlpha(1.0f);
            stickerLayout.setAlpha(1.0f);
            textLayout.setAlpha(1.0f);
            filterLayout.setAlpha(1.0f);
            changeBackgroundLayout.setAlpha(1.0f);
            rvPieceControl.setAlpha(1.0f);
        }, 1000);
        TART_SharePreferenceUtil.setHeightOfKeyboard(getApplicationContext(), 0);
        this.keyboardHeightProvider = new KeyboardHeightProvider(this);
        this.keyboardHeightProvider.addKeyboardListener(i -> {

            if (i < 0) {
                TART_SharePreferenceUtil.setHeightOfNotch(getApplicationContext(), -i);
            } else if (textEditorDialogFragment != null) {
                textEditorDialogFragment.updateAddTextBottomToolbarHeight(TART_SharePreferenceUtil.getHeightOfNotch(getApplicationContext()) + i);
                TART_SharePreferenceUtil.setHeightOfKeyboard(getApplicationContext(), i + TART_SharePreferenceUtil.getHeightOfNotch(getApplicationContext()));
            }
        });
        showLoading(false);
        this.currentBackgroundState = new TART_PuzzleBackgroundAdapter.SquareView(Color.parseColor("#ffffff"), "", true);
        this.rvBackgroundColor = findViewById(R.id.colorList);
        this.rvBackgroundColor.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.rvBackgroundColor.setHasFixedSize(true);
        this.rvBackgroundColor.setAdapter(new TART_PuzzleBackgroundAdapter(getApplicationContext(), this));
        this.rvBackgroundGradient = findViewById(R.id.radianList);
        this.rvBackgroundGradient.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.rvBackgroundGradient.setHasFixedSize(true);
        this.rvBackgroundGradient.setAdapter(new TART_PuzzleBackgroundAdapter(getApplicationContext(), (TART_PuzzleBackgroundAdapter.BackgroundChangeListener) this, true));
        this.rvBackgroundBlur = findViewById(R.id.backgroundList);
        this.rvBackgroundBlur.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.rvBackgroundBlur.setHasFixedSize(true);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.puzzleView.getLayoutParams();
        layoutParams.height = point.x;
        layoutParams.width = point.x;
        this.puzzleView.setLayoutParams(layoutParams);
        this.currentAspect = new AspectRatio(1, 1);
        this.puzzleView.setAspectRatio(new AspectRatio(1, 1));
        puzzle = this;
        this.currentMode = TART_ToolType.NONE;
        CGENativeLibrary.setLoadImageCallback(this.mLoadImageCallback, (Object) null);
        instance = this;
    }


    private void openTextFragment() {
        this.textEditorDialogFragment = TART_TextEditorDialogFragment.show(this);
        this.textEditor = new TART_TextEditorDialogFragment.TextEditor() {
            public void onDone(TART_AddTextProperties addTextProperties) {
                TART_PuzzleViewActivity.this.puzzleView.addSticker(new TART_TextSticker(TART_PuzzleViewActivity.this.getApplicationContext(), addTextProperties));
            }

            public void onBackButton() {
            }
        };
        this.textEditorDialogFragment.setOnTextEditorListener(this.textEditor);
    }

    public void isPermissionGranted(boolean z, String str) {
        if (z) {
            Bitmap createBitmap = TART_FileUtils.createBitmap(this.puzzleView, 1920);
            Bitmap createBitmap2 = this.puzzleView.createBitmap();
            new SavePuzzleAsFile().execute(createBitmap, createBitmap2);
        }
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

    public void selectBackgroundBlur() {
        ArrayList arrayList = new ArrayList();
        for (TART_PuzzlePiece drawable : this.puzzleView.getPuzzlePieces()) {
            arrayList.add(drawable.getDrawable());
        }
        TART_PuzzleBackgroundAdapter puzzleBackgroundAdapter = new TART_PuzzleBackgroundAdapter(getApplicationContext(), this, (List<Drawable>) arrayList);
        puzzleBackgroundAdapter.setSelectedSquareIndex(-1);
        this.rvBackgroundBlur.setAdapter(puzzleBackgroundAdapter);
        this.rvBackgroundBlur.setVisibility(View.VISIBLE);
        this.tvChangeBackgroundBlur.setBackgroundResource(R.drawable.knack_border_bottom);
        this.tvChangeBackgroundBlur.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.rvBackgroundGradient.setVisibility(View.GONE);
        this.tvChangeBackgroundGradient.setBackgroundResource(0);
        this.tvChangeBackgroundGradient.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.rvBackgroundColor.setVisibility(View.GONE);
        this.tvChangeBackgroundColor.setBackgroundResource(0);
        this.tvChangeBackgroundColor.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
    }

    public void selectBackgroundColorTab() {
        this.rvBackgroundColor.setVisibility(View.VISIBLE);
        this.tvChangeBackgroundColor.setBackgroundResource(R.drawable.knack_border_bottom);
        this.tvChangeBackgroundColor.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.rvBackgroundColor.scrollToPosition(0);
        ((TART_PuzzleBackgroundAdapter) this.rvBackgroundColor.getAdapter()).setSelectedSquareIndex(-1);
        this.rvBackgroundColor.getAdapter().notifyDataSetChanged();
        this.rvBackgroundGradient.setVisibility(View.GONE);
        this.tvChangeBackgroundGradient.setBackgroundResource(0);
        this.tvChangeBackgroundGradient.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.rvBackgroundBlur.setVisibility(View.GONE);
        this.tvChangeBackgroundBlur.setBackgroundResource(0);
        this.tvChangeBackgroundBlur.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
    }

    public void selectBackgroundGradientTab() {
        this.rvBackgroundGradient.setVisibility(View.VISIBLE);
        this.tvChangeBackgroundGradient.setBackgroundResource(R.drawable.knack_border_bottom);
        this.tvChangeBackgroundGradient.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.rvBackgroundGradient.scrollToPosition(0);
        ((TART_PuzzleBackgroundAdapter) this.rvBackgroundGradient.getAdapter()).setSelectedSquareIndex(-1);
        this.rvBackgroundGradient.getAdapter().notifyDataSetChanged();
        this.rvBackgroundColor.setVisibility(View.GONE);
        this.tvChangeBackgroundColor.setBackgroundResource(0);
        this.tvChangeBackgroundColor.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.rvBackgroundBlur.setVisibility(View.GONE);
        this.tvChangeBackgroundBlur.setBackgroundResource(0);
        this.tvChangeBackgroundBlur.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
    }


    public void selectLayoutTool() {
        this.puzzleList.setVisibility(View.VISIBLE);
        this.tvChangeLayout.setBackgroundResource(R.drawable.knack_border_bottom);
        this.tvChangeLayout.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.changeBorder.setVisibility(View.GONE);
        this.tvChangeBorder.setBackgroundResource(0);
        this.tvChangeBorder.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.radiusLayout.setVisibility(View.GONE);
        this.tvChangeRatio.setBackgroundResource(0);
        this.tvChangeRatio.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
    }


    public void selectRadiusTool() {
        this.radiusLayout.setVisibility(View.VISIBLE);
        this.tvChangeRatio.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.tvChangeRatio.setBackgroundResource(R.drawable.knack_border_bottom);
        this.puzzleList.setVisibility(View.GONE);
        this.tvChangeLayout.setBackgroundResource(0);
        this.tvChangeLayout.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.changeBorder.setVisibility(View.GONE);
        this.tvChangeBorder.setBackgroundResource(0);
        this.tvChangeBorder.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
    }


    public void selectBorderTool() {
        this.changeBorder.setVisibility(View.VISIBLE);
        this.tvChangeBorder.setBackgroundResource(R.drawable.knack_border_bottom);
        this.tvChangeBorder.setTextColor(ContextCompat.getColor(this, R.color.selected));
        this.puzzleList.setVisibility(View.GONE);
        this.tvChangeLayout.setBackgroundResource(0);
        this.tvChangeLayout.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.radiusLayout.setVisibility(View.GONE);
        this.tvChangeRatio.setBackgroundResource(0);
        this.tvChangeRatio.setTextColor(ContextCompat.getColor(this, R.color.text_unselected));
        this.sbChangeBorderRadius.setProgress((int) this.puzzleView.getPieceRadian());
        this.sbChangeBorderSize.setProgress((int) this.puzzleView.getPiecePadding());
    }

    private void showUpFunction(View view) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mainActivity);
        constraintSet.connect(this.wrapPuzzleView.getId(), 3, this.adsContainer.getId(), 4, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 1, this.mainActivity.getId(), 1, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 4, view.getId(), 3, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 2, this.mainActivity.getId(), 2, 0);
        constraintSet.applyTo(this.mainActivity);
    }


    public void showDownFunction() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mainActivity);
        constraintSet.connect(this.wrapPuzzleView.getId(), 3, this.adsContainer.getId(), 4, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 1, this.mainActivity.getId(), 1, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 4, this.mRvTools.getId(), 3, 0);
        constraintSet.connect(this.wrapPuzzleView.getId(), 2, this.mainActivity.getId(), 2, 0);
        constraintSet.applyTo(this.mainActivity);
    }

    public void onToolSelected(TART_ToolType toolType) {
        this.currentMode = toolType;
        switch (toolType) {
            case LAYOUT:
                this.puzzleLayout = this.puzzleView.getPuzzleLayout();
                this.currentAspect = this.puzzleView.getAspectRatio();
                this.pieceBorderRadius = this.puzzleView.getPieceRadian();
                this.piecePadding = this.puzzleView.getPiecePadding();
                this.puzzleList.scrollToPosition(0);
                ((TART_PuzzleAdapter) this.puzzleList.getAdapter()).setSelectedIndex(-1);
                this.puzzleList.getAdapter().notifyDataSetChanged();
                this.radiusLayout.scrollToPosition(0);
                ((TART_AspectRatioPreviewAdapter) this.radiusLayout.getAdapter()).setLastSelectedView(-1);
                this.radiusLayout.getAdapter().notifyDataSetChanged();
                selectLayoutTool();
                slideUpSaveView();
                slideUp(this.changeLayoutLayout);
                slideDown(this.mRvTools);
                showUpFunction(this.changeLayoutLayout);
                this.puzzleView.setLocked(false);
                this.puzzleView.setTouchEnable(false);
                return;
            case BORDER:
                this.puzzleLayout = this.puzzleView.getPuzzleLayout();
                this.currentAspect = this.puzzleView.getAspectRatio();
                this.pieceBorderRadius = this.puzzleView.getPieceRadian();
                this.piecePadding = this.puzzleView.getPiecePadding();
                this.puzzleList.scrollToPosition(0);
                ((TART_PuzzleAdapter) this.puzzleList.getAdapter()).setSelectedIndex(-1);
                this.puzzleList.getAdapter().notifyDataSetChanged();
                this.radiusLayout.scrollToPosition(0);
                ((TART_AspectRatioPreviewAdapter) this.radiusLayout.getAdapter()).setLastSelectedView(-1);
                this.radiusLayout.getAdapter().notifyDataSetChanged();
                selectBorderTool();
                slideUpSaveView();
                slideUp(this.changeLayoutLayout);
                slideDown(this.mRvTools);
                showUpFunction(this.changeLayoutLayout);
                this.puzzleView.setLocked(false);
                this.puzzleView.setTouchEnable(false);
                return;
            case RATIO:
                this.puzzleLayout = this.puzzleView.getPuzzleLayout();
                this.currentAspect = this.puzzleView.getAspectRatio();
                this.pieceBorderRadius = this.puzzleView.getPieceRadian();
                this.piecePadding = this.puzzleView.getPiecePadding();
                this.puzzleList.scrollToPosition(0);
                ((TART_PuzzleAdapter) this.puzzleList.getAdapter()).setSelectedIndex(-1);
                this.puzzleList.getAdapter().notifyDataSetChanged();
                this.radiusLayout.scrollToPosition(0);
                ((TART_AspectRatioPreviewAdapter) this.radiusLayout.getAdapter()).setLastSelectedView(-1);
                this.radiusLayout.getAdapter().notifyDataSetChanged();
                selectRadiusTool();
                slideUpSaveView();
                slideUp(this.changeLayoutLayout);
                slideDown(this.mRvTools);
                showUpFunction(this.changeLayoutLayout);
                this.puzzleView.setLocked(false);
                this.puzzleView.setTouchEnable(false);
                return;
            case FILTER:
                if (this.lstOriginalDrawable.isEmpty()) {
                    for (TART_PuzzlePiece drawable : this.puzzleView.getPuzzlePieces()) {
                        this.lstOriginalDrawable.add(drawable.getDrawable());
                    }
                }
                new LoadFilterBitmap().execute();
                slideDown(this.mRvTools);
                slideUp(this.filterLayout);
                slideUpSaveView();
                return;
            case STICKER:
                this.puzzleView.setTouchEnable(false);
                slideUpSaveView();
                slideDown(this.mRvTools);
                slideUp(this.stickerLayout);
                this.puzzleView.setLocked(false);
                this.puzzleView.setTouchEnable(false);
                return;
            case TEXT:
                this.puzzleView.setTouchEnable(false);
                slideUpSaveView();
                this.puzzleView.setLocked(false);
                openTextFragment();
                slideDown(this.mRvTools);
                slideUp(this.textLayout);
                this.addNewText.setVisibility(View.VISIBLE);
                return;
            case BACKGROUND:
                this.puzzleView.setLocked(false);
                this.puzzleView.setTouchEnable(false);
                slideUpSaveView();
                selectBackgroundColorTab();
                slideDown(this.mRvTools);
                slideUp(this.changeBackgroundLayout);
                showUpFunction(this.changeBackgroundLayout);
                if (this.puzzleView.getBackgroundResourceMode() == 0) {
                    this.currentBackgroundState.isColor = true;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawableId = ((ColorDrawable) this.puzzleView.getBackground()).getColor();
                    return;
                } else if (this.puzzleView.getBackgroundResourceMode() == 2 || (this.puzzleView.getBackground() instanceof ColorDrawable)) {
                    this.currentBackgroundState.isBitmap = true;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.puzzleView.getBackground();
                    return;
                } else if (this.puzzleView.getBackground() instanceof GradientDrawable) {
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.puzzleView.getBackground();
                    return;
                } else {
                    return;
                }
            default:
        }
    }


    public void loadPhoto() {
        final int i;
        final ArrayList arrayList = new ArrayList();
        if (this.lstPaths.size() > this.puzzleLayout.getAreaCount()) {
            i = this.puzzleLayout.getAreaCount();
        } else {
            i = this.lstPaths.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            Target r4 = new Target() {
                public void onBitmapFailed(Exception exc, Drawable drawable) {
                }

                public void onPrepareLoad(Drawable drawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    int width = bitmap.getWidth();
                    float f = (float) width;
                    float height = (float) bitmap.getHeight();
                    float max = Math.max(f / f, height / f);
                    if (max > 1.0f) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / max), (int) (height / max), false);
                    }
                    arrayList.add(bitmap);
                    if (arrayList.size() == i) {
                        if (TART_PuzzleViewActivity.this.lstPaths.size() < TART_PuzzleViewActivity.this.puzzleLayout.getAreaCount()) {
                            for (int i = 0; i < TART_PuzzleViewActivity.this.puzzleLayout.getAreaCount(); i++) {
                                TART_PuzzleViewActivity.this.puzzleView.addPiece((Bitmap) arrayList.get(i % i));
                            }
                        } else {
                            TART_PuzzleViewActivity.this.puzzleView.addPieces(arrayList);
                        }
                    }
                    TART_PuzzleViewActivity.this.targets.remove(this);
                }
            };
            Picasso picasso = Picasso.get();
            picasso.load("file:///" + this.lstPaths.get(i2)).resize(this.deviceWidth, this.deviceWidth).centerInside().config(Bitmap.Config.RGB_565).into((Target) r4);
            this.targets.add(r4);
        }
    }

    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getHeight(), 0.0f}).start();
    }


    public void onDestroy() {
        super.onDestroy();
        try {
            this.puzzleView.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void slideUpSaveView() {
        this.saveControl.setVisibility(View.GONE);
    }

    public void slideDownSaveView() {
        this.saveControl.setVisibility(View.VISIBLE);
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", 0.0f, (float) view.getHeight()).start();
    }

    public void onBackPressed() {
        if (this.currentMode == null) {
            super.onBackPressed();
            return;
        }
        try {
            switch (this.currentMode) {
                case LAYOUT:
                case BORDER:
                case RATIO:
                    slideDown(this.changeLayoutLayout);
                    slideUp(this.mRvTools);
                    slideDownSaveView();
                    showDownFunction();
                    this.puzzleView.updateLayout(this.puzzleLayout);
                    this.puzzleView.setPiecePadding(this.piecePadding);
                    this.puzzleView.setPieceRadian(this.pieceBorderRadius);
                    this.currentMode = TART_ToolType.NONE;
                    getWindowManager().getDefaultDisplay().getSize(new Point());
                    onNewAspectRatioSelected(this.currentAspect);
                    this.puzzleView.setAspectRatio(this.currentAspect);
                    this.puzzleView.setLocked(true);
                    this.puzzleView.setTouchEnable(true);
                    return;
                case FILTER:
                    slideUp(this.mRvTools);
                    slideDown(this.filterLayout);
                    this.puzzleView.setLocked(true);
                    this.puzzleView.setTouchEnable(true);
                    for (int i = 0; i < this.lstOriginalDrawable.size(); i++) {
                        this.puzzleView.getPuzzlePieces().get(i).setDrawable(this.lstOriginalDrawable.get(i));
                    }
                    this.puzzleView.invalidate();
                    slideDownSaveView();
                    this.currentMode = TART_ToolType.NONE;
                    return;
                case STICKER:
                    if (this.puzzleView.getStickers().size() <= 0) {
                        slideUp(this.wrapStickerList);
                        slideDown(this.stickerLayout);
                        this.addNewSticker.setVisibility(View.GONE);
                        this.puzzleView.setHandlingSticker((Sticker) null);
                        slideUp(this.mRvTools);
                        this.puzzleView.setLocked(true);
                        this.currentMode = TART_ToolType.NONE;
                    } else if (this.addNewSticker.getVisibility() == View.VISIBLE) {
                        this.puzzleView.getStickers().clear();
                        this.addNewSticker.setVisibility(View.GONE);
                        this.puzzleView.setHandlingSticker(null);
                        slideUp(this.wrapStickerList);
                        slideDown(this.stickerLayout);
                        slideUp(this.mRvTools);
                        this.puzzleView.setLocked(true);
                        this.puzzleView.setTouchEnable(true);
                        this.currentMode = TART_ToolType.NONE;
                    } else {
                        slideDown(this.wrapStickerList);
                        this.addNewSticker.setVisibility(View.VISIBLE);
                    }
                    slideDownSaveView();
                    return;
                case TEXT:
                    if (!this.puzzleView.getStickers().isEmpty()) {
                        this.puzzleView.getStickers().clear();
                        this.puzzleView.setHandlingSticker( null);
                    }
                    slideDown(this.textLayout);
                    this.addNewText.setVisibility(View.GONE);
                    this.puzzleView.setHandlingSticker( null);
                    slideUp(this.mRvTools);
                    slideDownSaveView();
                    this.puzzleView.setLocked(true);
                    this.currentMode = TART_ToolType.NONE;
                    this.puzzleView.setTouchEnable(true);
                    return;
                case BACKGROUND:
                    slideUp(this.mRvTools);
                    slideDown(this.changeBackgroundLayout);
                    this.puzzleView.setLocked(true);
                    this.puzzleView.setTouchEnable(true);
                    if (this.currentBackgroundState.isColor) {
                        this.puzzleView.setBackgroundResourceMode(0);
                        this.puzzleView.setBackgroundColor(this.currentBackgroundState.drawableId);
                    } else if (this.currentBackgroundState.isBitmap) {
                        this.puzzleView.setBackgroundResourceMode(2);
                        this.puzzleView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.puzzleView.setBackgroundResourceMode(1);
                        if (this.currentBackgroundState.drawable != null) {
                            this.puzzleView.setBackground(this.currentBackgroundState.drawable);
                        } else {
                            this.puzzleView.setBackgroundResource(this.currentBackgroundState.drawableId);
                        }
                    }
                    slideDownSaveView();
                    showDownFunction();
                    this.currentMode = TART_ToolType.NONE;
                    return;
                case PIECE:
                    slideDown(this.rvPieceControl);
                    slideUp(this.mRvTools);
                    slideDownSaveView();
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.rvPieceControl.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    this.rvPieceControl.setLayoutParams(layoutParams);
                    this.currentMode = TART_ToolType.NONE;
                    this.puzzleView.setHandlingPiece( null);
                    this.puzzleView.setPreviousHandlingPiece( null);
                    this.puzzleView.invalidate();
                    this.currentMode = TART_ToolType.NONE;
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

    private void showDiscardDialog() {
        new AlertDialog.Builder(this).setMessage(R.string.dialog_discard_title).setPositiveButton(R.string.discard, (dialogInterface, i) -> {
            TART_PuzzleViewActivity.this.currentMode = null;
            TART_PuzzleViewActivity.this.finish();
        }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }

    public void onItemClick(TART_PuzzleLayout puzzleLayout2, int i) {
        TART_PuzzleLayout parse = TART_PuzzleLayoutParser.parse(puzzleLayout2.generateInfo());
        puzzleLayout2.setRadian(this.puzzleView.getPieceRadian());
        puzzleLayout2.setPadding(this.puzzleView.getPiecePadding());
        this.puzzleView.updateLayout(parse);
    }

    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int[] calculateWidthAndHeight = calculateWidthAndHeight(aspectRatio, point);
        this.puzzleView.setLayoutParams(new ConstraintLayout.LayoutParams(calculateWidthAndHeight[0], calculateWidthAndHeight[1]));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.wrapPuzzleView);
        constraintSet.connect(this.puzzleView.getId(), 3, this.wrapPuzzleView.getId(), 3, 0);
        constraintSet.connect(this.puzzleView.getId(), 1, this.wrapPuzzleView.getId(), 1, 0);
        constraintSet.connect(this.puzzleView.getId(), 4, this.wrapPuzzleView.getId(), 4, 0);
        constraintSet.connect(this.puzzleView.getId(), 2, this.wrapPuzzleView.getId(), 2, 0);
        constraintSet.applyTo(this.wrapPuzzleView);
        this.puzzleView.setAspectRatio(aspectRatio);
    }

    public void replaceCurrentPiece(String str) {
        new OnLoadBitmapFromUri().execute(str);
    }

    private int[] calculateWidthAndHeight(AspectRatio aspectRatio, Point point) {
        int height = this.wrapPuzzleView.getHeight();
        if (aspectRatio.getHeight() > aspectRatio.getWidth()) {
            int ratio = (int) (aspectRatio.getRatio() * ((float) height));
            if (ratio < point.x) {
                return new int[]{ratio, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio.getRatio())};
        }
        int ratio2 = (int) (((float) point.x) / aspectRatio.getRatio());
        if (ratio2 > height) {
            return new int[]{(int) (((float) height) * aspectRatio.getRatio()), height};
        }
        return new int[]{point.x, ratio2};
    }

    public void onPause() {
        super.onPause();
        this.keyboardHeightProvider.onPause();
    }

    public void onResume() {
        super.onResume();
        this.keyboardHeightProvider.onResume();
    }

    public void addSticker(Bitmap bitmap) {
        this.puzzleView.addSticker(new TART_DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        slideDown(this.wrapStickerList);
        this.addNewSticker.setVisibility(View.VISIBLE);
    }

    public void onBackgroundSelected(final TART_PuzzleBackgroundAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.puzzleView.setBackgroundColor(squareView.drawableId);
            this.puzzleView.setBackgroundResourceMode(0);
        } else if (squareView.drawable != null) {
            this.puzzleView.setBackgroundResourceMode(2);
            new AsyncTask<Void, Bitmap, Bitmap>() {

                public void onPreExecute() {
                    TART_PuzzleViewActivity.this.showLoading(true);
                }


                public Bitmap doInBackground(Void... voidArr) {
                    return TART_FilterUtils.getBlurImageFromBitmap(((BitmapDrawable) squareView.drawable).getBitmap(), 5.0f);
                }


                public void onPostExecute(Bitmap bitmap) {
                    TART_PuzzleViewActivity.this.showLoading(false);
                    TART_PuzzleViewActivity.this.puzzleView.setBackground(new BitmapDrawable(TART_PuzzleViewActivity.this.getResources(), bitmap));
                }
            }.execute();
        } else {
            this.puzzleView.setBackgroundResource(squareView.drawableId);
            this.puzzleView.setBackgroundResourceMode(1);
        }
    }

    public void onFilterSelected(String str) {
        new LoadBitmapWithFilter().execute(new String[]{str});
    }


    public void finishCrop(Bitmap bitmap) {
        this.puzzleView.replace(bitmap, "");
    }

    public void onSaveFilter(Bitmap bitmap) {
        this.puzzleView.replace(bitmap, "");
    }

    @Override
    public void onPieceFuncSelected(TART_ToolType toolType) {
        switch (toolType) {
            case REPLACE:
                TART_PhotoPicker.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).setForwardMain(true).start(this);
                return;
            case H_FLIP:
                this.puzzleView.flipHorizontally();
                return;
            case V_FLIP:
                this.puzzleView.flipVertically();
                return;
            case ROTATE:
                this.puzzleView.rotate(90.0f);
                return;
            case CROP:
                TART_CropDialogFragment.show(this, this, ((BitmapDrawable) this.puzzleView.getHandlingPiece().getDrawable()).getBitmap());
                return;
            case FILTER:
                new LoadFilterBitmapForCurrentPiece().execute();

        }
    }

    class LoadFilterBitmap extends AsyncTask<Void, Void, Void> {
        LoadFilterBitmap() {
        }


        public void onPreExecute() {
            TART_PuzzleViewActivity.this.showLoading(true);
        }


        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            TART_PuzzleViewActivity.this.lstBitmapWithFilter.clear();
            TART_PuzzleViewActivity.this.lstBitmapWithFilter.addAll(TART_FilterUtils.getLstBitmapWithFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) TART_PuzzleViewActivity.this.puzzleView.getPuzzlePieces().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }


        public void onPostExecute(Void voidR) {
            TART_PuzzleViewActivity.this.rvFilterView.setAdapter(new TART_FilterViewAdapter(TART_PuzzleViewActivity.this.lstBitmapWithFilter, TART_PuzzleViewActivity.this, TART_PuzzleViewActivity.this, Arrays.asList(TART_FilterUtils.EFFECT_CONFIGS)));
            TART_PuzzleViewActivity.this.slideDown(TART_PuzzleViewActivity.this.mRvTools);
            TART_PuzzleViewActivity.this.slideUp(TART_PuzzleViewActivity.this.filterLayout);
            TART_PuzzleViewActivity.this.showLoading(false);
            TART_PuzzleViewActivity.this.puzzleView.setLocked(false);
            TART_PuzzleViewActivity.this.puzzleView.setTouchEnable(false);
        }
    }

    class LoadFilterBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadFilterBitmapForCurrentPiece() {
        }


        public void onPreExecute() {
            TART_PuzzleViewActivity.this.showLoading(true);
        }


        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return TART_FilterUtils.getLstBitmapWithFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) TART_PuzzleViewActivity.this.puzzleView.getHandlingPiece().getDrawable()).getBitmap(), 100, 100));
        }


        public void onPostExecute(List<Bitmap> list) {
            TART_PuzzleViewActivity.this.showLoading(false);
            if (TART_PuzzleViewActivity.this.puzzleView.getHandlingPiece() != null) {
                TART_FilterDialogFragment.show(TART_PuzzleViewActivity.this, TART_PuzzleViewActivity.this, ((BitmapDrawable) TART_PuzzleViewActivity.this.puzzleView.getHandlingPiece().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadBitmapWithFilter extends AsyncTask<String, List<Bitmap>, List<Bitmap>> {
        LoadBitmapWithFilter() {
        }


        public void onPreExecute() {
            TART_PuzzleViewActivity.this.showLoading(true);
        }


        public List<Bitmap> doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            for (Drawable drawable : TART_PuzzleViewActivity.this.lstOriginalDrawable) {
                arrayList.add(TART_FilterUtils.getBitmapWithFilter(((BitmapDrawable) drawable).getBitmap(), strArr[0]));
            }
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            for (int i = 0; i < list.size(); i++) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(TART_PuzzleViewActivity.this.getResources(), list.get(i));
                bitmapDrawable.setAntiAlias(true);
                bitmapDrawable.setFilterBitmap(true);
                TART_PuzzleViewActivity.this.puzzleView.getPuzzlePieces().get(i).setDrawable(bitmapDrawable);
            }
            TART_PuzzleViewActivity.this.puzzleView.invalidate();
            TART_PuzzleViewActivity.this.showLoading(false);
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }


        public void onPreExecute() {
            TART_PuzzleViewActivity.this.showLoading(true);
        }


        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));
                Bitmap rotateBitmap = TART_SystemUtil.rotateBitmap(MediaStore.Images.Media.getBitmap(TART_PuzzleViewActivity.this.getContentResolver(), fromFile), new ExifInterface(TART_PuzzleViewActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
                float width = (float) rotateBitmap.getWidth();
                float height = (float) rotateBitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                return max > 1.0f ? Bitmap.createScaledBitmap(rotateBitmap, (int) (width / max), (int) (height / max), false) : rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_PuzzleViewActivity.this.showLoading(false);
            TART_PuzzleViewActivity.this.puzzleView.replace(bitmap, "");
        }
    }

    class SavePuzzleAsFile extends AsyncTask<Bitmap, String, String> {
        SavePuzzleAsFile() {
        }


        public void onPreExecute() {
            TART_PuzzleViewActivity.this.showLoading(true);
        }


        public String doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = bitmapArr[0];
            Bitmap bitmap2 = bitmapArr[1];
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawBitmap(bitmap, null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), (Paint) null);
            canvas.drawBitmap(bitmap2, null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), (Paint) null);
            bitmap.recycle();
            bitmap2.recycle();
            File saveBitmapAsFile = TART_FileUtils.saveBitmapAsFile(TART_PuzzleViewActivity.this,createBitmap);
            if (saveBitmapAsFile == null) {
                return null;
            }
            try {
                MediaScannerConnection.scanFile(TART_PuzzleViewActivity.this.getApplicationContext(), new String[]{saveBitmapAsFile.getAbsolutePath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                createBitmap.recycle();
                return saveBitmapAsFile.getAbsolutePath();
            } catch (Exception e) {
                createBitmap.recycle();
                return null;
            } catch (Throwable th) {
                createBitmap.recycle();
                throw th;
            }
        }


        public void onPostExecute(String str) {
            TART_PuzzleViewActivity.this.showLoading(false);
            Intent intent = new Intent(TART_PuzzleViewActivity.this, TART_SaveAndShareActivity.class);
            intent.putExtra("path", str);
            TART_PuzzleViewActivity.this.startActivity(intent);
        }
    }
}
