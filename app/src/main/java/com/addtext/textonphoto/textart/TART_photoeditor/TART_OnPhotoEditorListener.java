package com.addtext.textonphoto.textart.TART_photoeditor;

public interface TART_OnPhotoEditorListener {
    void onAddViewListener(TART_ViewType viewType, int i);


    void onRemoveViewListener(int i);

    void onRemoveViewListener(TART_ViewType viewType, int i);

    void onStartViewChangeListener(TART_ViewType viewType);

    void onStopViewChangeListener(TART_ViewType viewType);
}
