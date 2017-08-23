package com.bignerdranch.android.mycriminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.mycriminalintent.database.CrimeDBSchema.CrimeTable;


/**
 * Created by samsung on 2017. 8. 23..
 */

public class CrimeDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="crimeDB.db";
    private static final int VERSION = 1;

    public CrimeDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ CrimeTable.NAME +"("+
        " _id integer primary key autoincrement, "+
        CrimeTable.Cols.UUID+", "+
        CrimeTable.Cols.TITLE+", "+
        CrimeTable.Cols.DATE+", "+
        CrimeTable.Cols.SOLVED+ ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}
