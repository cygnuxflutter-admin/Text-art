package com.addtext.textonphoto.textart.TART_features.TART_puzzle;

import android.graphics.RectF;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant.TART_SlantPuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight.TART_StraightPuzzleLayout;

public class TART_PuzzleLayoutParser {
    private TART_PuzzleLayoutParser() {
    }

    public static TART_PuzzleLayout parse(final TART_PuzzleLayout.Info info) {
        TART_PuzzleLayout puzzleLayout;
        if (info.type == 0) {
            puzzleLayout = new TART_StraightPuzzleLayout() {
                public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
                    return null;
                }

                public void layout() {
                    int size = info.steps.size();
                    int i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        TART_PuzzleLayout.Step step = info.steps.get(i2);
                        switch (step.type) {
                            case 0:
                                addLine(step.position, step.lineDirection(), info.lines.get(i).getStartRatio());
                                break;
                            case 1:
                                addCross(step.position, step.hRatio, step.vRatio);
                                break;
                            case 2:
                                cutAreaEqualPart(step.position, step.hSize, step.vSize);
                                break;
                            case 3:
                                cutAreaEqualPart(step.position, step.part, step.lineDirection());
                                break;
                            case 4:
                                cutSpiral(step.position);
                                break;
                        }
                        i += step.numOfLine;
                    }
                }
            };
        } else {
            puzzleLayout = new TART_SlantPuzzleLayout() {
                public TART_PuzzleLayout clone(TART_PuzzleLayout puzzleLayout) {
                    return null;
                }

                public void layout() {
                    int size = info.steps.size();
                    for (int i = 0; i < size; i++) {
                        TART_PuzzleLayout.Step step = info.steps.get(i);
                        switch (step.type) {
                            case 0:
                                addLine(step.position, step.lineDirection(), info.lines.get(i).getStartRatio(), info.lines.get(i).getEndRatio());
                                break;
                            case 1:
                                addCross(step.position, 0.5f, 0.5f, 0.5f, 0.5f);
                                break;
                            case 2:
                                cutArea(step.position, step.hSize, step.vSize);
                                break;
                        }
                    }
                }
            };
        }
        puzzleLayout.setOuterBounds(new RectF(info.left, info.top, info.right, info.bottom));
        puzzleLayout.layout();
        puzzleLayout.setColor(info.color);
        puzzleLayout.setRadian(info.radian);
        puzzleLayout.setPadding(info.padding);
        int size = info.lineInfos.size();
        for (int i = 0; i < size; i++) {
            TART_PuzzleLayout.LineInfo lineInfo = info.lineInfos.get(i);
            TART_Line line = puzzleLayout.getLines().get(i);
            line.startPoint().x = lineInfo.startX;
            line.startPoint().y = lineInfo.startY;
            line.endPoint().x = lineInfo.endX;
            line.endPoint().y = lineInfo.endY;
        }
        puzzleLayout.sortAreas();
        puzzleLayout.update();
        return puzzleLayout;
    }
}
