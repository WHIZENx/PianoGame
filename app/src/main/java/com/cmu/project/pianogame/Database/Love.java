package com.cmu.project.pianogame.Database;

public class Love {

    private String id;
    private int love;

    public Love(String id, int love) {
        this.id = id;
        this.love = love;
    }

    public Love() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }
}
