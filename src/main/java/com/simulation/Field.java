package com.simulation;

public class Field {

    private final int width;
    private final int height;

    public Field(int width, int height) {

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Field width and height must be greater than zero.");
        }

        this.width = width;
        this.height = height;
    }

    public boolean contains(int x, int y) {

        return x >= 0 &&
                x < width &&
                y >= 0 &&
                y < height;
    }

    public boolean contains(Position position) {
        return contains(position.getX(), position.getY());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}