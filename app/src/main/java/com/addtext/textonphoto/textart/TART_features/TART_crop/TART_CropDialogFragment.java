package com.addtext.textonphoto.textart.TART_features.TART_crop;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.addtext.textonphoto.textart.TART_features.TART_crop.TART_adapter.TART_AspectRatioPreviewAdapter;
import com.addtext.textonphoto.textart.R;
import com.steelkiwi.cropiwa.AspectRatio;
import java.io.File;
import java.io.FileOutputStream;

public class TART_CropDialogFragment extends DialogFragment implements TART_AspectRatioPreviewAdapter.OnNewSelectedListener {
    private static final String TAG = "CropDialogFragment";
    private Bitmap bitmap;
    private RelativeLayout loadingView;
    public CropImageView mCropView;
    public OnCropPhoto onCropPhoto;

    public interface OnCropPhoto {
        void finishCrop(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static TART_CropDialogFragment show(@NonNull AppCompatActivity appCompatActivity, OnCropPhoto onCropPhoto2, Bitmap bitmap2) {
        TART_CropDialogFragment cropDialogFragment = new TART_CropDialogFragment();
        cropDialogFragment.setBitmap(bitmap2);
        cropDialogFragment.setOnCropPhoto(onCropPhoto2);
        cropDialogFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return cropDialogFragment;
    }

    public void setOnCropPhoto(OnCropPhoto onCropPhoto2) {
        this.onCropPhoto = onCropPhoto2;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_NoActionBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View inflate = layoutInflater.inflate(R.layout.knack_crop_layout, viewGroup, false);
        TART_AspectRatioPreviewAdapter aspectRatioPreviewAdapter = new TART_AspectRatioPreviewAdapter();
        aspectRatioPreviewAdapter.setListener(this);
        RecyclerView recyclerView = inflate.findViewById(R.id.fixed_ratio_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(aspectRatioPreviewAdapter);
        this.mCropView = inflate.findViewById(R.id.crop_view);

        View rotateBtn = inflate.findViewById(R.id.rotate);
        if (rotateBtn != null) {
            rotateBtn.setOnClickListener(view -> {
                if (TART_CropDialogFragment.this.mCropView != null) {
                    TART_CropDialogFragment.this.mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                }
            });
        }

        View saveBtn = inflate.findViewById(R.id.imgSave);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(view -> new OnSaveCrop().execute());
        }

        this.loadingView = inflate.findViewById(R.id.loadingView);
        if (this.loadingView != null) {
            this.loadingView.setVisibility(View.GONE);
        }

        View closeBtn = inflate.findViewById(R.id.imgClose);
        if (closeBtn != null) {
            closeBtn.setOnClickListener(view -> TART_CropDialogFragment.this.dismiss());
        }
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mCropView = view.findViewById(R.id.crop_view);
        if (this.mCropView != null && this.bitmap != null) {
            showLoading(true);
            new Thread(() -> {
                try {
                    File tempFile = new File(requireContext().getCacheDir(), "temp_crop_source.png");
                    try (FileOutputStream out = new FileOutputStream(tempFile)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    }
                    android.net.Uri uri = android.net.Uri.fromFile(tempFile);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (mCropView != null && isAdded()) {
                                mCropView.load(uri)
                                        .initialFrameScale(0.9f)
                                        .useThumbnail(true)
                                        .execute(new LoadCallback() {
                                            @Override
                                            public void onSuccess() {
                                                showLoading(false);
                                                mCropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                showLoading(false);
                                            }
                                        });
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> showLoading(false));
                    }
                }
            }).start();
        }
    }

    @Override
    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        if (this.mCropView == null) return;
        if (aspectRatio.getWidth() == 10 && aspectRatio.getHeight() == 10) {
            this.mCropView.setCropMode(CropImageView.CropMode.FREE);
        } else if (aspectRatio.getWidth() == 1 && aspectRatio.getHeight() == 1) {
            this.mCropView.setCropMode(CropImageView.CropMode.SQUARE);
        } else if (aspectRatio.getWidth() == 4 && aspectRatio.getHeight() == 3) {
            this.mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
        } else if (aspectRatio.getWidth() == 3 && aspectRatio.getHeight() == 4) {
            this.mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
        } else if (aspectRatio.getWidth() == 9 && aspectRatio.getHeight() == 16) {
            this.mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
        } else if (aspectRatio.getWidth() == 16 && aspectRatio.getHeight() == 9) {
            this.mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
        } else {
            this.mCropView.setCustomRatio(aspectRatio.getWidth(), aspectRatio.getHeight());
        }
    }

    class OnSaveCrop extends AsyncTask<Void, Bitmap, Bitmap> {
        OnSaveCrop() {
        }

        @Override
        public void onPreExecute() {
            TART_CropDialogFragment.this.showLoading(true);
        }

        @Override
        public Bitmap doInBackground(Void... voidArr) {
            if (TART_CropDialogFragment.this.mCropView != null) {
                return TART_CropDialogFragment.this.mCropView.getCroppedBitmap();
            }
            return null;
        }

        @Override
        public void onPostExecute(Bitmap croppedBitmap) {
            TART_CropDialogFragment.this.showLoading(false);
            if (croppedBitmap != null && TART_CropDialogFragment.this.onCropPhoto != null) {
                TART_CropDialogFragment.this.onCropPhoto.finishCrop(croppedBitmap);
            }
            TART_CropDialogFragment.this.dismiss();
        }
    }

    public void showLoading(boolean z) {
        if (this.loadingView != null) {
            this.loadingView.setVisibility(z ? View.VISIBLE : View.GONE);
        }
    }
}
