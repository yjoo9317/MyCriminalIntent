package com.bignerdranch.android.mycriminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yjoo9 on 7/30/2017.
 */

public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    private static final String ARG_CRIME_ID = "crim_id";
    private static final String DIALOG_DATE="dialog_date";
    private static final String DIALOG_TIME="dialog_time";
    private static final int REQUEST_DATE= 100 ;
    private static final int REQUEST_TIME=200;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;


    public static CrimeFragment newInstance(UUID id){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CRIME_ID, id);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID id = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(id);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitleField.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton = (Button)view.findViewById(R.id.crime_date);
        final Date date = mCrime.getDate();
        mDateButton.setText(date.toString());
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(date);
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) view.findViewById(R.id.crime_time_button);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fm = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(date);
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.list_item_crime_solved_checkbox);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode != Activity.RESULT_OK)
            return;
        else if(requestCode == REQUEST_DATE){
            Log.i(TAG, "updating crime date..");
            Date date = (Date) intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(date.toString());
        } else if(requestCode == REQUEST_TIME){

            Log.i(TAG, "updating crime time..");
            Date date = (Date) intent.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            updateTime();
        }
    }

    public void updateTime(){
        Date date = mCrime.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String time = "Crime Time: "+ calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
        mTimeButton.setText(time);
    }
}
