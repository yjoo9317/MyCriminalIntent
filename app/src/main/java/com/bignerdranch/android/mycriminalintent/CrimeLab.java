package com.bignerdranch.android.mycriminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.mycriminalintent.database.CrimeDBCursorWrapper;
import com.bignerdranch.android.mycriminalintent.database.CrimeDBHelper;
import com.bignerdranch.android.mycriminalintent.database.CrimeDBSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by samsung on 2017. 8. 1..
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab getCrimeLab(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){

        mContext = context.getApplicationContext();
        mDatabase = new CrimeDBHelper(mContext).getWritableDatabase();

    }

    public void addCrime(Crime crime){
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime){
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID+" = ?",
                new String[]{crime.getId().toString()});
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeDBCursorWrapper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }
    public Crime getCrime(UUID id){
        return null;
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1: 0);

        return  values;
    }

    private CrimeDBCursorWrapper queryCrimes(String where, String[] whereArgs){
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, where, whereArgs, null, null, null);
        return new CrimeDBCursorWrapper(cursor);
    }
}
