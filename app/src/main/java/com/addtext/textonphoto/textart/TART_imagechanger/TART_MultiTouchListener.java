package com.addtext.textonphoto.textart.TART_imagechanger;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.addtext.textonphoto.textart.R;

public class TART_MultiTouchListener implements View.OnTouchListener {
    private View alignView;
    private View deleteView;
    public Context mContext;
    private final GestureDetector mGestureListener;
    public boolean mIsTextPinchZoomable;
    public OnGestureControl mOnGestureControl;
    private final TART_OnPhotoEditorListener mOnPhotoEditorListener;
    private float mPrevRawX;
    private float mPrevRawY;
    private float mPrevX;
    private float mPrevY;
    private OnMultiTouchListener onMultiTouchListener;
    private final Rect outRect;
    private final RelativeLayout parentView;
    private final ImageView photoEditImageView;
    private View zoomView;
    public boolean isRotateEnabled = true;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    private final int[] location = new int[2];
    private int mActivePointerId = -1;
    public float maximumScale = 10.0f;
    public float minimumScale = 0.1f;
    private final TART_ScaleGestureDetector mScaleGestureDetector = new TART_ScaleGestureDetector(new ScaleGestureListener());

  
    public interface OnGestureControl {
        void onClick();

        void onDoubleClick();

        void onLongClick();

        void onSingleTap();
    }

    
    interface OnMultiTouchListener {
        void onEditTextClickListener(String str, int i);

        void onRemoveViewListener(View view);
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }


    public TART_MultiTouchListener(Context context, RelativeLayout relativeLayout, ImageView imageView, boolean z, TART_OnPhotoEditorListener onPhotoEditorListener) {
        this.mContext = context;
        this.mIsTextPinchZoomable = z;
        this.mGestureListener = new GestureDetector(context, new GestureListener());
        this.parentView = relativeLayout;
        this.photoEditImageView = imageView;
        this.mOnPhotoEditorListener = onPhotoEditorListener;
        View view = this.deleteView;
        if (view != null) {
            this.outRect = new Rect(view.getLeft(), this.deleteView.getTop(), this.deleteView.getRight(), this.deleteView.getBottom());
        } else {
            this.outRect = new Rect(0, 0, 0, 0);
        }
    }

