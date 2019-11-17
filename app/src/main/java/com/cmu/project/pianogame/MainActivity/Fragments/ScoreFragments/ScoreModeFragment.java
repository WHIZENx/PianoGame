package com.cmu.project.pianogame.MainActivity.Fragments.ScoreFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.project.pianogame.Database.Score;
import com.cmu.project.pianogame.MainActivity.Adapter.ScoresAdapter;
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

public class ScoreModeFragment extends Fragment {

    private RecyclerView recscore;
    private ScoresAdapter scoresAdapter;
    private List<Score> mScores;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_mode, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recscore  = view.findViewById(R.id.recscore);
        recscore.setHasFixedSize(true);
        recscore.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Scores").child("ScoreMode");

        scoreList();

        return view;
    }

    private void scoreList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mScores = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Score score = postsnap.getValue(Score.class);
                    mScores.add(score);
                }

                Collections.sort(mScores, new Comparator<Score>() {
                    @Override
                    public int compare(Score obj1, Score obj2) {
                        if (obj1.getMaxscore() < obj2.getMaxscore()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                scoresAdapter = new ScoresAdapter(getContext(), mScores);
                recscore.setAdapter(scoresAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
