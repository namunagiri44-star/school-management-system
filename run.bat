@echo off
REM Compiles and runs the School Management System (Windows)
if not exist out mkdir out
javac -d out -encoding UTF-8 -sourcepath src\main\java src\main\java\school\Main.java src\main\java\school\model\*.java src\main\java\school\service\*.java src\main\java\school\util\*.java
if errorlevel 1 (
    echo.
    echo Compilation failed. Please check the errors above.
    pause
    exit /b 1
)
java -cp out school.Main
pause
