package com.addtext.textonphoto.textart.TART_utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleView;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TART_FileUtils {

    public static boolean deleteImageFile(Context context, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        File file = new File(filePath);
        boolean success = false;

        // 1. Direct file deletion attempt
        try {
            if (file.exists()) {
                success = file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Query and delete from MediaStore Images & Files tables
        if (context != null) {
            ContentResolver resolver = context.getContentResolver();
            Uri[] baseUris = new Uri[]{
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.getContentUri("external_primary"),
                    MediaStore.Files.getContentUri("external")
            };

            String fileName = file.getName();
            String nameWithoutExt = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;

            for (Uri baseUri : baseUris) {
                try {
                    // Try delete by DATA directly
                    resolver.delete(baseUri, MediaStore.Images.Media.DATA + "=?", new String[]{filePath});

                    // Query by DATA
                    Cursor cursor = resolver.query(baseUri, new String[]{MediaStore.MediaColumns._ID}, MediaStore.MediaColumns.DATA + "=?", new String[]{filePath}, null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                            Uri itemUri = ContentUris.withAppendedId(baseUri, id);
                            try {
                                resolver.delete(itemUri, null, null);
                                success = true;
                            } catch (Exception ignored) {}
                        }
                        cursor.close();
                    }

                    // Query by DISPLAY_NAME (exact and without extension)
                    Cursor cursorName = resolver.query(baseUri, new String[]{MediaStore.MediaColumns._ID},
                            MediaStore.MediaColumns.DISPLAY_NAME + "=? OR " + MediaStore.MediaColumns.DISPLAY_NAME + "=?",
                            new String[]{fileName, nameWithoutExt}, null);
                    if (cursorName != null) {
                        while (cursorName.moveToNext()) {
                            long id = cursorName.getLong(cursorName.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                            Uri itemUri = ContentUris.withAppendedId(baseUri, id);
                            try {
                                resolver.delete(itemUri, null, null);
                                success = true;
                            } catch (Exception ignored) {}
                        }
                        cursorName.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 3. Try truncate and delete if file still exists on disk
        try {
            if (file.exists()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(new byte[0]);
                    fos.flush();
                } catch (Exception ignored) {}
                if (file.delete()) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Force MediaScanner scan and cleanup
        try {
            MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{filePath}, null, (path, uri) -> {
                if (uri != null && context != null) {
                    try {
                        context.getContentResolver().delete(uri, null, null);
                    } catch (Exception ignored) {}
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success || !file.exists();
    }

    public static Uri getUriFromFilePath(Context context, String filePath) {
        if (context == null || filePath == null) return null;
        ContentResolver resolver = context.getContentResolver();
        Uri[] baseUris = new Uri[]{MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Files.getContentUri("external")};
        for (Uri baseUri : baseUris) {
            try {
                Cursor cursor = resolver.query(baseUri, new String[]{MediaStore.MediaColumns._ID}, MediaStore.MediaColumns.DATA + "=?", new String[]{filePath}, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                        cursor.close();
                        return ContentUris.withAppendedId(baseUri, id);
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static File saveBitmapAsFile(Activity activity , Bitmap bitmap) {
        FileOutputStream fileOutputStream;
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String file = activity.getCacheDir().getAbsolutePath();//Environment.getExternalStorageDirectory().toString();
        File file2 = new File(file + "/TextOnPhoto");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        try {
            File file3 = new File(file + "/TextOnPhoto/" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".jpg");
            file3.createNewFile();
            fileOutputStream = new FileOutputStream(file3);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file3;
        } catch (Exception e2) {

            fileOutputStream = null;
            e2.printStackTrace();
            if (fileOutputStream != null) {
            }
            return null;
        }
    }





    public static Bitmap createBitmap(TART_PuzzleView puzzleView, int i) {
        puzzleView.clearHandling();
        puzzleView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(i, (int) (((float) i) / (((float) puzzleView.getWidth()) / ((float) puzzleView.getHeight()))), Bitmap.Config.ARGB_8888);
        puzzleView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap createBitmap(TART_PuzzleView puzzleView) {
        puzzleView.clearHandling();
        puzzleView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(puzzleView.getWidth(), puzzleView.getHeight(), Bitmap.Config.ARGB_8888);
        puzzleView.draw(new Canvas(createBitmap));
        return createBitmap;
    }


}
