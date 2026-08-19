package com.addtext.textonphoto.textart.TART_sticker.TART_event;

import android.view.MotionEvent;

import com.addtext.textonphoto.textart.TART_sticker.TART_StickerView;

public abstract class AbstractFlipEvent implements TART_StickerIconEvent {
    protected abstract int getFlipDirection();

    public void onActionDown(TART_StickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionMove(TART_StickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionUp(TART_StickerView paramStickerView, MotionEvent paramMotionEvent) {
        paramStickerView.flipCurrentSticker(getFlipDirection());
    }
}
