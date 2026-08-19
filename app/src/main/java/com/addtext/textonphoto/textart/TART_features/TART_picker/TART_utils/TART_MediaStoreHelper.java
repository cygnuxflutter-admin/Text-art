package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_PhotoPicker;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_PhotoDirectory;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TART_MediaStoreHelper {

    public interface PhotosResultCallback {
        void onResultCallback(List<TART_PhotoDirectory> list);
    }

    public static void getPhotoDirs(FragmentActivity fragmentActivity, Bundle bundle, PhotosResultCallback photosResultCallback) {
        //TODO getSupportLoaderManager
//        fragmentActivity.getSupportLoaderManager().initLoader(0, bundle, new PhotoDirLoaderCallbacks(fragmentActivity, photosResultCallback));
        LoaderManager.getInstance(fragmentActivity).initLoader(0, bundle, new PhotoDirLoaderCallbacks(fragmentActivity, photosResultCallback));
    }

    private static class PhotoDirLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
        private Context context;
        private PhotosResultCallback resultCallback;

        public void onLoaderReset(Loader<Cursor> loader) {
        }

        public PhotoDirLoaderCallbacks(Context context2, PhotosResultCallback photosResultCallback) {
            this.context = context2;
            this.resultCallback = photosResultCallback;
        }

        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new TART_PhotoDirectoryLoader(this.context, bundle.getBoolean(TART_PhotoPicker.EXTRA_SHOW_GIF, false));
        }

        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (cursor != null) {
                ArrayList arrayList = new ArrayList();
                TART_PhotoDirectory photoDirectory = new TART_PhotoDirectory();
                photoDirectory.setName(this.context.getString(R.string.all_image));
                photoDirectory.setId("ALL");
                if (cursor.moveToFirst()) {
                    do {
                        int i = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("bucket_id"));
                        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("bucket_display_name"));
                        String string3 = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (((long) cursor.getInt(cursor.getColumnIndexOrThrow("_size"))) >= 1) {
                            TART_PhotoDirectory photoDirectory2 = new TART_PhotoDirectory();
                            photoDirectory2.setId(string);
                            photoDirectory2.setName(string2);
                            if (!arrayList.contains(photoDirectory2)) {
                                photoDirectory2.setCoverPath(string3);
                                photoDirectory2.addPhoto(i, string3);
                                photoDirectory2.setDateAdded(cursor.getLong(cursor.getColumnIndexOrThrow("date_added")));
                                arrayList.add(photoDirectory2);
                            } else {
                                ((TART_PhotoDirectory) arrayList.get(arrayList.indexOf(photoDirectory2))).addPhoto(i, string3);
                            }
                            photoDirectory.addPhoto(i, string3);
                        }
                    } while (cursor.moveToNext());
                }
                if (photoDirectory.getPhotoPaths().size() > 0) {
                    photoDirectory.setCoverPath(photoDirectory.getPhotoPaths().get(0));
                }
                Collections.sort(arrayList);
                arrayList.add(0, photoDirectory);
                if (this.resultCallback != null) {
                    this.resultCallback.onResultCallback(arrayList);
                }
            }
        }
    }
}
