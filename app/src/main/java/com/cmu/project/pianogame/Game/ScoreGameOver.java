package com.cmu.project.pianogame.Game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmu.project.pianogame.Game.Utils.SaveScore;
import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.MainActivity.MainActivity;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Database.Score;
import com.cmu.project.pianogame.MainActivity.Service.Settings;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.Options.Utils;
import com.cmu.project.pianogame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreGameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Options.SetFullScreen(ScoreGameOver.this);
        Options.HideNavBar(ScoreGameOver.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_game_over);

        GifImageView gifImageView = findViewById(R.id.gif);
        ImageView home = findViewById(R.id.home);
        ImageView restart = findViewById(R.id.restart);
        TextView txtscores = findViewById(R.id.score);
        TextView coin = findViewById(R.id.txtcoin);
        final TextView best = findViewById(R.id.bestscore);
        TextView energy = findViewById(R.id.txtenergy);

        RelativeLayout relativeLayout = findViewById(R.id.revscore);
        relativeLayout.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        final int scores = intent.getIntExtra("score", 0);
        final int cur_coin = intent.getIntExtra("coins", 0);
        final int love = intent.getIntExtra("loves", 0);
        final int energys = intent.getIntExtra("energy", 0);

        SaveScore.SaveScore(scores, relativeLayout);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scores").child("ScoreMode").child(Firebase.getCurrent().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Score score = dataSnapshot.getValue(Score.class);
                    best.setText("Score: " + score.getMaxscore());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        txtscores.setText("SCORE: "+scores);

        int coins = scores/10;

        coin.setText("COIN: "+cur_coin+"+"+coins+" = "+(cur_coin+coins));

        energy.setText("ENERGY: "+energys+"/"+Settings.max_energy);

        Set.SetCoin(Firebase.getCurrent().getUid(), cur_coin+coins);

        Set.SetLove(Firebase.getCurrent().getUid(), love);

        if (StartGame.background_name != null) {
            int resID = getResources().getIdentifier(StartGame.background_name, "drawable",getPackageName());
            gifImageView.setImageResource(resID);
            gifImageView.setTag(StartGame.background_name);
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreGameOver.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("background_name", StartGame.background_name);
                Options.ChangePage(ScoreGameOver.this, intent);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (energys > 0) {
                    StartGame.mode = 0;
                    Sound.setSound(null);
                    Set.DecreaseEnergy(Firebase.getCurrent().getUid(), energys);
                    Intent intent = new Intent(ScoreGameOver.this, StartGame.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("background", StartGame.background_name);
                    intent.putExtra("boxcolor", StartGame.boxcolor);
                    intent.putExtra("music", StartGame.musics);
                    intent.putExtra("coins", cur_coin);
                    intent.putExtra("loves", love);
                    intent.putExtra("energy", energys-1);
                    Options.ChangePage(ScoreGameOver.this, intent);
                } else Utils.CreateDialogError(ScoreGameOver.this, "You have not enough Energy!!", 20);
            }
        });
    }

    @Override
    protected void onResume() {
        Options.HideNavBar(ScoreGameOver.this);
        super.onResume();
    }

    @Override
    public void onBackPressed() { }

}
