package com.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationService {

    public void run(Field field, List<Car> cars) {

        if (field == null) {
            throw new IllegalArgumentException(
                    "Field cannot be null.");
        }

        if (cars == null) {
            throw new IllegalArgumentException(
                    "Cars cannot be null.");
        }

        int maximumCommands = findMaximumCommands(cars);

        for (int commandIndex = 0;
             commandIndex < maximumCommands;
             commandIndex++) {

            /*
             * One simulation step.
             *
             * Every active car executes the command
             * at the same command index.
             */
            for (Car car : cars) {

                if (car.hasCollided()) {
                    continue;
                }

                if (car.hasCommandAt(commandIndex)) {

                    char command =
                            car.getCommandAt(commandIndex);

                    car.executeCommand(command, field);
                }
            }

            /*
             * Steps are user-facing and therefore
             * numbered from 1.
             */
            int step = commandIndex + 1;

            detectCollisions(cars, step);
        }
    }

    private int findMaximumCommands(List<Car> cars) {

        int maximum = 0;

        for (Car car : cars) {

            if (car.getCommands().length() > maximum) {
                maximum = car.getCommands().length();
            }
        }

        return maximum;
    }

    private void detectCollisions(List<Car> cars,
                                  int step) {

        Map<Position, List<Car>> carsByPosition =
                new HashMap<Position, List<Car>>();

        for (Car car : cars) {

            if (car.hasCollided()) {
                continue;
            }

            Position position = car.getPosition();

            List<Car> carsAtPosition =
                    carsByPosition.get(position);

            if (carsAtPosition == null) {

                carsAtPosition = new ArrayList<Car>();

                carsByPosition.put(
                        position,
                        carsAtPosition);
            }

            carsAtPosition.add(car);
        }

        for (Map.Entry<Position, List<Car>> entry
                : carsByPosition.entrySet()) {

            List<Car> carsAtPosition = entry.getValue();

            if (carsAtPosition.size() > 1) {

                markCollision(
                        carsAtPosition,
                        entry.getKey(),
                        step);
            }
        }
    }

    private void markCollision(List<Car> collidedCars,
                               Position position,
                               int step) {

        for (Car car : collidedCars) {

            for (Car other : collidedCars) {

                if (!car.getName().equals(other.getName())) {

                    car.collideWith(
                            other.getName(),
                            position,
                            step);

                    break;
                }
            }
        }
    }
}