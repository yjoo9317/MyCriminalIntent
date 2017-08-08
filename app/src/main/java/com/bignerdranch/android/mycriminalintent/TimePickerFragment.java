package com.bignerdranch.android.mycriminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by samsung on 2017. 8. 8..
 */

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "crime_time";
    private static final String TAG="TimePickerFragment";
    private static final String ARG_DATE = "date";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        TimePickerFragment tpf = new TimePickerFragment();
        tpf.setArguments(args);
        return tpf;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker_dialog);
        Date date = (Date)getArguments().getSerializable(ARG_DATE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int min = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR);
        int sec = calendar.get(Calendar.SECOND);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(min);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Crime Time")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        calendar.set(Calendar.HOUR, mTimePicker.getHour());
                        calendar.set(Calendar.MINUTE, mTimePicker.getMinute());
                        Date _date = calendar.getTime();
                        sendResult(Activity.RESULT_OK, _date);
                    }
                })
                .create();
    }

    public void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null)
            return;
        Log.i(TAG, "Sending Result[Time]..");
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
