package com.cmu.project.pianogame.MainActivity.Adapter;

import com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments.BoxFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments.GadgetsFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments.MusicFragment;
import com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments.ThemeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShopAdapter extends FragmentPagerAdapter {

    public ShopAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    public Fragment getItem(int position) {
        if(position == 0)
            return new ThemeFragment();
        else if(position == 1)
            return new BoxFragment();
        else if(position == 2)
            return new MusicFragment();
        else if(position == 3)
            return new GadgetsFragment();
        return null;
    }


}
