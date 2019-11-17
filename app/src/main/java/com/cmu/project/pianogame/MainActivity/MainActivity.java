package com.cmu.project.pianogame.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.view.View;
import android.widget.ImageView;

import com.cmu.project.pianogame.Game.Settings.GameSetting;
import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.MainActivity.Adapter.MainPagerAdapter;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.Theme;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.MainActivity.Service.Settings;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr_sarsarabi.library.LockableViewPager;

public class MainActivity extends AppCompatActivity {

    private static GifImageView gifImageView;

    private LockableViewPager pager;

    private int step;

    protected void onCreate(Bundle savedInstanceState) {
        Options.SetFullScreen(MainActivity.this);
        Options.HideNavBar(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Sound.getSound() != null) {
            Sound.Stop();
            Sound.setSound(null);
        }

        gifImageView = findViewById(R.id.gif);

        Intent intent = getIntent();
        String cur_theme = intent.getStringExtra("background_name");

        if (cur_theme == null) { setGifImage("bg"); }
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Themes").child(Firebase.getCurrent().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Theme theme = dataSnapshot.getValue(Theme.class);
                    setGifImage(theme.getCur_theme());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

//        if (Options.isValidContextForGlide(MainActivity.this)) Glide.with(MainActivity.this).load("https://docs.google.com/uc?id=1Y0bB7QDM6dfV0_mZuee_9Jjb21eMNprl").into(gifImageView);

        // Setting and Get Value from Firebase
        GameSetting.SetSetting();
        Settings.SetSetting();
        Set.getCoin(Firebase.getCurrent().getUid());
        Set.getLove(Firebase.getCurrent().getUid());
        Set.getEnergy(Firebase.getCurrent().getUid());
        Set.getColor(Firebase.getCurrent().getUid());
        Set.getMusic(Firebase.getCurrent().getUid());
        Set.getMaxScore(Firebase.getCurrent().getUid());

        setFragment();
    }

    private void setFragment() {
        MainPagerAdapter adapter = new MainPagerAdapter(this.getSupportFragmentManager());
        pager = findViewById(R.id.fragment_container);
        pager.setSwipeLocked(true);

        final ImageView img01 = findViewById(R.id.home_slide);
        final ImageView img02 = findViewById(R.id.score_slide);
        final ImageView img03 = findViewById(R.id.shop_slide);
        final ImageView img04 = findViewById(R.id.credit_slide);

        img01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
                PlayPiano();
            }
        });

        img02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(1);
                PlayPiano();
            }
        });

        img03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(2);
                PlayPiano();
            }
        });

        img04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(3);
                PlayPiano();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    img01.setImageResource(R.drawable.home_icon);
                    img02.setImageResource(R.drawable.score_icon1);
                    img03.setImageResource(R.drawable.shop_icon1);
                    img04.setImageResource(R.drawable.credit_icon1);
                } else if (position == 1) {
                    img01.setImageResource(R.drawable.home_icon1);
                    img02.setImageResource(R.drawable.score_icon);
                    img03.setImageResource(R.drawable.shop_icon1);
                    img04.setImageResource(R.drawable.credit_icon1);
                } else if (position == 2) {
                    img01.setImageResource(R.drawable.home_icon1);
                    img02.setImageResource(R.drawable.score_icon1);
                    img03.setImageResource(R.drawable.shop_icon);
                    img04.setImageResource(R.drawable.credit_icon1);
                } else if (position == 3) {
                    img01.setImageResource(R.drawable.home_icon1);
                    img02.setImageResource(R.drawable.score_icon1);
                    img03.setImageResource(R.drawable.shop_icon1);
                    img04.setImageResource(R.drawable.credit_icon);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setAdapter(adapter);
    }

    private void PlayPiano() {
        if (step == 14) step = 0;
        Sound.soundPiano(MainActivity.this, step);
        step++;
    }

    private void setGifImage(String tag) {
        gifImageView.setImageResource(Options.getResourceId(MainActivity.this, "drawable", tag));
        gifImageView.setTag(tag);
    }

    public static void setGifImageViewByTag(Context context, String tag) {
        gifImageView.setImageResource(Options.getResourceId(context, "drawable", tag));
        gifImageView.setTag(tag);
    }

    public static String getResourceName() { return String.valueOf(gifImageView.getTag()); }

    @Override
    protected void onResume() {
        Options.HideNavBar(MainActivity.this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Options.HideNavBar(MainActivity.this);
        super.onDestroy();
    }

}
