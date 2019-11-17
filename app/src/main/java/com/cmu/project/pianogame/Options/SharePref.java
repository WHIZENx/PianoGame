package com.cmu.project.pianogame.Options;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private SharedPreferences sharedPreferences;

    public SharePref(Context context) { sharedPreferences = context.getSharedPreferences("TIME_ENERGY",Context.MODE_PRIVATE); }

    public void setTime(long time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("currentTime", time);
        editor.apply();
    }

    public long loadTime() { return sharedPreferences.getLong("currentTime", 0); }

}
