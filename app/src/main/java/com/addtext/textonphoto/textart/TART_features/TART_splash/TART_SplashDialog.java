package com.addtext.textonphoto.textart.TART_features.TART_splash;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.addtext.textonphoto.textart.TART_filters.TART_FilterUtils;
import com.addtext.textonphoto.textart.TART_sticker.TART_SplashSticker;
import com.addtext.textonphoto.textart.TART_utils.TART_AssetUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_SharePreferenceUtil;
import com.addtext.textonphoto.textart.R;

public class TART_SplashDialog extends DialogFragment implements TART_SplashAdapter.SplashChangeListener {
    private static final String TAG = "SplashDialog";
    private ImageView backgroundView;

    public Bitmap bitmap;
    private Bitmap blackAndWhiteBitmap;
    private Bitmap blurBitmap;
    private ElegantNumberButton blurNumber;
    private TART_SplashSticker blurSticker;
    private SeekBar brushIntensity;

    public TextView draw;

    public LinearLayout drawLayout;
    private FrameLayout frameLayout;

    public boolean isSplashView;
    private RelativeLayout loadingView;
    private ImageView redo;

    public RecyclerView rvSplashView;

    public TextView shape;

    public SplashDialogListener splashDialogListener;
    private TART_SplashSticker splashSticker;

    public TART_SplashView splashView;
    private ImageView undo;
    private ViewGroup viewGroup;

    public interface SplashDialogListener {
        void onSaveSplash(Bitmap bitmap);
    }

    public void setSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static TART_SplashDialog show(@NonNull AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, SplashDialogListener splashDialogListener2, boolean z) {
        TART_SplashDialog splashDialog = new TART_SplashDialog();
        splashDialog.setBlurBitmap(bitmap3);
        splashDialog.setBitmap(bitmap2);
        splashDialog.setBlackAndWhiteBitmap(bitmap4);
        splashDialog.setSplashDialogListener(splashDialogListener2);
        splashDialog.setSplashView(z);
        splashDialog.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return splashDialog;
    }

