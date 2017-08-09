package com.bignerdranch.android.mycriminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

/**
 * Created by yjoo9 on 8/6/2017.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE="crime_date";
    private static final String TAG="DatePickerFragment";
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    private Button mButton;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        Log.i(TAG, "Date: "+date.toString());
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        final Date date = (Date) getActivity().getIntent().getSerializableExtra(EXTRA_DATE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = inflater.inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker_dialg);
        mDatePicker.init(year, month, day, null);
        mButton = (Button) view.findViewById(R.id.date_ok_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

        return view;
    }
/*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Date date = (Date) getArguments().getSerializable(ARG_DATE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker_dialg);
        mDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date _date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, _date);
                    }
                })
                .create();
    }
*/
    public void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }
}
