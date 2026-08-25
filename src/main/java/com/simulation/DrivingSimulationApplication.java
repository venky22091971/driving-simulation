package com.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DrivingSimulationApplication {

    private final Scanner scanner;

    public DrivingSimulationApplication() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {

        DrivingSimulationApplication application =
                new DrivingSimulationApplication();

        application.start();
    }

    public void start() {

        System.out.println(
                "Welcome to Car Crash Java!");
        System.out.println();

        boolean continueApplication = true;

        while (continueApplication) {

            Field field = readField();

            List<Car> cars = new ArrayList<Car>();

            boolean configuring = true;

            while (configuring) {

                printMainMenu();

                String option =
                        readLineOrExit();

                switch (option) {

                    case "1":

                        Car car = readCar(field, cars);

                        cars.add(car);

                        printCars(cars);

                        break;

                    case "2":

                        if (cars.isEmpty()) {

                            System.out.println(
                                    "Please add at least one car before running the simulation.");

                            System.out.println();

                            break;
                        }

                        printCars(cars);

                        SimulationService simulationService =
                                new SimulationService();

                        simulationService.run(field, cars);

                        printResults(cars);

                        configuring = false;

                        break;

                    default:

                        System.out.println(
                                "Invalid option. Please select 1 or 2.");

                        System.out.println();
                }
            }

            continueApplication = askStartOver();
        }

        System.out.println();
        System.out.println(
                "Thank you for running the simulation. Goodbye!");
    }

    private String readLineOrExit() {
 
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input stream closed. Exiting...");
            System.exit(0);
            return "";
        }
 
        return scanner.nextLine().trim();
    }

    private Field readField() {
 
        while (true) {
 
            System.out.println(
                    "Please enter the width and height of the simulation field in x y format:");
 
            String input =
                    readLineOrExit();

            String[] values =
                    input.split("\\s+");

            if (values.length != 2) {

                System.out.println(
                        "Invalid input. Example: 10 10");

                continue;
            }

            try {

                int width =
                        Integer.parseInt(values[0]);

                int height =
                        Integer.parseInt(values[1]);

                Field field =
                        new Field(width, height);

                System.out.println();

                System.out.printf(
                        "You have created a field of %d x %d.%n%n",
                        width,
                        height);

                return field;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Width and height must be valid integers.");

            } catch (IllegalArgumentException e) {

                System.out.println(e.getMessage());
            }
        }
    }

    private Car readCar(Field field,
                        List<Car> existingCars) {

        String carName =
                readCarName(existingCars);

        PositionAndDirection initial =
                readInitialPosition(
                        carName,
                        field,
                        existingCars);

        String commands =
                readCommands(carName);

        return new Car(
                carName,
                initial.x,
                initial.y,
                initial.direction,
                commands);
    }

    private String readCarName(List<Car> cars) {

        while (true) {

            System.out.println(
                    "Please enter the name of the car:");

            String name =
                    readLineOrExit();

            if (name.isEmpty()) {

                System.out.println(
                        "Car name cannot be empty.");

                continue;
            }

            boolean duplicate = false;

            for (Car car : cars) {

                if (car.getName()
                        .equalsIgnoreCase(name)) {

                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {

                System.out.println(
                        "A car with this name already exists.");

                continue;
            }

            return name;
        }
    }

    private PositionAndDirection readInitialPosition(
            String carName,
            Field field,
            List<Car> cars) {

        while (true) {

            System.out.printf(
                    "Please enter initial position of car %s in x y Direction format:%n",
                    carName);

            String input =
                    readLineOrExit();

            String[] values =
                    input.split("\\s+");

            if (values.length != 3) {

                System.out.println(
                        "Invalid input. Example: 1 2 N");

                continue;
            }

            try {

                int x =
                        Integer.parseInt(values[0]);

                int y =
                        Integer.parseInt(values[1]);

                Direction direction =
                        Direction.from(values[2]);

                if (!field.contains(x, y)) {

                    System.out.println(
                            "Initial position must be inside the simulation field.");

                    continue;
                }

                if (isPositionOccupied(
                        new Position(x, y),
                        cars)) {

                    System.out.println(
                            "Another car already occupies this position.");

                    continue;
                }

                return new PositionAndDirection(
                        x,
                        y,
                        direction);

            } catch (NumberFormatException e) {

                System.out.println(
                        "X and Y must be valid integers.");

            } catch (IllegalArgumentException e) {

                System.out.println(e.getMessage());
            }
        }
    }

    private String readCommands(String carName) {

        while (true) {

            System.out.printf(
                    "Please enter the commands for car %s:%n",
                    carName);

            String commands =
                    readLineOrExit()
                            .toUpperCase();

            if (!commands.matches("[LRF]+")) {

                System.out.println(
                        "Commands may contain only L, R and F.");

                continue;
            }

            return commands;
        }
    }

    private boolean isPositionOccupied(
            Position position,
            List<Car> cars) {

        for (Car car : cars) {

            if (car.getPosition()
                    .equals(position)) {

                return true;
            }
        }

        return false;
    }

    private void printMainMenu() {

        System.out.println(
                "Please choose from the following options:");

        System.out.println(
                "[1] Add a car to field");

        System.out.println(
                "[2] Run simulation");

        System.out.println();
    }

    private void printCars(List<Car> cars) {

        System.out.println();

        System.out.println(
                "Your current list of cars are:");

        for (Car car : cars) {

            System.out.printf(
                    "- %s, (%d,%d) %s, %s%n",
                    car.getName(),
                    car.getX(),
                    car.getY(),
                    car.getDirection(),
                    car.getCommands());
        }

        System.out.println();
    }

    private void printResults(List<Car> cars) {

        System.out.println(
                "After simulation, the result is:");

        for (Car car : cars) {

            if (car.hasCollided()) {

                Collision collision =
                        car.getCollision();

                System.out.printf(
                        "- %s, collides with %s at (%d,%d) at step %d%n",
                        car.getName(),
                        collision.getOtherCarName(),
                        collision.getPosition().getX(),
                        collision.getPosition().getY(),
                        collision.getStep());

            } else {

                System.out.printf(
                        "- %s, (%d,%d) %s%n",
                        car.getName(),
                        car.getX(),
                        car.getY(),
                        car.getDirection());
            }
        }

        System.out.println();
    }

    private boolean askStartOver() {

        while (true) {

            System.out.println(
                    "Please choose from the following options:");

            System.out.println(
                    "[1] Start over");

            System.out.println(
                    "[2] Exit");

            String option =
                    readLineOrExit();

            if ("1".equals(option)) {

                System.out.println();
                return true;
            }

            if ("2".equals(option)) {
                return false;
            }

            System.out.println(
                    "Invalid option. Please select 1 or 2.");
        }
    }

    private static class PositionAndDirection {

        private final int x;
        private final int y;
        private final Direction direction;

        private PositionAndDirection(
                int x,
                int y,
                Direction direction) {

            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
}