package com.bignerdranch.android.mycriminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by yjoo9 on 8/6/2017.
 */

public class CrimePagerActivity extends FragmentActivity {

    private static final String TAG="CrimePagerActivity";
    private static final String EXTRA_CRIME_ID="crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID); //what for?
        mCrimes = CrimeLab.getCrimeLab(this).getCrimes();
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for(int i = 0; i < mCrimes.size(); i++){
            Crime crime = mCrimes.get(i);
            if(crime.getId().equals(id)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
