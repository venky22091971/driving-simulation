package com.simulation;

public class Collision {

    private final String otherCarName;
    private final Position position;
    private final int step;

    public Collision(String otherCarName,
                     Position position,
                     int step) {

        this.otherCarName = otherCarName;
        this.position = position;
        this.step = step;
    }

    public String getOtherCarName() {
        return otherCarName;
    }

    public Position getPosition() {
        return position;
    }

    public int getStep() {
        return step;
    }
}