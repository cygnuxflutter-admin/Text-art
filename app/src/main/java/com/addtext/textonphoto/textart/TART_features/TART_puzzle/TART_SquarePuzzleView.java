package com.addtext.textonphoto.textart.TART_features.TART_puzzle;

import android.content.Context;
import android.util.AttributeSet;

public class TART_SquarePuzzleView extends TART_PuzzleView {
    public TART_SquarePuzzleView(Context context) {
        super(context);
    }

    public TART_SquarePuzzleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TART_SquarePuzzleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }


    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        }
        setMeasuredDimension(measuredWidth, measuredWidth);
    }
}
