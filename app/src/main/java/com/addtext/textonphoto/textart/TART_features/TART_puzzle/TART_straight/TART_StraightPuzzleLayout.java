package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_straight;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Pair;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Area;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_Line;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class TART_StraightPuzzleLayout implements TART_PuzzleLayout {
    private Comparator<TART_StraightArea> areaComparator = new TART_StraightArea.AreaComparator();
    private List<TART_StraightArea> areas = new ArrayList();
    private RectF bounds;
    private int color = -1;
    private List<TART_Line> lines = new ArrayList();
    private TART_StraightArea outerArea;
    private List<TART_Line> outerLines = new ArrayList(4);
    private float padding;
    private float radian;
    private ArrayList<TART_PuzzleLayout.Step> steps = new ArrayList<>();

    public abstract void layout();

    protected TART_StraightPuzzleLayout() {
    }

    protected TART_StraightPuzzleLayout(TART_StraightPuzzleLayout straightPuzzleLayout, boolean z) {
        this.bounds = straightPuzzleLayout.getBounds();
        this.outerArea = straightPuzzleLayout.getOuterArea();
        this.areas = straightPuzzleLayout.getAreas();
        this.lines = straightPuzzleLayout.getLines();
        this.outerLines = straightPuzzleLayout.getOuterLines();
        this.padding = straightPuzzleLayout.getPadding();
        this.radian = straightPuzzleLayout.getRadian();
        this.color = straightPuzzleLayout.getColor();
        this.areaComparator = straightPuzzleLayout.getAreaComparator();
        this.steps = straightPuzzleLayout.getSteps();
    }

    public List<TART_StraightArea> getAreas() {
        return this.areas;
    }

    public Comparator<TART_StraightArea> getAreaComparator() {
        return this.areaComparator;
    }

    public ArrayList<TART_PuzzleLayout.Step> getSteps() {
        return this.steps;
    }


    public void setOuterBounds(RectF rectF) {
        reset();
        this.bounds = rectF;
        PointF pointF = new PointF(rectF.left, rectF.top);
        PointF pointF2 = new PointF(rectF.right, rectF.top);
        PointF pointF3 = new PointF(rectF.left, rectF.bottom);
        PointF pointF4 = new PointF(rectF.right, rectF.bottom);
        TART_StraightLine straightLine = new TART_StraightLine(pointF, pointF3);
        TART_StraightLine straightLine2 = new TART_StraightLine(pointF, pointF2);
        TART_StraightLine straightLine3 = new TART_StraightLine(pointF2, pointF4);
        TART_StraightLine straightLine4 = new TART_StraightLine(pointF3, pointF4);
        this.outerLines.clear();
        this.outerLines.add(straightLine);
        this.outerLines.add(straightLine2);
        this.outerLines.add(straightLine3);
        this.outerLines.add(straightLine4);
        this.outerArea = new TART_StraightArea();
        this.outerArea.lineLeft = straightLine;
        this.outerArea.lineTop = straightLine2;
        this.outerArea.lineRight = straightLine3;
        this.outerArea.lineBottom = straightLine4;
        this.areas.clear();
        this.areas.add(this.outerArea);
    }

    public int getAreaCount() {
        return this.areas.size();
    }

    public List<TART_Line> getOuterLines() {
        return this.outerLines;
    }

    public List<TART_Line> getLines() {
        return this.lines;
    }

    public void update() {
        for (TART_Line update : this.lines) {
            update.update(width(), height());
        }
    }

    public RectF getBounds() {
        return this.bounds;
    }

    public float width() {
        if (this.outerArea == null) {
            return 0.0f;
        }
        return this.outerArea.width();
    }

    public float height() {
        if (this.outerArea == null) {
            return 0.0f;
        }
        return this.outerArea.height();
    }

    public void reset() {
        this.lines.clear();
        this.areas.clear();
        this.areas.add(this.outerArea);
        this.steps.clear();
    }

    public TART_Area getArea(int i) {
        return this.areas.get(i);
    }

    public TART_StraightArea getOuterArea() {
        return this.outerArea;
    }

    public void setPadding(float f) {
        this.padding = f;
        for (TART_StraightArea padding2 : this.areas) {
            padding2.setPadding(f);
        }
        this.outerArea.lineLeft.startPoint().set(this.bounds.left + f, this.bounds.top + f);
        this.outerArea.lineLeft.endPoint().set(this.bounds.left + f, this.bounds.bottom - f);
        this.outerArea.lineRight.startPoint().set(this.bounds.right - f, this.bounds.top + f);
        this.outerArea.lineRight.endPoint().set(this.bounds.right - f, this.bounds.bottom - f);
        update();
    }

    public float getPadding() {
        return this.padding;
    }


    public void addLine(int i, TART_Line.Direction direction, float f) {
        addLine(this.areas.get(i), direction, f);
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        int i2 = 0;
        step.type = 0;
        if (direction != TART_Line.Direction.HORIZONTAL) {
            i2 = 1;
        }
        step.direction = i2;
        step.position = i;
        this.steps.add(step);
    }

    private List<TART_StraightArea> addLine(TART_StraightArea straightArea, TART_Line.Direction direction, float f) {
        this.areas.remove(straightArea);
        TART_StraightLine createLine = TART_StraightUtils.createLine(straightArea, direction, f);
        this.lines.add(createLine);
        List<TART_StraightArea> cutArea = TART_StraightUtils.cutArea(straightArea, createLine);
        this.areas.addAll(cutArea);
        updateLineLimit();
        sortAreas();
        return cutArea;
    }


    public void cutAreaEqualPart(int i, int i2, TART_Line.Direction direction) {
        int i3;
        TART_StraightArea straightArea = this.areas.get(i);
        int i4 = i2;
        while (true) {
            i3 = 0;
            if (i4 <= 1) {
                break;
            }
            straightArea = addLine(straightArea, direction, ((float) (i4 - 1)) / ((float) i4)).get(0);
            i4--;
        }
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.type = 3;
        step.part = i2;
        step.numOfLine = i2 - 1;
        step.position = i;
        if (direction != TART_Line.Direction.HORIZONTAL) {
            i3 = 1;
        }
        step.direction = i3;
        this.steps.add(step);
    }


    public void addCross(int i, float f) {
        addCross(i, f, f);
    }


    public void addCross(int i, float f, float f2) {
        TART_StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        TART_StraightLine createLine = TART_StraightUtils.createLine(straightArea, TART_Line.Direction.HORIZONTAL, f);
        TART_StraightLine createLine2 = TART_StraightUtils.createLine(straightArea, TART_Line.Direction.VERTICAL, f2);
        this.lines.add(createLine);
        this.lines.add(createLine2);
        this.areas.addAll(TART_StraightUtils.cutAreaCross(straightArea, createLine, createLine2));
        updateLineLimit();
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.hRatio = f;
        step.vRatio = f2;
        step.type = 1;
        step.position = i;
        step.numOfLine = 2;
        this.steps.add(step);
    }


    public void cutAreaEqualPart(int i, int i2, int i3) {
        TART_StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        Pair<List<TART_StraightLine>, List<TART_StraightArea>> cutArea = TART_StraightUtils.cutArea(straightArea, i2, i3);
        List list = (List) cutArea.first;
        this.lines.addAll(list);
        this.areas.addAll((List) cutArea.second);
        updateLineLimit();
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.type = 2;
        step.position = i;
        step.numOfLine = list.size();
        step.hSize = i2;
        step.vSize = i3;
        this.steps.add(step);
    }


    public void cutSpiral(int i) {
        TART_StraightArea straightArea = this.areas.get(i);
        this.areas.remove(straightArea);
        Pair<List<TART_StraightLine>, List<TART_StraightArea>> cutAreaSpiral = TART_StraightUtils.cutAreaSpiral(straightArea);
        this.lines.addAll((Collection) cutAreaSpiral.first);
        this.areas.addAll((Collection) cutAreaSpiral.second);
        updateLineLimit();
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.numOfLine = this.lines.size();
        step.type = 4;
        step.position = i;
        this.steps.add(step);
    }

    public void sortAreas() {
        Collections.sort(this.areas, this.areaComparator);
    }

    private void updateLineLimit() {
        for (int i = 0; i < this.lines.size(); i++) {
            TART_Line line = this.lines.get(i);
            updateUpperLine(line);
            updateLowerLine(line);
        }
    }

    private void updateLowerLine(TART_Line line) {
        for (int i = 0; i < this.lines.size(); i++) {
            TART_Line line2 = this.lines.get(i);
            if (line2 != line && line2.direction() == line.direction()) {
                if (line2.direction() == TART_Line.Direction.HORIZONTAL) {
                    if (line2.maxX() > line.minX() && line.maxX() > line2.minX() && line2.minY() > line.lowerLine().maxY() && line2.maxY() < line.minY()) {
                        line.setLowerLine(line2);
                    }
                } else if (line2.maxY() > line.minY() && line.maxY() > line2.minY() && line2.minX() > line.lowerLine().maxX() && line2.maxX() < line.minX()) {
                    line.setLowerLine(line2);
                }
            }
        }
    }

    private void updateUpperLine(TART_Line line) {
        for (int i = 0; i < this.lines.size(); i++) {
            TART_Line line2 = this.lines.get(i);
            if (line2 != line && line2.direction() == line.direction()) {
                if (line2.direction() == TART_Line.Direction.HORIZONTAL) {
                    if (line2.maxX() > line.minX() && line.maxX() > line2.minX() && line2.maxY() < line.upperLine().minY() && line2.minY() > line.maxY()) {
                        line.setUpperLine(line2);
                    }
                } else if (line2.maxY() > line.minY() && line.maxY() > line2.minY() && line2.maxX() < line.upperLine().minX() && line2.minX() > line.maxX()) {
                    line.setUpperLine(line2);
                }
            }
        }
    }

    public float getRadian() {
        return this.radian;
    }

    public void setRadian(float f) {
        this.radian = f;
        for (TART_StraightArea radian2 : this.areas) {
            radian2.setRadian(f);
        }
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public TART_PuzzleLayout.Info generateInfo() {
        TART_PuzzleLayout.Info info = new TART_PuzzleLayout.Info();
        info.type = 0;
        info.padding = this.padding;
        info.radian = this.radian;
        info.color = this.color;
        info.steps = this.steps;
        ArrayList<TART_PuzzleLayout.LineInfo> arrayList = new ArrayList<>();
        for (TART_Line lineInfo : this.lines) {
            arrayList.add(new TART_PuzzleLayout.LineInfo(lineInfo));
        }
        info.lineInfos = arrayList;
        info.lines = new ArrayList<>(this.lines);
        info.left = this.bounds.left;
        info.top = this.bounds.top;
        info.right = this.bounds.right;
        info.bottom = this.bounds.bottom;
        return info;
    }
}
