package com.simulation;

public enum Direction {

    N, E, S, W;

    public Direction turnLeft() {
        switch (this) {
            case N:
                return W;
            case W:
                return S;
            case S:
                return E;
            case E:
                return N;
            default:
                throw new IllegalStateException("Invalid direction");
        }
    }

    public Direction turnRight() {
        switch (this) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
            default:
                throw new IllegalStateException("Invalid direction");
        }
    }

    public static Direction from(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Direction cannot be null.");
        }

        try {
            return Direction.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Direction must be N, E, S or W.");
        }
    }
}