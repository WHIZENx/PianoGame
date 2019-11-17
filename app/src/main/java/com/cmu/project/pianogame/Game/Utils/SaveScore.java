package com.cmu.project.pianogame.Game.Utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.cmu.project.pianogame.Database.Time;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.Score;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class SaveScore {

    private static DatabaseReference ref;

    public static void SaveRecTime(final float sec_time, final String time_string, final RelativeLayout relativeLayout) {

        ref = FirebaseDatabase.getInstance().getReference("Scores").child("RecMode").child(Firebase.getCurrent().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", Firebase.getCurrent().getUid());
                    hashMap.put("max_time", sec_time);
                    hashMap.put("time_string", time_string);
                    relativeLayout.setVisibility(View.VISIBLE);
                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) AnimOver(relativeLayout);
                        }
                    });
                } else {
                    Time times = dataSnapshot.getValue(Time.class);
                    relativeLayout.setVisibility(View.VISIBLE);
                    if (times.getMax_time() < sec_time) {
                        ref.child("time_string").setValue(time_string);
                        ref.child("max_time").setValue(sec_time).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) AnimOver(relativeLayout);
                            }
                        });
                    } else {
                        AnimOver(relativeLayout);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    public static void SaveScore(final int score, final RelativeLayout relativeLayout) {

        ref = FirebaseDatabase.getInstance().getReference("Scores").child("ScoreMode").child(Firebase.getCurrent().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", Firebase.getCurrent().getUid());
                    hashMap.put("maxscore", score);
                    relativeLayout.setVisibility(View.VISIBLE);
                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) AnimOver(relativeLayout);
                        }
                    });
                } else {
                    Score scores = dataSnapshot.getValue(Score.class);
                    relativeLayout.setVisibility(View.VISIBLE);
                    if (scores.getMaxscore() < score) {
                        ref.child("maxscore").setValue(score).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) AnimOver(relativeLayout);
                            }
                        });
                    } else {
                        AnimOver(relativeLayout);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private static void AnimOver(RelativeLayout relativeLayout) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        relativeLayout.startAnimation(alphaAnimation);
    }
}
