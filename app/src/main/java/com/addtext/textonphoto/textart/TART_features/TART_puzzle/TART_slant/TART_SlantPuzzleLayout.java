package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant;

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

public abstract class TART_SlantPuzzleLayout implements TART_PuzzleLayout {
    private Comparator<TART_SlantArea> areaComparator = new TART_SlantArea.AreaComparator();
    private List<TART_SlantArea> areas = new ArrayList();
    private RectF bounds;
    private int color = -1;
    private List<TART_Line> lines = new ArrayList();
    private TART_SlantArea outerArea;
    private List<TART_Line> outerLines = new ArrayList(4);
    private float padding;
    private float radian;
    private ArrayList<TART_PuzzleLayout.Step> steps = new ArrayList<>();

    public abstract void layout();

    protected TART_SlantPuzzleLayout() {
    }

    protected TART_SlantPuzzleLayout(TART_SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        this.bounds = slantPuzzleLayout.getBounds();
        this.outerArea = (TART_SlantArea) slantPuzzleLayout.getOuterArea();
        this.areas = slantPuzzleLayout.getAreas();
        this.lines = slantPuzzleLayout.getLines();
        this.outerLines = slantPuzzleLayout.getOuterLines();
        this.padding = slantPuzzleLayout.getPadding();
        this.radian = slantPuzzleLayout.getRadian();
        this.color = slantPuzzleLayout.getColor();
        this.areaComparator = slantPuzzleLayout.getAreaComparator();
        this.steps = slantPuzzleLayout.getSteps();
    }

    public RectF getBounds() {
        return this.bounds;
    }


    public List<TART_SlantArea> getAreas() {
        return this.areas;
    }


    public Comparator<TART_SlantArea> getAreaComparator() {
        return this.areaComparator;
    }


    public ArrayList<TART_PuzzleLayout.Step> getSteps() {
        return this.steps;
    }


