package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public class TART_FourSlantLayout extends TART_NumberSlantLayout {
    public int getThemeCount() {
        return 6;
    }

    public TART_FourSlantLayout(int i) {
        super(i);
    }

    public TART_FourSlantLayout(TART_PuzzleLayout puzzleLayout, boolean z) {
        super((TART_SlantPuzzleLayout) puzzleLayout, z);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.3f, 0.3f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.7f, 0.7f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.3f, 0.3f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.7f, 0.7f);
                return;
            case 2:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.6666667f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.6666667f, 0.6666667f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.6666667f, 0.6666667f);
                return;
            case 3:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f, 0.33333334f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.33333334f, 0.33333334f);
                return;
            case 4:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.33333334f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f, 0.5f);
                return;
            case 5:
                addLine(0, TART_Line.Direction.VERTICAL, 0.33333334f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.5f, 0.5f);
                return;
            case 6:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.7f, 0.3f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.3f, 0.5f);
                addLine(2, TART_Line.Direction.VERTICAL, 0.5f, 0.7f);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_FourSlantLayout(puzzleLayout, true);
    }
}
