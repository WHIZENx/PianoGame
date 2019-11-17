package com.cmu.project.pianogame.Database;

public class ThemeList {

    private String name, tag;
    private int price, number;
    private boolean buy;

    public ThemeList(String name, String tag, boolean buy, int price, int number) {
        this.name = name;
        this.tag = tag;
        this.buy = buy;
        this.price = price;
        this.number = number;
    }
    public ThemeList() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
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
}
