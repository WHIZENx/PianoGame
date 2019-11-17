package com.cmu.project.pianogame.Database;

public class Settings {

    private int box_entity;
    private int life;
    private int random_blank_box;
    private int random_mode_box;
    private int start_speed;
    private int finish_time_slow;
    private int speed_freeze;
    private int finish_time_bomb;
    private int finish_time_lucky;
    private float speed_lucky;
    private int speed_jackpot;
    private int count_speed;
    private int framerate_per_sec;
    private int time_out;
    private float percent_hitbox;
    private float width_box;
    private String start_color;

    public Settings(int box_entity, int life, int random_blank_box, int random_mode_box, int start_speed, int finish_time_slow, int speed_freeze, int finish_time_bomb, int finish_time_lucky, float speed_lucky, int speed_jackpot, int count_speed, int framerate_per_sec, int time_out, float percent_hitbox, float width_box, String start_color) {
        this.box_entity = box_entity;
        this.life = life;
        this.random_blank_box = random_blank_box;
        this.random_mode_box = random_mode_box;
        this.start_speed = start_speed;
        this.finish_time_slow = finish_time_slow;
        this.speed_freeze = speed_freeze;
        this.finish_time_bomb = finish_time_bomb;
        this.finish_time_lucky = finish_time_lucky;
        this.speed_lucky = speed_lucky;
        this.speed_jackpot = speed_jackpot;
        this.count_speed = count_speed;
        this.framerate_per_sec = framerate_per_sec;
        this.time_out = time_out;
        this.percent_hitbox = percent_hitbox;
        this.width_box = width_box;
        this.start_color = start_color;
    }

    public Settings() {
    }

    public int getBox_entity() {
        return box_entity;
    }

    public void setBox_entity(int box_entity) {
        this.box_entity = box_entity;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getRandom_blank_box() {
        return random_blank_box;
    }

    public void setRandom_blank_box(int random_blank_box) {
        this.random_blank_box = random_blank_box;
    }

    public int getRandom_mode_box() {
        return random_mode_box;
    }

    public void setRandom_mode_box(int random_mode_box) {
        this.random_mode_box = random_mode_box;
    }

    public int getStart_speed() {
        return start_speed;
    }

    public void setStart_speed(int start_speed) {
        this.start_speed = start_speed;
    }

    public int getFinish_time_slow() {
        return finish_time_slow;
    }

    public void setFinish_time_slow(int finish_time_slow) {
        this.finish_time_slow = finish_time_slow;
    }

    public int getSpeed_freeze() {
        return speed_freeze;
    }

    public void setSpeed_freeze(int speed_freeze) {
        this.speed_freeze = speed_freeze;
    }

    public int getFinish_time_bomb() {
        return finish_time_bomb;
    }

    public void setFinish_time_bomb(int finish_time_bomb) {
        this.finish_time_bomb = finish_time_bomb;
    }

    public int getFinish_time_lucky() {
        return finish_time_lucky;
    }

    public void setFinish_time_lucky(int finish_time_lucky) {
        this.finish_time_lucky = finish_time_lucky;
    }

    public float getSpeed_lucky() {
        return speed_lucky;
    }

    public void setSpeed_lucky(float speed_lucky) {
        this.speed_lucky = speed_lucky;
    }

    public int getSpeed_jackpot() {
        return speed_jackpot;
    }

    public void setSpeed_jackpot(int speed_jackpot) {
        this.speed_jackpot = speed_jackpot;
    }

    public int getCount_speed() {
        return count_speed;
    }

    public void setCount_speed(int count_speed) {
        this.count_speed = count_speed;
    }

    public int getFramerate_per_sec() {
        return framerate_per_sec;
    }

    public void setFramerate_per_sec(int framerate_per_sec) {
        this.framerate_per_sec = framerate_per_sec;
    }

    public int getTime_out() {
        return time_out;
    }

    public void setTime_out(int time_out) {
        this.time_out = time_out;
    }

    public float getPercent_hitbox() {
        return percent_hitbox;
    }

    public void setPercent_hitbox(float percent_hitbox) {
        this.percent_hitbox = percent_hitbox;
    }

    public float getWidth_box() {
        return width_box;
    }

    public void setWidth_box(float width_box) {
        this.width_box = width_box;
    }

    public String getStart_color() {
        return start_color;
    }

    public void setStart_color(String start_color) {
        this.start_color = start_color;
    }
}
