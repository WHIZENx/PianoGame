package com.cmu.project.pianogame.MainActivity.Fragments.ShopFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.project.pianogame.MainActivity.Adapter.ThemeAdapter;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.Database.Theme;
import com.cmu.project.pianogame.Database.ThemeList;
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

public class ThemeFragment extends Fragment {

    private RecyclerView rectheme;
    private ThemeAdapter themeAdapter;
    private List<ThemeList> mThemeList;

    private String cur_theme;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rectheme = view.findViewById(R.id.rectheme);
        rectheme.setHasFixedSize(true);
        rectheme.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Themes").child(Firebase.getCurrent().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Theme theme = dataSnapshot.getValue(Theme.class);
                cur_theme = theme.getCur_theme();
                themeList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return view;
    }
    private void themeList() {
        reference = FirebaseDatabase.getInstance().getReference("ThemeList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mThemeList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    ThemeList themeList = postsnap.getValue(ThemeList.class);
                    mThemeList.add(themeList);
                }

                Collections.sort(mThemeList, new Comparator<ThemeList>() {
                    @Override
                    public int compare(ThemeList obj1, ThemeList obj2) {
                        if (obj1.getNumber() > obj2.getNumber()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                themeAdapter = new ThemeAdapter(getContext(), mThemeList, cur_theme);
                rectheme.setAdapter(themeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
