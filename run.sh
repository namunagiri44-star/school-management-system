#!/bin/bash
# Compiles and runs the School Management System (macOS/Linux)
set -e
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out -encoding UTF-8 @sources.txt
rm -f sources.txt
java -cp out school.Main
