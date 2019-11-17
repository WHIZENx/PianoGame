package com.cmu.project.pianogame.MainActivity.Service;

import com.cmu.project.pianogame.Database.MainSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class Settings {

    // Default Setting *-*
    public static int max_energy = 30;
    public static long count_time = 60000;

    public static int price_box = 50;

    public static void SetSetting() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MainSettings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final MainSettings mainSettings = dataSnapshot.getValue(MainSettings.class);
                max_energy = mainSettings.getMax_energy();
                count_time = mainSettings.getCount_time();
                price_box = mainSettings.getPrice_box();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
