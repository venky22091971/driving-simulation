#!/usr/bin/env bash

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
BUILD_DIR="$PROJECT_DIR/build"

# Clean start every time
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

echo "Compiling Driving Simulation..."

find "$PROJECT_DIR/src/main/java" \
     -name "*.java" \
     > "$BUILD_DIR/sources.txt"

javac \
    -source 1.8 \
    -target 1.8 \
    -d "$BUILD_DIR" \
    @"$BUILD_DIR/sources.txt"

echo "Starting Driving Simulation..."
echo

java \
    -cp "$BUILD_DIR" \
    com.simulation.DrivingSimulationApplication