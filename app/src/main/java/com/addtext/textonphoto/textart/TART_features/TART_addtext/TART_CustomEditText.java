package com.addtext.textonphoto.textart.TART_features.TART_addtext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

public class TART_CustomEditText extends AppCompatEditText {
    private TART_TextEditorDialogFragment dialogFragment;

    public TART_CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setDialogFragment(TART_TextEditorDialogFragment textEditorDialogFragment) {
        this.dialogFragment = textEditorDialogFragment;
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4) {
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(), 0);
            this.dialogFragment.dismissAndShowSticker();
        }
        return false;
    }
}
