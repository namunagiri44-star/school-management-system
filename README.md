# School Management System (Java)

A console-based Java application for managing a school's day-to-day records:
**Students, Teachers, Classes, Subjects, Attendance, Marks, and Fees.**

All data is saved to plain CSV files under the `data/` folder, so your
records persist automatically between runs — no database server required.

## Features

- **Students** — add, view, search by name, filter by class, update, delete
- **Teachers** — add, view, search by name, update, delete
- **Classes** — add, view, update, delete (assign a class teacher)
- **Subjects** — add, view, filter by class, update, delete (assign a subject teacher)
- **Attendance** — mark Present/Absent/Late per student per date, view by student
  or by date, get an attendance % summary
- **Marks** — record marks per exam, auto-computed percentage and letter grade,
  full report card per student with overall average
- **Fees** — create fee records per term, view outstanding balances, mark as paid,
  list all unpaid fees across the school

## Requirements

- Java JDK 8 or later (JDK 17+ recommended). Check with:
  ```
  java -version
  javac -version
  ```
  If these commands aren't found, install a JDK first (e.g. from
  https://adoptium.net).

## Project Structure

```
SchoolManagementSystem/
├── src/main/java/school/
│   ├── Main.java                 # Entry point & console menus
│   ├── model/                    # Data classes (Student, Teacher, ...)
│   │   ├── Student.java
│   │   ├── Teacher.java
│   │   ├── SchoolClass.java
│   │   ├── Subject.java
│   │   ├── Attendance.java
│   │   ├── Marks.java
│   │   └── Fee.java
│   ├── service/                  # Business logic + CSV persistence
│   │   ├── StudentService.java
│   │   ├── TeacherService.java
│   │   ├── ClassService.java
│   │   ├── SubjectService.java
│   │   ├── AttendanceService.java
│   │   ├── MarksService.java
│   │   └── FeeService.java
│   └── util/
│       ├── FileUtil.java         # Generic CSV read/write helper
│       └── InputHelper.java      # Validated console input helper
├── data/                         # Auto-created CSV data files (your records)
├── run.sh                        # Build & run script (macOS/Linux)
├── run.bat                       # Build & run script (Windows)
└── README.md
```

## How to Run

### Option A — using the scripts
```bash
# macOS / Linux
./run.sh

# Windows
run.bat
```

### Option B — manual commands
```bash
mkdir -p out
javac -d out $(find src -name "*.java")   # macOS/Linux
java -cp out school.Main
```
On Windows (PowerShell), replace the `find` line with:
```
Get-ChildItem -Recurse -Filter *.java src | Foreach-Object { $_.FullName } > sources.txt
javac -d out "@sources.txt"
java -cp out school.Main
```

### Option C — open in an IDE
Import the `SchoolManagementSystem` folder into IntelliJ IDEA, Eclipse, or
VS Code as a plain Java project (source root: `src/main/java`), then run
`school.Main`.

## Using the App

On launch you'll see a main menu with numbered options for each module
(Students, Teachers, Classes, Subjects, Attendance, Marks, Fees). Each module
has its own sub-menu for Add / View / Search / Update / Delete style
operations. A typical workflow:

1. **Manage Classes** → add a class (e.g. "Grade 10", section "A")
2. **Manage Teachers** → add teachers
3. **Manage Students** → add students, assigning them the Class ID from step 1
4. **Manage Subjects** → add subjects for that Class ID, assigning a teacher
5. **Manage Attendance** → mark daily attendance by Student ID
6. **Manage Marks** → record exam marks by Student ID + Subject ID, then view
   a student's full report card
7. **Manage Fees** → create fee records per term and mark them paid when
   payment is received

IDs (Student ID, Class ID, Subject ID, Teacher ID) are shown whenever you add
or view a record — use them to link records together (e.g. which class a
student belongs to, which subject a mark applies to).

## Data Storage

Each module keeps its own CSV file in `data/`:
`students.csv`, `teachers.csv`, `classes.csv`, `subjects.csv`,
`attendance.csv`, `marks.csv`, `fees.csv`.

Fields are separated with `|` (not `,`) so ordinary text like addresses can
safely contain commas. You generally won't need to open these files by hand —
the app manages them for you — but they're plain text if you ever want to
inspect, back up, or import them into a spreadsheet.

## Extending the Project

This project is intentionally built with clear layers so it's easy to extend:

- Add a new field → update the model class's fields, constructor, `toCsv`/`fromCsv`, and `toString`
- Add a new operation → add a method to the relevant `*Service` class
- Add a new menu option → add a case to the relevant menu method in `Main.java`
- Swap CSV for a real database → only the `service` classes need to change
  (e.g. replace `FileUtil` calls with JDBC calls); models and `Main.java`
  stay the same
- Want a GUI instead of console? Wrap the same `service` classes in a
  Swing or JavaFX UI — the persistence and business logic won't need to change
