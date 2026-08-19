package com.addtext.textonphoto.textart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Base64InputStream;
import android.util.Log;
import androidx.core.app.NotificationCompat;
//import androidx.media2.exoplayer.external.metadata.icy.IcyHeaders;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Category;
import com.addtext.textonphoto.textart.TART_supermodel.TART_quotes.TART_Quotes;

/* loaded from: classes2.dex */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "tpquotes.sqlite";
    private static String DB_NAME_CRYPT = "tpquotes.crypt";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;
    public static final String KEY_CATEGORY = "name";
    public static final String KEY_ID = "_id";
    private static String TAG = "DataBaseHelper";
    private String TABLE_NAME_CATEGORY;
    private String TABLE_NAME_QUOTES;
    private final Context mContext;
    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate;

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.TABLE_NAME_QUOTES = "quotes";
        this.TABLE_NAME_CATEGORY = "category";
        this.mNeedUpdate = false;
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
            String str = TAG;
            Log.d(str, "" + DB_PATH);
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
            String str2 = TAG;
            Log.d(str2, "" + DB_PATH);
        }
        this.mContext = context;
        copyDataBase();
        getWritableDatabase();
    }

    private void copyDataBase() {
        if (checkDataBase()) {
            return;
        }
        getWritableDatabase();
        close();
        try {
            Log.d(TAG, "DATABASE does not EXISTS");
            copyDBFile();
        } catch (IOException unused) {
            throw new Error("ErrorCopyingDataBase");
        }
    }

    private void copyDBFile() throws IOException {
        InputStream open = this.mContext.getAssets().open(DB_NAME_CRYPT);
        FileOutputStream fileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
        try {
            Base64InputStream base64InputStream = new Base64InputStream(open, 0);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = base64InputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                Log.d("copyDBFile", "copy");
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        open.close();
    }

    private boolean checkDataBase() {
        return new File(DB_PATH + DB_NAME).exists();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 > i) {
            this.mNeedUpdate = true;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mDataBase != null) {
            this.mDataBase.close();
        }
        super.close();
    }

    public boolean openDataBase() throws SQLException {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 268435456);
        this.mDataBase = openDatabase;
        return openDatabase != null;
    }

    public void updateDataBase() throws IOException {
        if (this.mNeedUpdate) {
            File file = new File(DB_PATH + DB_NAME);
            if (file.exists()) {
                file.delete();
            }
            copyDataBase();
            this.mNeedUpdate = false;
        }
    }

    public ArrayList<TART_Quotes> getQuotes(String str) {
        Cursor query = getReadableDatabase().query(this.TABLE_NAME_QUOTES, null, "category_id = ?", new String[]{str}, null, null, "_id DESC");
        ArrayList<TART_Quotes> arrayList = new ArrayList<>();
        if (query.moveToFirst()) {
            do {
                TART_Quotes quotes2 = new TART_Quotes();
                quotes2.setId(query.getString(query.getColumnIndex(KEY_ID)));
                quotes2.setCategory_id(query.getString(query.getColumnIndex("category_id")));
                quotes2.setQuote(query.getString(query.getColumnIndex("quote")));
                quotes2.setLiked(query.getString(query.getColumnIndex("liked")));
                quotes2.setUtp(query.getString(query.getColumnIndex("utp")));
                arrayList.add(quotes2);
            } while (query.moveToNext());
            query.close();
            return arrayList;
        }
        query.close();
        return arrayList;
    }

    public ArrayList<TART_Quotes> getFavouriteList() {
        Cursor query = getReadableDatabase().query(this.TABLE_NAME_QUOTES, null, "liked = ?", new String[]{"1"}, null, null, null);
        ArrayList<TART_Quotes> arrayList = new ArrayList<>();
        if (query.moveToFirst()) {
            do {
                TART_Quotes quotes2 = new TART_Quotes();
                quotes2.setId(query.getString(query.getColumnIndex(KEY_ID)));
                quotes2.setCategory_id(query.getString(query.getColumnIndex("category_id")));
                quotes2.setQuote(query.getString(query.getColumnIndex("quote")));
                quotes2.setLiked(query.getString(query.getColumnIndex("liked")));
                quotes2.setUtp(query.getString(query.getColumnIndex("utp")));
                arrayList.add(quotes2);
            } while (query.moveToNext());
            query.close();
            return arrayList;
        }
        query.close();
        return arrayList;
    }

    public boolean updateLiked(String str, int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("liked", Integer.valueOf(i));
            writableDatabase.update(this.TABLE_NAME_QUOTES, contentValues, "_id = ?", new String[]{String.valueOf(str)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<TART_Category> getCategory() {
        Cursor query = getReadableDatabase().query(this.TABLE_NAME_CATEGORY, null, null, null, null, null, "name ASC");
        ArrayList<TART_Category> arrayList = new ArrayList<>();
        if (query.moveToFirst()) {
            do {
                TART_Category category = new TART_Category();
                category.setId(query.getString(query.getColumnIndex(KEY_ID)));
                category.setName(query.getString(query.getColumnIndex("name")));
                category.setStatus(query.getString(query.getColumnIndex(NotificationCompat.CATEGORY_STATUS)));
                arrayList.add(category);
            } while (query.moveToNext());
            query.close();
            return arrayList;
        }
        query.close();
        return arrayList;
    }
}
