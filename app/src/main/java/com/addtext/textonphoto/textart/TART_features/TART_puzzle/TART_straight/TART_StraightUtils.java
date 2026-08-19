package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight;

import android.graphics.PointF;
import android.util.Pair;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;

import java.util.ArrayList;
import java.util.List;

class TART_StraightUtils {
    TART_StraightUtils() {
    }

    static TART_StraightLine createLine(TART_StraightArea straightArea, TART_Line.Direction direction, float f) {
        PointF pointF = new PointF();
        PointF pointF2 = new PointF();
        if (direction == TART_Line.Direction.HORIZONTAL) {
            pointF.x = straightArea.left();
            pointF.y = (straightArea.height() * f) + straightArea.top();
            pointF2.x = straightArea.right();
            pointF2.y = (straightArea.height() * f) + straightArea.top();
        } else if (direction == TART_Line.Direction.VERTICAL) {
            pointF.x = (straightArea.width() * f) + straightArea.left();
            pointF.y = straightArea.top();
            pointF2.x = (straightArea.width() * f) + straightArea.left();
            pointF2.y = straightArea.bottom();
        }
        TART_StraightLine straightLine = new TART_StraightLine(pointF, pointF2);
        if (direction == TART_Line.Direction.HORIZONTAL) {
            straightLine.attachLineStart = straightArea.lineLeft;
            straightLine.attachLineEnd = straightArea.lineRight;
            straightLine.setUpperLine(straightArea.lineBottom);
            straightLine.setLowerLine(straightArea.lineTop);
        } else if (direction == TART_Line.Direction.VERTICAL) {
            straightLine.attachLineStart = straightArea.lineTop;
            straightLine.attachLineEnd = straightArea.lineBottom;
            straightLine.setUpperLine(straightArea.lineRight);
            straightLine.setLowerLine(straightArea.lineLeft);
        }
        straightLine.setStartRatio(f);
        return straightLine;
    }

    static List<TART_StraightArea> cutArea(TART_StraightArea straightArea, TART_StraightLine straightLine) {
        ArrayList arrayList = new ArrayList();
        if (straightLine.direction() == TART_Line.Direction.HORIZONTAL) {
            TART_StraightArea straightArea2 = new TART_StraightArea(straightArea);
            straightArea2.lineBottom = straightLine;
            arrayList.add(straightArea2);
            TART_StraightArea straightArea3 = new TART_StraightArea(straightArea);
            straightArea3.lineTop = straightLine;
            arrayList.add(straightArea3);
        } else if (straightLine.direction() == TART_Line.Direction.VERTICAL) {
            TART_StraightArea straightArea4 = new TART_StraightArea(straightArea);
            straightArea4.lineRight = straightLine;
            arrayList.add(straightArea4);
            TART_StraightArea straightArea5 = new TART_StraightArea(straightArea);
            straightArea5.lineLeft = straightLine;
            arrayList.add(straightArea5);
        }
        return arrayList;
    }

    static Pair<List<TART_StraightLine>, List<TART_StraightArea>> cutArea(TART_StraightArea straightArea, int i, int i2) {
        int i3;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(i);
        TART_StraightArea straightArea2 = new TART_StraightArea(straightArea);
        for (int i4 = i + 1; i4 > 1; i4--) {
            TART_StraightLine createLine = createLine(straightArea2, TART_Line.Direction.HORIZONTAL, ((float) (i4 - 1)) / ((float) i4));
            arrayList2.add(createLine);
            straightArea2.lineBottom = createLine;
        }
        ArrayList arrayList3 = new ArrayList();
        TART_StraightArea straightArea3 = new TART_StraightArea(straightArea);
        int i5 = i2 + 1;
        while (true) {
            i3 = 0;
            if (i5 <= 1) {
                break;
            }
            TART_StraightLine createLine2 = createLine(straightArea3, TART_Line.Direction.VERTICAL, ((float) (i5 - 1)) / ((float) i5));
            arrayList3.add(createLine2);
            TART_StraightArea straightArea4 = new TART_StraightArea(straightArea3);
            straightArea4.lineLeft = createLine2;
            while (i3 <= arrayList2.size()) {
                TART_StraightArea straightArea5 = new TART_StraightArea(straightArea4);
                if (i3 == 0) {
                    straightArea5.lineTop = (TART_StraightLine) arrayList2.get(i3);
                } else if (i3 == arrayList2.size()) {
                    straightArea5.lineBottom = (TART_StraightLine) arrayList2.get(i3 - 1);
                } else {
                    straightArea5.lineTop = (TART_StraightLine) arrayList2.get(i3);
                    straightArea5.lineBottom = (TART_StraightLine) arrayList2.get(i3 - 1);
                }
                arrayList.add(straightArea5);
                i3++;
            }
            straightArea3.lineRight = createLine2;
            i5--;
        }
        while (i3 <= arrayList2.size()) {
            TART_StraightArea straightArea6 = new TART_StraightArea(straightArea3);
            if (i3 == 0) {
                straightArea6.lineTop = (TART_StraightLine) arrayList2.get(i3);
            } else if (i3 == arrayList2.size()) {
                straightArea6.lineBottom = (TART_StraightLine) arrayList2.get(i3 - 1);
            } else {
                straightArea6.lineTop = (TART_StraightLine) arrayList2.get(i3);
                straightArea6.lineBottom = (TART_StraightLine) arrayList2.get(i3 - 1);
            }
            arrayList.add(straightArea6);
            i3++;
        }
        ArrayList arrayList4 = new ArrayList();
        arrayList4.addAll(arrayList2);
        arrayList4.addAll(arrayList3);
        return new Pair<>(arrayList4, arrayList);
    }

