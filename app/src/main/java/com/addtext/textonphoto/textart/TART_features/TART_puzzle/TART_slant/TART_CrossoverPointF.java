package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_slant;

import android.graphics.PointF;

class TART_CrossoverPointF extends PointF {
    TART_SlantLine horizontal;
    TART_SlantLine vertical;

    TART_CrossoverPointF() {
    }

    TART_CrossoverPointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    TART_CrossoverPointF(TART_SlantLine slantLine, TART_SlantLine slantLine2) {
        this.horizontal = slantLine;
        this.vertical = slantLine2;
    }


}
