package com.addtext.textonphoto.textart.TART_features.TART_puzzle;

import android.graphics.PointF;

public interface TART_Line {

    enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    TART_Line attachEndLine();

    TART_Line attachStartLine();

    boolean contains(float f, float f2, float f3);

    Direction direction();

    PointF endPoint();

    float getEndRatio();

    float getStartRatio();

    float length();

    TART_Line lowerLine();

    float maxX();

    float maxY();

    float minX();

    float minY();

    boolean move(float f, float f2);

    void offset(float f, float f2);

    void prepareMove();

    void setEndRatio(float f);

    void setLowerLine(TART_Line line);

    void setStartRatio(float f);

    void setUpperLine(TART_Line line);

    float slope();

    PointF startPoint();

    void update(float f, float f2);

    TART_Line upperLine();
}
