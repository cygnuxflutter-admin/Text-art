 package com.addtext.textonphoto.textart.imagepicker;


 import android.annotation.SuppressLint;
 import android.app.Activity;
 import android.app.Dialog;
 import android.content.Context;
 import android.content.Intent;
 import android.graphics.Color;
 import android.net.Uri;
 import android.view.View;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import com.addtext.textonphoto.textart.BuildConfig;
 import com.addtext.textonphoto.textart.R;
 import com.afollestad.materialdialogs.MaterialDialog;


 import java.util.Objects;

 public class MaterialDialogUtils {

     private MaterialDialogUtils() {
     }

     public static MaterialDialogUtils getInstance() {
         return SingletonHolder.INSTANCE;
     }
//
//     public MaterialDialog createAnimationDialog(Context activity) {
//         return new MaterialDialog.Builder(activity)
//                 .customView(R.layout.lottie_anim_dialog, false)
//                 .contentColor(Color.TRANSPARENT)
//                 .backgroundColor(Color.TRANSPARENT)
//                 .build();
//     }

     private static class SingletonHolder {
         static final MaterialDialogUtils INSTANCE = new MaterialDialogUtils();
     }

     public void PermissionDialog(Activity activity) {

         @SuppressLint("ResourceType")
         Dialog materialDialog = new Dialog(activity, 16974126);
         materialDialog.requestWindowFeature(1);
         materialDialog.setContentView(R.layout.permission_dialog);
         materialDialog.setCancelable(false);

         materialDialog.show();

         TextView btn_cancel = materialDialog.findViewById(R.id.btn_cancel);
         TextView btn_settings = materialDialog.findViewById(R.id.btn_settings);

         btn_cancel.setOnClickListener(v -> materialDialog.dismiss());

         btn_settings.setOnClickListener(v -> {
             materialDialog.dismiss();
             activity.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
         });
     }


 }
