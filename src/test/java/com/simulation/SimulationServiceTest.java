package com.simulation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SimulationServiceTest {

    @Test
    public void shouldProduceCorrectFinalPositionForSingleCar() {

        Field field =
                new Field(10, 10);

        Car car =
                new Car(
                        "A",
                        1,
                        2,
                        Direction.N,
                        "FFRFFFFRRL");

        List<Car> cars =
                new ArrayList<Car>();

        cars.add(car);

        SimulationService service =
                new SimulationService();

        service.run(field, cars);

        assertEquals(5, car.getX());
        assertEquals(4, car.getY());

        assertEquals(
                Direction.S,
                car.getDirection());

        assertFalse(car.hasCollided());
    }

    @Test
    public void shouldDetectCollisionAtStepSeven() {

        Field field =
                new Field(10, 10);

        Car carA =
                new Car(
                        "A",
                        1,
                        2,
                        Direction.N,
                        "FFRFFFFRRL");

        Car carB =
                new Car(
                        "B",
                        7,
                        8,
                        Direction.W,
                        "FFLFFFFFFF");

        List<Car> cars =
                Arrays.asList(carA, carB);

        SimulationService service =
                new SimulationService();

        service.run(field, cars);

        assertTrue(carA.hasCollided());
        assertTrue(carB.hasCollided());

        assertEquals(
                7,
                carA.getCollision().getStep());

        assertEquals(
                7,
                carB.getCollision().getStep());

        assertEquals(
                new Position(5, 4),
                carA.getCollision().getPosition());

        assertEquals(
                new Position(5, 4),
                carB.getCollision().getPosition());

        assertEquals(
                "B",
                carA.getCollision()
                        .getOtherCarName());

        assertEquals(
                "A",
                carB.getCollision()
                        .getOtherCarName());
    }

    @Test
    public void shouldIgnoreForwardMovementOutsideField() {

        Field field =
                new Field(10, 10);

        Car car =
                new Car(
                        "A",
                        0,
                        0,
                        Direction.S,
                        "F");

        SimulationService service =
                new SimulationService();

        service.run(
                field,
                Arrays.asList(car));

        assertEquals(0, car.getX());
        assertEquals(0, car.getY());

        assertEquals(
                Direction.S,
                car.getDirection());
    }

    @Test
    public void shouldTurnLeft() {

        Field field =
                new Field(10, 10);

        Car car =
                new Car(
                        "A",
                        1,
                        1,
                        Direction.N,
                        "L");

        new SimulationService()
                .run(
                        field,
                        Arrays.asList(car));

        assertEquals(
                Direction.W,
                car.getDirection());
    }

    @Test
    public void shouldTurnRight() {

        Field field =
                new Field(10, 10);

        Car car =
                new Car(
                        "A",
                        1,
                        1,
                        Direction.N,
                        "R");

        new SimulationService()
                .run(
                        field,
                        Arrays.asList(car));

        assertEquals(
                Direction.E,
                car.getDirection());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectInvalidCommand() {

        new Car(
                "A",
                1,
                1,
                Direction.N,
                "FFXLR");
    }
}