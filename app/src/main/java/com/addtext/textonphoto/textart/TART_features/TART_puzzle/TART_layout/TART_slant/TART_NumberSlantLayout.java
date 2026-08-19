package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import android.util.Log;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public abstract class TART_NumberSlantLayout extends TART_SlantPuzzleLayout {
    static final String TAG = "NumberSlantLayout";
    protected int theme;

    public abstract int getThemeCount();

    public TART_NumberSlantLayout() {
    }

    public TART_NumberSlantLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TART_NumberSlantLayout(int i) {
        if (i >= getThemeCount()) {
            StringBuilder sb = new StringBuilder();
            sb.append("NumberSlantLayout: the most theme count is ");
            sb.append(getThemeCount());
            sb.append(" ,you should let theme from 0 to ");
            sb.append(getThemeCount() - 1);
            sb.append(" .");
            Log.e(TAG, sb.toString());
        }
        this.theme = i;
    }

    public int getTheme() {
        return this.theme;
    }
}
