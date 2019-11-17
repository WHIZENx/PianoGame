package com.cmu.project.pianogame.MainActivity.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cmu.project.pianogame.Game.StartGame;
import com.cmu.project.pianogame.MainActivity.MainActivity;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Database.Coin;
import com.cmu.project.pianogame.Database.Energy;
import com.cmu.project.pianogame.Database.Users;
import com.cmu.project.pianogame.MainActivity.Service.Settings;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.Options.SharePref;
import com.cmu.project.pianogame.Options.Utils;
import com.cmu.project.pianogame.R;
import com.cmu.project.pianogame.SubActivity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView textenergy, showscore, username, coin, current_music;
    private RelativeLayout relativeLayout;
    private long getTime;

    private CircleImageView profile_img;
    private RelativeLayout loading;

    private SharePref sharePref;
    private CountDownTimer countDownTimer;

    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        sharePref = new SharePref(getContext());

        if (sharePref.loadTime() == 0) {
            if (Set.energys < Settings.max_energy) {
                countdown(Settings.count_time);
                sharePref.setTime(getTime);
            }
        } else if (Set.energys < Settings.max_energy) countdown(sharePref.loadTime());

        progressBar = view.findViewById(R.id.progress_bar);
        textenergy = view.findViewById(R.id.text_energy);
        relativeLayout = view.findViewById(R.id.lin);
        showscore = view.findViewById(R.id.showscore);
        username = view.findViewById(R.id.username);
        coin = view.findViewById(R.id.coin);
        profile_img = view.findViewById(R.id.profile_img);
        loading = view.findViewById(R.id.loading);

        GifImageView love = view.findViewById(R.id.love);
        RelativeLayout rev_main = view.findViewById(R.id.rev_main);

        Options.scaleAnimation(getContext(), love);
        Options.setUpDownAnimation(getContext(), rev_main);

        LinearLayout revmusic = view.findViewById(R.id.revmusic);
        current_music = view.findViewById(R.id.current_music);

        GifImageView btn_play = view.findViewById(R.id.btn_play);
        ImageView btn_logout = view.findViewById(R.id.btn_logout);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Set.energys > 0) {
                    Utils.CreateDialogMode(getActivity());
                    Utils.getClassic().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startscoregame();
                        }
                    });
                    Utils.getRecode().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startrecgame();
                        }
                    });
                }
                else Utils.CreateDialogError(getActivity(), "You have not enough Energy!!", 20);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Options.showMessage(getContext(), "Successful logout account!", 2);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Options.ChangePage((AppCompatActivity) getActivity(), intent);
            }
        });

        final Drawable draw = getActivity().getResources().getDrawable(R.drawable.progress_bar);

        ref = FirebaseDatabase.getInstance().getReference("Users").child(Firebase.getCurrent().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (Options.isValidContextForGlide(getContext())) Glide.with(getContext()).load(users.getImageURL()).into(profile_img);
                    username.setText(users.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Energy").child(Firebase.getCurrent().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Energy energy = dataSnapshot.getValue(Energy.class);
                progressBar.setProgressDrawable(draw);
                progressBar.setProgress((int) ((energy.getEnergy() * 100f/Settings.max_energy)));
                if (Set.energys == Settings.max_energy) textenergy.setText(String.format("%d/%d (00:00 sec)", Settings.max_energy, Settings.max_energy));
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

        ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showscore.setText("Max Score: " + Set.max_score_mode);
                current_music.setText(Set.name_musics);

                relativeLayout.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rev_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast, (ViewGroup) view.findViewById(R.id.toast_layout_root));

                TextView text = layout.findViewById(R.id.text);
                text.setText("You have love "+Set.loves+"!");

                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        });

        Options.setUpFadeAnimation(revmusic);

        return view;
    }

    private void countdown(long timeset) {
        countDownTimer = new CountDownTimer(timeset, 1000) {
            public void onTick(long millisUntilFinished) {
                if (Set.energys >= Settings.max_energy) {
                    getTime = 0;
                    textenergy.setText(String.format("%d/%d (00:00 sec)", Settings.max_energy, Settings.max_energy));
                } else {
                    getTime = millisUntilFinished;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    if (sec < 10) textenergy.setText(String.format("%d/%d (0%d:0%d sec)", Set.energys, Settings.max_energy, min, sec));
                    else textenergy.setText(String.format("%d/%d (0%d:%d sec)", Set.energys, Settings.max_energy, min, sec));
                }
            }

            public void onFinish() {
                countDownTimer.cancel();
                if (Set.energys < Settings.max_energy) {
                    Set.IncreaseEnergy(Firebase.getCurrent().getUid(), Set.energys);
                    countdown(Settings.count_time);
                }
            }
        };
        countDownTimer.start();
    }

    private void startscoregame() {
        StartGame.mode = 0;
        startGame();
    }

    private void startrecgame() {
        StartGame.mode = 1;
        startGame();
    }

    private void startGame() {
        Set.DecreaseEnergy(Firebase.getCurrent().getUid(), Set.energys);
        Intent intent = new Intent(getContext(), StartGame.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("background", MainActivity.getResourceName());
        intent.putExtra("boxcolor", Set.colors);
        intent.putExtra("music", Set.musics);
        intent.putExtra("coins", Set.coins);
        intent.putExtra("loves", Set.loves);
        intent.putExtra("energy", Set.energys-1);
        Options.ChangePage((AppCompatActivity) getActivity(), intent);
    }

    @Override
    public void onPause() {
        sharePref.setTime(getTime);
        if (countDownTimer != null) countDownTimer.cancel();
        super.onPause();
    }

    @Override
    public void onStop() {
        sharePref.setTime(getTime);
        if (countDownTimer != null) countDownTimer.cancel();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        sharePref.setTime(getTime);
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }
}
