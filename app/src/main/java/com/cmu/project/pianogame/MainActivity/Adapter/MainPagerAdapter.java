package com.cmu.project.pianogame.MainActivity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cmu.project.pianogame.MainActivity.Fragments.CreditFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.MainFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ScoreFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ShopFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    public Fragment getItem(int position) {
        if(position == 0)
            return new MainFragment();
        else if(position == 1)
            return new ScoreFragment();
        else if(position == 2)
            return new ShopFragment();
        else if(position == 3)
            return new CreditFragment();
        return null;
    }
}
