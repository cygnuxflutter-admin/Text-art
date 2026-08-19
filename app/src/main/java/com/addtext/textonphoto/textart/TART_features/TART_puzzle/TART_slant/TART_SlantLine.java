package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant;

import android.graphics.PointF;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;

class TART_SlantLine implements TART_Line {
    TART_SlantLine attachLineEnd;
    TART_SlantLine attachLineStart;
    public final TART_Line.Direction direction;
    TART_CrossoverPointF end;
    private float endRatio;
    TART_Line lowerLine;
    private PointF previousEnd = new PointF();
    private PointF previousStart = new PointF();
    TART_CrossoverPointF start;
    private float startRatio;
    TART_Line upperLine;

    TART_SlantLine(TART_Line.Direction direction2) {
        this.direction = direction2;
    }

    TART_SlantLine(TART_CrossoverPointF crossoverPointF, TART_CrossoverPointF crossoverPointF2, TART_Line.Direction direction2) {
        this.start = crossoverPointF;
        this.end = crossoverPointF2;
        this.direction = direction2;
    }

    public void setStartRatio(float f) {
        this.startRatio = f;
    }

    public float getStartRatio() {
        return this.startRatio;
    }

    public void setEndRatio(float f) {
        this.endRatio = f;
    }

    public float getEndRatio() {
        return this.endRatio;
    }

    public float length() {
        return (float) Math.sqrt(Math.pow((double) (this.end.x - this.start.x), 2.0d) + Math.pow((double) (this.end.y - this.start.y), 2.0d));
    }

    public PointF startPoint() {
        return this.start;
    }

    public PointF endPoint() {
        return this.end;
    }

    public TART_Line lowerLine() {
        return this.lowerLine;
    }

    public TART_Line upperLine() {
        return this.upperLine;
    }

    public TART_Line attachStartLine() {
        return this.attachLineStart;
    }

    public TART_Line attachEndLine() {
        return this.attachLineEnd;
    }

    public void setLowerLine(TART_Line line) {
        this.lowerLine = line;
    }

    public void setUpperLine(TART_Line line) {
        this.upperLine = line;
    }

    public TART_Line.Direction direction() {
        return this.direction;
    }

    public float slope() {
        return TART_SlantUtils.calculateSlope(this);
    }

    public boolean contains(float f, float f2, float f3) {
        return TART_SlantUtils.contains(this, f, f2, f3);
    }

    public boolean move(float f, float f2) {
        if (this.direction == TART_Line.Direction.HORIZONTAL) {
            if (this.previousStart.y + f < this.lowerLine.maxY() + f2 || this.previousStart.y + f > this.upperLine.minY() - f2 || this.previousEnd.y + f < this.lowerLine.maxY() + f2 || this.previousEnd.y + f > this.upperLine.minY() - f2) {
                return false;
            }
            this.start.y = this.previousStart.y + f;
            this.end.y = this.previousEnd.y + f;
            return true;
        } else if (this.previousStart.x + f < this.lowerLine.maxX() + f2 || this.previousStart.x + f > this.upperLine.minX() - f2 || this.previousEnd.x + f < this.lowerLine.maxX() + f2 || this.previousEnd.x + f > this.upperLine.minX() - f2) {
            return false;
        } else {
            this.start.x = this.previousStart.x + f;
            this.end.x = this.previousEnd.x + f;
            return true;
        }
    }

    public void prepareMove() {
        this.previousStart.set(this.start);
        this.previousEnd.set(this.end);
    }

    public void update(float f, float f2) {
        TART_SlantUtils.intersectionOfLines(this.start, this, this.attachLineStart);
        TART_SlantUtils.intersectionOfLines(this.end, this, this.attachLineEnd);
    }

    public float minX() {
        return Math.min(this.start.x, this.end.x);
    }

    public float maxX() {
        return Math.max(this.start.x, this.end.x);
    }

    public float minY() {
        return Math.min(this.start.y, this.end.y);
    }

    public float maxY() {
        return Math.max(this.start.y, this.end.y);
    }

    public void offset(float f, float f2) {
        this.start.offset(f, f2);
        this.end.offset(f, f2);
    }

    public String toString() {
        return "start --> " + this.start.toString() + ",end --> " + this.end.toString();
    }
}
