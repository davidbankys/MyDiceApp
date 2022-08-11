package com.db.mydiceapp;

public class Dice {
    private String name;
    private int sides;
    private int sideUp;
    public Dice(int sides) {
        this.sides = sides;
        this.name = getDiceNameFor(sides);
        roll();
    }
    public void roll() {
        int roundRandom = (int) Math.round(Math.random() * 10);
        if (roundRandom == 0) {
            this.sideUp = 1;
        } else {
            this.sideUp = (int) Math.round((roundRandom * this.sides) / 10);
        }
    }

    public int getSideUp() {
        return this.sideUp;
    }
    private String getDiceNameFor(int sides) {
        return this.name = "d" + sides;
    }
}

