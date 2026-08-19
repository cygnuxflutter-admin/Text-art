package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_EightStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 11;
    }


    public TART_EightStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_EightStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                cutAreaEqualPart(0, 3, 1);
                return;
            case 1:
                cutAreaEqualPart(0, 1, 3);
                return;
            case 2:
                cutAreaEqualPart(0, 4, TART_Line.Direction.VERTICAL);
                addLine(3, TART_Line.Direction.HORIZONTAL, 0.8f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.6f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.4f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.2f);
                return;
            case 3:
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                addLine(3, TART_Line.Direction.VERTICAL, 0.8f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.6f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.4f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.2f);
                return;
            case 4:
                cutAreaEqualPart(0, 4, TART_Line.Direction.VERTICAL);
                addLine(3, TART_Line.Direction.HORIZONTAL, 0.2f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.4f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.6f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.8f);
                return;
            case 5:
                cutAreaEqualPart(0, 4, TART_Line.Direction.HORIZONTAL);
                addLine(3, TART_Line.Direction.VERTICAL, 0.2f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.4f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.6f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.8f);
                return;
            case 6:
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(2, 3, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(1, 2, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                return;
            case 7:
                cutAreaEqualPart(0, 3, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(2, 3, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(1, 2, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                return;
            case 8:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.8f);
                cutAreaEqualPart(1, 5, TART_Line.Direction.VERTICAL);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 9:
                cutAreaEqualPart(0, 3, TART_Line.Direction.HORIZONTAL);
                cutAreaEqualPart(2, 2, TART_Line.Direction.VERTICAL);
                cutAreaEqualPart(1, 3, TART_Line.Direction.VERTICAL);
                addLine(0, TART_Line.Direction.VERTICAL, 0.75f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                return;
            case 10:
                cutAreaEqualPart(0, 2, 1);
                addLine(5, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(4, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_EightStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
