package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_FiveStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 17;
    }

    public TART_FiveStraightLayout() {
    }

    public TART_FiveStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_FiveStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.VERTICAL, 0.25f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.6666667f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                addLine(3, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 2:
                addLine(0, TART_Line.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 3:
                addLine(0, TART_Line.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.HORIZONTAL);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 4:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.75f);
                cutAreaEqualPart(1, 4, TART_Line.Direction.VERTICAL);
                return;
            case 5:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.25f);
                cutAreaEqualPart(0, 4, TART_Line.Direction.VERTICAL);
                return;
            case 6:
                addLine(0, TART_Line.Direction.VERTICAL, 0.75f);
                cutAreaEqualPart(1, 4, TART_Line.Direction.HORIZONTAL);
                return;
            case 7:
                addLine(0, TART_Line.Direction.VERTICAL, 0.25f);
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                return;
            case 8:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.25f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(3, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 9:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.4f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                cutAreaEqualPart(2, 3, TART_Line.Direction.VERTICAL);
                return;
            case 10:
                addCross(0, 0.33333334f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 11:
                addCross(0, 0.6666667f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 12:
                addCross(0, 0.33333334f, 0.6666667f);
                addLine(3, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 13:
                addCross(0, 0.6666667f, 0.33333334f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 14:
                cutSpiral(0);
                return;
            case 15:
                cutAreaEqualPart(0, 5, TART_Line.Direction.HORIZONTAL);
                return;
            case 16:
                cutAreaEqualPart(0, 5, TART_Line.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 5, TART_Line.Direction.HORIZONTAL);
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_FiveStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
