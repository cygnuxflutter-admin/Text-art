package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public class TART_OneSlantLayout extends TART_NumberSlantLayout {
    public int getThemeCount() {
        return 4;
    }

    public TART_OneSlantLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TART_OneSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 2:
                addCross(0, 0.56f, 0.44f, 0.56f, 0.44f);
                return;
            case 3:
                cutArea(0, 1, 2);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_OneSlantLayout((TART_SlantPuzzleLayout) puzzleLayout, true);
    }
}
