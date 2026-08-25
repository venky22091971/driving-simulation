package com.simulation;

public class Car {

    private final String name;

    private int x;
    private int y;

    private Direction direction;
    private final String commands;

    private Collision collision;

    public Car(String name,
               int x,
               int y,
               Direction direction,
               String commands) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Car name cannot be empty.");
        }

        if (direction == null) {
            throw new IllegalArgumentException(
                    "Direction cannot be null.");
        }

        String normalizedCommands =
                commands == null
                        ? ""
                        : commands.trim().toUpperCase();

        if (!normalizedCommands.matches("[LRF]*")) {
            throw new IllegalArgumentException(
                    "Commands may contain only L, R and F.");
        }

        this.name = name.trim();
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.commands = normalizedCommands;
    }

    public void executeCommand(char command, Field field) {

        switch (command) {

            case 'L':
                turnLeft();
                break;

            case 'R':
                turnRight();
                break;

            case 'F':
                moveForward(field);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported command: " + command);
        }
    }

    private void turnLeft() {
        direction = direction.turnLeft();
    }

    private void turnRight() {
        direction = direction.turnRight();
    }

    private void moveForward(Field field) {

        int newX = x;
        int newY = y;

        switch (direction) {

            case N:
                newY++;
                break;

            case E:
                newX++;
                break;

            case S:
                newY--;
                break;

            case W:
                newX--;
                break;

            default:
                throw new IllegalStateException(
                        "Unknown direction.");
        }

        /*
         * If the new position is outside the field,
         * the forward command is ignored.
         */
        if (field.contains(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }

    public boolean hasCommandAt(int index) {
        return index >= 0 && index < commands.length();
    }

    public char getCommandAt(int index) {

        if (!hasCommandAt(index)) {
            throw new IllegalArgumentException(
                    "No command available at index: " + index);
        }

        return commands.charAt(index);
    }

    public void collideWith(String otherCarName,
                            Position position,
                            int step) {

        if (collision == null) {
            collision = new Collision(
                    otherCarName,
                    position,
                    step);
        }
    }

    public boolean hasCollided() {
        return collision != null;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getCommands() {
        return commands;
    }

    public Collision getCollision() {
        return collision;
    }
}