package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_SixStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 12;
    }

    public TART_SixStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_SixStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                cutAreaEqualPart(0, 2, 1);
                return;
            case 1:
                cutAreaEqualPart(0, 1, 2);
                return;
            case 2:
                addCross(0, 0.6666667f, 0.5f);
                addLine(3, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 3:
                addCross(0, 0.5f, 0.6666667f);
                addLine(3, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 4:
                addCross(0, 0.5f, 0.33333334f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 5:
                addCross(0, 0.33333334f, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 6:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.8f);
                cutAreaEqualPart(1, 5, TART_Line.Direction.VERTICAL);
                return;
            case 7:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.25f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.25f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.6666667f);
                addLine(4, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 8:
                addCross(0, 0.33333334f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(4, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 9:
                addCross(0, 0.6666667f, 0.33333334f);
                addLine(3, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 10:
                addCross(0, 0.6666667f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 11:
                addCross(0, 0.33333334f, 0.6666667f);
                addLine(3, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 12:
                addCross(0, 0.33333334f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            default:
                addCross(0, 0.6666667f, 0.5f);
                addLine(3, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.5f);
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_SixStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
