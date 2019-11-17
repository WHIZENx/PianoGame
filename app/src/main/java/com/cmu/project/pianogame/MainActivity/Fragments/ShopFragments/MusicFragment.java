package com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.project.pianogame.MainActivity.Adapter.MusicAdapter;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.MusicList;
import com.cmu.project.pianogame.Database.Music;
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

public class MusicFragment extends Fragment {

    private RecyclerView recmusic;
    private MusicAdapter musicAdapter;
    private List<MusicList> mMusicList;

    private String cur_music;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recmusic = view.findViewById(R.id.recmusic);
        recmusic.setHasFixedSize(true);
        recmusic.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Musics").child(Firebase.getCurrent().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Music music = dataSnapshot.getValue(Music.class);
                cur_music = music.getCur_music();
                musicList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return view;
    }
    private void musicList() {
        reference = FirebaseDatabase.getInstance().getReference("MusicList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMusicList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    MusicList musicList = postsnap.getValue(MusicList.class);
                    mMusicList.add(musicList);
                }

                Collections.sort(mMusicList, new Comparator<MusicList>() {
                    @Override
                    public int compare(MusicList obj1, MusicList obj2) {
                        if (obj1.getNumber() > obj2.getNumber()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                musicAdapter = new MusicAdapter(getContext(), mMusicList, cur_music);
                recmusic.setAdapter(musicAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
