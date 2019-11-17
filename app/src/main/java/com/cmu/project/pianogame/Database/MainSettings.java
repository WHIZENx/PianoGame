package com.cmu.project.pianogame.Database;

public class MainSettings {

    private int max_energy, price_box;
    private long count_time;

    public MainSettings(int max_energy, int price_box, long count_time) {
        this.max_energy = max_energy;
        this.price_box = price_box;
        this.count_time = count_time;
    }

    public MainSettings() {
    }

    public int getMax_energy() {
        return max_energy;
    }

    public void setMax_energy(int max_energy) {
        this.max_energy = max_energy;
    }

    public int getPrice_box() {
        return price_box;
    }

    public void setPrice_box(int price_box) {
        this.price_box = price_box;
    }

    public long getCount_time() {
        return count_time;
    }

    public void setCount_time(long count_time) {
        this.count_time = count_time;
    }
}
