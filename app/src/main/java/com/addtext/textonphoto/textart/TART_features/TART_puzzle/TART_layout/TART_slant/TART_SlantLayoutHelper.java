package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;

import java.util.ArrayList;
import java.util.List;

public class TART_SlantLayoutHelper {
    private TART_SlantLayoutHelper() {
    }

    public static List<TART_PuzzleLayout> getAllThemeLayout(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        switch (i) {
            case 1:
                while (i2 < 4) {
                    arrayList.add(new TART_OneSlantLayout(i2));
                    i2++;
                }
                break;
            case 2:
                while (i2 < 2) {
                    arrayList.add(new TART_TwoSlantLayout(i2));
                    i2++;
                }
                break;
            case 3:
                while (i2 < 6) {
                    arrayList.add(new TART_ThreeSlantLayout(i2));
                    i2++;
                }
                break;
            case 4:
                while (i2 < 7) {
                    arrayList.add(new TART_FourSlantLayout(i2));
                    i2++;
                }
                break;
            case 6:
                while (i2 < 2) {
                    arrayList.add(new TART_SixSlantLayout(i2));
                    i2++;
                }
                break;
            case 7:
                while (i2 < 1) {
                    arrayList.add(new TART_SevenSlantLayout(i2));
                    i2++;
                }
                break;
        }
        return arrayList;
    }
}
