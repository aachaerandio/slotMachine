package com.aachaerandio.slotmachine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Araceli on 27/08/2014.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "states";

    final static String _ID = "_id";
    final static String COLUMN_ICON_A = "iconA";
    final static String COLUMN_ICON_B = "iconB";
    final static String COLUMN_ICON_C = "iconC";
    public static final String COLUMN_DATE = "date";

    public final static String[] columns = { _ID, COLUMN_ICON_A, COLUMN_ICON_B, COLUMN_ICON_C, COLUMN_DATE};

    final private static String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +  "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ICON_A + " NUMBER, " +
                    COLUMN_ICON_B + " NUMBER, " +
                    COLUMN_ICON_C + " NUMBER, " +
                    COLUMN_DATE + " DATE)";

    final private static String DATABASE_NAME = "slotMachine_db";
    final private static Integer VERSION = 1;

    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void deleteDatabase() {
        mContext.deleteDatabase(DATABASE_NAME);
    }
}
