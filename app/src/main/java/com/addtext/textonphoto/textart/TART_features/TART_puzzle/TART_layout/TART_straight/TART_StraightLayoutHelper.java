package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;

import java.util.ArrayList;
import java.util.List;

public class TART_StraightLayoutHelper {
    private TART_StraightLayoutHelper() {
    }

    public static List<TART_PuzzleLayout> getAllThemeLayout(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        switch (i) {
            case 1:
                while (i2 < 6) {
                    arrayList.add(new TART_OneStraightLayout(i2));
                    i2++;
                }
                break;
            case 2:
                while (i2 < 6) {
                    arrayList.add(new TART_TwoStraightLayout(i2));
                    i2++;
                }
                break;
            case 3:
                while (i2 < 6) {
                    arrayList.add(new TART_ThreeStraightLayout(i2));
                    i2++;
                }
                break;
            case 4:
                while (i2 < 8) {
                    arrayList.add(new TART_FourStraightLayout(i2));
                    i2++;
                }
                break;
            case 5:
                while (i2 < 17) {
                    arrayList.add(new TART_FiveStraightLayout(i2));
                    i2++;
                }
                break;
            case 6:
                while (i2 < 12) {
                    arrayList.add(new TART_SixStraightLayout(i2));
                    i2++;
                }
                break;
            case 7:
                while (i2 < 9) {
                    arrayList.add(new TART_SevenStraightLayout(i2));
                    i2++;
                }
                break;
            case 8:
                while (i2 < 11) {
                    arrayList.add(new TART_EightStraightLayout(i2));
                    i2++;
                }
                break;
            case 9:
                while (i2 < 8) {
                    arrayList.add(new TART_NineStraightLayout(i2));
                    i2++;
                }
                break;
        }
        return arrayList;
    }
}
