package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_OneStraightLayout extends TART_NumberStraightLayout {
    public int getThemeCount() {
        return 6;
    }

    public TART_OneStraightLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public TART_OneStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                return;
            case 2:
                addCross(0, 0.5f);
                return;
            case 3:
                cutAreaEqualPart(0, 2, 1);
                return;
            case 4:
                cutAreaEqualPart(0, 1, 2);
                return;
            case 5:
                cutAreaEqualPart(0, 2, 2);
                return;
            default:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_OneStraightLayout((TART_StraightPuzzleLayout) puzzleLayout, true);
    }
}
