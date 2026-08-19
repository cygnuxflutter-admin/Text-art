package com.addtext.textonphoto.textart.TART_features.TART_picker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_fragment.TART_ImagePagerFragment;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;

public class TART_PhotoPagerActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TART_ImagePagerFragment pagerFragment;
    private boolean showDelete;

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.knack___picker_activity_photo_pager);
        int intExtra = getIntent().getIntExtra(TART_PhotoPreview.EXTRA_CURRENT_ITEM, 0);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(TART_PhotoPreview.EXTRA_PHOTOS);
        this.showDelete = getIntent().getBooleanExtra(TART_PhotoPreview.EXTRA_SHOW_DELETE, true);
        if (this.pagerFragment == null) {
            this.pagerFragment = (TART_ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        this.pagerFragment.setPhotos(stringArrayListExtra, intExtra);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.actionBar = getSupportActionBar();
        if (this.actionBar != null) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                this.actionBar.setElevation(25.0f);
            }
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, this.pagerFragment.getPaths());
        setResult(-1, intent);
        finish();
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
