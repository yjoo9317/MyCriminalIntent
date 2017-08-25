package com.bignerdranch.android.mycriminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.mycriminalintent.Crime;
import com.bignerdranch.android.mycriminalintent.database.CrimeDBSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by samsung on 2017. 8. 25..
 */

public class CrimeDBCursorWrapper extends CursorWrapper {
    public CrimeDBCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Crime getCrime(){
        String uuid = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuid));
        crime.setTitle(title);
        crime.setSolved(isSolved == 1 ? true : false);
        crime.setDate(new Date(date));

        return crime;
    }
}
