package com.cmu.project.pianogame.Database;

public class Time {

    private String id;
    private float max_time;
    private String time_string;

    public Time(String id, float max_time, String time_string) {
        this.id = id;
        this.max_time = max_time;
        this.time_string = time_string;
    }

    public Time() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getMax_time() {
        return max_time;
    }

    public void setMax_time(float max_time) {
        this.max_time = max_time;
    }

    public String getTime_string() {
        return time_string;
    }

    public void setTime_string(String time_string) {
        this.time_string = time_string;
    }
}
