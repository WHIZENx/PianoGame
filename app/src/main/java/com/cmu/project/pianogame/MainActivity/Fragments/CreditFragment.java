package com.cmu.project.pianogame.MainActivity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;

public class CreditFragment extends Fragment {

    RelativeLayout rev1, rev2, rev3, rev4, rev5;
    TextView textView;
    boolean finish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit, container, false);

//        rev1 = view.findViewById(R.id.credit);
//        rev2 = view.findViewById(R.id.people_1);
//        rev3 = view.findViewById(R.id.people_2);
//        rev4 = view.findViewById(R.id.people_3);
//        rev5 = view.findViewById(R.id.people_4);
//
//        textView = view.findViewById(R.id.credit_text);
//
//        rev1.setVisibility(View.INVISIBLE);
//        rev2.setVisibility(View.INVISIBLE);
//        rev3.setVisibility(View.INVISIBLE);
//        rev4.setVisibility(View.INVISIBLE);
//        rev5.setVisibility(View.INVISIBLE);
//
//        rev1.setVisibility(View.VISIBLE);
//        rev1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rev2.setVisibility(View.VISIBLE);
//                rev2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
//            }
//        }, 700);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rev3.setVisibility(View.VISIBLE);
//                rev3.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
//            }
//        }, 1400);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rev4.setVisibility(View.VISIBLE);
//                rev4.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
//            }
//        }, 2100);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rev5.setVisibility(View.VISIBLE);
//                rev5.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
//                finish = true;
//            }
//        }, 2800);
//
//        if (finish) Options.setUpFadeTextAnimation(textView);

        return view;
    }
}
