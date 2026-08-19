package com.addtext.textonphoto.textart.TART_photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import com.addtext.textonphoto.textart.TART_sticker.TART_StickerView;
import com.addtext.textonphoto.textart.R;

import org.wysaid.view.ImageGLSurfaceView;

public class TART_PhotoEditorView extends TART_StickerView {
    View xmlView;
    private Bitmap currentBitmap;
    private TART_BrushDrawingView mBrushDrawingView;

    public ImageGLSurfaceView mGLSurfaceView;
    private TART_FilterImageView mImgSource;

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


    @SuppressLint({"Recycle", "ResourceType"})
    private void init(@Nullable AttributeSet attributeSet) {
        this.mImgSource = new TART_FilterImageView(getContext());
        this.mImgSource.setId(1);
        this.mImgSource.setAdjustViewBounds(true);
        this.mImgSource.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.black));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(13, -1);
        this.mBrushDrawingView = new TART_BrushDrawingView(getContext());
        this.mBrushDrawingView.setVisibility(View.GONE);
        this.mBrushDrawingView.setId(2);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(13, -1);
        layoutParams2.addRule(6, 1);
        layoutParams2.addRule(8, 1);
        this.mGLSurfaceView = new ImageGLSurfaceView(getContext(), attributeSet);
        this.mGLSurfaceView.setId(3);
        this.mGLSurfaceView.setVisibility(View.VISIBLE);
        this.mGLSurfaceView.setAlpha(1.0f);
        this.mGLSurfaceView.setDisplayMode(ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(13, -1);
        layoutParams3.addRule(6, 1);
        layoutParams3.addRule(8, 1);
        addView(this.mImgSource, layoutParams);
        addView(this.mGLSurfaceView, layoutParams3);
        addView(this.mBrushDrawingView, layoutParams2);



        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        xmlView = inflater.inflate(R.layout.knack_layout_watermark, null);

        addView(xmlView, layoutParams);

    }


    public void setImageSource(final Bitmap bitmap) {
        this.mImgSource.setImageBitmap(bitmap);
        if (this.mGLSurfaceView.getImageHandler() != null) {
            this.mGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.mGLSurfaceView.setSurfaceCreatedCallback(new ImageGLSurfaceView.OnSurfaceCreatedCallback() {
                public void surfaceCreated() {
                    TART_PhotoEditorView.this.mGLSurfaceView.setImageBitmap(bitmap);
                }
            });
        }
        this.currentBitmap = bitmap;
    }

    public void setImageSource(Bitmap bitmap, ImageGLSurfaceView.OnSurfaceCreatedCallback onSurfaceCreatedCallback) {
        this.mImgSource.setImageBitmap(bitmap);
        if (this.mGLSurfaceView.getImageHandler() != null) {
            this.mGLSurfaceView.setImageBitmap(bitmap);
        } else {
            this.mGLSurfaceView.setSurfaceCreatedCallback(onSurfaceCreatedCallback);
        }
        this.currentBitmap = bitmap;
    }

    public Bitmap getCurrentBitmap() {
        return this.currentBitmap;
    }
    public Bitmap getSaveImg() {

        xmlView.setDrawingCacheEnabled(true);
        xmlView.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(xmlView.getDrawingCache());
        xmlView.setDrawingCacheEnabled(false);

        return bitmapOverlayToCenter(currentBitmap,bitmap);
    }

    public Bitmap bitmapOverlayToCenter(Bitmap bitmap1, Bitmap overlayBitmap) {
        int bitmap1Width = bitmap1.getWidth();
        int bitmap1Height = bitmap1.getHeight();
        int bitmap2Width = overlayBitmap.getWidth();
        int bitmap2Height = overlayBitmap.getHeight();

        float marginLeft = (float) (bitmap1Width * 0.5 - bitmap2Width * 0.5);
        float marginTop = (float) (bitmap1Height * 0.5 - bitmap2Height * 0.5);

        Bitmap finalBitmap = Bitmap.createBitmap(bitmap1Width, bitmap1Height, bitmap1.getConfig());
        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawBitmap(bitmap1, new Matrix(), null);
        canvas.drawBitmap(overlayBitmap, marginLeft, marginTop, null);
        return finalBitmap;
    }


    public TART_BrushDrawingView getBrushDrawingView() {
        return this.mBrushDrawingView;
    }

    public ImageGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public void saveGLSurfaceViewAsBitmap(@NonNull final TART_OnSaveBitmap onSaveBitmap) {
        if (this.mGLSurfaceView.getVisibility() == VISIBLE) {
            this.mGLSurfaceView.getResultBitmap(new ImageGLSurfaceView.QueryResultBitmapCallback() {
                public void get(Bitmap bitmap) {
                    onSaveBitmap.onBitmapReady(bitmap);
                }
            });
        }
    }

    public void setFilterEffect(String str) {
        this.mGLSurfaceView.setFilterWithConfig(str);
    }

    public void setFilterIntensity(float f) {
        this.mGLSurfaceView.setFilterIntensity(f);
    }
}
