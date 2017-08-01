package com.bignerdranch.android.mycriminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2017. 8. 1..
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        mAdapter = new CrimeAdapter(crimeLab.getCrimes());
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeViewHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_checkbox);
            itemView.setOnClickListener(this);
        }

        public void bindCrime(Crime crime){
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedCheckBox.setChecked(crime.isSolved());
            mCrime = crime;
        }

        @Override
        public void onClick(View view){
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount(){
            return mCrimes.size();
        }
    }
}
