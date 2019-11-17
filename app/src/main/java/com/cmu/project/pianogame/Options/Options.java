package com.cmu.project.pianogame.Options;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cmu.project.pianogame.R;

public class Options {

    public static int getResourceId(Context context, String type, String tag) { return context.getResources().getIdentifier(tag, type, context.getPackageName()); }

    public static boolean HaveNevBar(Context context) { return KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK) && ViewConfiguration.get((context)).hasPermanentMenuKey(); }

    public static void HideNavBar(Context context) {
        Window window = ((AppCompatActivity) context).getWindow();
        final View decorView = window.getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {
            window.getDecorView().setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
            {
                @Override
                public void onSystemUiVisibilityChange(int visibility)
                {
                    if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) decorView.setSystemUiVisibility(flags);
                }
            });
        }
    }

    public static int NavbarSize(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId) + 2;
    }

    public static void SetFullScreen(Context context) {
        Window window = ((AppCompatActivity) context).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void ChangePage(AppCompatActivity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
        activity.finish();
    }

    public static void showMessage(Context context, String text, int mode) {
        if (mode == 1) Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        else if (mode == 2) Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) return false;
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            return !activity.isDestroyed() && !activity.isFinishing();
        }
        return true;
    }

    public static void setUpFadeAnimation(final LinearLayout linearLayout) {
        // Start from 0.1f if you desire 90% fade animation
        final Animation fadeIn = new AlphaAnimation(0.2f, 1.0f);
        fadeIn.setDuration(1000);
        fadeIn.setStartOffset(500);
        // End to 0.1f if you desire 90% fade animation
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.2f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(500);

        fadeIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                linearLayout.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) { linearLayout.startAnimation(fadeIn); }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        linearLayout.startAnimation(fadeOut);
    }

    public static void scaleAnimation(Context context, final GifImageView gifImageView) {
        final Animation scale_down = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        final Animation scale_up = AnimationUtils.loadAnimation(context, R.anim.scale_up);

        scale_down.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation animation) { gifImageView.startAnimation(scale_up); }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }

        });

        scale_up.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                gifImageView.startAnimation(scale_down);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });
        gifImageView.startAnimation(scale_down);
    }

    public static void setUpDownAnimation(Context context, final RelativeLayout relativeLayout) {
        Animation up_down = AnimationUtils.loadAnimation(context, R.anim.up_down);
        relativeLayout.startAnimation(up_down);
    }
}
