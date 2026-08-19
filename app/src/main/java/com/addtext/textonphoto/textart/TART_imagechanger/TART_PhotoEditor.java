package com.addtext.textonphoto.textart.TART_imagechanger;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addtext.textonphoto.textart.R;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TART_PhotoEditor implements TART_BrushViewChangeListener {
    private static final String TAG = "PhotoEditor";
    private final List<View> addedViews;
    private final View alignView;
    private final TART_BrushDrawingView brushDrawingView;
    private final Context context;
    private final View deleteView;
    private TART_RoundFrameLayout frBorder;
    Handler handler;
    private final ImageView imageView;
    private final boolean isTextPinchZoomable;
    private final Typeface mDefaultEmojiTypeface;
    private final Typeface mDefaultTextTypeface;
    private final LayoutInflater mLayoutInflater;
    public TART_OnPhotoEditorListener mOnPhotoEditorListener;
    public PointF midPoint;
    public TART_PhotoEditorView parentView;
    private final List<View> redoViews;
    private final PointF startPoint;
    private final View zoomView;


    public interface OnSaveListener {
        void onFailure(Exception exc);

        void onSuccess(Uri uri);
    }

    private TART_PhotoEditor(Builder builder) {
        this.handler = new Handler();
        this.midPoint = new PointF();
        this.startPoint = new PointF();
        this.context = builder.context;
        this.parentView = builder.parentView;
        this.imageView = builder.imageView;
        this.deleteView = builder.deleteView;
        this.alignView = builder.alignView;
        this.zoomView = builder.zoomView;
        this.brushDrawingView = builder.brushDrawingView;
        this.isTextPinchZoomable = builder.isTextPinchZoomable;
        this.mDefaultTextTypeface = builder.textTypeface;
        this.mDefaultEmojiTypeface = builder.emojiTypeface;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.brushDrawingView.setBrushViewChangeListener(this);
        this.addedViews = new ArrayList();
        this.redoViews = new ArrayList();
    }

    public void addImage(Bitmap bitmap) {
        final View layout = getLayout(TART_ViewType.IMAGE);
        final ImageView imageView = (ImageView) layout.findViewById(R.id.imgPhotoEditorImage);
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.frmBorder);
        final View findViewById = layout.findViewById(R.id.imgPhotoEditorClose);
        final View findViewById2 = layout.findViewById(R.id.imgPhotoEditorZoom);
        imageView.setImageBitmap(bitmap);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findViewById.setVisibility(8);
                findViewById2.setVisibility(8);
                frameLayout.setBackgroundResource(0);
            }
        };
        findViewById.setVisibility(0);
        findViewById2.setVisibility(0);
        frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
        this.handler.postDelayed(runnable, 2500L);
        TART_MultiTouchListener multiTouchListener = getMultiTouchListener();
        multiTouchListener.setOnGestureControl(new TART_MultiTouchListener.OnGestureControl() { // from class: com.addtext.textonphoto.textart.imagechanger.PhotoEditor.2
            @Override 
            public void onDoubleClick() {
            }

            @Override 
            public void onLongClick() {
            }

            @Override 
            public void onClick() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.removeCallbacks(runnable);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
            }

            @Override 
            public void onSingleTap() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.removeCallbacks(runnable);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
                if (TART_PhotoEditor.this.mOnPhotoEditorListener != null) {
                    TART_PhotoEditor.this.mOnPhotoEditorListener.onClickGetImageViewListener(imageView, layout);
                }
            }
        });
        layout.setOnTouchListener(multiTouchListener);
        addViewToParent(layout, TART_ViewType.IMAGE);
    }

    public void addText(String str, int i) {
        addText(null, str, i);
    }

    public void addText(Typeface typeface, String str, int i) {
        this.brushDrawingView.setBrushDrawingMode(false);
        final View layout = getLayout(TART_ViewType.TEXT);
        final TART_StrokeTextView strokeTextView = (TART_StrokeTextView) layout.findViewById(R.id.tvPhotoEditorText);
        final View findViewById = layout.findViewById(R.id.imgPhotoEditorClose);
        final View findViewById2 = layout.findViewById(R.id.imgPhotoEditorZoom);
        final TART_RoundFrameLayout roundFrameLayout = (TART_RoundFrameLayout) layout.findViewById(R.id.frmBorder_highlight);
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.frmBorder);
        strokeTextView.setText(str);
        strokeTextView.setTextColor(i);
        final Runnable runnable = new Runnable() { // from class: com.addtext.textonphoto.textart.imagechanger.PhotoEditor.3
            @Override // java.lang.Runnable
            public void run() {
                findViewById.setVisibility(8);
                findViewById2.setVisibility(8);
                frameLayout.setBackgroundResource(0);
            }
        };
        findViewById.setVisibility(0);
        findViewById2.setVisibility(0);
        frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
        this.handler.removeCallbacks(runnable);
        this.handler.postDelayed(runnable, 2500L);
        if (typeface != null) {
            strokeTextView.setTypeface(typeface);
        }
        TART_MultiTouchListener multiTouchListener = getMultiTouchListener();
        multiTouchListener.setOnGestureControl(new TART_MultiTouchListener.OnGestureControl() { // from class: com.addtext.textonphoto.textart.imagechanger.PhotoEditor.4
            @Override 
            public void onLongClick() {
            }

            @Override 
            public void onClick() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.removeCallbacks(runnable);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
            }

            @Override 
            public void onDoubleClick() {
                String charSequence = strokeTextView.getText().toString();
                int currentTextColor = strokeTextView.getCurrentTextColor();
                if (TART_PhotoEditor.this.mOnPhotoEditorListener != null) {
                    TART_PhotoEditor.this.mOnPhotoEditorListener.onEditTextChangeListener(layout, charSequence, currentTextColor);
                }
            }

            @Override 
            public void onSingleTap() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.removeCallbacks(runnable);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
                if (TART_PhotoEditor.this.mOnPhotoEditorListener != null) {
                    TART_PhotoEditor.this.mOnPhotoEditorListener.onClickGetEditTextChangeListener(strokeTextView, roundFrameLayout);
                }
            }
        });
        layout.setOnTouchListener(multiTouchListener);
        addViewToParent(layout, TART_ViewType.TEXT);
        this.mOnPhotoEditorListener.onAdded(strokeTextView, roundFrameLayout);
        strokeTextView.getText().toString();
        strokeTextView.getCurrentTextColor();
    }

    public void editText(View view, String str, int i) {
        editText(view, null, str, i);
    }

    public void editText(View view, Typeface typeface, String str, int i) {
        TextView textView = (TextView) view.findViewById(R.id.tvPhotoEditorText);
        if (textView == null || !this.addedViews.contains(view) || TextUtils.isEmpty(str)) {
            return;
        }
        textView.setText(str);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        textView.setTextColor(i);
        this.parentView.updateViewLayout(view, view.getLayoutParams());
        int indexOf = this.addedViews.indexOf(view);
        if (indexOf > -1) {
            this.addedViews.set(indexOf, view);
        }
    }

    public void addEmoji(String str) {
        addEmoji(null, str);
    }

    public void addEmoji(Typeface typeface, String str) {
        this.brushDrawingView.setBrushDrawingMode(false);
        View layout = getLayout(TART_ViewType.EMOJI);
        TextView textView = (TextView) layout.findViewById(R.id.tvPhotoEditorText);
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.frmBorder);
        final View findViewById = layout.findViewById(R.id.imgPhotoEditorClose);
        final View findViewById2 = layout.findViewById(R.id.imgPhotoEditorZoom);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findViewById.setVisibility(8);
                findViewById2.setVisibility(8);
                frameLayout.setBackgroundResource(0);
            }
        };
        findViewById.setVisibility(0);
        findViewById2.setVisibility(0);
        frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
        this.handler.postDelayed(runnable, 2500L);
        textView.setTextSize(56.0f);
        textView.setText(str);
        TART_MultiTouchListener multiTouchListener = getMultiTouchListener();
        multiTouchListener.setOnGestureControl(new TART_MultiTouchListener.OnGestureControl() { // from class: com.addtext.textonphoto.textart.imagechanger.PhotoEditor.6
            @Override 
            public void onDoubleClick() {
            }

            @Override 
            public void onLongClick() {
            }

            @Override 
            public void onClick() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
            }

            @Override 
            public void onSingleTap() {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(0);
                frameLayout.setBackgroundResource(R.drawable.knack_rounded_border_tv);
                TART_PhotoEditor.this.handler.postDelayed(runnable, 2500L);
            }
        });
        layout.setOnTouchListener(multiTouchListener);
        addViewToParent(layout, TART_ViewType.EMOJI);
    }

    private void addViewToParent(View view, TART_ViewType viewType) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        this.parentView.addView(view, layoutParams);
        this.addedViews.add(view);
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onAddViewListener(viewType, this.addedViews.size());
        }
    }

    private TART_MultiTouchListener getMultiTouchListener() {
        return new TART_MultiTouchListener(this.context, this.parentView, this.imageView, this.isTextPinchZoomable, this.mOnPhotoEditorListener);
    }

   
    public static  class AnonymousClass11 {
        static final  int[] $SwitchMap$quotes$photo$textonphoto$imagechanger$ViewType;

        static {
            int[] iArr = new int[TART_ViewType.values().length];
            $SwitchMap$quotes$photo$textonphoto$imagechanger$ViewType = iArr;
            try {
                iArr[TART_ViewType.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$quotes$photo$textonphoto$imagechanger$ViewType[TART_ViewType.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$quotes$photo$textonphoto$imagechanger$ViewType[TART_ViewType.EMOJI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private View getLayout(final TART_ViewType viewType) {
        int i = AnonymousClass11.$SwitchMap$quotes$photo$textonphoto$imagechanger$ViewType[viewType.ordinal()];
        View view = null;
        if (i == 1) {
            view = this.mLayoutInflater.inflate(R.layout.knack_view_photo_editor_text, (ViewGroup) null);
            TextView textView = (TextView) view.findViewById(R.id.tvPhotoEditorText);
            if (textView != null && this.mDefaultTextTypeface != null) {
                textView.setGravity(17);
                if (this.mDefaultEmojiTypeface != null) {
                    textView.setTypeface(this.mDefaultTextTypeface);
                }
            }
        } else if (i == 2) {
            view = this.mLayoutInflater.inflate(R.layout.knack_view_photo_editor_image, (ViewGroup) null);
        } else if (i == 3) {
            View inflate = this.mLayoutInflater.inflate(R.layout.knack_view_photo_editor_emoji, (ViewGroup) null);
            TextView textView2 = (TextView) inflate.findViewById(R.id.tvPhotoEditorText);
            if (textView2 != null) {
                Typeface typeface = this.mDefaultEmojiTypeface;
                if (typeface != null) {
                    textView2.setTypeface(typeface);
                }
                textView2.setGravity(17);
                textView2.setLayerType(1, null);
            }
            view = inflate;
        }
        if (view != null) {
            view.setTag(viewType);
            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frmBorder);
            final View findViewById = view.findViewById(R.id.imgPhotoEditorClose);
            if (findViewById != null) {
                View finalView = view;
                findViewById.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.imagechanger.PhotoEditor.7
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        TART_PhotoEditor.this.viewUndo(finalView, viewType);
                    }
                });
            }
            final View findViewById2 = view.findViewById(R.id.imgPhotoEditorZoom);
            if (findViewById2 != null) {
                findViewById2.setOnTouchListener(new View.OnTouchListener() { // from class: quotes.photo.textonphoto.imagechanger.PhotoEditor.8
                    float scaleX = 1.0f;
                    float rotation = 0.0f;

                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        Log.d("XXXXXXXX", "setOnTouchListener " + motionEvent.getRawX() + " " + motionEvent.getRawY());
                        int action = motionEvent.getAction();
                        if (action != 0) {
                            if (action == 1 || action == 2) {
                                TART_PhotoEditor.this.zoomAndRotateSticker((View) view2.getParent(), motionEvent, frameLayout, findViewById, findViewById2, this.scaleX, this.rotation);
                                return false;
                            }
                            return false;
                        }
                        this.scaleX = ((View) view2.getParent()).getScaleX();
                        this.rotation = ((View) view2.getParent()).getRotation();
                        TART_PhotoEditor.this.getPointF(view2, motionEvent);
                        Log.d("XXXXXXXX", "ACTION_DOWN " + this.scaleX + " " + this.rotation + " mid " + TART_PhotoEditor.this.midPoint.x + " " + TART_PhotoEditor.this.midPoint.y);
                        return true;
                    }
                });
            }
        }
        return view;
    }

    public PointF getPointF(View view, MotionEvent motionEvent) {
        View view2 = (View) view.getParent();
        this.startPoint.set(motionEvent.getRawX(), motionEvent.getRawY());
        this.midPoint.set(view2.getX() + (view2.getWidth() / 2), view2.getY() + (view2.getHeight() / 2));
        return this.midPoint;
    }

    public void zoomAndRotateSticker(View view, MotionEvent motionEvent, FrameLayout frameLayout, View view2, View view3, float f, float f2) {
        if (view != null) {
            float floatA = (getFloatA(this.midPoint.x, this.midPoint.y, motionEvent.getRawX(), motionEvent.getRawY()) / getFloatA(this.startPoint.x, this.startPoint.y, this.midPoint.x, this.midPoint.y)) * f;
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
            view.setScaleX(floatA);
            view.setScaleY(floatA);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            int dimension = (int) (this.context.getResources().getDimension(R.dimen.frame_margin) / floatA);
            layoutParams.setMargins(dimension, dimension, dimension, dimension);
            frameLayout.setLayoutParams(layoutParams);
            view2.setPivotX(0.0f);
            view2.setPivotY(0.0f);
            view3.setPivotX(view3.getWidth());
            view3.setPivotY(view3.getHeight());
            float f3 = 1.0f / floatA;
            view2.setScaleX(f3);
            view2.setScaleY(f3);
            view3.setScaleX(f3);
            view3.setScaleY(f3);
            float degrees = f2 + ((float) Math.toDegrees(Math.atan2(motionEvent.getRawY() - this.midPoint.y, motionEvent.getRawX() - this.midPoint.x) - Math.atan2(this.startPoint.y - this.midPoint.y, this.startPoint.x - this.midPoint.x)));
            view.setRotation(degrees);
            view.requestLayout();
            Log.d("XXXXXXXX", "ACTION_MOVE  " + floatA + " " + degrees + " " + motionEvent.getRawX() + " " + motionEvent.getRawY());
        }
    }

    public float getFloatA(float f, float f2, float f3, float f4) {
        double d = f - f3;
        double d2 = f2 - f4;
        return (float) Math.sqrt((d * d) + (d2 * d2));
    }

    public void setBrushDrawingMode(boolean z) {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.setBrushDrawingMode(z);
        }
    }

    public Boolean getBrushDrawableMode() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        return Boolean.valueOf(brushDrawingView != null && brushDrawingView.getBrushDrawingMode());
    }

    public void setBrushSize(float f) {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.setBrushSize(f);
        }
    }

    public void setOpacity(int i) {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.setOpacity((int) ((i / 100.0d) * 255.0d));
        }
    }

    public void setBrushColor(int i) {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.setBrushColor(i);
        }
    }

    public void setBrushEraserSize(float f) {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.setBrushEraserSize(f);
        }
    }

    public float getEraserSize() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            return brushDrawingView.getEraserSize();
        }
        return 0.0f;
    }

    public float getBrushSize() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            return brushDrawingView.getBrushSize();
        }
        return 0.0f;
    }

    public int getBrushColor() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            return brushDrawingView.getBrushColor();
        }
        return 0;
    }

    public void brushEraser() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.brushEraser();
        }
    }

    public void viewUndo(View view, TART_ViewType viewType) {
        if (this.addedViews.size() <= 0 || !this.addedViews.contains(view)) {
            return;
        }
        this.parentView.removeView(view);
        this.addedViews.remove(view);
        this.redoViews.add(view);
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
            this.mOnPhotoEditorListener.onRemoveViewListener(viewType, this.addedViews.size());
        }
    }

    public boolean undo() {
        if (this.addedViews.size() > 0) {
            List<View> list = this.addedViews;
            View view = list.get(list.size() - 1);
            if (view instanceof TART_BrushDrawingView) {
                TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
                return brushDrawingView != null && brushDrawingView.undo();
            }
            List<View> list2 = this.addedViews;
            list2.remove(list2.size() - 1);
            this.parentView.removeView(view);
            this.redoViews.add(view);
            TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
            if (onPhotoEditorListener != null) {
                onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
                Object tag = view.getTag();
                if (tag != null && (tag instanceof TART_ViewType)) {
                    this.mOnPhotoEditorListener.onRemoveViewListener((TART_ViewType) tag, this.addedViews.size());
                }
            }
        }
        return this.addedViews.size() != 0;
    }

    public boolean redo() {
        if (this.redoViews.size() > 0) {
            List<View> list = this.redoViews;
            View view = list.get(list.size() - 1);
            if (view instanceof TART_BrushDrawingView) {
                TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
                return brushDrawingView != null && brushDrawingView.redo();
            }
            List<View> list2 = this.redoViews;
            list2.remove(list2.size() - 1);
            this.parentView.addView(view);
            this.addedViews.add(view);
            Object tag = view.getTag();
            TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
            if (onPhotoEditorListener != null && tag != null && (tag instanceof TART_ViewType)) {
                onPhotoEditorListener.onAddViewListener((TART_ViewType) tag, this.addedViews.size());
            }
        }
        return this.redoViews.size() != 0;
    }

    private void clearBrushAllViews() {
        TART_BrushDrawingView brushDrawingView = this.brushDrawingView;
        if (brushDrawingView != null) {
            brushDrawingView.clearAll();
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

    public void clearHelperBox() {
        for (int i = 0; i < this.parentView.getChildCount(); i++) {
            View childAt = this.parentView.getChildAt(i);
            FrameLayout frameLayout = (FrameLayout) childAt.findViewById(R.id.frmBorder);
            if (frameLayout != null) {
                frameLayout.setBackgroundResource(0);
                View findViewById = childAt.findViewById(R.id.imgPhotoEditorClose);
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
                View findViewById2 = childAt.findViewById(R.id.imgPhotoEditorZoom);
                if (findViewById2 != null) {
                    findViewById2.setVisibility(8);
                }
            }
        }
    }

    public void setFilterEffect(TART_CustomEffect customEffect) {
        this.parentView.setFilterEffect(customEffect);
    }

    public void setFilterEffect(TART_PhotoFilter photoFilter) {
        this.parentView.setFilterEffect(photoFilter);
    }

    public void saveImage(Uri uri, OnSaveListener onSaveListener) {
        saveAsFile(onSaveListener);
    }

    public void saveAsFile(OnSaveListener onSaveListener) {
        saveAsFile(new TART_SaveSettings.Builder().build(), onSaveListener);
    }

    public void saveAsFile(final TART_SaveSettings saveSettings, final OnSaveListener onSaveListener) {
        final Uri[] uriArr = {null};
        this.parentView.saveFilter(new TART_OnSaveBitmap() { // from class: quotes.photo.textonphoto.imagechanger.PhotoEditor.9
            /* JADX WARN: Type inference failed for: r2v1, types: [quotes.photo.textonphoto.imagechanger.PhotoEditor$9$1] */
            @Override // quotes.photo.textonphoto.imagechanger.OnSaveBitmap
            public void onBitmapReady(Bitmap bitmap) {
                new AsyncTask<String, String, Exception>() { // from class: quotes.photo.textonphoto.imagechanger.PhotoEditor.9.1
                    @Override // android.os.AsyncTask
                    public void onPreExecute() {
                        super.onPreExecute();
                        TART_PhotoEditor.this.clearHelperBox();
                        TART_PhotoEditor.this.parentView.setDrawingCacheEnabled(false);
                    }

                    @Override // android.os.AsyncTask
                    public Exception doInBackground(String... strArr) {
                        ContentValues contentValues;
                        Bitmap drawingCache;
                        if (Build.VERSION.SDK_INT <= 28) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/TextOnPhoto");
                            if (!file.exists() && !file.mkdirs()) {
                                return null;
                            }
                            File file2 = new File(file.getPath() + File.separator + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".jpg");
                            try {
                                file2.createNewFile();
                                uriArr[0] = Uri.fromFile(file2);
                                contentValues = null;
                            } catch (Exception unused) {
                                return null;
                            }
                        } else {
                            Uri contentUri = MediaStore.Images.Media.getContentUri("external_primary");
                            File file3 = new File(Environment.DIRECTORY_PICTURES, "TextOnPhoto");
                            long currentTimeMillis = System.currentTimeMillis();
                            contentValues = new ContentValues();
                            contentValues.put("_display_name", new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()));
                            contentValues.put("mime_type", "image/jpeg");
                            contentValues.put("date_added", Long.valueOf(currentTimeMillis));
                            contentValues.put("date_modified", Long.valueOf(currentTimeMillis));
                            contentValues.put("relative_path", file3 + "/");
                            contentValues.put("is_pending", (Integer) 1);
                            uriArr[0] = TART_PhotoEditor.this.context.getContentResolver().insert(contentUri, contentValues);
                        }
                        try {
                            OutputStream openOutputStream = TART_PhotoEditor.this.context.getContentResolver().openOutputStream(uriArr[0], "w");
                            if (TART_PhotoEditor.this.parentView != null) {
                                TART_PhotoEditor.this.parentView.setDrawingCacheEnabled(true);
                                if (saveSettings.isTransparencyEnabled()) {
                                    drawingCache = TART_BitmapUtil.removeTransparency(TART_PhotoEditor.this.parentView.getDrawingCache());
                                } else {
                                    drawingCache = TART_PhotoEditor.this.parentView.getDrawingCache();
                                }
                                drawingCache.compress(Bitmap.CompressFormat.PNG, 100, openOutputStream);
                                if (Build.VERSION.SDK_INT > 28) {
                                    contentValues.clear();
                                    contentValues.put("is_pending", (Integer) 0);
                                    TART_PhotoEditor.this.context.getContentResolver().update(uriArr[0], contentValues, null, null);
                                    Log.d(TART_PhotoEditor.TAG, "fileoutputStream " + openOutputStream);
                                }
                            }
                            openOutputStream.flush();
                            openOutputStream.close();
                            Log.d(TART_PhotoEditor.TAG, "Filed Saved Successfully");
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TART_PhotoEditor.TAG, "Failed to save File");
                            return e;
                        }
                    }

                    @Override // android.os.AsyncTask
                    public void onPostExecute(Exception exc) {
                        super.onPostExecute( exc);
                        if (exc == null) {
                            if (saveSettings.isClearViewsEnabled()) {
                                TART_PhotoEditor.this.clearAllViews();
                            }
                            onSaveListener.onSuccess(uriArr[0]);
                            return;
                        }
                        onSaveListener.onFailure(exc);
                    }
                }.execute(new String[0]);
            }

            @Override // quotes.photo.textonphoto.imagechanger.OnSaveBitmap
            public void onFailure(Exception exc) {
                onSaveListener.onFailure(exc);
            }
        });
    }

    public void saveAsBitmap(TART_OnSaveBitmap onSaveBitmap) {
        saveAsBitmap(new TART_SaveSettings.Builder().build(), onSaveBitmap);
    }

    public void saveAsBitmap(final TART_SaveSettings saveSettings, final TART_OnSaveBitmap onSaveBitmap) {
        this.parentView.saveFilter(new TART_OnSaveBitmap() {
            @Override 
            public void onBitmapReady(Bitmap bitmap) {
                new AsyncTask<String, String, Bitmap>() { 
                    @Override 
                    public void onPreExecute() {
                        super.onPreExecute();
                        TART_PhotoEditor.this.clearHelperBox();
                        TART_PhotoEditor.this.parentView.setDrawingCacheEnabled(false);
                    }

                    @Override // android.os.AsyncTask
                    public Bitmap doInBackground(String... strArr) {
                        if (TART_PhotoEditor.this.parentView == null) {
                            return null;
                        }
                        TART_PhotoEditor.this.parentView.setDrawingCacheEnabled(true);
                        if (saveSettings.isTransparencyEnabled()) {
                            return TART_BitmapUtil.removeTransparency(TART_PhotoEditor.this.parentView.getDrawingCache());
                        }
                        return TART_PhotoEditor.this.parentView.getDrawingCache();
                    }

                    @Override // android.os.AsyncTask
                    public void onPostExecute(Bitmap bitmap2) {
                        super.onPostExecute( bitmap2);
                        if (bitmap2 != null) {
                            if (saveSettings.isClearViewsEnabled()) {
                                TART_PhotoEditor.this.clearAllViews();
                            }
                            onSaveBitmap.onBitmapReady(bitmap2);
                            return;
                        }
                        onSaveBitmap.onFailure(new Exception("Failed to load the bitmap"));
                    }
                }.execute(new String[0]);
            }

            @Override // quotes.photo.textonphoto.imagechanger.OnSaveBitmap
            public void onFailure(Exception exc) {
                onSaveBitmap.onFailure(exc);
            }
        });
    }

    private static String convertEmoji(String str) {
        try {
            return new String(Character.toChars(Integer.parseInt(str.substring(2), 16)));
        } catch (NumberFormatException unused) {
            return "";
        }
    }

    public void setOnPhotoEditorListener(TART_OnPhotoEditorListener onPhotoEditorListener) {
        this.mOnPhotoEditorListener = onPhotoEditorListener;
    }

    public boolean isCacheEmpty() {
        return this.addedViews.size() == 0 && this.redoViews.size() == 0;
    }

    @Override
    public void onViewAdd(TART_BrushDrawingView brushDrawingView) {
        if (this.redoViews.size() > 0) {
            List<View> list = this.redoViews;
            list.remove(list.size() - 1);
        }
        this.addedViews.add(brushDrawingView);
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onAddViewListener(TART_ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    @Override
    public void onViewRemoved(TART_BrushDrawingView brushDrawingView) {
        if (this.addedViews.size() > 0) {
            List<View> list = this.addedViews;
            View remove = list.remove(list.size() - 1);
            if (!(remove instanceof TART_BrushDrawingView)) {
                this.parentView.removeView(remove);
            }
            this.redoViews.add(remove);
        }
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
            this.mOnPhotoEditorListener.onRemoveViewListener(TART_ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    @Override
    public void onStartDrawing() {
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onStartViewChangeListener(TART_ViewType.BRUSH_DRAWING);
        }
    }

    @Override
    public void onStopDrawing() {
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onStopViewChangeListener(TART_ViewType.BRUSH_DRAWING);
        }
    }

    public static class Builder {
        public View alignView;
        public TART_BrushDrawingView brushDrawingView;
        public Context context;
        public View deleteView;
        public Typeface emojiTypeface;
        public ImageView imageView;
        public boolean isTextPinchZoomable = true;
        public TART_PhotoEditorView parentView;
        public Typeface textTypeface;
        public View zoomView;

        public Builder(Context context, TART_PhotoEditorView photoEditorView) {
            this.context = context;
            this.parentView = photoEditorView;
            this.imageView = photoEditorView.getSource();
            this.brushDrawingView = photoEditorView.getBrushDrawingView();
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

    public static List<String> getEmojis(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getResources().getStringArray(R.array.photo_editor_emoji)) {
            arrayList.add(convertEmoji(str));
        }
        return arrayList;
    }
}
