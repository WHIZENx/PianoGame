package com.cmu.project.pianogame.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.cmu.project.pianogame.Game.Settings.GameSetting;

public class Model {

    protected float height, left, top, width;
    protected Paint paint = new Paint();

    Model(float l, float t, float w, float h) {
        width = w;
        height = h;
        left = l;
        top = t;
    }
}

class NoneEntity extends Model {

    NoneEntity(float l, float t, float w, float h) { super(l, t, w, h); }
}

abstract class Entity extends Model {

    protected boolean check;
    protected float width_entity, height_entity;

    Entity(float l, float t, float w, float h, boolean c) {
        super(l, t, w, h);
        check = c;
    }

    boolean BoxHitBox(float w, float h, float add_w, float add_h, float x, float y) { return left - add_w <= x && x <= (left + w) && height - add_h <= y && y <= (height + h); }

    void clear_draw(Canvas canvas) {
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, 0, 0, paint);
    }

    boolean checkOver(float h) { return height >= h; }

    void setCheck(boolean c) { check = c; }

    void setSpeed(int speed) {
        top += speed;
        height += speed;
    }

    abstract void drawBox(Canvas canvas);
}

class BoxNormal extends Entity {

    protected int overtime;
    protected boolean over;

    BoxNormal(float l, float t, float w, float h, boolean c) { super(l, t, w, h, c); }

    BoxNormal() { super(0, 0, 0,0, true); }

    void drawBox(Canvas canvas) {
        if (!over) {
            if (!check) paint.setColor(Color.parseColor(StartGame.boxcolor));
            else paint.setColor(Color.argb(100, 255, 255, 255));
        } else {
            if (!check) {
                overtime += 1;
                if (overtime >= 1 && overtime < 20) paint.setColor(Color.RED);
                else if (overtime >= 20 && overtime < 40) paint.setColor(Color.parseColor(StartGame.boxcolor));
                else overtime = 0;
            }
        }
        canvas.drawRect(left, top, width, height, paint);
    }

    void drawStartBox(Canvas canvas, String BoxColor) {
        if (!over) {
            if (!check) paint.setColor(Color.parseColor(BoxColor));
            else paint.setColor(Color.argb(100, 255, 255, 255));
        } else {
            if (!check) {
                overtime += 1;
                if (overtime >= 1 && overtime < 20) paint.setColor(Color.RED);
                else if (overtime >= 20 && overtime < 40) paint.setColor(Color.parseColor(BoxColor));
                else overtime = 0;
            }
        }
        canvas.drawRect(left, top, width, height, paint);
    }
}

class BoxSlow extends Entity {

    BoxSlow(float l, float t, float w, float h, boolean c) {
        super(l, t, w, h, c);
    }

    void drawBox(Canvas canvas) {
        if (!check) {
            paint.setColor(Color.BLUE);
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}

class BoxBomb extends Entity {

    BoxBomb(float l, float t, float w, float h, boolean c) {
        super(l, t, w, h, c);
    }

    void drawBox(Canvas canvas) {
        if (!check) {
            paint.setColor(Color.RED);
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}

class BoxJackpot extends Entity {

    private boolean check_width;
    protected boolean running;

    BoxJackpot(float l, float t, float w, float h, boolean c) {
        super(l, t, w, h, c);
    }

    void drawBox(Canvas canvas) {
        if (!check) {
            paint.setColor(Color.YELLOW);
            if (running) {
                int x_move = GameSetting.speed_jackpot;;
                if (!check_width) {
                    left += x_move;
                    width += x_move;
                    if (width >= ScoreMode.screenWidth) check_width = true;
                } else {
                    left -= x_move;
                    width -= x_move;
                    if (left <= 0) check_width = false;
                }
            }
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}

class BoxLucky extends Entity {

    protected boolean running;

    BoxLucky(float l, float t, float w, float h, boolean c) { super(l, t, w, h, c); }

    void drawBox(Canvas canvas) {
        if (!check) {
            paint.setColor(Color.GREEN);
            if (running) {
                if (Math.abs(left - width) > width_entity * GameSetting.speed_lucky && Math.abs(top - height) > height_entity * GameSetting.speed_lucky) {
                    left += width_entity * GameSetting.speed_lucky;
                    width -= width_entity * GameSetting.speed_lucky;
                    top -= height_entity * GameSetting.speed_lucky;
                    height += height_entity * GameSetting.speed_lucky;
                } else {
                    left = 0;
                    top = 0;
                    width = 0;
                    height = 0;
                    check = true;
                }
            }
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}

class Logo extends BoxNormal {

    protected Bitmap bitmap;
    protected float left, top;

    Logo(Bitmap b, float l, float t, float h) {
        super(l, t, 0, 0, true);
        bitmap = b;
        left = l + b.getWidth()/2f;
        top = t - b.getHeight()/2f - h/2;
    }

    Logo() { super(); }

    void drawLogo(Canvas canvas) { if(!check) canvas.drawBitmap(bitmap, left, top, paint); }

    void setSpeed(int speed) { top += speed; }
}

class Stroke extends NoneEntity {

    Stroke(float l, float t, float w, float h) {
        super(l, t, w, h);
    }

    void drawStroke(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, width, height, paint);
    }
}

class StatusBarSlow extends NoneEntity {

    StatusBarSlow(float l, float t, float w, float h) {
        super(l, t, w, h);
    }

    void drawStatusBar(Canvas canvas, float percent) {
        if (width >= left) {
            paint.setColor(Color.CYAN);
            width -= percent;
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}

class StatusBarLucky extends NoneEntity {

    StatusBarLucky(float l, float t, float w, float h) {
        super(l, t, w, h);
    }

    void drawStatusBar(Canvas canvas, float percent) {
        if (width >= left) {
            paint.setColor(Color.GREEN);
            width -= percent;
            canvas.drawRect(left, top, width, height, paint);
        }
    }
}