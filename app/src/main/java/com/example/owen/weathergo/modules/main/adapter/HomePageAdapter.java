package com.example.owen.weathergo.modules.main.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 2017/4/20.
 * Info:
 */

public class HomePageAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tittles = new ArrayList<>();

    public HomePageAdapter(FragmentManager fm){
        super(fm);
    }
    public HomePageAdapter(FragmentManager fm, TabLayout tb){
        super(fm);
    }

    public void addTab(Fragment fm,String tittle){
        fragments.add(fm);
        tittles.add(tittle);
    }
    /**
     * Return the Fragment associated with a specified position.
     */

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
