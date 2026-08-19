package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_SevenStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 9;
    }

    public TART_SevenStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_SevenStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 4, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                cutAreaEqualPart(1, 4, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 2:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 1, 2);
                return;
            case 3:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(1, 3, TART_Line.Direction.VERTICAL);
                addCross(0, 0.5f);
                return;
            case 4:
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(2, 3, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 5:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            case 6:
                addLine(0, TART_Line.Direction.VERTICAL, 0.6666667f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.4f);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 7:
                addLine(0, TART_Line.Direction.VERTICAL, 0.25f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.6666667f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.75f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 8:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.25f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(2, 3, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_SevenStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
