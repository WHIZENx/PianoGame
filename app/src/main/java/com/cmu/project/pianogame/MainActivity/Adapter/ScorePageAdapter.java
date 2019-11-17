package com.cmu.project.pianogame.MainActivity.Adapter;

import com.cmu.project.pianogame.MainActivity.Fragments.ScoreFragments.RecModeFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ScoreFragments.ScoreModeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ScorePageAdapter extends FragmentPagerAdapter {

    public ScorePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    public Fragment getItem(int position) {
        if(position == 0)
            return new ScoreModeFragment();
        else if(position == 1)
            return new RecModeFragment();
        return null;
    }


}
