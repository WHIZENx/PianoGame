package com.cmu.project.pianogame.Database;

public class Energy {

    private String id;
    private int energy;

    public Energy(String id, int energy) {
        this.id = id;
        this.energy = energy;
    }

    public Energy() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
