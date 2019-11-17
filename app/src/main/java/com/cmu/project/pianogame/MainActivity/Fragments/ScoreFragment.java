package com.cmu.project.pianogame.MainActivity.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmu.project.pianogame.MainActivity.Adapter.ScorePageAdapter;
import com.cmu.project.pianogame.R;
import com.mr_sarsarabi.library.LockableViewPager;

public class ScoreFragment extends Fragment {

    private ScorePageAdapter adapter;
    private LockableViewPager pager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);

        adapter = new ScorePageAdapter(getChildFragmentManager());
        pager = (LockableViewPager) view.findViewById(R.id.fragment_container_score);
        pager.setSwipeLocked(true);

        setFragment();

        return view;
    }

    private void setFragment() {

        final TextView txt01 = view.findViewById(R.id.text1);
        final TextView txt02 = view.findViewById(R.id.text2);

        txt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
            }
        });

        txt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(1);
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    txt01.setTextColor(Color.RED);
                    txt02.setTextColor(Color.BLACK);
                } else if (position == 1) {
                    txt01.setTextColor(Color.BLACK);
                    txt02.setTextColor(Color.RED);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        pager.setAdapter(adapter);
    }
}
