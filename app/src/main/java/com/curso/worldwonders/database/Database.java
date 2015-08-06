package com.curso.worldwonders.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.curso.worldwonders.database.tables.WondersTable;

/**
 * Created by Junior on 06/08/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "worldwonders";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(WondersTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
