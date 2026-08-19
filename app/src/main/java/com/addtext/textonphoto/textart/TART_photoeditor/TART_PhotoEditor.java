package com.addtext.textonphoto.textart.TART_photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.UiThread;

import com.addtext.textonphoto.textart.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.wysaid.view.ImageGLSurfaceView;

public class TART_PhotoEditor implements TART_BrushViewChangeListener {
    private static final String TAG = "PhotoEditor";
    private List<View> addedViews;
    private TART_BrushDrawingView brushDrawingView;
    private Context context;
    private View deleteView;
    private ImageGLSurfaceView glSurfaceView;
    private boolean isTextPinchZoomable;
    private Typeface mDefaultEmojiTypeface;
    private Typeface mDefaultTextTypeface;
    private final LayoutInflater mLayoutInflater;
    private TART_OnPhotoEditorListener mOnPhotoEditorListener;

    public TART_PhotoEditorView parentView;
    private List<View> redoViews;

    public interface OnSaveListener {
        void onFailure(@NonNull Exception exc);

        void onSuccess(@NonNull String str);
    }

    private TART_PhotoEditor(Builder builder) {
        this.context = builder.context;
        this.parentView = builder.parentView;
        this.deleteView = builder.deleteView;
        this.brushDrawingView = builder.brushDrawingView;
        this.glSurfaceView = builder.glSurfaceView;
        this.isTextPinchZoomable = builder.isTextPinchZoomable;
        this.mDefaultTextTypeface = builder.textTypeface;
        this.mDefaultEmojiTypeface = builder.emojiTypeface;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.brushDrawingView.setBrushViewChangeListener(this);
        this.addedViews = new ArrayList();
        this.redoViews = new ArrayList();
    }

    public TART_BrushDrawingView getBrushDrawingView() {
        return this.brushDrawingView;
    }


