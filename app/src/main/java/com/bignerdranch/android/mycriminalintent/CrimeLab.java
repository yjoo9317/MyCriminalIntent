package com.bignerdranch.android.mycriminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.mycriminalintent.database.CrimeDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by samsung on 2017. 8. 1..
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
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

        mCrimes = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #"+i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id))
                return crime;
        }
        Log.e("CrimeLab", "Couldn't find Crime #"+id);
        return null;
    }
}
