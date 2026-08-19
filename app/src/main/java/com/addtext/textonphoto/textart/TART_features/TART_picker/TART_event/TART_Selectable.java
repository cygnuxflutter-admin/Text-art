package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_event;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_Photo;

public interface TART_Selectable {

    int getSelectedItemCount();

    boolean isSelected(TART_Photo photo);

}
