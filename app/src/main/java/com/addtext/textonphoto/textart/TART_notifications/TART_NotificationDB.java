package com.addtext.textonphoto.textart.TART_notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TART_NotificationDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifications.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTIFICATIONS = "notifications";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_BODY = "body";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IS_READ = "is_read"; // 0 for false, 1 for true

    public TART_NotificationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_BODY + " TEXT, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_IS_READ + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    public void insertNotification(String title, String body, long time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_BODY, body);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_IS_READ, 0);
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
    }

    public int getUnreadCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTIFICATIONS + " WHERE " + COLUMN_IS_READ + " = 0", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public void markAllAsRead() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_READ, 1);
        db.update(TABLE_NOTIFICATIONS, values, COLUMN_IS_READ + " = 0", null);
        db.close();
    }

    public List<NotificationModel> getAllNotifications() {
        List<NotificationModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS + " ORDER BY " + COLUMN_TIME + " DESC", null);
        
        if (cursor.moveToFirst()) {
            do {
                NotificationModel model = new NotificationModel();
                model.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                model.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                model.body = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BODY));
                model.time = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                model.isRead = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_READ)) == 1;
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public static class NotificationModel {
        public int id;
        public String title;
        public String body;
        public long time;
        public boolean isRead;
    }
}
