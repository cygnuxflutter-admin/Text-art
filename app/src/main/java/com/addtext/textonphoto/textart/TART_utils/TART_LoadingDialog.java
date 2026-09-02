package com.addtext.textonphoto.textart.TART_utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_views.TART_DotsLoaderView;

public class TART_LoadingDialog {
    private Dialog dialog;
    private TART_DotsLoaderView dotsLoader;

    public TART_LoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.knack_dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0.3f);
        }
        dotsLoader = dialog.findViewById(R.id.dotsLoader);
    }

    public void show() {
        try {
            if (dotsLoader != null) {
                dotsLoader.startAnimation();
            }
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void dismiss() {
        if (dotsLoader != null) {
            dotsLoader.stopAnimation();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
