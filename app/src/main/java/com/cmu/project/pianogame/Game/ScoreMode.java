package com.cmu.project.pianogame.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cmu.project.pianogame.Game.Resource.Background;
import com.cmu.project.pianogame.Game.Resource.PlayAnimation;
import com.cmu.project.pianogame.Game.Settings.GameSetting;
import com.cmu.project.pianogame.Game.Utils.ItemHitBox;
import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("ViewConstructor")
public class ScoreMode extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    // For drawing
    private Paint scorePaint = new Paint(), start = new Paint();
    private Paint lovePaint = new Paint(), numlovePaint = new Paint(), closePaint = new Paint();
    private Canvas canvas;
    private Rect score_rect, start_rect, love_rect;
    private SurfaceHolder ourHolder;

    // Holds a reference to the Activity
    protected Context context;

    // Screen resolution
    protected static int screenWidth;
    private int screenHeight;

    private float centerWidth, centerHeight;

    private Random random;

    private Background background, bg_slow;

    private int BoxEntity = GameSetting.box_entity;
    private int life = GameSetting.life;
    private float height, width;
    private Entity[] boxes = new Entity[BoxEntity];
    private Logo[] logos = new Logo[BoxEntity];
    private float[] top_list = new float[BoxEntity];
    private Stroke[] stroke = new Stroke[4];
    private StatusBarSlow statusBarSlow;
    private StatusBarLucky statusBarLucky;

    private int speed, count, score, time_out, box_over;

    private boolean preStart, pregameOver, gameOver, preLove, postLove;

    private Bitmap logo_img, love_img, close;

    private PlayAnimation angle;

    private int coins, loves, energy;
    private int save_position;

    @SuppressLint("ResourceType")
    ScoreMode(Context context, int screenWidth, int screenHeight, int coins, int loves, int energy) {
        super(context);
        this.context = context;
        this.coins = coins;
        this.loves = loves;
        this.energy = energy;

        int statusbar_height = Options.NavbarSize(context);
        this.screenWidth = screenWidth;
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

        bg_slow = new Background(
                this.context,
                screenWidth,
                screenHeight,
                "slow",  0, bg_size);

        logo_img = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        love_img = BitmapFactory.decodeResource(getResources(), R.drawable.love);
        close = BitmapFactory.decodeResource(getResources(), R.drawable.close);

        angle = new PlayAnimation(context, R.drawable.angle1);

        ourHolder = getHolder();
        ourHolder.setFixedSize(screenWidth, screenHeight + statusbar_height);

        score_rect = new Rect(0,0,screenWidth,screenHeight);
        start_rect = new Rect(0,0,screenWidth,screenHeight);
        love_rect = new Rect(0,0,screenWidth,screenHeight);

        centerWidth = screenWidth / 2;
        centerHeight = screenHeight / 2;

        scorePaint.setColor(Color.RED);
        Typeface font = Typeface.create(Typeface.createFromAsset(context.getAssets(), GameSetting.font_game), Typeface.NORMAL);
        scorePaint.setTypeface(font);
        start.setTypeface(font);
        start.setTextSize(70);
        start.getTextBounds("START", 0, "START".length(), start_rect);

        numlovePaint.setTypeface(font);
        numlovePaint.setTextSize(100);

        random = new Random();

        height = ((screenHeight + setbackKey) / 4) + 10;
        width = screenWidth / 4;

        float[] point_box = new float[]{0, screenWidth / 4, (screenWidth / 2f), (3 * (screenWidth / 4f))}; //Left
        float[] width_block = new float[BoxEntity]; // Top

        for(int i = 0; i<boxes.length; i++) {
            float height_box = -height * i + height*3;
            if (i == 0) width_block[i] = point_box[random.nextInt(4)];
            else while (width_block[i - 1] == width_block[i]) width_block[i] = point_box[random.nextInt(4)];
            drawBox(i, width_block[i], height_box);
        }

        for(int i=0;i<stroke.length;i++) { stroke[i] = new Stroke(point_box[i], 0, width + point_box[i], screenHeight + setbackKey); }
    }

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

    private void drawBox(int num, float width_box, float height_box) {
        float left_num = width_box + GameSetting.width_box;
        float width_num = width + width_box - GameSetting.width_box;
        if (num == 0) {
            boxes[num] = new BoxNormal(left_num, height_box, width_num, height_box - height, false);
            logos[num] = new Logo();
        }
        else {
            if (random.nextInt(GameSetting.random_blank_box) != 0) {
                logos[num] = new Logo(logo_img, width_box, height_box, height);
                switch (random.nextInt(GameSetting.random_mode_box)) {
                    case 1: boxes[num] = new BoxSlow(left_num, height_box, width_num, height_box - height, false); break;
                    case 2: boxes[num] = new BoxBomb(left_num, height_box, width_num, height_box - height, false); break;
                    case 3: boxes[num] = new BoxJackpot(left_num, height_box, width_num, height_box - height, false); break;
                    case 4: boxes[num] = new BoxLucky(left_num, height_box, width_num, height_box - height, false); break;
                    default: boxes[num] = new BoxNormal(left_num, height_box, width_num, height_box - height, false); break;
                }
            }
            else {
                boxes[num] = new BoxNormal();
                logos[num] = new Logo();
            }
        }
        boxes[num].width_entity = width;
        boxes[num].height_entity = height;
    }

    private boolean longtouch;
    private final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            longtouch = true;
        }
    });

    private float x, y;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        int action = event.getAction();

        gestureDetector.onTouchEvent(event);
        if (action == MotionEvent.ACTION_DOWN)
            if (!preLove) {
                if (!preStart) onHit(save_position);
                else for (int i = 0; i < boxes.length; i++) onHit(i);
            } else {
                if (ItemHitBox.HitBox(centerWidth - close.getWidth()/2f,
                        centerHeight + close.getHeight()/2f + love_img.getHeight()/2f + love_rect.height(),
                        close.getWidth(),
                        close.getHeight(),
                        x,
                        y)) {
                    life = 0;
                }
                if (life > 0 && ItemHitBox.HitBox(centerWidth - love_img.getWidth()/2f,
                        centerHeight - love_img.getHeight()/2f,
                        love_img.getWidth(),
                        love_img.getHeight(),
                        x,
                        y)) {
                    life--;
                    loves--;
                    postLove = true;
                }
        }
        if (action == MotionEvent.ACTION_MOVE) {
            if (longtouch && mode_lucky) {
                for(int i = 0; i< boxes.length; i++) onHit(i);
                circle_x = x;
                circle_y = y;
            }
        }
        return true;
    }

    private boolean eff_hit;
    private int save_speed = GameSetting.start_speed;
    private float circle_x, circle_y;
    private void onHit(int num) {
        if (boxes[num].BoxHitBox(width + width *GameSetting.percent_hitbox, height + height*GameSetting.percent_hitbox,  width *GameSetting.percent_hitbox, height*GameSetting.percent_hitbox, x, y) && !boxes[num].check && !pregameOver && !mode_bomb
                || mode_lucky && boxes[num].BoxHitBox(angle.mMovie.width(), angle.mMovie.height(), 0, 0, circle_x, circle_y)) {
            if (num == save_position) {
                postStart = false;
                preStart = true;
                pre_speed = GameSetting.start_speed;
                Sound.Audio(context, Options.getResourceId(context, "raw", StartGame.musics));
                if (save_position == 0) Sound.Play();
                else Sound.Resume(Sound.getLength());
                Sound.Loop(true);
            }
            boxes[num].setCheck(true);
            if(boxes[num] instanceof BoxSlow) {
                Sound.playsound(context, 1);
                if (!mode_slow) save_speed = speed;
                mode_slow = true;
                time_slow = 0;
                statusBarSlow = new StatusBarSlow(centerWidth - 100, score_rect.height()+50, centerWidth+100, score_rect.height()+60);
            } else if(boxes[num] instanceof BoxBomb) {
                Sound.playsound(context, 2);
                mode_bomb = true;
                Sound.Stop();
                Sound.setSound(null);
            } else if(boxes[num] instanceof BoxJackpot) {
                Sound.playsound(context, 0);
                score += 10;
                eff_hit = true;
            } else if(boxes[num] instanceof BoxLucky) {
                Sound.playsound(context, 0);
                if (!mode_lucky) {
                    circle_x = x;
                    circle_y = y;
                }
                mode_lucky = true;
                time_lucky = 0;
                statusBarLucky = new StatusBarLucky(centerWidth - 100, score_rect.height()+50+20, centerWidth+100, score_rect.height()+60+20);
            } else {
                Sound.playsound(context, 0);
                score += 1;
                eff_hit = true;
                if (num != save_position) logos[num].setCheck(false);
            }
        }
    }

    private boolean mode_slow, mode_bomb, mode_lucky, running_slow;
    private int time_slow;
    private void modeSlow(Canvas canvas) {
        time_slow += 1;
        if (time_slow == GameSetting.finish_time_slow) {
            mode_slow = false;
            time_slow = 0;
            postStart = false;
            running_slow = false;
        } else {
            if (!running_slow) {
                save_speed = speed;
                running_slow = true;
            }
            bg_slow.DrawBackground(canvas);
            speed = GameSetting.speed_freeze;
            statusBarSlow.drawStatusBar(canvas,  GameSetting.percent_bar(GameSetting.finish_time_slow));
        }
    }

    private int time_bomb;
    private void modeBomb(Canvas canvas) {
        if (mode_slow) mode_slow = false;
        time_bomb += 1;
        if (time_bomb == GameSetting.finish_time_bomb) {
            pregameOver = true;
            gameOver = true;
            time_bomb = 0;
        }
        speed = 0;
        if (time_bomb >= 1 && time_bomb < 20 || time_bomb >= 40 && time_bomb < 60 || time_bomb >= 80 && time_bomb < 100) canvas.drawColor(Color.argb(150,200,0,0));
    }

    private int time_lucky;
    private void modeLucky(Canvas canvas) {
        time_lucky += 1;
        if (time_lucky == GameSetting.finish_time_lucky) {
            mode_lucky = false;
            longtouch = false;
            time_lucky = 0;
        } else {
            angle.PlayAnim(canvas, circle_x, circle_y);
            longtouch = true;
            statusBarLucky.drawStatusBar(canvas,  GameSetting.percent_bar(GameSetting.finish_time_lucky));
        }
    }

    private boolean postStart;
    private int pre_speed;
    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            background.DrawBackground(canvas);
            for (Stroke value : stroke) value.drawStroke(canvas);

            if (!pregameOver) {
                if (preStart) {
                    if (!mode_slow) {
                        if (postStart) {
                            if (count == GameSetting.count_speed) {
                                speed += 1;
                                count = 0;
                            }
                            count += 1;
                        } else {
                            if (pre_speed >= save_speed) {
                                pre_speed = GameSetting.start_speed;
                                postStart = true;
                                speed = save_speed;
                            } else {
                                speed = pre_speed;
                                pre_speed++;
                            }
                        }
                    }
                }

                for (int i = 0; i < boxes.length; i++) {
                    if (preStart) {
                        if (boxes[i] instanceof BoxLucky) ((BoxLucky) boxes[i]).running = true;
                        else if (boxes[i] instanceof BoxJackpot) ((BoxJackpot) boxes[i]).running = true;
                    }
                    if (mode_bomb) {
                        if (boxes[i] instanceof BoxLucky) ((BoxLucky) boxes[i]).running = false;
                        else if (boxes[i] instanceof BoxJackpot) ((BoxJackpot) boxes[i]).running = false;
                    }
                    drawbox(i);
                }

                if (!boxes[save_position].check) canvas.drawText("START", (boxes[save_position].left + boxes[save_position].width - start_rect.width()) / 2, (boxes[save_position].top + boxes[save_position].height) / 2, start);

                if (mode_slow && !mode_bomb) modeSlow(canvas);
                if (mode_lucky && !mode_bomb) modeLucky(canvas);
                if (mode_bomb) modeBomb(canvas);

                if (!eff_hit) scorePaint.setTextSize(150);
                else {
                    scorePaint.setTextSize(200);
                    eff_hit = false;
                }
                if (!mode_bomb) {
                    scorePaint.getTextBounds("" + score, 0, ("" + score).length(), score_rect);
                    canvas.drawText("" + score, centerWidth - score_rect.width() / 2f, 120, scorePaint);
                }
            } else if (!gameOver){
                if(postLove && life == 0) Sound.Stop();
                else Sound.Pause();
                speed -= 1;
                for(int i = 0; i< boxes.length; i++) {
                    if (boxes[i] instanceof BoxLucky) ((BoxLucky) boxes[i]).running = false;
                    else if (boxes[i] instanceof BoxJackpot) ((BoxJackpot) boxes[i]).running = false;
                    checkbox(i);
                }
                if (preLove && life > 0) {
                    time_out = 0;
                    PlayAnimation.fadeIn(numlovePaint);
                    PlayAnimation.fadeIn(lovePaint);
                    PlayAnimation.fadeIn(closePaint);
                    canvas.drawBitmap(love_img, centerWidth - love_img.getWidth() / 2f, centerHeight - love_img.getHeight() / 2f, lovePaint);
                    numlovePaint.getTextBounds("x" + loves, 0, ("x" + loves).length(), love_rect);
                    canvas.drawText("x" + loves, centerWidth - love_rect.width() / 2f, centerHeight + love_img.getHeight() / 2f + love_rect.height(), numlovePaint);
                    canvas.drawBitmap(close, centerWidth - close.getWidth() / 2f, centerHeight + close.getHeight() / 2f + love_img.getHeight() / 2f + love_rect.height(), closePaint);
                }
                if (postLove) {
                    boxes[save_position] = new BoxNormal(boxes[save_position].left, boxes[save_position].top, boxes[save_position].width, boxes[save_position].height, false);
                    preStart = false;
                    pregameOver = false;
                    preLove = false;
                    speed = 0;
                }
            } else {
                Intent intent = new Intent(context, ScoreGameOver.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("score", score);
                intent.putExtra("coins", coins);
                intent.putExtra("loves", loves);
                intent.putExtra("energy", energy);
                Options.ChangePage((AppCompatActivity) context, intent);
            }

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawbox(int num) {
        if (boxes[num].checkOver(screenHeight+height)) {
            boxes[num].clear_draw(canvas);
            logos[num].clear_draw(canvas);
        }
        if (boxes[num].checkOver(screenHeight) && !boxes[num].check && boxes[num] instanceof BoxNormal) {
            if (postLove) postLove = false;
            save_speed = speed;
            pregameOver = true;
            box_over = num;
            speed = 0;
            mode_slow = false;
            time_slow = 0;
            pre_speed = 0;
        } else {
            if (boxes[num].top >= 0) {
                if (num == save_position) ((BoxNormal) boxes[num]).drawStartBox(canvas, GameSetting.start_color);
                else boxes[num].drawBox(canvas);
                logos[num].drawLogo(canvas);
            }
            boxes[num].setSpeed(speed);
            logos[num].setSpeed(speed);
        }
        top_list[num] = boxes[num].top;
    }

    private void checkbox(int num) {
        if (boxes[num].top + speed >= top_list[num] - height) {
            boxes[num].setSpeed(speed);
            logos[num].setSpeed(speed);
            boxes[num].drawBox(canvas);
            logos[num].drawLogo(canvas);
        } else {
            boxes[num].setSpeed(0);
            logos[num].setSpeed(0);
            if (num != box_over) {
                boxes[num].drawBox(canvas);
                logos[num].drawLogo(canvas);
            }
            else {
                if (loves > 0 && life > 0) {
                    save_position = num;
                    boxes[num].drawBox(canvas);
                    logos[num].drawLogo(canvas);
                    preLove = true;
                } else {
                    time_out += 1;
                    if (time_out == GameSetting.time_out) {
                        gameOver = true;
                        time_out = 0;
                    }
                    boxes[num].drawBox(canvas);
                    ((BoxNormal) boxes[num]).over = true;
                }
            }
        }
    }

    // Clean up our thread if the game is stopped
    void pause() {
        if (Sound.getSound() != null || !Sound.isClear() && !mode_bomb && preStart && life != 0) Sound.Pause();
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
