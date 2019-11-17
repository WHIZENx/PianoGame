package com.cmu.project.pianogame.Game.Settings;

import com.cmu.project.pianogame.Database.Settings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class GameSetting {

    // Default Setting *-*
    public static int box_entity = 10000;

    public static int life = 1;

    public static int random_blank_box = 20;
    public static int random_mode_box = 100;

    public static int start_speed = 20;

    public static int finish_time_slow = 200;
    public static int speed_freeze = 20;

    public static int finish_time_bomb = 100;

    public static int finish_time_lucky = 200;
    public static float speed_lucky = 0.01f;

    public static int speed_jackpot = 15;

    public static int count_speed = 300;

    public static int framerate_per_sec = 1000;

    public static int time_out = 150;

    public static float percent_hitbox = 0.1f;

    public static float width_box = 5;

    public static String font_game = "fonts/ARCADECLASSIC.TTF";

    public static String start_color = "#9BC8FF";

    public static float percent_bar(int time) { return 200f/time; }

    public static void SetSetting() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Settings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Settings settings = dataSnapshot.getValue(Settings.class);
                box_entity = settings.getBox_entity();
                life = settings.getLife();
                random_blank_box = settings.getRandom_blank_box();
                random_mode_box = settings.getRandom_mode_box();
                start_speed = settings.getStart_speed();
                finish_time_slow = settings.getFinish_time_slow();
                speed_freeze = settings.getSpeed_freeze();
                finish_time_bomb = settings.getFinish_time_bomb();
                finish_time_lucky = settings.getFinish_time_lucky();
                speed_lucky = settings.getSpeed_lucky();
                speed_jackpot = settings.getSpeed_jackpot();
                count_speed = settings.getCount_speed();
                framerate_per_sec = settings.getFramerate_per_sec();
                time_out = settings.getTime_out();
                percent_hitbox = settings.getPercent_hitbox();
                width_box = settings.getWidth_box();
                start_color = settings.getStart_color();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
