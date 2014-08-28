package com.aachaerandio.slotmachine.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.aachaerandio.slotmachine.data.State.SlotIcon;

/**
 * Created by Araceli on 27/08/2014.
 */
public class SlotService {

    private SQLiteDatabase mDB = null;
    private DatabaseOpenHelper mDbHelper;
    private final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public SlotService(Activity activity) {
        // Create a new DatabaseHelper
        mDbHelper = new DatabaseOpenHelper(activity);

        // Get the underlying database for writing
        mDB = mDbHelper.getWritableDatabase();
    }

    public void insert(State data) {

        ContentValues values = new ContentValues();

        values.put(DatabaseOpenHelper.COLUMN_ICON_A, data.getIconA().ordinal());
        values.put(DatabaseOpenHelper.COLUMN_ICON_B, data.getIconB().ordinal());
        values.put(DatabaseOpenHelper.COLUMN_ICON_C, data.getIconC().ordinal());
        values.put(DatabaseOpenHelper.COLUMN_DATE, DATETIME_FORMAT.format(new Date()));
        mDB.insert(DatabaseOpenHelper.TABLE_NAME, null, values);
    }


    public ArrayList<State> read() {
        ArrayList<State> result = new ArrayList<State>();

        Cursor c = mDB.query(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper.columns, null, new String[] {}, null, null,
                DatabaseOpenHelper._ID +" DESC");

        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                State item = new State();
                item.setId(c.getLong(c.getColumnIndex(DatabaseOpenHelper._ID)));
                item.setCreated(parseDate(c.getString(c.getColumnIndex(DatabaseOpenHelper.COLUMN_DATE))));
                SlotIcon[] icons = SlotIcon.values();
                item.setIconA(icons[c.getInt(c.getColumnIndex(DatabaseOpenHelper.COLUMN_ICON_A))]);
                item.setIconB(icons[c.getInt(c.getColumnIndex(DatabaseOpenHelper.COLUMN_ICON_B))]);
                item.setIconC(icons[c.getInt(c.getColumnIndex(DatabaseOpenHelper.COLUMN_ICON_C))]);

                result.add(item);
            }while(c.moveToNext());
        }

        return result;
    }

    private Date parseDate(String string){

        Date d = null;
        try {
            d = DATETIME_FORMAT.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public void delete(long data) {

        mDB.delete(DatabaseOpenHelper.TABLE_NAME, DatabaseOpenHelper._ID + "=" + data, null);

    }

    public void clearAll() {

        mDB.delete(DatabaseOpenHelper.TABLE_NAME, null, null);

    }

    public void destroy(){
        mDB.close();
    }

}
