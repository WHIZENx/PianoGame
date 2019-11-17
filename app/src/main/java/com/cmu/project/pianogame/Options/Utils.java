package com.cmu.project.pianogame.Options;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.project.pianogame.R;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

public class Utils {

    private static Dialog myDialog;
    @SuppressLint("StaticFieldLeak")
    private static ImageView yes, classic, recode;

    public static void CreateImgDialog(final Context context, String tag) {
        myDialog = new Dialog(context, R.style.DialogSlideAnim);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.show_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        GifImageView gifImageView = myDialog.findViewById(R.id.gif_dialog);

        int resID = context.getResources().getIdentifier(tag, "drawable", context.getPackageName());
        gifImageView.setImageResource(resID);

        myDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        myDialog.show();
        myDialog.getWindow().getDecorView().setSystemUiVisibility(((AppCompatActivity) context).getWindow().getDecorView().getSystemUiVisibility());
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    public static void CreateDialogSuccess(final Context context, String text, String coins, int size) {
        myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.popup_success);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        TextView textView = myDialog.findViewById(R.id.text);
        TextView coin = myDialog.findViewById(R.id.txtcoin);

        yes = myDialog.findViewById(R.id.yes);
        ImageView no = myDialog.findViewById(R.id.no);

        textView.setText(text);
        textView.setTextSize(size);
        coin.setText(coins+" COINS");

        myDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        myDialog.show();
        myDialog.getWindow().getDecorView().setSystemUiVisibility(((AppCompatActivity) context).getWindow().getDecorView().getSystemUiVisibility());
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }

    public static void CreateDialogError(final Context context, String text, int size) {
        myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.popup_error);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        TextView textView = myDialog.findViewById(R.id.text);
        ImageView ok = myDialog.findViewById(R.id.ok);

        textView.setText(text);
        textView.setTextSize(size);

        myDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        myDialog.show();
        myDialog.getWindow().getDecorView().setSystemUiVisibility(((AppCompatActivity) context).getWindow().getDecorView().getSystemUiVisibility());
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }

    public static ImageView getYes() {
        return yes;
    }

    public static Dialog getMyDialog() {
        return myDialog;
    }

    public static void CreateDialogMode(final Context context) {
        myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.popup_mode);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        classic = myDialog.findViewById(R.id.classic);
        recode = myDialog.findViewById(R.id.recode);

        myDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        myDialog.show();
        myDialog.getWindow().getDecorView().setSystemUiVisibility(((AppCompatActivity) context).getWindow().getDecorView().getSystemUiVisibility());
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public static ImageView getClassic() {
        return classic;
    }

    public static ImageView getRecode() {
        return recode;
    }
}
