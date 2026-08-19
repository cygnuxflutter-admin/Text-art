package com.addtext.textonphoto.textart.TART_utils;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant.TART_SlantLayoutHelper;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight.TART_StraightLayoutHelper;

import java.util.ArrayList;
import java.util.List;

public class TART_PuzzleUtils {

    private TART_PuzzleUtils() {
    }

    public static List<TART_PuzzleLayout> getPuzzleLayouts(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(TART_SlantLayoutHelper.getAllThemeLayout(i));
        arrayList.addAll(TART_StraightLayoutHelper.getAllThemeLayout(i));
        return arrayList;
    }
}
