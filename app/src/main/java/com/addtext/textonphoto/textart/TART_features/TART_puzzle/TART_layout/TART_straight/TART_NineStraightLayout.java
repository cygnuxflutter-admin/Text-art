package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_NineStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 8;
    }

    public TART_NineStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_NineStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                cutAreaEqualPart(0, 2, 2);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 4, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                return;
            case 2:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 4, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(0, 4, TART_Line.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 3, TART_Line.Direction.VERTICAL);
                addLine(1, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            case 4:
                addLine(0, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 3, TART_Line.Direction.HORIZONTAL);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 5:
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.HORIZONTAL);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                addLine(2, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.VERTICAL);
                addLine(0, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                return;
            case 7:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 1, 3);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_NineStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
