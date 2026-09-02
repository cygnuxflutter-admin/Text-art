package com.addtext.textonphoto.textart.TART_utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.addtext.textonphoto.textart.BuildConfig;
import com.addtext.textonphoto.textart.R;

public class RateButtonDialog {

    private Context context;
    private String title;
    private String messages;
    private Drawable icon;
    private onRatingDialogListener listener;
    AlertDialog dialog;
    Boolean submitRate;
    private TART_PreferenceClass preferenceClass;

    private Uri image;



    public RateButtonDialog(Context context, String title, String messages, onRatingDialogListener listener,Uri image) {
        this.context = context;
        this.title = title;
        this.messages = messages;
        this.listener = listener;
        // this.submitRate = submitRate;
        this.image = image;
    }


    public interface onRatingDialogListener {
        void onRatingSelected(float rating);

        void onDialogCancelled();
    }

    public void Show() {
        preferenceClass = new TART_PreferenceClass(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_rating, null);
        builder.setView(dialogView);
        ImageView iconImageView = dialogView.findViewById(R.id.dialog_rating_icon);
        TextView messageTextView = dialogView.findViewById(R.id.dialog_rating_title);
        RatingBar ratingBar = dialogView.findViewById(R.id.dialog_rating_rating_bar);
        Button btn_ok = dialogView.findViewById(R.id.dialog_rating_button_positive);
        Button btn_cancel = dialogView.findViewById(R.id.dialog_rating_button_negative);
        iconImageView.setImageDrawable(icon);
        messageTextView.setText(messages);

        btn_ok.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          float rate = ratingBar.getRating(); //getRate(); // Assuming you have a method to retrieve the user's rating

                                          if (rate > 3) {
                                              preferenceClass.setRateSubmited("rateSubmitted", true);
                                              submitRateToPlayStore(rate);
                                              if (listener != null) listener.onRatingSelected(rate);
                                          } else {
                                              showThankYouDialog();
                                              if (listener != null) listener.onRatingSelected(rate);
                                              dialog.dismiss();
                                          }
                                          dialog.dismiss();
                                      }
                                  }

        );

        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              if (dialog != null)
                                                  dialog.dismiss();
                                              if (listener != null) listener.onDialogCancelled();
                                              submitRate = false;
                                          }
                                      }

        );


        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }

    private void submitRateToPlayStore(float rate) {
        // TODO: Implement the logic to submit the rate to the Play Store
        try {
            // Assuming you have a way to launch the Play Store page for rating
            String playStoreUrl = "market://details?id=" + BuildConfig.APPLICATION_ID; // Replace with your app's package name
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl));
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void showThankYouDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thank You")
                .setMessage("Thank you for your feedback!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Show popup indicating rate has not been submitted
    private void showRateNotSubmittedPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rate Not Submitted")
                .setMessage("You have not submitted your rate yet.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}





