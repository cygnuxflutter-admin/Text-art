package com.addtext.textonphoto.textart.TART_imagechanger;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;


public interface TART_OnPhotoEditorListener {
    void onAddViewListener(TART_ViewType viewType, int i);

    void onAdded(TART_StrokeTextView strokeTextView, TART_RoundFrameLayout roundFrameLayout);

    void onClickGetBitmaoOverlay(Bitmap bitmap);

    void onClickGetEditTextChangeListener(TART_StrokeTextView strokeTextView, TART_RoundFrameLayout roundFrameLayout);

    void onClickGetGraphicViewListener(ImageView imageView, View view, View view2);

    void onClickGetImageViewListener(ImageView imageView, View view);

    void onEditTextChangeListener(View view, String str, int i);

    void onRemoveViewListener(int i);

    void onRemoveViewListener(TART_ViewType viewType, int i);

    void onStartViewChangeListener(TART_ViewType viewType);

    void onStopViewChangeListener(TART_ViewType viewType);
}
