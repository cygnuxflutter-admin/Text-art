package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import android.util.Log;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public abstract class TART_NumberStraightLayout extends TART_StraightPuzzleLayout {
    static final String TAG = "NumberStraightLayout";
    protected int theme;

    public abstract int getThemeCount();

    public TART_NumberStraightLayout() {
    }

    public TART_NumberStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_NumberStraightLayout(int i) {
        if (i >= getThemeCount()) {
            StringBuilder sb = new StringBuilder();
            sb.append("NumberStraightLayout: the most theme count is ");
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
