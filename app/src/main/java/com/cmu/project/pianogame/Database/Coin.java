package com.cmu.project.pianogame.Database;

public class Coin {

    private String id;
    private int coin;

    public Coin(String id, int coin) {
        this.id = id;
        this.coin = coin;
    }

    public Coin() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
