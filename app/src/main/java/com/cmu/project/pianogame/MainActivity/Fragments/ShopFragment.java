package com.cmu.project.pianogame.MainActivity.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cmu.project.pianogame.MainActivity.Adapter.ShopAdapter;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.Coin;
import com.cmu.project.pianogame.Database.Users;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr_sarsarabi.library.LockableViewPager;

public class ShopFragment extends Fragment {

    private CircleImageView profile_img;
    private TextView coin;

    private ShopAdapter adapter;
    private LockableViewPager pager;
    private View view;

    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        coin = view.findViewById(R.id.coin);
        profile_img = view.findViewById(R.id.profile_img);

        adapter = new ShopAdapter(getChildFragmentManager());
        pager = view.findViewById(R.id.fragment_container_shop);
        pager.setSwipeLocked(true);

        ref = FirebaseDatabase.getInstance().getReference("Users").child(Firebase.getCurrent().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                if(Options.isValidContextForGlide(getContext())) Glide.with(getContext()).load(users.getImageURL()).into(profile_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        ref = FirebaseDatabase.getInstance().getReference("Coins").child(Firebase.getCurrent().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Coin coins = dataSnapshot.getValue(Coin.class);
                coin.setText(coins.getCoin()+" COIN");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        setFragment();

        return view;
    }

    private void setFragment() {

        final TextView txt01 = view.findViewById(R.id.text1);
        final TextView txt02 = view.findViewById(R.id.text2);
        final TextView txt03 = view.findViewById(R.id.text3);
        final TextView txt04 = view.findViewById(R.id.text4);

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

        txt03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(2);
            }
        });

        txt04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(3);
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
                    txt03.setTextColor(Color.BLACK);
                    txt04.setTextColor(Color.BLACK);
                } else if (position == 1) {
                    txt01.setTextColor(Color.BLACK);
                    txt02.setTextColor(Color.RED);
                    txt03.setTextColor(Color.BLACK);
                    txt04.setTextColor(Color.BLACK);
                } else if (position == 2) {
                    txt01.setTextColor(Color.BLACK);
                    txt02.setTextColor(Color.BLACK);
                    txt03.setTextColor(Color.RED);
                    txt04.setTextColor(Color.BLACK);
                } else if (position == 3) {
                    txt01.setTextColor(Color.BLACK);
                    txt02.setTextColor(Color.BLACK);
                    txt03.setTextColor(Color.BLACK);
                    txt04.setTextColor(Color.RED);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        pager.setAdapter(adapter);
    }

}