package com.addtext.textonphoto.textart.TART_sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.addtext.textonphoto.textart.R;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TART_StickerView extends RelativeLayout {

    private final float[] bitmapPoints = new float[8];

    private final Paint borderPaint = new Paint();

    private final Paint borderPaintRed = new Paint();

    private final float[] bounds = new float[8];

    private boolean bringToFrontCurrentSticker;

    private int circleRadius;

    private boolean constrained;

    private final PointF currentCenterPoint = new PointF();

    private TART_BitmapStickerIcon currentIcon;

    private int currentMode = 0;

    private float currentMoveingX;

    private float currentMoveingY;

    private final Matrix downMatrix = new Matrix();

    private float downX;

    private float downY;

    private boolean drawCirclePoint = false;

    private Sticker handlingSticker;

    private final List<TART_BitmapStickerIcon> icons = new ArrayList<TART_BitmapStickerIcon>(4);

    private long lastClickTime = 0L;

    private Sticker lastHandlingSticker;

    private final Paint linePaint = new Paint();

    private boolean locked;

    private PointF midPoint = new PointF();

    private int minClickDelayTime = 200;

    private final Matrix moveMatrix = new Matrix();

    private float oldDistance = 0.0F;

    private float oldRotation = 0.0F;

    private boolean onMoving = false;

    private OnStickerOperationListener onStickerOperationListener;

    private Paint paintCircle;

    private final float[] point = new float[2];

    private boolean showBorder;

    private boolean showIcons;

    private final Matrix sizeMatrix = new Matrix();

    private final RectF stickerRect = new RectF();

    private final List<Sticker> stickers = new ArrayList<Sticker>();

    private final float[] tmp = new float[2];

    private int touchSlop;
    public static final int[] StickerView = {R.attr.borderAlpha, R.attr.borderColor, R.attr.bringToFrontCurrentSticker, R.attr.showBorder, R.attr.showIcons};


    public TART_StickerView(Context paramContext) {
        this(paramContext, (AttributeSet) null);
    }

    public TART_StickerView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    @SuppressLint("ResourceType")
    public TART_StickerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        TypedArray typedArray;
        this.paintCircle = new Paint();
        this.paintCircle.setAntiAlias(true);
        this.paintCircle.setDither(true);
        this.paintCircle.setColor(ContextCompat.getColor(getContext(), 2131099703));
        this.paintCircle.setStrokeWidth(TART_SystemUtil.dpToPx(getContext(), 2));
        this.paintCircle.setStyle(Paint.Style.STROKE);
        this.touchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
        typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, StickerView);
        if (typedArray != null)
            typedArray.recycle();
    }

    @RequiresApi(api = 21)
    public TART_StickerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    }

    @NonNull
    public TART_StickerView addSticker(@NonNull Sticker paramSticker) {
        return addSticker(paramSticker, 1);
    }

    public TART_StickerView addSticker(@NonNull final Sticker sticker, final int position) {
        if (ViewCompat.isLaidOut( this)) {
            addStickerImmediately(sticker, position);
            return this;
        }
        post(new Runnable() {
            public void run() {
                TART_StickerView.this.addStickerImmediately(sticker, position);
            }
        });
        return this;
    }

    protected void addStickerImmediately(@NonNull Sticker paramSticker, int paramInt) {
        setStickerPosition(paramSticker, paramInt);
        paramSticker.getMatrix().postScale(1.0F, 1.0F, getWidth(), getHeight());
        this.handlingSticker = paramSticker;
        this.stickers.add(paramSticker);
        if (this.onStickerOperationListener != null)
            this.onStickerOperationListener.onStickerAdded(paramSticker);
        invalidate();
    }

    public void alignHorizontally() {
        this.moveMatrix.set(this.downMatrix);
        this.moveMatrix.postRotate(-getCurrentSticker().getCurrentAngle(), this.midPoint.x, this.midPoint.y);
        this.handlingSticker.setMatrix(this.moveMatrix);
    }

    protected float calculateDistance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        double d1 = (paramFloat1 - paramFloat3);
        double d2 = (paramFloat2 - paramFloat4);
        return (float) Math.sqrt(d1 * d1 + d2 * d2);
    }

    protected float calculateDistance(@Nullable MotionEvent paramMotionEvent) {
        return (paramMotionEvent == null || paramMotionEvent.getPointerCount() < 2) ? 0.0F : calculateDistance(paramMotionEvent.getX(0), paramMotionEvent.getY(0), paramMotionEvent.getX(1), paramMotionEvent.getY(1));
    }

    @NonNull
    protected PointF calculateMidPoint() {
        if (this.handlingSticker == null) {
            this.midPoint.set(0.0F, 0.0F);
            return this.midPoint;
        }
        this.handlingSticker.getMappedCenterPoint(this.midPoint, this.point, this.tmp);
        return this.midPoint;
    }

    @NonNull
    protected PointF calculateMidPoint(@Nullable MotionEvent paramMotionEvent) {
        if (paramMotionEvent == null || paramMotionEvent.getPointerCount() < 2) {
            this.midPoint.set(0.0F, 0.0F);
            return this.midPoint;
        }
        float f1 = (paramMotionEvent.getX(0) + paramMotionEvent.getX(1)) / 2.0F;
        float f2 = (paramMotionEvent.getY(0) + paramMotionEvent.getY(1)) / 2.0F;
        this.midPoint.set(f1, f2);
        return this.midPoint;
    }

    protected float calculateRotation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        double d = (paramFloat1 - paramFloat3);
        return (float) Math.toDegrees(Math.atan2((paramFloat2 - paramFloat4), d));
    }

    protected float calculateRotation(@Nullable MotionEvent paramMotionEvent) {
        return (paramMotionEvent == null || paramMotionEvent.getPointerCount() < 2) ? 0.0F : calculateRotation(paramMotionEvent.getX(0), paramMotionEvent.getY(0), paramMotionEvent.getX(1), paramMotionEvent.getY(1));
    }


    protected void configIconMatrix(@NonNull TART_BitmapStickerIcon paramBitmapStickerIcon, float paramFloat1, float paramFloat2, float paramFloat3) {
        paramBitmapStickerIcon.setX(paramFloat1);
        paramBitmapStickerIcon.setY(paramFloat2);
        paramBitmapStickerIcon.getMatrix().reset();
        paramBitmapStickerIcon.getMatrix().postRotate(paramFloat3, (paramBitmapStickerIcon.getWidth() / 2), (paramBitmapStickerIcon.getHeight() / 2));
        paramBitmapStickerIcon.getMatrix().postTranslate(paramFloat1 - (paramBitmapStickerIcon.getWidth() / 2), paramFloat2 - (paramBitmapStickerIcon.getHeight() / 2));
    }

    protected void constrainSticker(@NonNull Sticker paramSticker) {
        int i = getWidth();
        int j = getHeight();
        paramSticker.getMappedCenterPoint(this.currentCenterPoint, this.point, this.tmp);
        float f1 = this.currentCenterPoint.x;
        float f3 = 0.0F;
        if (f1 < 0.0F) {
            f1 = -this.currentCenterPoint.x;
        } else {
            f1 = 0.0F;
        }
        float f4 = this.currentCenterPoint.x;
        float f5 = i;
        float f2 = f1;
        if (f4 > f5)
            f2 = f5 - this.currentCenterPoint.x;
        f1 = f3;
        if (this.currentCenterPoint.y < 0.0F)
            f1 = -this.currentCenterPoint.y;
        f3 = this.currentCenterPoint.y;
        f4 = j;
        if (f3 > f4)
            f1 = f4 - this.currentCenterPoint.y;
        paramSticker.getMatrix().postTranslate(f2, f1);
    }

    @NonNull
    public Bitmap createBitmap() throws OutOfMemoryError {
        this.handlingSticker = null;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        draw(new Canvas(bitmap));
        return bitmap;
    }

    protected void dispatchDraw(Canvas paramCanvas) {
        super.dispatchDraw(paramCanvas);
        if (this.drawCirclePoint && this.onMoving) {
            paramCanvas.drawCircle(this.downX, this.downY, this.circleRadius, this.paintCircle);
            paramCanvas.drawLine(this.downX, this.downY, this.currentMoveingX, this.currentMoveingY, this.paintCircle);
        }
        drawStickers(paramCanvas);
    }

    protected void drawStickers(Canvas paramCanvas) {
        boolean bool = false;
        int i;
        for (i = 0; i < this.stickers.size(); i++) {
            Sticker sticker = this.stickers.get(i);
            if (sticker != null && sticker.isShow())
                sticker.draw(paramCanvas);
        }
        if (this.handlingSticker != null && !this.locked && (this.showBorder || this.showIcons)) {
            getStickerPoints(this.handlingSticker, this.bitmapPoints);
            float f5 = this.bitmapPoints[0];
            float f6 = this.bitmapPoints[1];
            float f7 = this.bitmapPoints[2];
            float f8 = this.bitmapPoints[3];
            float f4 = this.bitmapPoints[4];
            float f3 = this.bitmapPoints[5];
            float f2 = this.bitmapPoints[6];
            float f1 = this.bitmapPoints[7];
            if (this.showBorder) {
                paramCanvas.drawLine(f5, f6, f7, f8, this.borderPaint);
                paramCanvas.drawLine(f5, f6, f4, f3, this.borderPaint);
                paramCanvas.drawLine(f7, f8, f2, f1, this.borderPaint);
                paramCanvas.drawLine(f2, f1, f4, f3, this.borderPaint);
            }
            if (this.showIcons) {
                float f = calculateRotation(f2, f1, f4, f3);
                for (i = 0; i < this.icons.size(); i++) {
                    TART_BitmapStickerIcon bitmapStickerIcon = this.icons.get(i);
                    switch (bitmapStickerIcon.getPosition()) {
                        case 3:
                            if ((this.handlingSticker instanceof TART_TextSticker && bitmapStickerIcon.getTag().equals("ROTATE")) || (this.handlingSticker instanceof TART_DrawableSticker && bitmapStickerIcon.getTag().equals("ZOOM"))) {
                                configIconMatrix(bitmapStickerIcon, f2, f1, f);
                                bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                                break;
                            }
                            if (this.handlingSticker instanceof TART_BeautySticker) {
                                TART_BeautySticker beautySticker = (TART_BeautySticker) this.handlingSticker;
                                if (beautySticker.getType() == 1) {
                                    configIconMatrix(bitmapStickerIcon, f2, f1, f);
                                    bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                                    break;
                                }
                                if (beautySticker.getType() == 2 || beautySticker.getType() == 8 || beautySticker.getType() == 4) {
                                    configIconMatrix(bitmapStickerIcon, f2, f1, f);
                                    bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                                }
                            }
                            break;
                        case 2:
                            if (this.handlingSticker instanceof TART_BeautySticker) {
                                if (((TART_BeautySticker) this.handlingSticker).getType() == 0) {
                                    configIconMatrix(bitmapStickerIcon, f4, f3, f);
                                    bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                                }
                                break;
                            }
                            configIconMatrix(bitmapStickerIcon, f4, f3, f);
                            bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                            break;
                        case 1:
                            if ((this.handlingSticker instanceof TART_TextSticker && bitmapStickerIcon.getTag().equals("EDIT")) || (this.handlingSticker instanceof TART_DrawableSticker && bitmapStickerIcon.getTag().equals("FLIP"))) {
                                configIconMatrix(bitmapStickerIcon, f7, f8, f);
                                bitmapStickerIcon.draw(paramCanvas, this.borderPaint);
                            }
                            break;
                        case 0:
                            configIconMatrix(bitmapStickerIcon, f5, f6, f);
                            bitmapStickerIcon.draw(paramCanvas, this.borderPaintRed);
                            break;
                    }
                }
            }
        }
        invalidate();
    }

    public void editTextSticker() {
        this.onStickerOperationListener.onStickerDoubleTapped(this.handlingSticker);
    }

    @Nullable
    protected TART_BitmapStickerIcon findCurrentIconTouched() {
        for (TART_BitmapStickerIcon bitmapStickerIcon : this.icons) {
            float f1 = bitmapStickerIcon.getX() - this.downX;
            float f2 = bitmapStickerIcon.getY() - this.downY;
            if ((f1 * f1 + f2 * f2) <= Math.pow((bitmapStickerIcon.getIconRadius() + bitmapStickerIcon.getIconRadius()), 2.0D))
                return bitmapStickerIcon;
        }
        return null;
    }

    @Nullable
    protected Sticker findHandlingSticker() {
        for (int i = this.stickers.size() - 1; i >= 0; i--) {
            if (isInStickerArea(this.stickers.get(i), this.downX, this.downY))
                return this.stickers.get(i);
        }
        return null;
    }

    public void flip(@Nullable Sticker paramSticker, int paramInt) {
        if (paramSticker != null) {
            paramSticker.getCenterPoint(this.midPoint);
            if ((paramInt & 0x1) > 0) {
                paramSticker.getMatrix().preScale(-1.0F, 1.0F, this.midPoint.x, this.midPoint.y);
                paramSticker.setFlippedHorizontally(!paramSticker.isFlippedHorizontally());
            }
            if ((paramInt & 0x2) > 0) {
                paramSticker.getMatrix().preScale(1.0F, -1.0F, this.midPoint.x, this.midPoint.y);
                paramSticker.setFlippedVertically(!paramSticker.isFlippedVertically());
            }
            if (this.onStickerOperationListener != null)
                this.onStickerOperationListener.onStickerFlipped(paramSticker);
            invalidate();
        }
    }

    public void flipCurrentSticker(int paramInt) {
        flip(this.handlingSticker, paramInt);
    }

    @Nullable
    public Sticker getCurrentSticker() {
        return this.handlingSticker;
    }

    public Matrix getDownMatrix() {
        return this.downMatrix;
    }

    @NonNull
    public List<TART_BitmapStickerIcon> getIcons() {
        return this.icons;
    }

    public Sticker getLastHandlingSticker() {
        return this.lastHandlingSticker;
    }

    public int getMinClickDelayTime() {
        return this.minClickDelayTime;
    }

    public Matrix getMoveMatrix() {
        return this.moveMatrix;
    }

    @Nullable
    public OnStickerOperationListener getOnStickerOperationListener() {
        return this.onStickerOperationListener;
    }

    public Matrix getSizeMatrix() {
        return this.sizeMatrix;
    }

    public int getStickerCount() {
        return this.stickers.size();
    }

    public void getStickerPoints(@Nullable Sticker paramSticker, @NonNull float[] paramArrayOffloat) {
        if (paramSticker == null) {
            Arrays.fill(paramArrayOffloat, 0.0F);
            return;
        }
        paramSticker.getBoundPoints(this.bounds);
        paramSticker.getMappedPoints(paramArrayOffloat, this.bounds);
    }

    @NonNull
    public float[] getStickerPoints(@Nullable Sticker paramSticker) {
        float[] arrayOfFloat = new float[8];
        getStickerPoints(paramSticker, arrayOfFloat);
        return arrayOfFloat;
    }

    public List<Sticker> getStickers() {
        return this.stickers;
    }

    protected void handleCurrentMode(@NonNull MotionEvent paramMotionEvent) {
        switch (this.currentMode) {
            default:
                return;
            case 3:
                if (this.handlingSticker != null && this.currentIcon != null) {
                    this.currentIcon.onActionMove(this, paramMotionEvent);
                    return;
                }
                break;
            case 2:
                if (this.handlingSticker != null) {
                    float f1 = calculateDistance(paramMotionEvent);
                    float f2 = calculateRotation(paramMotionEvent);
                    this.moveMatrix.set(this.downMatrix);
                    this.moveMatrix.postScale(f1 / this.oldDistance, f1 / this.oldDistance, this.midPoint.x, this.midPoint.y);
                    this.moveMatrix.postRotate(f2 - this.oldRotation, this.midPoint.x, this.midPoint.y);
                    this.handlingSticker.setMatrix(this.moveMatrix);
                    return;
                }
                break;
            case 1:
                this.currentMoveingX = paramMotionEvent.getX();
                this.currentMoveingY = paramMotionEvent.getY();
                if (this.drawCirclePoint)
                    this.onStickerOperationListener.onTouchDragForBeauty(this.currentMoveingX, this.currentMoveingY);
                if (this.handlingSticker != null) {
                    this.moveMatrix.set(this.downMatrix);
                    if (this.handlingSticker instanceof TART_BeautySticker) {
                        TART_BeautySticker beautySticker = (TART_BeautySticker) this.handlingSticker;
                        if (beautySticker.getType() == 10 || beautySticker.getType() == 11) {
                            this.moveMatrix.postTranslate(0.0F, paramMotionEvent.getY() - this.downY);
                        } else {
                            this.moveMatrix.postTranslate(paramMotionEvent.getX() - this.downX, paramMotionEvent.getY() - this.downY);
                        }
                    } else {
                        this.moveMatrix.postTranslate(paramMotionEvent.getX() - this.downX, paramMotionEvent.getY() - this.downY);
                    }
                    this.handlingSticker.setMatrix(this.moveMatrix);
                    if (this.constrained)
                        constrainSticker(this.handlingSticker);
                }
                break;
            case 0:
            case 4:
                break;
        }
    }

    public boolean isConstrained() {
        return this.constrained;
    }

    protected boolean isInStickerArea(@NonNull Sticker paramSticker, float paramFloat1, float paramFloat2) {
        this.tmp[0] = paramFloat1;
        this.tmp[1] = paramFloat2;
        return paramSticker.contains(this.tmp);
    }

    public boolean isNoneSticker() {
        return (getStickerCount() == 0);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        if (this.locked)
            return super.onInterceptTouchEvent(paramMotionEvent);
        if (paramMotionEvent.getAction() != 0)
            return super.onInterceptTouchEvent(paramMotionEvent);
        this.downX = paramMotionEvent.getX();
        this.downY = paramMotionEvent.getY();
        return (findCurrentIconTouched() != null || findHandlingSticker() != null);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (paramBoolean) {
            this.stickerRect.left = paramInt1;
            this.stickerRect.top = paramInt2;
            this.stickerRect.right = paramInt3;
            this.stickerRect.bottom = paramInt4;
        }
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        for (paramInt1 = 0; paramInt1 < this.stickers.size(); paramInt1++) {
            Sticker sticker = this.stickers.get(paramInt1);
            if (sticker != null)
                transformSticker(sticker);
        }
    }

    protected boolean onTouchDown(@NonNull MotionEvent paramMotionEvent) {
        this.currentMode = 1;
        this.downX = paramMotionEvent.getX();
        this.downY = paramMotionEvent.getY();
        this.onMoving = true;
        this.currentMoveingX = paramMotionEvent.getX();
        this.currentMoveingY = paramMotionEvent.getY();
        this.midPoint = calculateMidPoint();
        this.oldDistance = calculateDistance(this.midPoint.x, this.midPoint.y, this.downX, this.downY);
        this.oldRotation = calculateRotation(this.midPoint.x, this.midPoint.y, this.downX, this.downY);
        this.currentIcon = findCurrentIconTouched();
        if (this.currentIcon != null) {
            this.currentMode = 3;
            this.currentIcon.onActionDown(this, paramMotionEvent);
        } else {
            this.handlingSticker = findHandlingSticker();
        }
        if (this.handlingSticker != null) {
            this.downMatrix.set(this.handlingSticker.getMatrix());
            if (this.bringToFrontCurrentSticker) {
                this.stickers.remove(this.handlingSticker);
                this.stickers.add(this.handlingSticker);
            }
            if (this.onStickerOperationListener != null)
                this.onStickerOperationListener.onStickerTouchedDown(this.handlingSticker);
        }
        if (this.drawCirclePoint) {
            this.onStickerOperationListener.onTouchDownForBeauty(this.currentMoveingX, this.currentMoveingY);
            invalidate();
            return true;
        }
        if (this.currentIcon == null && this.handlingSticker == null)
            return false;
        invalidate();
        return true;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (this.locked)
            return super.onTouchEvent(paramMotionEvent);
        switch (paramMotionEvent.getAction()) {
            default:
                return true;
            case 6:
                if (this.currentMode == 2 && this.handlingSticker != null && this.onStickerOperationListener != null)
                    this.onStickerOperationListener.onStickerZoomFinished(this.handlingSticker);
                this.currentMode = 0;
                return true;
            case 5:
                this.oldDistance = calculateDistance(paramMotionEvent);
                this.oldRotation = calculateRotation(paramMotionEvent);
                this.midPoint = calculateMidPoint(paramMotionEvent);
                if (this.handlingSticker != null && isInStickerArea(this.handlingSticker, paramMotionEvent.getX(1), paramMotionEvent.getY(1)) && findCurrentIconTouched() == null) {
                    this.currentMode = 2;
                    return true;
                }
                return true;
            case 2:
                handleCurrentMode(paramMotionEvent);
                invalidate();
                return true;
            case 1:
                onTouchUp(paramMotionEvent);
                return true;
            case 0:
                break;
        }
        if (!onTouchDown(paramMotionEvent)) {
            if (this.onStickerOperationListener == null)
                return false;
            this.onStickerOperationListener.onStickerTouchOutside();
            invalidate();
            if (!this.drawCirclePoint)
                return false;
        }
        return true;
    }

    protected void onTouchUp(@NonNull MotionEvent paramMotionEvent) {
        long l = SystemClock.uptimeMillis();
        this.onMoving = false;
        if (this.drawCirclePoint)
            this.onStickerOperationListener.onTouchUpForBeauty(paramMotionEvent.getX(), paramMotionEvent.getY());
        if (this.currentMode == 3 && this.currentIcon != null && this.handlingSticker != null)
            this.currentIcon.onActionUp(this, paramMotionEvent);
        if (this.currentMode == 1 && Math.abs(paramMotionEvent.getX() - this.downX) < this.touchSlop && Math.abs(paramMotionEvent.getY() - this.downY) < this.touchSlop && this.handlingSticker != null) {
            this.currentMode = 4;
            if (this.onStickerOperationListener != null)
                this.onStickerOperationListener.onStickerClicked(this.handlingSticker);
            if (l - this.lastClickTime < this.minClickDelayTime && this.onStickerOperationListener != null)
                this.onStickerOperationListener.onStickerDoubleTapped(this.handlingSticker);
        }
        if (this.currentMode == 1 && this.handlingSticker != null && this.onStickerOperationListener != null)
            this.onStickerOperationListener.onStickerDragFinished(this.handlingSticker);
        this.currentMode = 0;
        this.lastClickTime = l;
    }

    public boolean remove(@Nullable Sticker paramSticker) {
        if (this.stickers.contains(paramSticker)) {
            this.stickers.remove(paramSticker);
            if (this.onStickerOperationListener != null)
                this.onStickerOperationListener.onStickerDeleted(paramSticker);
            if (this.handlingSticker == paramSticker)
                this.handlingSticker = null;
            invalidate();
            return true;
        }
        Log.d("StickerView", "remove: the sticker is not in this StickerView");
        return false;
    }

    public void removeAllStickers() {
        this.stickers.clear();
        if (this.handlingSticker != null) {
            this.handlingSticker.release();
            this.handlingSticker = null;
        }
        invalidate();
    }

    public boolean removeCurrentSticker() {
        return remove(this.handlingSticker);
    }

    public boolean replace(@Nullable Sticker paramSticker) {
        return replace(paramSticker, true);
    }

    public boolean replace(@Nullable Sticker paramSticker, boolean paramBoolean) {
        if (this.handlingSticker == null)
            this.handlingSticker = this.lastHandlingSticker;
        if (this.handlingSticker != null && paramSticker != null) {
            float f1 = getWidth();
            float f2 = getHeight();
            if (paramBoolean) {
                paramSticker.setMatrix(this.handlingSticker.getMatrix());
                paramSticker.setFlippedVertically(this.handlingSticker.isFlippedVertically());
                paramSticker.setFlippedHorizontally(this.handlingSticker.isFlippedHorizontally());
            } else {
                this.handlingSticker.getMatrix().reset();
                float f3 = (f1 - this.handlingSticker.getWidth()) / 2.0F;
                float f4 = (f2 - this.handlingSticker.getHeight()) / 2.0F;
                paramSticker.getMatrix().postTranslate(f3, f4);
                if (f1 < f2) {
                    if (this.handlingSticker instanceof TART_TextSticker) {
                        f3 = f1 / this.handlingSticker.getWidth();
                    } else {
                        f3 = f1 / this.handlingSticker.getDrawable().getIntrinsicWidth();
                    }
                } else if (this.handlingSticker instanceof TART_TextSticker) {
                    f3 = f2 / this.handlingSticker.getHeight();
                } else {
                    f3 = f2 / this.handlingSticker.getDrawable().getIntrinsicHeight();
                }
                Matrix matrix = paramSticker.getMatrix();
                f3 /= 2.0F;
                matrix.postScale(f3, f3, f1 / 2.0F, f2 / 2.0F);
            }
            int i = this.stickers.indexOf(this.handlingSticker);
            this.stickers.set(i, paramSticker);
            this.handlingSticker = paramSticker;
            invalidate();
            return true;
        }
        return false;
    }

    public void save(@NonNull File paramFile) {
        try {
            TART_StickerUtils.saveImageToGallery(paramFile, createBitmap());
            TART_StickerUtils.notifySystemGallery(getContext(), paramFile);
            return;
        } catch (IllegalArgumentException | IllegalStateException illegalArgumentException) {
            return;
        }
    }

    public void sendToLayer(int paramInt1, int paramInt2) {
        if (this.stickers.size() >= paramInt1 && this.stickers.size() >= paramInt2) {
            Sticker sticker = this.stickers.get(paramInt1);
            this.stickers.remove(paramInt1);
            this.stickers.add(paramInt2, sticker);
            invalidate();
        }
    }

    public void setCircleRadius(int paramInt) {
        this.circleRadius = paramInt;
    }

    @NonNull
    public TART_StickerView setConstrained(boolean paramBoolean) {
        this.constrained = paramBoolean;
        postInvalidate();
        return this;
    }

    public void setDrawCirclePoint(boolean paramBoolean) {
        this.drawCirclePoint = paramBoolean;
        this.onMoving = false;
    }

    public void setHandlingSticker(Sticker paramSticker) {
        this.lastHandlingSticker = this.handlingSticker;
        this.handlingSticker = paramSticker;
        invalidate();
    }

    public void setIcons(@NonNull List<TART_BitmapStickerIcon> paramList) {
        this.icons.clear();
        this.icons.addAll(paramList);
        invalidate();
    }

    @NonNull
    public TART_StickerView setLocked(boolean paramBoolean) {
        this.locked = paramBoolean;
        invalidate();
        return this;
    }

    @NonNull
    public TART_StickerView setMinClickDelayTime(int paramInt) {
        this.minClickDelayTime = paramInt;
        return this;
    }

    @NonNull
    public TART_StickerView setOnStickerOperationListener(@Nullable OnStickerOperationListener paramOnStickerOperationListener) {
        this.onStickerOperationListener = paramOnStickerOperationListener;
        return this;
    }

    protected void setStickerPosition(@NonNull Sticker paramSticker, int paramInt) {
        float f2 = getWidth();
        float f1 = getHeight();
        float f3 = f2 - paramSticker.getWidth();
        f1 -= paramSticker.getHeight();
        if (paramSticker instanceof TART_BeautySticker) {
            TART_BeautySticker beautySticker = (TART_BeautySticker) paramSticker;
            float f = f1 / 2.0F;
            if (beautySticker.getType() == 0) {
                f1 = f3 / 3.0F;
                f2 = f;
            } else if (beautySticker.getType() == 1) {
                f1 = f3 * 2.0F / 3.0F;
                f2 = f;
            } else if (beautySticker.getType() == 2) {
                f1 = f3 / 2.0F;
                f2 = f;
            } else if (beautySticker.getType() == 4) {
                f1 = f3 / 2.0F;
                f2 = f;
            } else if (beautySticker.getType() == 10) {
                f1 = f3 / 2.0F;
                f2 = f * 2.0F / 3.0F;
            } else {
                f1 = f3;
                f2 = f;
                if (beautySticker.getType() == 11) {
                    f1 = f3 / 2.0F;
                    f2 = f * 3.0F / 2.0F;
                }
            }
        } else {
            if ((paramInt & 0x2) > 0) {
                f2 = f1 / 4.0F;
            } else if ((paramInt & 0x10) > 0) {
                f2 = f1 * 0.75F;
            } else {
                f2 = f1 / 2.0F;
            }
            if ((paramInt & 0x4) > 0) {
                f1 = f3 / 4.0F;
            } else if ((paramInt & 0x8) > 0) {
                f1 = f3 * 0.75F;
            } else {
                f1 = f3 / 2.0F;
            }
        }
        paramSticker.getMatrix().postTranslate(f1, f2);
    }

    public void showLastHandlingSticker() {
        if (this.lastHandlingSticker != null && !this.lastHandlingSticker.isShow()) {
            this.lastHandlingSticker.setShow(true);
            invalidate();
        }
    }

    public void swapLayers(int paramInt1, int paramInt2) {
        if (this.stickers.size() >= paramInt1 && this.stickers.size() >= paramInt2) {
            Collections.swap(this.stickers, paramInt1, paramInt2);
            invalidate();
        }
    }

    protected void transformSticker(@Nullable Sticker paramSticker) {
        if (paramSticker == null) {
            Log.e("StickerView", "transformSticker: the bitmapSticker is null or the bitmapSticker bitmap is null");
            return;
        }
        this.sizeMatrix.reset();
        float f2 = getWidth();
        float f3 = getHeight();
        float f1 = paramSticker.getWidth();
        float f4 = paramSticker.getHeight();
        float f5 = (f2 - f1) / 2.0F;
        float f6 = (f3 - f4) / 2.0F;
        this.sizeMatrix.postTranslate(f5, f6);
        if (f2 < f3) {
            f1 = f2 / f1;
        } else {
            f1 = f3 / f4;
        }
        Matrix matrix = this.sizeMatrix;
        f1 /= 2.0F;
        matrix.postScale(f1, f1, f2 / 2.0F, f3 / 2.0F);
        paramSticker.getMatrix().reset();
        paramSticker.setMatrix(this.sizeMatrix);
        invalidate();
    }

    public void zoomAndRotateCurrentSticker(@NonNull MotionEvent paramMotionEvent) {
        zoomAndRotateSticker(this.handlingSticker, paramMotionEvent);
    }

    public void zoomAndRotateSticker(@Nullable Sticker paramSticker, @NonNull MotionEvent paramMotionEvent) {
        if (paramSticker != null) {
            float f1;
            boolean bool = paramSticker instanceof TART_BeautySticker;
            if (bool) {
                TART_BeautySticker beautySticker = (TART_BeautySticker) paramSticker;
                if (beautySticker.getType() == 10 || beautySticker.getType() == 11)
                    return;
            }
            if (paramSticker instanceof TART_TextSticker) {
                f1 = this.oldDistance;
            } else {
                f1 = calculateDistance(this.midPoint.x, this.midPoint.y, paramMotionEvent.getX(), paramMotionEvent.getY());
            }
            float f2 = calculateRotation(this.midPoint.x, this.midPoint.y, paramMotionEvent.getX(), paramMotionEvent.getY());
            this.moveMatrix.set(this.downMatrix);
            this.moveMatrix.postScale(f1 / this.oldDistance, f1 / this.oldDistance, this.midPoint.x, this.midPoint.y);
            if (!bool)
                this.moveMatrix.postRotate(f2 - this.oldRotation, this.midPoint.x, this.midPoint.y);
            this.handlingSticker.setMatrix(this.moveMatrix);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface ActionMode {
        public static final int CLICK = 4;

        public static final int DRAG = 1;

        public static final int ICON = 3;

        public static final int NONE = 0;

        public static final int ZOOM_WITH_TWO_FINGER = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface Flip {
    }

    public static interface OnStickerOperationListener {
        void onStickerAdded(@NonNull Sticker param1Sticker);

        void onStickerClicked(@NonNull Sticker param1Sticker);

        void onStickerDeleted(@NonNull Sticker param1Sticker);

        void onStickerDoubleTapped(@NonNull Sticker param1Sticker);

        void onStickerDragFinished(@NonNull Sticker param1Sticker);

        void onStickerFlipped(@NonNull Sticker param1Sticker);

        void onStickerTouchOutside();

        void onStickerTouchedDown(@NonNull Sticker param1Sticker);

        void onStickerZoomFinished(@NonNull Sticker param1Sticker);

        void onTouchDownForBeauty(float param1Float1, float param1Float2);

        void onTouchDragForBeauty(float param1Float1, float param1Float2);

        void onTouchUpForBeauty(float param1Float1, float param1Float2);
    }
}
