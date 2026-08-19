package com.addtext.textonphoto.textart.TART_imagechanger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.addtext.textonphoto.textart.R;


public class TART_PhotoEditorView extends RelativeLayout {
    private static final String TAG = "PhotoEditorView";
    private TART_BrushDrawingView mBrushDrawingView;
    public TART_ImageFilterView mImageFilterView;
    public TART_FilterImageView mImgSource;

    public TART_PhotoEditorView(Context context) {
        super(context);
        init(null);
    }

    public TART_PhotoEditorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public TART_PhotoEditorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public TART_PhotoEditorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        Drawable drawable;
        TART_FilterImageView filterImageView = new TART_FilterImageView(getContext());
        this.mImgSource = filterImageView;
        filterImageView.setId(1);
        this.mImgSource.setAdjustViewBounds(true);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13, -1);
        int[] iArr = {R.attr.photo_src};
        if (attributeSet != null && (drawable = getContext().obtainStyledAttributes(attributeSet, iArr).getDrawable(0)) != null) {
            this.mImgSource.setImageDrawable(drawable);
            mImgSource.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        TART_BrushDrawingView brushDrawingView = new TART_BrushDrawingView(getContext());
        this.mBrushDrawingView = brushDrawingView;
        brushDrawingView.setVisibility(8);
        this.mBrushDrawingView.setId(2);
        LayoutParams layoutParams2 = new LayoutParams(-1, -2);
        layoutParams2.addRule(13, -1);
        layoutParams2.addRule(6, 1);
        layoutParams2.addRule(8, 1);
        TART_ImageFilterView imageFilterView = new TART_ImageFilterView(getContext());
        this.mImageFilterView = imageFilterView;
        imageFilterView.setId(3);
        this.mImageFilterView.setVisibility(8);
        LayoutParams layoutParams3 = new LayoutParams(-1, -2);
        layoutParams3.addRule(13, -1);
        layoutParams3.addRule(6, 1);
        layoutParams3.addRule(8, 1);
        this.mImgSource.setOnImageChangedListener(new TART_FilterImageView.OnImageChangedListener() {
            @Override
            public final void onBitmapLoaded(Bitmap bitmap) {
                TART_PhotoEditorView.this.lambda$init$0$PhotoEditorView(bitmap);
            }
        });
        addView(this.mImgSource, layoutParams);
        addView(this.mImageFilterView, layoutParams3);
        addView(this.mBrushDrawingView, layoutParams2);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View xmlView = inflater.inflate(R.layout.knack_layout_watermark, null);

        addView(xmlView, layoutParams);


    }

    public  void lambda$init$0$PhotoEditorView(Bitmap bitmap) {
        this.mImageFilterView.setFilterEffect(TART_PhotoFilter.NONE);
        this.mImageFilterView.setSourceBitmap(bitmap);
        Log.d(TAG, "onBitmapLoaded() called with: sourceBitmap = [" + bitmap + "]");
    }

    public ImageView getSource() {
        return this.mImgSource;
    }

    public TART_BrushDrawingView getBrushDrawingView() {
        return this.mBrushDrawingView;
    }

    public void saveFilter(final TART_OnSaveBitmap onSaveBitmap) {
        if (this.mImageFilterView.getVisibility() == 0) {
            this.mImageFilterView.saveBitmap(new TART_OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap bitmap) {
                    Log.e(TART_PhotoEditorView.TAG, "saveFilter: " + bitmap);
                    TART_PhotoEditorView.this.mImgSource.setImageBitmap(bitmap);
                    TART_PhotoEditorView.this.mImageFilterView.setVisibility(8);
                    onSaveBitmap.onBitmapReady(bitmap);
                }

                @Override // com.addtext.textonphoto.textart.imagechanger.OnSaveBitmap
                public void onFailure(Exception exc) {
                    onSaveBitmap.onFailure(exc);
                }
            });
        } else {
            onSaveBitmap.onBitmapReady(this.mImgSource.getBitmap());
        }
    }

    public void setFilterEffect(TART_PhotoFilter photoFilter) {
        this.mImageFilterView.setVisibility(0);
        this.mImageFilterView.setSourceBitmap(this.mImgSource.getBitmap());
        this.mImageFilterView.setFilterEffect(photoFilter);
    }

    public void setFilterEffect(TART_CustomEffect customEffect) {
        this.mImageFilterView.setVisibility(0);
        this.mImageFilterView.setSourceBitmap(this.mImgSource.getBitmap());
        this.mImageFilterView.setFilterEffect(customEffect);
    }
}
