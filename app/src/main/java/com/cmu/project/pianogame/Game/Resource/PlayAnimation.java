package com.cmu.project.pianogame.Game.Resource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@SuppressLint("ViewConstructor")
public class PlayAnimation extends SurfaceView {

    public Movie mMovie;
    private long mMovieStart;
    private static int alpha_in;
    public boolean DECODE_STREAM = false;

    public PlayAnimation(Context context, int id) {
        super(context);

        InputStream is = context.getResources().openRawResource(id);

        // Set to false to use decodeByteArray
        if (DECODE_STREAM) {
            mMovie = Movie.decodeStream(is);
        } else {
            byte[] array = streamToBytes(is);
            mMovie = Movie.decodeByteArray(array, 0, array.length);
        }
    }

    private byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException ignored) {
        }
        return os.toByteArray();
    }

    public void PlayAnim(Canvas canvas, float x, float y) {
        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) { // first time
            mMovieStart = now;
        }
        if (mMovie != null) {
            int dur = mMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }
            int relTime = (int) ((now - mMovieStart) % dur);
            mMovie.setTime(relTime);
            mMovie.draw(canvas, x - mMovie.width()/2f, y - mMovie.height()*2.5f);
            invalidate();
        }
    }

    public static void fadeIn(Paint paint) {
        alpha_in += 10;
        if (alpha_in >= 255) alpha_in = 255;
        paint.setAlpha(alpha_in);
    }

}