    public static void move(Context context, View view, TransformInfo transformInfo, TART_StrokeTextView strokeTextView, int i) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        if (strokeTextView != null) {
            strokeTextView.setTextSize((int) ((i * max) / (context.getResources().getDisplayMetrics().densityDpi / 160.0f)));
        } else {
            view.setScaleX(max);
            view.setScaleY(max);
            View findViewById = view.findViewById(R.id.frmBorder);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById.getLayoutParams();
            int dimension = (int) (context.getResources().getDimension(R.dimen.frame_margin) / max);
            layoutParams.setMargins(dimension, dimension, dimension, dimension);
            findViewById.setLayoutParams(layoutParams);
            View findViewById2 = view.findViewById(R.id.imgPhotoEditorClose);
            findViewById2.setPivotX(0.0f);
            findViewById2.setPivotY(0.0f);
            float f = 1.0f / max;
            findViewById2.setScaleX(f);
            findViewById2.setScaleY(f);
            View findViewById3 = view.findViewById(R.id.imgPhotoEditorZoom);
            findViewById3.setPivotX(findViewById3.getWidth());
            findViewById3.setPivotY(findViewById3.getHeight());
            findViewById3.setScaleX(f);
            findViewById3.setScaleY(f);
        }
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() == f && view.getPivotY() == f2) {
            return;
        }
        float[] fArr = {0.0f, 0.0f};
        view.getMatrix().mapPoints(fArr);
        view.setPivotX(f);
        view.setPivotY(f2);
        float[] fArr2 = {0.0f, 0.0f};
        view.getMatrix().mapPoints(fArr2);
        float f3 = fArr2[0] - fArr[0];
        float f4 = fArr2[1] - fArr[1];
        view.setTranslationX(view.getTranslationX() - f3);
        view.setTranslationY(view.getTranslationY() - f4);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        this.mGestureListener.onTouchEvent(motionEvent);
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            int actionMasked = motionEvent.getActionMasked() & action;
            if (actionMasked == 6) {
                int i = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i) == this.mActivePointerId) {
                    int i2 = i == 0 ? 1 : 0;
                    this.mPrevX = motionEvent.getX(i2);
                    this.mPrevY = motionEvent.getY(i2);
                    this.mActivePointerId = motionEvent.getPointerId(i2);
                }
            } else if (actionMasked == 0) {
                this.mPrevX = motionEvent.getX();
                this.mPrevY = motionEvent.getY();
                this.mPrevRawX = motionEvent.getRawX();
                this.mPrevRawY = motionEvent.getRawY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                View view2 = this.deleteView;
                if (view2 != null) {
                    view2.setVisibility(View.VISIBLE);
                }
                view.bringToFront();
                firePhotoEditorSDKListener(view, true);
            } else if (actionMasked == 1) {
                this.mActivePointerId = -1;
                View view3 = this.deleteView;
                if (view3 != null && isViewInBounds(view3, rawX, rawY)) {
                    OnMultiTouchListener onMultiTouchListener = this.onMultiTouchListener;
                    if (onMultiTouchListener != null) {
                        onMultiTouchListener.onRemoveViewListener(view);
                    }
                } else if (!isViewInBounds(this.photoEditImageView, rawX, rawY)) {
                    view.animate().translationY(0.0f).translationY(0.0f);
                }
                firePhotoEditorSDKListener(view, false);
            } else if (actionMasked == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    }
                }
            } else if (actionMasked == 3) {
                this.mActivePointerId = -1;
            }
            return true;
        }
        return true;
    }

    private void firePhotoEditorSDKListener(View view, boolean z) {
        Object tag = view.getTag();
        TART_OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener == null || tag == null || !(tag instanceof TART_ViewType)) {
            return;
        }
        if (z) {
            onPhotoEditorListener.onStartViewChangeListener((TART_ViewType) view.getTag());
        } else {
            onPhotoEditorListener.onStopViewChangeListener((TART_ViewType) view.getTag());
        }
    }

    private boolean isViewInBounds(View view, int i, int i2) {
        view.getDrawingRect(this.outRect);
        view.getLocationOnScreen(this.location);
        Rect rect = this.outRect;
        int[] iArr = this.location;
        rect.offset(iArr[0], iArr[1]);
        return this.outRect.contains(i, i2);
    }

    public void setOnMultiTouchListener(OnMultiTouchListener onMultiTouchListener) {
        this.onMultiTouchListener = onMultiTouchListener;
    }

  
    private class ScaleGestureListener extends TART_ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private final TART_Vector2D mPrevSpanVector;
        private TART_StrokeTextView strokeTextView;
        private int textSize;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new TART_Vector2D();
            this.textSize = -1;
        }

        @Override 
        public boolean onScaleBegin(View view, TART_ScaleGestureDetector scaleGestureDetector) {
            View findViewById = view.findViewById(R.id.tvPhotoEditorText);
            if (findViewById != null && (findViewById instanceof TART_StrokeTextView)) {
            TART_StrokeTextView strokeTextView = (TART_StrokeTextView) findViewById;
                this.strokeTextView = strokeTextView;
                this.textSize = (int) strokeTextView.getTextSize();
            }
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return TART_MultiTouchListener.this.mIsTextPinchZoomable;
        }

        @Override 
        public boolean onScale(View view, TART_ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = TART_MultiTouchListener.this.isScaleEnabled ? scaleGestureDetector.getScaleFactor() : 1.0f;
            transformInfo.deltaAngle = TART_MultiTouchListener.this.isRotateEnabled ? TART_Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = TART_MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            transformInfo.deltaY = TART_MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusY() - this.mPivotY : 0.0f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = TART_MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = TART_MultiTouchListener.this.maximumScale;
            TART_MultiTouchListener.move(TART_MultiTouchListener.this.mContext, view, transformInfo, this.strokeTextView, this.textSize);
            return !TART_MultiTouchListener.this.mIsTextPinchZoomable;
        }

        @Override // com.addtext.textonphoto.textart.imagechanger.ScaleGestureDetector.SimpleOnScaleGestureListener, quotes.photo.textonphoto.imagechanger.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(View view, TART_ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(view, scaleGestureDetector);
            this.textSize = -1;
            this.strokeTextView = null;
        }
    }

    public class TransformInfo {
        float deltaAngle;
        float deltaScale;
        float deltaX;
        float deltaY;
        float maximumScale;
        float minimumScale;
        float pivotX;
        float pivotY;

        private TransformInfo() {
        }
    }

    public void setOnGestureControl(OnGestureControl onGestureControl) {
        this.mOnGestureControl = onGestureControl;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        @Override 
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (TART_MultiTouchListener.this.mOnGestureControl == null) {
                return true;
            }
            TART_MultiTouchListener.this.mOnGestureControl.onClick();
            return true;
        }

        @Override 
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
            if (TART_MultiTouchListener.this.mOnGestureControl != null) {
                TART_MultiTouchListener.this.mOnGestureControl.onLongClick();
            }
        }

        @Override 
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (TART_MultiTouchListener.this.mOnGestureControl == null) {
                return true;
            }
            TART_MultiTouchListener.this.mOnGestureControl.onDoubleClick();
            return true;
        }

        @Override 
        public boolean onDown(MotionEvent motionEvent) {
            if (TART_MultiTouchListener.this.mOnGestureControl != null) {
                TART_MultiTouchListener.this.mOnGestureControl.onSingleTap();
            }
            return super.onDown(motionEvent);
        }
    }
}
