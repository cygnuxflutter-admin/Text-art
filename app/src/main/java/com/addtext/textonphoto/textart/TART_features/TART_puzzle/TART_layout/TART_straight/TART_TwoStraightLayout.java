package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_TwoStraightLayout extends TART_NumberStraightLayout {
    private float mRadio = 0.5f;

    public int getThemeCount() {
        return 6;
    }

    public TART_TwoStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_TwoStraightLayout(int i) {
        super(i);
    }


    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, this.mRadio);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, this.mRadio);
                return;
            case 2:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 3:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                return;
            case 4:
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                return;
            case 5:
                addLine(0, TART_Line.Direction.VERTICAL, 0.6666667f);
                return;
            default:
                addLine(0, TART_Line.Direction.HORIZONTAL, this.mRadio);
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_TwoStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
