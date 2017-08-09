package com.bignerdranch.android.mycriminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created by samsung on 2017. 8. 9..
 */

public class DatePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_DATE = "crime_date";

    @Override
    protected Fragment createFragment(){
        return new DatePickerFragment();
    }

    public static Intent newIntent(Context context, Date date){
        Intent intent = new Intent(context, DatePickerActivity.class);
        intent.putExtra(DatePickerFragment.EXTRA_DATE, date);
        return intent;
    }

}
