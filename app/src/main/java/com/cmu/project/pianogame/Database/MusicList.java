package com.cmu.project.pianogame.Database;

public class MusicList {

    private String name, tag;
    private int price, number;
    private boolean buy;

    public MusicList(String name, String tag, int price, int number, boolean buy) {
        this.name = name;
        this.tag = tag;
        this.price = price;
        this.number = number;
        this.buy = buy;
    }

    public MusicList() {}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }
}
