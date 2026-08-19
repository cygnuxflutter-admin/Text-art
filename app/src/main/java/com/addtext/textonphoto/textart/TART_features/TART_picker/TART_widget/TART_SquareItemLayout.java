package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class TART_SquareItemLayout extends RelativeLayout {
    public TART_SquareItemLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TART_SquareItemLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TART_SquareItemLayout(Context context) {
        super(context);
    }


    public void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }
}
