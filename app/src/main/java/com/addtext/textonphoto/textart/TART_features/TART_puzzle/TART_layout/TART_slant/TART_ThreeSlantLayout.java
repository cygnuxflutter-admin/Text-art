package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;

public class TART_ThreeSlantLayout extends TART_NumberSlantLayout {
    public int getThemeCount() {
        return 6;
    }

    public TART_ThreeSlantLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
        this.theme = ((TART_NumberSlantLayout) slantPuzzleLayout).getTheme();
    }

    public TART_ThreeSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 1:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.5f);
                addLine(1, TART_Line.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 2:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 3:
                addLine(0, TART_Line.Direction.VERTICAL, 0.5f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 4:
                addLine(0, TART_Line.Direction.HORIZONTAL, 0.44f, 0.56f);
                addLine(0, TART_Line.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 5:
                addLine(0, TART_Line.Direction.VERTICAL, 0.56f, 0.44f);
                addLine(1, TART_Line.Direction.HORIZONTAL, 0.44f, 0.56f);
                return;
            default:
                return;
        }
    }

    public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
        return new TART_ThreeSlantLayout((TART_SlantPuzzleLayout) puzzleLayout, true);
    }
}
