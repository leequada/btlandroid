package com.example.bigproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterViewPager extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragment = new ArrayList<>();

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size();
    }
    public void addFragment(Fragment f){
        fragment.add(f);
    }
}