    static List<TART_StraightArea> cutAreaCross(TART_StraightArea straightArea, TART_StraightLine straightLine, TART_StraightLine straightLine2) {
        ArrayList arrayList = new ArrayList();
        TART_StraightArea straightArea2 = new TART_StraightArea(straightArea);
        straightArea2.lineBottom = straightLine;
        straightArea2.lineRight = straightLine2;
        arrayList.add(straightArea2);
        TART_StraightArea straightArea3 = new TART_StraightArea(straightArea);
        straightArea3.lineBottom = straightLine;
        straightArea3.lineLeft = straightLine2;
        arrayList.add(straightArea3);
        TART_StraightArea straightArea4 = new TART_StraightArea(straightArea);
        straightArea4.lineTop = straightLine;
        straightArea4.lineRight = straightLine2;
        arrayList.add(straightArea4);
        TART_StraightArea straightArea5 = new TART_StraightArea(straightArea);
        straightArea5.lineTop = straightLine;
        straightArea5.lineLeft = straightLine2;
        arrayList.add(straightArea5);
        return arrayList;
    }

    static Pair<List<TART_StraightLine>, List<TART_StraightArea>> cutAreaSpiral(TART_StraightArea straightArea) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        float width = straightArea.width();
        float height = straightArea.height();
        float left = straightArea.left();
        float pVar = straightArea.top();
        float f = height / 3.0f;
        float f2 = pVar + f;
        PointF pointF = new PointF(left, f2);
        float f3 = width / 3.0f;
        float f4 = (f3 * 2.0f) + left;
        PointF pointF2 = new PointF(f4, pVar);
        float f5 = (f * 2.0f) + pVar;
        PointF pointF3 = new PointF(width + left, f5);
        float f6 = left + f3;
        PointF pointF4 = new PointF(f6, pVar + height);
        PointF pointF5 = new PointF(f6, f2);
        PointF pointF6 = new PointF(f4, f2);
        PointF pointF7 = new PointF(f4, f5);
        PointF pointF8 = new PointF(f6, f5);
        TART_StraightLine straightLine = new TART_StraightLine(pointF, pointF6);
        TART_StraightLine straightLine2 = new TART_StraightLine(pointF2, pointF7);
        TART_StraightLine straightLine3 = new TART_StraightLine(pointF8, pointF3);
        TART_StraightLine straightLine4 = new TART_StraightLine(pointF5, pointF4);
        straightLine.setAttachLineStart(straightArea.lineLeft);
        straightLine.setAttachLineEnd(straightLine2);
        straightLine.setLowerLine(straightArea.lineTop);
        straightLine.setUpperLine(straightLine3);
        straightLine2.setAttachLineStart(straightArea.lineTop);
        straightLine2.setAttachLineEnd(straightLine3);
        straightLine2.setLowerLine(straightLine4);
        straightLine2.setUpperLine(straightArea.lineRight);
        straightLine3.setAttachLineStart(straightLine4);
        straightLine3.setAttachLineEnd(straightArea.lineRight);
        straightLine3.setLowerLine(straightLine);
        straightLine3.setUpperLine(straightArea.lineBottom);
        straightLine4.setAttachLineStart(straightLine);
        straightLine4.setAttachLineEnd(straightArea.lineBottom);
        straightLine4.setLowerLine(straightArea.lineLeft);
        straightLine4.setUpperLine(straightLine2);
        arrayList.add(straightLine);
        arrayList.add(straightLine2);
        arrayList.add(straightLine3);
        arrayList.add(straightLine4);
        TART_StraightArea straightArea2 = new TART_StraightArea(straightArea);
        straightArea2.lineRight = straightLine2;
        straightArea2.lineBottom = straightLine;
        arrayList2.add(straightArea2);
        TART_StraightArea straightArea3 = new TART_StraightArea(straightArea);
        straightArea3.lineLeft = straightLine2;
        straightArea3.lineBottom = straightLine3;
        arrayList2.add(straightArea3);
        TART_StraightArea straightArea4 = new TART_StraightArea(straightArea);
        straightArea4.lineRight = straightLine4;
        straightArea4.lineTop = straightLine;
        arrayList2.add(straightArea4);
        TART_StraightArea straightArea5 = new TART_StraightArea(straightArea);
        straightArea5.lineTop = straightLine;
        straightArea5.lineRight = straightLine2;
        straightArea5.lineLeft = straightLine4;
        straightArea5.lineBottom = straightLine3;
        arrayList2.add(straightArea5);
        TART_StraightArea straightArea6 = new TART_StraightArea(straightArea);
        straightArea6.lineLeft = straightLine4;
        straightArea6.lineTop = straightLine3;
        arrayList2.add(straightArea6);
        return new Pair<>(arrayList, arrayList2);
    }
}
