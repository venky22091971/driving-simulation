# Driving Simulation

A simple Java 8 CLI application that simulates cars moving inside a rectangular field based on a sequence of commands.

#Features
Create a rectangular simulation field
Add multiple cars with unique names
Set initial position and direction
Supports commands:
L - Turn left
R - Turn right
F - Move forward
Prevents cars from moving outside the field
Detects collisions between cars
Supports multiple cars running in lock-step
Validates user input
Starts with a clean state every time

#Technology
Java 8
Maven
JUnit 4
Command Line Interface

#How to Run
Make the startup script executable:
chmod +x start.sh
Run the application:
./start.sh
Run Tests
mvn clean test

#Scenario 1 - Normal scenario
Input
10 10
1
A
1 2 N
FFRFFFFRRL
2
Result:
After simulation, the result is:
- A, (5,4) S

#Scenario 2 - Collision Scenario
Input
Car A: 1 2 N - FFRFFFFRRL
Car B: 7 8 W - FFLFFFFFFF
Result:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7

#Assumptions
Field coordinates start at (0,0).
A 10 x 10 field has valid coordinates from (0,0) to (9,9).
Cars execute one command per simulation step.
Collision detection occurs after each step.
Cars stop executing further commands after a collision.
Forward commands that move outside the field are ignored.
Car names must be unique.