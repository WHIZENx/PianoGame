package com.cmu.project.pianogame.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cmu.project.pianogame.Game.Resource.Background;
import com.cmu.project.pianogame.Game.Settings.GameSetting;
import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.Options.Options;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("ViewConstructor")
public class RecMode extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    private Paint scorePaint = new Paint(), start = new Paint();
    private Canvas canvas;
    private Rect score_rect, start_rect;
    private SurfaceHolder ourHolder;

    protected Context context;

    // Screen resolution
    private int screenHeight;

    private float centerWidth;

    private Background background;
    private int energy;

    private boolean preStart, pregameOver, gameOver;

    private int BoxEntity = GameSetting.box_entity;
    private float height, width;
    private Entity[] boxes = new Entity[BoxEntity];
    private float[] top_list = new float[BoxEntity];
    private Stroke[] stroke = new Stroke[4];

    RecMode(Context context, int screenWidth, int screenHeight, int energy) {
        super(context);
        this.context = context;
        this.energy = energy;

        int statusbar_height = Options.NavbarSize(context);
        this.screenHeight = screenHeight + statusbar_height;

        int bg_size, setbackKey;
        if (Options.HaveNevBar(context)) {
            setbackKey = 0;
            bg_size = 110;
        } else {
            setbackKey = statusbar_height;
            bg_size = 120;
        }

        background = new Background(
                this.context,
                screenWidth,
                screenHeight,
                StartGame.background_name,  0, bg_size);

        ourHolder = getHolder();
        ourHolder.setFixedSize(screenWidth, screenHeight + statusbar_height);

        score_rect = new Rect(0,0,screenWidth,screenHeight);
        start_rect = new Rect(0,0,screenWidth,screenHeight);

        centerWidth = screenWidth / 2;

        scorePaint.setColor(Color.RED);
        Typeface font = Typeface.create(Typeface.createFromAsset(context.getAssets(), GameSetting.font_game), Typeface.NORMAL);
        start.setTypeface(font);
        start.setTextSize(70);
        start.getTextBounds("START", 0, "START".length(), start_rect);
        scorePaint.setTypeface(font);
        scorePaint.setTextSize(150);

        Random random = new Random();

        height = ((screenHeight + setbackKey) / 4) + 10;
        width = screenWidth / 4;

        float[] point_box = new float[]{0, screenWidth / 4, (screenWidth / 2f), (3 * (screenWidth / 4f))};
        float[] width_block = new float[BoxEntity];

        for(int i = 0; i<boxes.length; i++) {
            float height_box = -height * i + height*3;
            if (i == 0) width_block[i] = point_box[random.nextInt(4)];
            else while (width_block[i - 1] == width_block[i]) width_block[i] = point_box[random.nextInt(4)];
            float left_num = width_block[i] + GameSetting.width_box;
            float width_num = width + width_block[i] - GameSetting.width_box;
            boxes[i] = new BoxNormal(left_num, height_box, width_num, height_box - height, false);
        }
        for(int i=0;i<stroke.length;i++) { stroke[i] = new Stroke(point_box[i], 0, width + point_box[i], screenHeight + setbackKey); }
    }

    private long milli = 0, sec = 0, min = 0;
    @Override
    public void run() {
        while (running) {
            long startFrameTime = System.currentTimeMillis();

            // Calculate the fps this frame
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                draw();
                // Control the fps
                long fps = GameSetting.framerate_per_sec / timeThisFrame;
            }
        }
    }

    private float x, y;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (!preStart) onHit(0);
            else for (int i = 0; i < boxes.length; i++) onHit(i);
        }
        return true;
    }

    private int start_speed = GameSetting.start_speed;
    private int speed;
    private void onHit(int num) {
        if (boxes[num].BoxHitBox(width + width * GameSetting.percent_hitbox, height + height * GameSetting.percent_hitbox, width * GameSetting.percent_hitbox, height * GameSetting.percent_hitbox, x, y) && !boxes[num].check && !pregameOver) {
            if (num == 0) {
                speed = start_speed;
                preStart = true;
                Sound.Audio(context, Options.getResourceId(context, "raw", StartGame.musics));
                Sound.Play();
                Sound.Loop(true);
            }
            Sound.playsound(context, 0);
            boxes[num].setCheck(true);
        }
    }

    private String min_string, sec_string, milli_string;
    private int time_out, count;
    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            background.DrawBackground(canvas);
            for (Stroke value : stroke) value.drawStroke(canvas);

            if (!pregameOver) {
                if (preStart) {
                    if (count == GameSetting.count_speed) {
                        speed += 1;
                        count = 0;
                    }
                    count += 1;

                    if (milli == 100) {
                        milli = 0;
                        if (sec == 59) {
                            sec = 0;
                            min++;
                        }
                        else sec++;
                    }
                    else {
                        if (milli < 100) milli += 2;
                        else milli++;
                    }
                }

                if (min < 10) min_string = "0"+min;
                else min_string = ""+min;
                if (sec < 10) sec_string = "0"+sec;
                else sec_string = ""+sec;
                if (milli < 10) milli_string = "0"+milli;
                else if (milli == 100) milli_string = "99";
                else milli_string = ""+milli;

                for (int i = 0; i < boxes.length; i++) drawbox(i);

                if (!boxes[0].check) canvas.drawText("START", (boxes[0].left + boxes[0].width - start_rect.width()) / 2, (boxes[0].top + boxes[0].height) / 2, start);

                scorePaint.getTextBounds(min_string+":"+sec_string+":"+milli_string, 0, (min_string+":"+sec_string+":"+milli_string).length(), score_rect);
                canvas.drawText(min_string+":"+sec_string+":"+milli_string, centerWidth - score_rect.width() / 2f, 120, scorePaint);
            } else if (!gameOver){
                preStart = false;
                Sound.Pause();
                speed -= 1;
                for(int i = 0; i< boxes.length; i++) checkbox(i);
            } else {
                Sound.setSound(null);
                float sec_time = (min*100 + sec*100 + milli)/100f;
                Intent intent = new Intent(context, RecGameOver.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("time", min_string+":"+sec_string+":"+milli_string);
                intent.putExtra("sec_time", sec_time);
                intent.putExtra("energy", energy);
                Options.ChangePage((AppCompatActivity) context, intent);
            }

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private int box_over;
    private void drawbox(int num) {
        if (boxes[num].checkOver(screenHeight+height)) boxes[num].clear_draw(canvas);
        if (boxes[num].checkOver(screenHeight) && !boxes[num].check) {
            start_speed = speed;
            pregameOver = true;
            box_over = num;
            speed = 0;
        } else {
            if (boxes[num].top >= 0) {
                if (num == 0) ((BoxNormal) boxes[num]).drawStartBox(canvas, GameSetting.start_color);
                else boxes[num].drawBox(canvas);
            }
            boxes[num].setSpeed(speed);
        }
        top_list[num] = boxes[num].top;
    }

    private void checkbox(int num) {
        if (boxes[num].top + speed >= top_list[num] - height) {
            boxes[num].setSpeed(speed);
            boxes[num].drawBox(canvas);
        } else {
            boxes[num].setSpeed(0);
            if (num != box_over) boxes[num].drawBox(canvas);
            else {
                time_out += 1;
                if (time_out == GameSetting.time_out) {
                    gameOver = true;
                    time_out = 0;
                    Sound.Stop();
                }
                boxes[num].drawBox(canvas);
                ((BoxNormal) boxes[num]).over = true;
            }
        }
    }

    // Clean up our thread if the game is stopped
    void pause() {
        if (Sound.getSound() != null || !Sound.isClear() && preStart) Sound.Pause();
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    void resume() {
        if (Sound.getSound() != null && preStart) Sound.Resume(Sound.getLength());
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
