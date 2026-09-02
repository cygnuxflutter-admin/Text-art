package com.addtext.textonphoto.textart.TART_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class TART_DotsLoaderView extends View {

    private static final int DOT_COUNT = 12;
    private static final int ANIMATION_DURATION = 1200;

    private Paint paint;
    private int activeDot = 0;
    private ValueAnimator animator;

    // Orange brand color
    private final int baseColor = 0xFFFF5520;

    public TART_DotsLoaderView(Context context) {
        super(context);
        init();
    }

    public TART_DotsLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TART_DotsLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        animator = ValueAnimator.ofInt(0, DOT_COUNT - 1);
        animator.setDuration(ANIMATION_DURATION);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            activeDot = (int) animation.getAnimatedValue();
            invalidate();
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    public void startAnimation() {
        if (animator != null && !animator.isRunning()) {
            animator.start();
        }
    }

    public void stopAnimation() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) * 0.75f;

        // Dot size scales with view size
        float dotRadiusMax = Math.min(centerX, centerY) * 0.12f;
        float dotRadiusMin = dotRadiusMax * 0.55f;

        for (int i = 0; i < DOT_COUNT; i++) {
            double angle = Math.toRadians((360.0 / DOT_COUNT) * i - 90);
            float dotX = centerX + (float) (radius * Math.cos(angle));
            float dotY = centerY + (float) (radius * Math.sin(angle));

            // Calculate distance from active dot (wrapping around)
            int distance = Math.min(
                    Math.abs(i - activeDot),
                    DOT_COUNT - Math.abs(i - activeDot)
            );

            // Closer dots are bigger and more opaque
            float fraction = 1f - (distance / (float) (DOT_COUNT / 2));
            fraction = Math.max(0.2f, fraction);

            float dotRadius = dotRadiusMin + (dotRadiusMax - dotRadiusMin) * fraction;
            int alpha = (int) (255 * fraction);

            paint.setColor(baseColor);
            paint.setAlpha(alpha);

            canvas.drawCircle(dotX, dotY, dotRadius, paint);
        }
    }
}
