package com.addtext.textonphoto.textart.TART_features.TART_crop.TART_adapter;

import com.steelkiwi.cropiwa.AspectRatio;

class TART_AspectRatioCustom extends AspectRatio {
    private int selectedIem;
    private int unselectItem;

    TART_AspectRatioCustom(int i, int i2, int i3, int i4) {
        super(i, i2);
        this.selectedIem = i4;
        this.unselectItem = i3;
    }

    public int getSelectedIem() {
        return this.selectedIem;
    }

    public int getUnselectItem() {
        return this.unselectItem;
    }
}
