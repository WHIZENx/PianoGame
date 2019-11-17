package com.cmu.project.pianogame.Game.Utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.cmu.project.pianogame.R;

public class Sound {

    private static MediaPlayer mediaPlayer;
    private static int length;
    private static boolean clear;

    public static void playsound(Context context, int n) {
        MediaPlayer mp;
        switch (n) {
            case 1: mp = MediaPlayer.create(context, R.raw.freeze); break;
            case 2: mp = MediaPlayer.create(context, R.raw.error); break;
            default: mp = MediaPlayer.create(context, R.raw.click); break;
        }
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) { mp.release(); }
        });
    }

    public static void Audio(Context context, int id) { mediaPlayer = MediaPlayer.create(context, id); }

    public static void Play() {
        if (mediaPlayer.isPlaying()) mediaPlayer.release();
        mediaPlayer.start();
    }

    public static void Loop(boolean loop) { mediaPlayer.setLooping(loop); }

    public static void Stop() {
        Sound.mediaPlayer.release();
        clear = true;
    }

    public static void Pause() {
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
    }

    public static int getLength() { return length; }

    public static void Resume(int length) {
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
    }

    public static void soundPiano(Context context, int i) {
        switch (i) {
            case 0: mediaPlayer = MediaPlayer.create(context, R.raw.one); break;
            case 1: mediaPlayer = MediaPlayer.create(context, R.raw.two); break;
            case 2: mediaPlayer = MediaPlayer.create(context, R.raw.three); break;
            case 3: mediaPlayer = MediaPlayer.create(context, R.raw.four); break;
            case 4: mediaPlayer = MediaPlayer.create(context, R.raw.five); break;
            case 5: mediaPlayer = MediaPlayer.create(context, R.raw.six); break;
            case 6: mediaPlayer = MediaPlayer.create(context, R.raw.seven); break;
            case 7: mediaPlayer = MediaPlayer.create(context, R.raw.eight); break;
            case 8: mediaPlayer = MediaPlayer.create(context, R.raw.nine); break;
            case 9: mediaPlayer = MediaPlayer.create(context, R.raw.ten); break;
            case 10: mediaPlayer = MediaPlayer.create(context, R.raw.eleven); break;
            case 11: mediaPlayer = MediaPlayer.create(context, R.raw.twelve); break;
            case 12: mediaPlayer = MediaPlayer.create(context, R.raw.thirteen); break;
            case 13: mediaPlayer = MediaPlayer.create(context, R.raw.fourteen); break;
        }
        if (mediaPlayer.isPlaying()) mediaPlayer.release();
        mediaPlayer.start();
    }

    public static MediaPlayer getSound() { return mediaPlayer; }

    public static boolean isClear() { return clear; }

    public static void setSound(MediaPlayer mediaPlayer) { Sound.mediaPlayer = mediaPlayer; }
}
