package com.addtext.textonphoto.textart.TART_imagechanger;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class TART_RoundTextView extends AppCompatTextView {
    private final TART_RoundViewDelegate delegate;

    public TART_RoundTextView(Context context) {
        this(context, null);
    }

    public TART_RoundTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TART_RoundTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new TART_RoundViewDelegate(this, context, attributeSet);
    }

    public TART_RoundViewDelegate getDelegate() {
        return this.delegate;
    }

    @Override
    public void onMeasure(int i, int i2) {
        if (!this.delegate.isWidthHeightEqual() || getWidth() <= 0 || getHeight() <= 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(getWidth(), getHeight()), 1073741824);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    @Override
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.delegate.isRadiusHalfHeight()) {
            this.delegate.setCornerRadius(getHeight() / 2);
        } else {
            this.delegate.setBgSelector();
        }
    }
}
