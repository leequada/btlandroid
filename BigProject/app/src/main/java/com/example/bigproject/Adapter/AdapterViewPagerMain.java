package com.example.bigproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bigproject.Fragment.Home_fragment;
import com.example.bigproject.Fragment.Notification_fragment;
import com.example.bigproject.Fragment.Profile_fragment;
import com.example.bigproject.Fragment.Search_fragment;

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {
    private int pagenum;
    public AdapterViewPagerMain(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pagenum = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Home_fragment();
            case 1: return new Search_fragment();
            case 2: return new Profile_fragment();
            case 3: return new Notification_fragment();
            default: return new Home_fragment();
        }
    }

    @Override
    public int getCount() {
        return pagenum;
    }
}
