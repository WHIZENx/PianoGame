package com.cmu.project.pianogame.Game.Resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background {

    private int width, height, endY;
    private Bitmap bitmap;

    public Background(Context context, int screenWidth, int screenHeight, String bitmapName, int sY, int eY){

        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        //Position the background vertically
        int startY = sY * (screenHeight / 100);
        endY = eY * (screenHeight / 100);

        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, endY - startY, true);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

    }

    public void DrawBackground(Canvas canvas) {
        Rect fromRect1 = new Rect(0, 0, width, height);
        Rect toRect1 = new Rect(0,0, width, endY);

        canvas.drawBitmap(bitmap, fromRect1, toRect1, new Paint());
    }
}