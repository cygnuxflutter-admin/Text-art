package com.addtext.textonphoto.textart.TART_screens;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.addtext.textonphoto.textart.R;

public class TART_NoInternetActivity extends AppCompatActivity {
    public static boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knack_activity_no_internet);
    }

    private android.content.BroadcastReceiver networkReceiver = new android.content.BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("com.addtext.textonphoto.textart.NETWORK_RESTORED".equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(networkReceiver, new android.content.IntentFilter("com.addtext.textonphoto.textart.NETWORK_RESTORED"), android.content.Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(networkReceiver, new android.content.IntentFilter("com.addtext.textonphoto.textart.NETWORK_RESTORED"));
        }
        if (com.addtext.textonphoto.textart.TART_utils.TART_NetworkUtils.isNetworkAvailable(this)) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
        try {
            unregisterReceiver(networkReceiver);
        } catch (Exception e) {}
    }

    @Override
    public void onBackPressed() {
        // Do not allow going back if there is no internet
    }
}
