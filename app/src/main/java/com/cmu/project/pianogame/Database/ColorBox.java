package com.cmu.project.pianogame.Database;

public class ColorBox {

    private String id, colorbox;

    public ColorBox(String id, String colorbox) {
        this.id = id;
        this.colorbox = colorbox;
    }

    public ColorBox() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColorbox() {
        return colorbox;
    }

    public void setColorbox(String colorbox) {
        this.colorbox = colorbox;
    }
}
