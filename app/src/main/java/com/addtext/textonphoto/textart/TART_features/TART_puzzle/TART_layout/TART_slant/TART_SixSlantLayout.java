package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public class TART_SixSlantLayout extends TART_NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public TART_SixSlantLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TART_SixSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.7f, 0.7f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f, 0.5f);
                addLine(2, TART_Line.Direction.HORIZONTAL, 0.3f, 0.3f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.3f, 0.3f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.5f, 0.5f);
                addLine(4, TART_Line.Direction.VERTICAL, 0.7f, 0.7f);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_SixSlantLayout((TART_SlantPuzzleLayout) puzzleLayout, true);
    }
}
