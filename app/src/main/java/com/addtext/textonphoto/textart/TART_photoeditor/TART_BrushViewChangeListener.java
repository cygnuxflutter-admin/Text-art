package com.addtext.textonphoto.textart.TART_photoeditor;

interface TART_BrushViewChangeListener {
    void onStartDrawing();

    void onStopDrawing();

    void onViewAdd(TART_BrushDrawingView brushDrawingView);

    void onViewRemoved(TART_BrushDrawingView brushDrawingView);
}
