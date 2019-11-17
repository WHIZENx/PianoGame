package com.cmu.project.pianogame.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.MainActivity.MainActivity;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.Theme;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private boolean locked;

    private ImageView logo1;
    private String set_cur_theme;
    private RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Options.SetFullScreen(SplashActivity.this);
        Options.HideNavBar(SplashActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Sound.Audio(SplashActivity.this, Options.getResourceId(SplashActivity.this, "raw", "openapp"));
        Sound.Play();
        Sound.Loop(true);

        logo1 = findViewById(R.id.logo);
        logo1.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in));

        loading = findViewById(R.id.loading);
        openApp();
    }

    private void openApp() {

        int END_TIME = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!locked) logo1.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out_activity));
            }
        }, END_TIME);

        if(Firebase.getCurrent() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Themes").child(Firebase.getCurrent().getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Theme theme = dataSnapshot.getValue(Theme.class);
                    if (dataSnapshot.exists()) set_cur_theme = theme.getCur_theme();
                    else set_cur_theme = "bg";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        int SPLASH_TIME = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!locked) {
                    loading.setVisibility(View.VISIBLE);
                    loading.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in_activity));
                    active();
                }
            }
        }, SPLASH_TIME);
    }

    private void active() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent;
                if(Firebase.getCurrent() != null) intent = new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                else intent = new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("background_name", set_cur_theme);
                Options.ChangePage(SplashActivity.this, intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        Sound.Pause();
        super.onPause();
        locked = true;
    }

    @Override
    protected void onResume() {
        Sound.Resume(Sound.getLength());
        super.onResume();
        locked = false;
        openApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locked = true;
        finish();
    }

}
