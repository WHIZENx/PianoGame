package com.cmu.project.pianogame.Database;

public class Theme {

    private String id;
    private String cur_theme;

    public Theme(String id, String cur_theme) {
        this.id = id;
        this.cur_theme = cur_theme;
    }

    public Theme() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCur_theme() {
        return cur_theme;
    }

    public void setCur_theme(String cur_theme) {
        this.cur_theme = cur_theme;
    }
}
