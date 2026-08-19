package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public class TART_SevenSlantLayout extends TART_NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public TART_SevenSlantLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TART_SevenSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        if (this.theme == 0) {
            addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
            addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
            addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(1, TART_Line.Direction.HORIZONTAL, 0.33f, 0.33f);
            addLine(3, TART_Line.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(2, TART_Line.Direction.HORIZONTAL, 0.5f, 0.5f);
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_SevenSlantLayout((TART_SlantPuzzleLayout) puzzleLayout, true);
    }
}
