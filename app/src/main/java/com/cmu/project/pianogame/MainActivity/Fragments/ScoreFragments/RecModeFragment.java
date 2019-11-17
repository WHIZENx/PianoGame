package com.cmu.project.pianogame.MainActivity.Fragments.ScoreFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.project.pianogame.Database.Time;
import com.cmu.project.pianogame.MainActivity.Adapter.RecsAdapter;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecModeFragment extends Fragment {

    private RecyclerView recscore;
    private RecsAdapter recsAdapter;
    private List<Time> mTimes;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec_mode, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recscore  = view.findViewById(R.id.rectime);
        recscore.setHasFixedSize(true);
        recscore.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Scores").child("RecMode");

        timeList();

        return view;
    }

    private void timeList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTimes = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Time time = postsnap.getValue(Time.class);
                    mTimes.add(time);
                }

                Collections.sort(mTimes, new Comparator<Time>() {
                    @Override
                    public int compare(Time obj1, Time obj2) {
                        if (obj1.getMax_time() < obj2.getMax_time()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                recsAdapter = new RecsAdapter(getContext(), mTimes);
                recscore.setAdapter(recsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
