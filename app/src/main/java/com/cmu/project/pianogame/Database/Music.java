package com.cmu.project.pianogame.Database;

public class Music {

    private String id;
    private String cur_music, cur_name_music;

    public Music(String id, String cur_music, String cur_name_music) {
        this.id = id;
        this.cur_music = cur_music;
        this.cur_name_music = cur_name_music;
    }

    public Music() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCur_music() {
        return cur_music;
    }

    public void setCur_music(String cur_music) {
        this.cur_music = cur_music;
    }

    public String getCur_name_music() {
        return cur_name_music;
    }

    public void setCur_name_music(String cur_name_music) {
        this.cur_name_music = cur_name_music;
    }
}
