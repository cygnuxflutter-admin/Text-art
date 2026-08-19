package com.addtext.textonphoto.textart.TART_sticker.TART_event;

import android.view.MotionEvent;

import com.addtext.textonphoto.textart.TART_sticker.TART_StickerView;

public interface TART_StickerIconEvent {
    void onActionDown(TART_StickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionMove(TART_StickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionUp(TART_StickerView paramStickerView, MotionEvent paramMotionEvent);
}
