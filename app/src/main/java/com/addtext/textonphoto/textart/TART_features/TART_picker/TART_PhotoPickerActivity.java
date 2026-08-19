package com.addtext.textonphoto.textart.TART_features.TART_picker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.addtext.textonphoto.textart.TART_screens.TART_EditImageActivity;
import com.addtext.textonphoto.textart.TART_screens.TART_PuzzleViewActivity;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_Photo;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_event.TART_OnItemCheckListener;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_fragment.TART_ImagePagerFragment;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_fragment.TART_PhotoPickerFragment;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;

public class TART_PhotoPickerActivity extends AppCompatActivity {
    private boolean forwardMain;
    private TART_ImagePagerFragment imagePagerFragment;
    private int maxCount = 9;
    private ArrayList<String> originalPhotos = null;
    private TART_PhotoPickerFragment pickerFragment;
    private boolean showGif = false;

    public TART_PhotoPickerActivity getActivity() {
        return this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        boolean booleanExtra = getIntent().getBooleanExtra(TART_PhotoPicker.EXTRA_SHOW_CAMERA, true);
        boolean booleanExtra2 = getIntent().getBooleanExtra(TART_PhotoPicker.EXTRA_SHOW_GIF, false);
        boolean booleanExtra3 = getIntent().getBooleanExtra(TART_PhotoPicker.EXTRA_PREVIEW_ENABLED, true);
        this.forwardMain = getIntent().getBooleanExtra(TART_PhotoPicker.MAIN_ACTIVITY, false);
        setShowGif(booleanExtra2);
        setContentView(R.layout.knack___picker_activity_photo_picker);
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(getResources().getString(R.string.tap_to_select));
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            supportActionBar.setElevation(25.0f);
        }
        this.maxCount = getIntent().getIntExtra(TART_PhotoPicker.EXTRA_MAX_COUNT, 9);
        int intExtra = getIntent().getIntExtra(TART_PhotoPicker.EXTRA_GRID_COLUMN, 3);
        this.originalPhotos = getIntent().getStringArrayListExtra(TART_PhotoPicker.EXTRA_ORIGINAL_PHOTOS);
        this.pickerFragment = (TART_PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (this.pickerFragment == null) {
            this.pickerFragment = TART_PhotoPickerFragment.newInstance(booleanExtra, booleanExtra2, booleanExtra3, intExtra, this.maxCount, this.originalPhotos);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.pickerFragment, "tag").commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        this.pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new TART_OnItemCheckListener() {
            public final boolean onItemCheck(int i, TART_Photo photo, int i2) {
                if (!forwardMain) {
                    Intent intent = new Intent(TART_PhotoPickerActivity.this, TART_EditImageActivity.class);
                    intent.putExtra(TART_PhotoPicker.KEY_SELECTED_PHOTOS, photo.getPath());
                    startActivity(intent);
                    return true;
                }
                TART_PuzzleViewActivity.getInstance().replaceCurrentPiece(photo.getPath());
                finish();
                return true;
            }
        });
    }


    public void onBackPressed() {
        if (this.imagePagerFragment == null || !this.imagePagerFragment.isVisible()) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void setShowGif(boolean z) {
        this.showGif = z;
    }
}
