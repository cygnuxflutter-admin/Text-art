package com.addtext.textonphoto.textart.TART_utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleView;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TART_FileUtils {

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