    public void setBrushDrawingMode(boolean z) {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.setBrushDrawingMode(z);
        }
    }

    public void setAdjustFilter(String str) {
        this.glSurfaceView.setFilterWithConfig(str);
    }

    public void setFilterIntensityForIndex(float f, int i, boolean z) {
        this.glSurfaceView.setFilterIntensityForIndex(f, i, z);
    }

    public void setBrushMode(int i) {
        this.brushDrawingView.setDrawMode(i);
    }

    public void setBrushMagic(TART_DrawBitmapModel drawBitmapModel) {
        this.brushDrawingView.setCurrentMagicBrush(drawBitmapModel);
    }


    public void setBrushSize(float f) {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.setBrushSize(f);
        }
    }


    public void setBrushColor(@ColorInt int i) {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.setBrushColor(i);
        }
    }

    public void setBrushEraserSize(float f) {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.setBrushEraserSize(f);
        }
    }


    public void brushEraser() {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.brushEraser();
        }
    }


    public boolean undo() {
        if (this.addedViews.size() > 0) {
            View view = this.addedViews.get(this.addedViews.size() - 1);
            if (!(view instanceof TART_BrushDrawingView)) {
                this.addedViews.remove(this.addedViews.size() - 1);
                this.parentView.removeView(view);
                this.redoViews.add(view);
                if (this.mOnPhotoEditorListener != null) {
                    this.mOnPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
                    Object tag = view.getTag();
                    if (tag != null && (tag instanceof TART_ViewType)) {
                        this.mOnPhotoEditorListener.onRemoveViewListener((TART_ViewType) tag, this.addedViews.size());
                    }
                }
            } else if (this.brushDrawingView == null || !this.brushDrawingView.undo()) {
                return false;
            } else {
                return true;
            }
        }
        if (this.addedViews.size() != 0) {
            return true;
        }
        return false;
    }

    public boolean redo() {
        if (this.redoViews.size() > 0) {
            View view = this.redoViews.get(this.redoViews.size() - 1);
            if (!(view instanceof TART_BrushDrawingView)) {
                this.redoViews.remove(this.redoViews.size() - 1);
                this.parentView.addView(view);
                this.addedViews.add(view);
                Object tag = view.getTag();
                if (!(this.mOnPhotoEditorListener == null || tag == null || !(tag instanceof TART_ViewType))) {
                    this.mOnPhotoEditorListener.onAddViewListener((TART_ViewType) tag, this.addedViews.size());
                }
            } else if (this.brushDrawingView == null || !this.brushDrawingView.redo()) {
                return false;
            } else {
                return true;
            }
        }
        if (this.redoViews.size() != 0) {
            return true;
        }
        return false;
    }

    public void redoBrush() {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.redo();
        }
    }

    public void undoBrush() {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.undo();
        }
    }

    public void clearBrushAllViews() {
        if (this.brushDrawingView != null) {
            this.brushDrawingView.clearAll();
        }
    }

    public void clearAllViews() {
        for (int i = 0; i < this.addedViews.size(); i++) {
            this.parentView.removeView(this.addedViews.get(i));
        }
        if (this.addedViews.contains(this.brushDrawingView)) {
            this.parentView.addView(this.brushDrawingView);
        }
        this.addedViews.clear();
        this.redoViews.clear();
        clearBrushAllViews();
    }

    @UiThread
    public void clearHelperBox() {
        for (int i = 0; i < this.parentView.getChildCount(); i++) {
            View childAt = this.parentView.getChildAt(i);
            FrameLayout frameLayout = (FrameLayout) childAt.findViewById(R.id.frmBorder);
            if (frameLayout != null) {
                frameLayout.setBackgroundResource(0);
            }
            ImageView imageView = (ImageView) childAt.findViewById(R.id.imgPhotoEditorClose);
            if (imageView != null) {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    public void setFilterEffect(String str) {
        this.parentView.setFilterEffect(str);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    @SuppressLint({"StaticFieldLeak"})
    @Deprecated
    public void saveImage(@NonNull String str, @NonNull OnSaveListener onSaveListener) {
        saveAsFile(str, onSaveListener);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    public void saveAsFile(@NonNull String str, @NonNull OnSaveListener onSaveListener) {
        saveAsFile(str, new TART_SaveSettings.Builder().build(), onSaveListener);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    @SuppressLint({"StaticFieldLeak"})
    public void saveAsFile(@NonNull final String str, @NonNull final TART_SaveSettings saveSettings, @NonNull final OnSaveListener onSaveListener) {
        this.parentView.saveGLSurfaceViewAsBitmap(new TART_OnSaveBitmap() {
            public void onBitmapReady(Bitmap bitmap) {
                new AsyncTask<String, String, Exception>() {

                    public void onPreExecute() {
                        super.onPreExecute();
                        TART_PhotoEditor.this.clearHelperBox();
                    }


                    @SuppressLint({"MissingPermission"})
                    public Exception doInBackground(String... strArr) {
                        Bitmap bitmap;
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(str), false);
                            if (TART_PhotoEditor.this.parentView != null) {
                                if (saveSettings.isTransparencyEnabled()) {
                                    bitmap = TART_BitmapUtil.removeTransparency(getBitmapFromView(TART_PhotoEditor.this.parentView));
                                } else {
                                    bitmap = getBitmapFromView(TART_PhotoEditor.this.parentView);
                                }
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            }
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Log.d(TART_PhotoEditor.TAG, "Filed Saved Successfully");
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TART_PhotoEditor.TAG, "Failed to save File");
                            return e;
                        }
                    }


                    public void onPostExecute(Exception exc) {
                        super.onPostExecute(exc);
                        if (exc == null) {
                            if (saveSettings.isClearViewsEnabled()) {
                                TART_PhotoEditor.this.clearAllViews();
                            }
                            onSaveListener.onSuccess(str);
                            return;
                        }
                        onSaveListener.onFailure(exc);
                    }
                }.execute(new String[0]);
            }

            public void onFailure(Exception exc) {
                onSaveListener.onFailure(exc);
            }
        });
    }

    @SuppressLint({"StaticFieldLeak"})
    public void saveStickerAsBitmap(@NonNull TART_OnSaveBitmap onSaveBitmap) {
        saveStickerAsBitmap(new TART_SaveSettings.Builder().build(), onSaveBitmap);
    }

    @SuppressLint({"StaticFieldLeak"})
    public void saveStickerAsBitmap(@NonNull TART_SaveSettings saveSettings, @NonNull TART_OnSaveBitmap onSaveBitmap) {
        Bitmap bitmap;

        if (saveSettings.isTransparencyEnabled()) {
            bitmap = TART_BitmapUtil.removeTransparency(getBitmapFromView(this.parentView));
        } else {
            bitmap = getBitmapFromView(this.parentView);
        }
        onSaveBitmap.onBitmapReady(bitmap);
    }

    private static String convertEmoji(String str) {
        try {
            return new String(Character.toChars(Integer.parseInt(str.substring(2), 16)));
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public void setOnPhotoEditorListener(@NonNull TART_OnPhotoEditorListener onPhotoEditorListener) {
        this.mOnPhotoEditorListener = onPhotoEditorListener;
    }

    public boolean isCacheEmpty() {
        return this.addedViews.size() == 0 && this.redoViews.size() == 0;
    }

    public void onViewAdd(TART_BrushDrawingView brushDrawingView2) {
        if (this.redoViews.size() > 0) {
            this.redoViews.remove(this.redoViews.size() - 1);
        }
        this.addedViews.add(brushDrawingView2);
        if (this.mOnPhotoEditorListener != null) {
            this.mOnPhotoEditorListener.onAddViewListener(TART_ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    public void onViewRemoved(TART_BrushDrawingView brushDrawingView2) {
        if (this.addedViews.size() > 0) {
            View remove = this.addedViews.remove(this.addedViews.size() - 1);
            if (!(remove instanceof TART_BrushDrawingView)) {
                this.parentView.removeView(remove);
            }
            this.redoViews.add(remove);
        }
        if (this.mOnPhotoEditorListener != null) {
            this.mOnPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
            this.mOnPhotoEditorListener.onRemoveViewListener(TART_ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    public void onStartDrawing() {
        if (this.mOnPhotoEditorListener != null) {
            this.mOnPhotoEditorListener.onStartViewChangeListener(TART_ViewType.BRUSH_DRAWING);
        }
    }

    public void onStopDrawing() {
        if (this.mOnPhotoEditorListener != null) {
            this.mOnPhotoEditorListener.onStopViewChangeListener(TART_ViewType.BRUSH_DRAWING);
        }
    }

    public static class Builder {

        public TART_BrushDrawingView brushDrawingView;

        public Context context;

        public View deleteView;

        public Typeface emojiTypeface;

        public ImageGLSurfaceView glSurfaceView;

        public boolean isTextPinchZoomable = true;

        public TART_PhotoEditorView parentView;

        public Typeface textTypeface;

        public Builder(Context context2, TART_PhotoEditorView photoEditorView) {
            this.context = context2;
            this.parentView = photoEditorView;
            this.brushDrawingView = photoEditorView.getBrushDrawingView();
            this.glSurfaceView = photoEditorView.getGLSurfaceView();
        }


        public Builder setDeleteView(View view) {
            this.deleteView = view;
            return this;
        }

        public Builder setDefaultTextTypeface(Typeface typeface) {
            this.textTypeface = typeface;
            return this;
        }

        public Builder setDefaultEmojiTypeface(Typeface typeface) {
            this.emojiTypeface = typeface;
            return this;
        }

        public Builder setPinchTextScalable(boolean z) {
            this.isTextPinchZoomable = z;
            return this;
        }

        public TART_PhotoEditor build() {
            return new TART_PhotoEditor(this);
        }
    }

    public static ArrayList<String> getEmojis(Context context2) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String convertEmoji : context2.getResources().getStringArray(R.array.photo_editor_emoji)) {
            arrayList.add(convertEmoji(convertEmoji));
        }
        return arrayList;
    }

    public Bitmap getBitmapFromView(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
