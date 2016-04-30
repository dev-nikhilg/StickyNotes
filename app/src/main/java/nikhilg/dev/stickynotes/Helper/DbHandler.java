package nikhilg.dev.stickynotes.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;
import java.util.List;

import nikhilg.dev.stickynotes.Classes.MyApplication;
import nikhilg.dev.stickynotes.Classes.NotesObject;

/**
 * Created by nik on 29/4/16.
 */
public class DbHandler {
    private static final int DATABASE_VERSION = 1;

    private static final String N_TABLE_NAME = "Notes";
    private static final String N_COL_1 = "id";
    private static final String N_COL_2 = "created_on";
    private static final String N_COL_3 = "last_modified_on";
    private static final String N_COL_4 = "small_title";
    private static final String N_COL_5 = "title";
    private static final String N_COL_6 = "body";
    private static final String N_COL_7 = "show_icon";

    private static StickyNoteDbHelper dbInstance;

    public static SQLiteDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new StickyNoteDbHelper(context.getApplicationContext());
        }
        return dbInstance.getWritableDatabase();
    }

    public static class StickyNoteDbHelper extends SQLiteOpenHelper {

        public StickyNoteDbHelper(Context context) {
            super(context, context.getPackageName(), new LoggingCursorFactory(), DATABASE_VERSION);
        }

        private static class LoggingCursorFactory implements SQLiteDatabase.CursorFactory {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return new SQLiteCursor(masterQuery, editTable, query);
            }
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + N_TABLE_NAME + " (id INTEGER PRIMARY KEY, created_on TEXT, last_modified_on TEXT, small_title TEXT, title TEXT, body TEXT, show_icon INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public static void AddNoteToDb(int id, String created_on, String updated_on, String small_title, String title, String body, int show_icon) {
        SQLiteDatabase db = getInstance(MyApplication.context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(N_COL_1, id);
        contentValues.put(N_COL_2, created_on);
        contentValues.put(N_COL_3, updated_on);
        contentValues.put(N_COL_4, small_title);
        contentValues.put(N_COL_5, title);
        contentValues.put(N_COL_6, body);
        contentValues.put(N_COL_7, show_icon);

        db.insert(N_TABLE_NAME, null, contentValues);
    }

    public static List<NotesObject> FetchAllNotes() {
        SQLiteDatabase db= getInstance(MyApplication.context);
        Cursor cursor = db.rawQuery("select * from " + N_TABLE_NAME + " order by last_modified_on DESC", null);
        List<NotesObject> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            NotesObject note = new NotesObject();
            note.setId(cursor.getInt(0));
            note.setCreatedOn(cursor.getString(1));
            note.setLastModifiedOn(cursor.getString(2));
            note.setSmallTitle(cursor.getString(3));
            note.setTitle(cursor.getString(4));
            note.setNoteBody(cursor.getString(5));
            note.setShowIcon(cursor.getInt(6));
            list.add(note);
        }
        cursor.close();
        return list;
    }

    public static NotesObject FetchNote(int id) {
        SQLiteDatabase db= getInstance(MyApplication.context);
        Cursor cursor = db.rawQuery("select * from " + N_TABLE_NAME + " where " + N_COL_1 + " = " + id, null);
        NotesObject note = new NotesObject();
        while (cursor.moveToNext()) {
            note.setId(cursor.getInt(1));
            note.setCreatedOn(cursor.getString(2));
            note.setLastModifiedOn(cursor.getString(3));
            note.setSmallTitle(cursor.getString(4));
            note.setTitle(cursor.getString(5));
            note.setNoteBody(cursor.getString(6));
            note.setShowIcon(cursor.getInt(7));
        }
        cursor.close();
        return note;
    }

    public static int deleteNote(int id) {
        int return_val = -1;
        SQLiteDatabase db= getInstance(MyApplication.context);
        if (db != null) {
            return_val = db.delete(N_TABLE_NAME, "id=?",new String[]{String.valueOf(id)});
        }
        return return_val;
    }
}