    public void setBlackAndWhiteBitmap(Bitmap bitmap2) {
        this.blackAndWhiteBitmap = bitmap2;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    public void setSplashDialogListener(SplashDialogListener splashDialogListener2) {
        this.splashDialogListener = splashDialogListener2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup2, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.knack_splash_layout, viewGroup2, false);
        this.viewGroup = viewGroup2;
        this.backgroundView = inflate.findViewById(R.id.backgroundView);
        this.splashView = inflate.findViewById(R.id.splashView);
        this.drawLayout = inflate.findViewById(R.id.drawLayout);
        this.drawLayout.setVisibility(View.GONE);
        this.frameLayout = inflate.findViewById(R.id.frameLayout);
        this.undo = inflate.findViewById(R.id.undo);

        this.undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.splashView.undo();
            }
        });
        this.redo = inflate.findViewById(R.id.redo);
        this.redo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.splashView.redo();
            }
        });
        this.brushIntensity = inflate.findViewById(R.id.brushIntensity);
        this.brushIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_SplashDialog.this.splashView.setBrushBitmapSize(i + 25);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                TART_SplashDialog.this.splashView.updateBrush();
            }
        });
        this.loadingView = inflate.findViewById(R.id.loadingView);
        this.loadingView.setVisibility(View.GONE);
        this.blurNumber = inflate.findViewById(R.id.blurNumber);
        this.backgroundView.setImageBitmap(this.bitmap);
        this.shape = inflate.findViewById(R.id.shape);
        this.draw = inflate.findViewById(R.id.draw);
        if (this.isSplashView) {
            this.splashView.setImageBitmap(this.blackAndWhiteBitmap);
            this.blurNumber.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.shape.getLayoutParams();
            layoutParams.horizontalBias = 0.3f;
            this.shape.setLayoutParams(layoutParams);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.draw.getLayoutParams();
            layoutParams2.horizontalBias = 0.7f;
            this.draw.setLayoutParams(layoutParams2);
        } else {
            this.splashView.setImageBitmap(this.blurBitmap);
            this.blurNumber.setRange(0, 10);
        }
        this.blurNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            public void onValueChange(ElegantNumberButton elegantNumberButton, int i, int i2) {
                new LoadBlurBitmap((float) i2).execute(new Void[0]);
            }
        });
        this.rvSplashView = inflate.findViewById(R.id.rvSplashView);
        this.rvSplashView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.rvSplashView.setHasFixedSize(true);
        this.rvSplashView.setAdapter(new TART_SplashAdapter(getActivity(), this, this.isSplashView));
        if (this.isSplashView) {
            this.splashSticker = new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(getContext(), "splash/icons/mask1.webp"), TART_AssetUtils.loadBitmapFromAssets(getContext(), "splash/icons/frame1.webp"));
            this.splashView.addSticker(this.splashSticker);
        } else {
            this.blurSticker = new TART_SplashSticker(TART_AssetUtils.loadBitmapFromAssets(getContext(), "blur/icons/blur_1_mask.webp"), TART_AssetUtils.loadBitmapFromAssets(getContext(), "blur/icons/blur_1_shadow.webp"));
            this.splashView.addSticker(this.blurSticker);
        }
        this.splashView.refreshDrawableState();
        this.splashView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.shape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.draw.setBackgroundResource(0);
                TART_SplashDialog.this.draw.setTextColor(ContextCompat.getColor(getContext(),R.color.unselected_color));
                TART_SplashDialog.this.shape.setBackgroundResource(R.drawable.knack_border_bottom);
                TART_SplashDialog.this.shape.setTextColor(ContextCompat.getColor(getContext(),R.color.selected));
                TART_SplashDialog.this.splashView.setCurrentSplashMode(0);
                TART_SplashDialog.this.drawLayout.setVisibility(View.GONE);
                TART_SplashDialog.this.rvSplashView.setVisibility(View.VISIBLE);
                TART_SplashDialog.this.splashView.refreshDrawableState();
                TART_SplashDialog.this.splashView.invalidate();
                if (TART_SplashDialog.this.isSplashView) {
                    if (TART_SharePreferenceUtil.isFirstShapeSplash(TART_SplashDialog.this.getContext())) {
                        TART_SplashDialog.this.showShapeTutorial();
                    }
                } else if (TART_SharePreferenceUtil.isFirstShapeBlur(TART_SplashDialog.this.getContext())) {
                    TART_SplashDialog.this.showShapeTutorial();
                }
            }
        });
        this.draw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.splashView.refreshDrawableState();
                TART_SplashDialog.this.splashView.setLayerType(View.LAYER_TYPE_SOFTWARE, (Paint) null);
                TART_SplashDialog.this.shape.setBackgroundResource(0);
                TART_SplashDialog.this.shape.setTextColor(ContextCompat.getColor(getContext(),R.color.unselected_color));
                TART_SplashDialog.this.draw.setBackgroundResource(R.drawable.knack_border_bottom);
                TART_SplashDialog.this.draw.setTextColor(ContextCompat.getColor(getContext(),R.color.selected));
                TART_SplashDialog.this.splashView.setCurrentSplashMode(1);
                TART_SplashDialog.this.drawLayout.setVisibility(View.VISIBLE);
                TART_SplashDialog.this.rvSplashView.setVisibility(View.GONE);
                TART_SplashDialog.this.splashView.invalidate();
                if (TART_SplashDialog.this.isSplashView) {
                    if (TART_SharePreferenceUtil.isFirstDrawSplash(TART_SplashDialog.this.getContext())) {
                        TART_SplashDialog.this.showDrawTutorial();
                    }
                } else if (TART_SharePreferenceUtil.isFirstDrawBlur(TART_SplashDialog.this.getContext())) {
                    TART_SplashDialog.this.showDrawTutorial();
                }
            }
        });
        inflate.findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.splashDialogListener.onSaveSplash(TART_SplashDialog.this.splashView.getBitmap(TART_SplashDialog.this.bitmap));
                TART_SplashDialog.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TART_SplashDialog.this.dismiss();
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (TART_SplashDialog.this.isSplashView) {
                    if (TART_SharePreferenceUtil.isFirstShapeSplash(TART_SplashDialog.this.getContext())) {
                        TART_SplashDialog.this.showShapeTutorial();
                    }
                } else if (TART_SharePreferenceUtil.isFirstShapeBlur(TART_SplashDialog.this.getContext())) {
                    TART_SplashDialog.this.showShapeTutorial();
                }
            }
        }, 1000);
        return inflate;
    }


    public void showDrawTutorial() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.knack_draw_splash, this.viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        inflate.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TART_SplashDialog.this.isSplashView) {
                    TART_SharePreferenceUtil.setFirstDrawSplash(TART_SplashDialog.this.getContext(), false);
                } else {
                    TART_SharePreferenceUtil.setFirstDrawBlur(TART_SplashDialog.this.getContext(), false);
                }
                create.dismiss();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }


    public void showShapeTutorial() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.knack_pinch_to_zoom_splash, this.viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        inflate.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TART_SplashDialog.this.isSplashView) {
                    TART_SharePreferenceUtil.setFirstShapeSplash(TART_SplashDialog.this.getContext(), false);
                } else {
                    TART_SharePreferenceUtil.setFirstShapeBlur(TART_SplashDialog.this.getContext(), false);
                }
                create.dismiss();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.splashView.getSticker().release();
        if (this.blurBitmap != null) {
            this.blurBitmap.recycle();
        }
        this.blurBitmap = null;
        if (this.blackAndWhiteBitmap != null) {
            this.blackAndWhiteBitmap.recycle();
        }
        this.blackAndWhiteBitmap = null;
        this.bitmap = null;
    }

    public void onSelected(TART_SplashSticker splashSticker2) {
        this.splashView.addSticker(splashSticker2);
    }

    class LoadBlurBitmap extends AsyncTask<Void, Bitmap, Bitmap> {
        private float intensity;

        public LoadBlurBitmap(float f) {
            this.intensity = f;
        }


        public void onPreExecute() {
            TART_SplashDialog.this.showLoading(true);
        }


        public Bitmap doInBackground(Void... voidArr) {
            return TART_FilterUtils.getBlurImageFromBitmap(TART_SplashDialog.this.bitmap, this.intensity);
        }


        public void onPostExecute(Bitmap bitmap) {
            TART_SplashDialog.this.showLoading(false);
            TART_SplashDialog.this.splashView.setImageBitmap(bitmap);
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.loadingView.setVisibility(View.VISIBLE);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.loadingView.setVisibility(View.GONE);
    }
}
