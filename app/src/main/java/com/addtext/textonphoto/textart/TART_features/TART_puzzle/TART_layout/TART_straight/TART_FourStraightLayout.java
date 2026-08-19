package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_FourStraightLayout extends TART_NumberStraightLayout {
    private static final String TAG = "FourStraightLayout";

    public int getThemeCount() {
        return 8;
    }

    public TART_FourStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_FourStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addCross(0, 0.5f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            case 2:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 4:
                addLine(0, TART_Line.Direction.VERTICAL, 0.6666667f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 5:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                return;
            case 7:
                cutAreaEqualPart(0, 4, TART_Line.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_FourStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
