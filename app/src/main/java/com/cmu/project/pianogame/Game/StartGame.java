package com.cmu.project.pianogame.Game;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Display;

import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.Options.Options;

public class StartGame extends AppCompatActivity {

    private ScoreMode scoreMode;
    private RecMode recMode;
    public static int mode;
    protected static String background_name, boxcolor, musics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point resolution = new Point();
        display.getSize(resolution);

        //set the view for our game
        Intent intent = getIntent();
        background_name = intent.getStringExtra("background");
        boxcolor = intent.getStringExtra("boxcolor");
        musics = intent.getStringExtra("music");
        int coins = intent.getIntExtra("coins", 0);
        int loves = intent.getIntExtra("loves", 0);
        int energy = intent.getIntExtra("energy", 0);
        if (mode == 0) scoreMode = new ScoreMode(this, resolution.x, resolution.y, coins, loves, energy);
        else recMode = new RecMode(StartGame.this, resolution.x, resolution.y, energy);

        // Make our GameView the view for the Activity
        Options.SetFullScreen(StartGame.this);
        if (mode == 0) setContentView(scoreMode);
        else setContentView(recMode);
    }

    // If the Activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        if (mode == 0) scoreMode.pause();
        else recMode.pause();

    }

    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        Options.HideNavBar(StartGame.this);
        super.onResume();
        if (mode == 0) scoreMode.resume();
        else recMode.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Sound.getSound() != null) Sound.Stop();
    }

    @Override
    public void onBackPressed() {
    }
}
