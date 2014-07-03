package com.speakapp.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DataBaseManager
{
    private static final String TABLE_NAME = "EventsTable";
    private SQLiteDatabase mEventsDatabase;
    private final PPSQLiteHelper mSQLiteHelper;
    private static DataBaseManager msInstance;

    private ArrayList<String> mEventsColumn = new ArrayList<String>()
    {{
//        add(AppIdentifierField.KEY);
//        add(CarrierField.KEY);
//        add(ConnectionTypeField.KEY);
//        add(DataTimeField.KEY);
//        add(DeviceTypeField.KEY);
//        add(IpAddressField.KEY);
//        add(LocaleField.KEY);
//        add(LocationField.KEY);
//        add(MacAddressField.KEY);
//        add(PlatformField.KEY);
//        add(UserIdentifierField.KEY);
//        add(EventTypeField.KEY);
//        add(EventDescriptionField.KEY);
//        add(CustomIdentifiersField.KEY);
    }};

    private DataBaseManager(Context context)
    {
        mSQLiteHelper = new PPSQLiteHelper(context);
        //  openDatabase();
    }

    public static void initInstance(Context context)
    {
        if (msInstance == null)
        {
            msInstance = new DataBaseManager(context);
        }
    }

    public static synchronized DataBaseManager getInstance()
    {
        return msInstance;
    }

    private void openDatabase() throws SQLException
    {
        mEventsDatabase = mSQLiteHelper.getWritableDatabase();
    }

    public void closeDatabase()
    {
        mSQLiteHelper.close();
    }

    public void addValue(String value, String key)
    {
        openDatabase();
        ContentValues values = new ContentValues();

            values.put(key, value);


        mEventsDatabase.insert(TABLE_NAME, null, values);
        closeDatabase();
    }

    public int deleteAllEvents()
    {
        openDatabase();
        int deletedRowsCounter = mEventsDatabase.delete(TABLE_NAME, "1", null);
        closeDatabase();
        return deletedRowsCounter;
    }

    public void removeEvent(String eventId)
    {
        openDatabase();
        mEventsDatabase.delete(TABLE_NAME, "id = ?", new String[] {eventId});
        closeDatabase();
    }

    public ArrayList<String> getAllEvents()
    {
        openDatabase();
        ArrayList<String> allEventList = new ArrayList<String>();

        // Get all events form event table
        String[] strings = mEventsColumn.toArray(new String[0]);
        Cursor cursor = mEventsDatabase.query(TABLE_NAME, strings , null, null, null, null, null);

        // Move all events to array
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            allEventList.add(cursor.getString(0));

            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeDatabase();
        return allEventList;
    }



    private class PPSQLiteHelper extends SQLiteOpenHelper
    {
        public static final String DATABASE_NAME = "{DBNAME}";
        public static final int DATABASE_VERSION = 1;

        private String getCreateDatabaseQuery()
        {
            StringBuilder sb = new StringBuilder(String.format("create table %s(id INTEGER PRIMARY KEY, ", TABLE_NAME));
            for (String key : mEventsColumn)
            {
                sb.append(key);
                sb.append(" TEXT, ");
            }
            if (sb.lastIndexOf(",") != -1)
            {
                sb.deleteCharAt(sb.lastIndexOf(","));
            }

            sb.append(");");

            return sb.toString();
        }

        public PPSQLiteHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database)
        {
            database.execSQL(getCreateDatabaseQuery());
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
        {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(database);
        }
    }

}