    public void setOuterBounds(RectF rectF) {
        reset();
        this.bounds = rectF;
        TART_CrossoverPointF crossoverPointF = new TART_CrossoverPointF(rectF.left, rectF.top);
        TART_CrossoverPointF crossoverPointF2 = new TART_CrossoverPointF(rectF.right, rectF.top);
        TART_CrossoverPointF crossoverPointF3 = new TART_CrossoverPointF(rectF.left, rectF.bottom);
        TART_CrossoverPointF crossoverPointF4 = new TART_CrossoverPointF(rectF.right, rectF.bottom);
        TART_SlantLine slantLine = new TART_SlantLine(crossoverPointF, crossoverPointF3, TART_Line.Direction.VERTICAL);
        TART_SlantLine slantLine2 = new TART_SlantLine(crossoverPointF, crossoverPointF2, TART_Line.Direction.HORIZONTAL);
        TART_SlantLine slantLine3 = new TART_SlantLine(crossoverPointF2, crossoverPointF4, TART_Line.Direction.VERTICAL);
        TART_SlantLine slantLine4 = new TART_SlantLine(crossoverPointF3, crossoverPointF4, TART_Line.Direction.HORIZONTAL);
        this.outerLines.clear();
        this.outerLines.add(slantLine);
        this.outerLines.add(slantLine2);
        this.outerLines.add(slantLine3);
        this.outerLines.add(slantLine4);
        this.outerArea = new TART_SlantArea();
        this.outerArea.lineLeft = slantLine;
        this.outerArea.lineTop = slantLine2;
        this.outerArea.lineRight = slantLine3;
        this.outerArea.lineBottom = slantLine4;
        this.outerArea.updateCornerPoints();
        this.areas.clear();
        this.areas.add(this.outerArea);
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
            if (line2.direction() == line.direction() && line2.attachStartLine() == line.attachStartLine() && line2.attachEndLine() == line.attachEndLine()) {
                if (line2.direction() == TART_Line.Direction.HORIZONTAL) {
                    if (line2.minY() > line.lowerLine().maxY() && line2.maxY() < line.minY()) {
                        line.setLowerLine(line2);
                    }
                } else if (line2.minX() > line.lowerLine().maxX() && line2.maxX() < line.minX()) {
                    line.setLowerLine(line2);
                }
            }
        }
    }

    private void updateUpperLine(TART_Line line) {
        for (int i = 0; i < this.lines.size(); i++) {
            TART_Line line2 = this.lines.get(i);
            if (line2.direction() == line.direction() && line2.attachStartLine() == line.attachStartLine() && line2.attachEndLine() == line.attachEndLine()) {
                if (line2.direction() == TART_Line.Direction.HORIZONTAL) {
                    if (line2.maxY() < line.upperLine().minY() && line2.minY() > line.maxY()) {
                        line.setUpperLine(line2);
                    }
                } else if (line2.maxX() < line.upperLine().minX() && line2.minX() > line.maxX()) {
                    line.setUpperLine(line2);
                }
            }
        }
    }

    public int getAreaCount() {
        return this.areas.size();
    }

    public void reset() {
        this.lines.clear();
        this.areas.clear();
        this.areas.add(this.outerArea);
        this.steps.clear();
    }

    public void update() {
        for (int i = 0; i < this.lines.size(); i++) {
            this.lines.get(i).update(width(), height());
        }
        for (int i2 = 0; i2 < this.areas.size(); i2++) {
            this.areas.get(i2).updateCornerPoints();
        }
    }

    public void sortAreas() {
        Collections.sort(this.areas, this.areaComparator);
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

    public List<TART_Line> getOuterLines() {
        return this.outerLines;
    }

    public TART_Area getOuterArea() {
        return this.outerArea;
    }

    public TART_SlantArea getArea(int i) {
        sortAreas();
        return this.areas.get(i);
    }

    public List<TART_Line> getLines() {
        return this.lines;
    }

    public void setPadding(float f) {
        this.padding = f;
        for (TART_SlantArea padding2 : this.areas) {
            padding2.setPadding(f);
        }
        this.outerArea.lineLeft.startPoint().set(this.bounds.left + f, this.bounds.top + f);
        this.outerArea.lineLeft.endPoint().set(this.bounds.left + f, this.bounds.bottom - f);
        this.outerArea.lineRight.startPoint().set(this.bounds.right - f, this.bounds.top + f);
        this.outerArea.lineRight.endPoint().set(this.bounds.right - f, this.bounds.bottom - f);
        this.outerArea.updateCornerPoints();
        update();
    }

    public float getPadding() {
        return this.padding;
    }

    public float getRadian() {
        return this.radian;
    }

    public void setRadian(float f) {
        this.radian = f;
        for (TART_SlantArea radian2 : this.areas) {
            radian2.setRadian(f);
        }
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }


    public List<TART_SlantArea> addLine(int i, TART_Line.Direction direction, float f) {
        return addLine(i, direction, f, f);
    }


    public List<TART_SlantArea> addLine(int i, TART_Line.Direction direction, float f, float f2) {
        TART_SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        TART_SlantLine createLine = TART_SlantUtils.createLine(slantArea, direction, f, f2);
        this.lines.add(createLine);
        List<TART_SlantArea> cutAreaWith = TART_SlantUtils.cutAreaWith(slantArea, createLine);
        this.areas.addAll(cutAreaWith);
        updateLineLimit();
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        int i2 = 0;
        step.type = 0;
        if (direction != TART_Line.Direction.HORIZONTAL) {
            i2 = 1;
        }
        step.direction = i2;
        step.position = i;
        this.steps.add(step);
        return cutAreaWith;
    }


    public void addCross(int i, float f, float f2, float f3, float f4) {
        TART_SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        TART_SlantLine createLine = TART_SlantUtils.createLine(slantArea, TART_Line.Direction.HORIZONTAL, f, f2);
        TART_SlantLine createLine2 = TART_SlantUtils.createLine(slantArea, TART_Line.Direction.VERTICAL, f3, f4);
        this.lines.add(createLine);
        this.lines.add(createLine2);
        this.areas.addAll(TART_SlantUtils.cutAreaCross(slantArea, createLine, createLine2));
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.type = 1;
        step.position = i;
        this.steps.add(step);
    }


    public void cutArea(int i, int i2, int i3) {
        TART_SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        Pair<List<TART_SlantLine>, List<TART_SlantArea>> cutAreaWith = TART_SlantUtils.cutAreaWith(slantArea, i2, i3);
        this.lines.addAll((Collection) cutAreaWith.first);
        this.areas.addAll((Collection) cutAreaWith.second);
        updateLineLimit();
        sortAreas();
        TART_PuzzleLayout.Step step = new TART_PuzzleLayout.Step();
        step.type = 2;
        step.position = i;
        step.hSize = i2;
        step.vSize = i3;
        this.steps.add(step);
    }

    public TART_PuzzleLayout.Info generateInfo() {
        TART_PuzzleLayout.Info info = new TART_PuzzleLayout.Info();
        info.type = 1;
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
