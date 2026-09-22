package school;

import school.model.*;
import school.service.*;
import school.util.InputHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final InputHelper in = new InputHelper(sc);

    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final ClassService classService = new ClassService();
    private static final SubjectService subjectService = new SubjectService();
    private static final AttendanceService attendanceService = new AttendanceService();
    private static final MarksService marksService = new MarksService();
    private static final FeeService feeService = new FeeService();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   WELCOME TO THE SCHOOL MANAGEMENT SYSTEM");
        System.out.println("=================================================");
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: studentMenu(); break;
                case 2: teacherMenu(); break;
                case 3: classMenu(); break;
                case 4: subjectMenu(); break;
                case 5: attendanceMenu(); break;
                case 6: marksMenu(); break;
                case 7: feeMenu(); break;
                case 0:
                    running = false;
                    System.out.println("\nThank you for using the School Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void printMainMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Teachers");
        System.out.println("3. Manage Classes");
        System.out.println("4. Manage Subjects");
        System.out.println("5. Manage Attendance");
        System.out.println("6. Manage Marks");
        System.out.println("7. Manage Fees");
        System.out.println("0. Exit");
        System.out.println("============================================");
    }

    // ==================== STUDENT ====================
    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- STUDENT MANAGEMENT -----");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Name");
            System.out.println("4. View Students by Class");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    String name = in.readNonEmptyString("Name: ");
                    int age = in.readInt("Age: ");
                    String gender = in.readNonEmptyString("Gender: ");
                    String classId = in.readString("Class ID (see Manage Classes, blank if none): ");
                    String contact = in.readString("Contact: ");
                    String address = in.readString("Address: ");
                    Student s = studentService.add(name, age, gender, classId, contact, address);
                    System.out.println("Student added successfully with ID: " + s.getId());
                    break;
                }
                case 2: {
                    List<Student> all = studentService.getAll();
                    if (all.isEmpty()) System.out.println("No students found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    String keyword = in.readNonEmptyString("Enter name keyword: ");
                    List<Student> results = studentService.searchByName(keyword);
                    if (results.isEmpty()) System.out.println("No matching students found.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 4: {
                    String classId = in.readNonEmptyString("Enter Class ID: ");
                    List<Student> results = studentService.findByClassId(classId);
                    if (results.isEmpty()) System.out.println("No students found in that class.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 5: {
                    int id = in.readInt("Enter Student ID to update: ");
                    Optional<Student> opt = studentService.findById(id);
                    if (opt.isEmpty()) { System.out.println("Student not found."); break; }
                    Student existing = opt.get();
                    System.out.println("Current: " + existing);
                    String name = in.readString("New Name (blank to keep '" + existing.getName() + "'): ");
                    if (name.isEmpty()) name = existing.getName();
                    int age = in.readInt("New Age: ");
                    String gender = in.readString("New Gender (blank to keep): ");
                    if (gender.isEmpty()) gender = existing.getGender();
                    String classId = in.readString("New Class ID (blank to keep): ");
                    if (classId.isEmpty()) classId = existing.getClassId();
                    String contact = in.readString("New Contact (blank to keep): ");
                    if (contact.isEmpty()) contact = existing.getContact();
                    String address = in.readString("New Address (blank to keep): ");
                    if (address.isEmpty()) address = existing.getAddress();
                    studentService.update(id, name, age, gender, classId, contact, address);
                    System.out.println("Student updated successfully.");
                    break;
                }
                case 6: {
                    int id = in.readInt("Enter Student ID to delete: ");
                    System.out.println(studentService.delete(id) ? "Student deleted." : "Student not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== TEACHER ====================
    private static void teacherMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- TEACHER MANAGEMENT -----");
            System.out.println("1. Add Teacher");
            System.out.println("2. View All Teachers");
            System.out.println("3. Search Teacher by Name");
            System.out.println("4. Update Teacher");
            System.out.println("5. Delete Teacher");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    String name = in.readNonEmptyString("Name: ");
                    String spec = in.readString("Specialization/Subject: ");
                    String contact = in.readString("Contact: ");
                    String address = in.readString("Address: ");
                    Teacher t = teacherService.add(name, spec, contact, address);
                    System.out.println("Teacher added successfully with ID: " + t.getId());
                    break;
                }
                case 2: {
                    List<Teacher> all = teacherService.getAll();
                    if (all.isEmpty()) System.out.println("No teachers found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    String keyword = in.readNonEmptyString("Enter name keyword: ");
                    List<Teacher> results = teacherService.searchByName(keyword);
                    if (results.isEmpty()) System.out.println("No matching teachers found.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 4: {
                    int id = in.readInt("Enter Teacher ID to update: ");
                    Optional<Teacher> opt = teacherService.findById(id);
                    if (opt.isEmpty()) { System.out.println("Teacher not found."); break; }
                    Teacher existing = opt.get();
                    System.out.println("Current: " + existing);
                    String name = in.readString("New Name (blank to keep): ");
                    if (name.isEmpty()) name = existing.getName();
                    String spec = in.readString("New Specialization (blank to keep): ");
                    if (spec.isEmpty()) spec = existing.getSpecialization();
                    String contact = in.readString("New Contact (blank to keep): ");
                    if (contact.isEmpty()) contact = existing.getContact();
                    String address = in.readString("New Address (blank to keep): ");
                    if (address.isEmpty()) address = existing.getAddress();
                    teacherService.update(id, name, spec, contact, address);
                    System.out.println("Teacher updated successfully.");
                    break;
                }
                case 5: {
                    int id = in.readInt("Enter Teacher ID to delete: ");
                    System.out.println(teacherService.delete(id) ? "Teacher deleted." : "Teacher not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== CLASS ====================
    private static void classMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- CLASS MANAGEMENT -----");
            System.out.println("1. Add Class");
            System.out.println("2. View All Classes");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    String name = in.readNonEmptyString("Class Name (e.g. Grade 10): ");
                    String section = in.readString("Section (e.g. A): ");
                    int teacherId = in.readInt("Class Teacher ID (-1 if none): ");
                    SchoolClass c = classService.add(name, section, teacherId);
                    System.out.println("Class added successfully with ID: " + c.getId());
                    break;
                }
                case 2: {
                    List<SchoolClass> all = classService.getAll();
                    if (all.isEmpty()) System.out.println("No classes found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    int id = in.readInt("Enter Class ID to update: ");
                    Optional<SchoolClass> opt = classService.findById(id);
                    if (opt.isEmpty()) { System.out.println("Class not found."); break; }
                    SchoolClass existing = opt.get();
                    System.out.println("Current: " + existing);
                    String name = in.readString("New Name (blank to keep): ");
                    if (name.isEmpty()) name = existing.getName();
                    String section = in.readString("New Section (blank to keep): ");
                    if (section.isEmpty()) section = existing.getSection();
                    int teacherId = in.readInt("New Class Teacher ID: ");
                    classService.update(id, name, section, teacherId);
                    System.out.println("Class updated successfully.");
                    break;
                }
                case 4: {
                    int id = in.readInt("Enter Class ID to delete: ");
                    System.out.println(classService.delete(id) ? "Class deleted." : "Class not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== SUBJECT ====================
    private static void subjectMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- SUBJECT MANAGEMENT -----");
            System.out.println("1. Add Subject");
            System.out.println("2. View All Subjects");
            System.out.println("3. View Subjects by Class");
            System.out.println("4. Update Subject");
            System.out.println("5. Delete Subject");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    String name = in.readNonEmptyString("Subject Name: ");
                    String code = in.readString("Subject Code: ");
                    String classId = in.readString("Class ID this subject belongs to: ");
                    int teacherId = in.readInt("Teacher ID (-1 if unassigned): ");
                    Subject s = subjectService.add(name, code, classId, teacherId);
                    System.out.println("Subject added successfully with ID: " + s.getId());
                    break;
                }
                case 2: {
                    List<Subject> all = subjectService.getAll();
                    if (all.isEmpty()) System.out.println("No subjects found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    String classId = in.readNonEmptyString("Enter Class ID: ");
                    List<Subject> results = subjectService.findByClassId(classId);
                    if (results.isEmpty()) System.out.println("No subjects found for that class.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 4: {
                    int id = in.readInt("Enter Subject ID to update: ");
                    Optional<Subject> opt = subjectService.findById(id);
                    if (opt.isEmpty()) { System.out.println("Subject not found."); break; }
                    Subject existing = opt.get();
                    System.out.println("Current: " + existing);
                    String name = in.readString("New Name (blank to keep): ");
                    if (name.isEmpty()) name = existing.getName();
                    String code = in.readString("New Code (blank to keep): ");
                    if (code.isEmpty()) code = existing.getCode();
                    String classId = in.readString("New Class ID (blank to keep): ");
                    if (classId.isEmpty()) classId = existing.getClassId();
                    int teacherId = in.readInt("New Teacher ID: ");
                    subjectService.update(id, name, code, classId, teacherId);
                    System.out.println("Subject updated successfully.");
                    break;
                }
                case 5: {
                    int id = in.readInt("Enter Subject ID to delete: ");
                    System.out.println(subjectService.delete(id) ? "Subject deleted." : "Subject not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== ATTENDANCE ====================
    private static void attendanceMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- ATTENDANCE MANAGEMENT -----");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View All Attendance Records");
            System.out.println("3. View Attendance by Student");
            System.out.println("4. View Attendance by Date");
            System.out.println("5. View Attendance Summary for a Student");
            System.out.println("6. Delete Attendance Record");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    int studentId = in.readInt("Student ID: ");
                    if (!studentService.exists(studentId)) { System.out.println("No such student."); break; }
                    String date = in.readString("Date (YYYY-MM-DD, blank for today): ");
                    if (date.isEmpty()) date = LocalDate.now().toString();
                    String status = in.readNonEmptyString("Status (Present/Absent/Late): ");
                    Attendance a = attendanceService.mark(studentId, date, status);
                    System.out.println("Attendance recorded with ID: " + a.getId());
                    break;
                }
                case 2: {
                    List<Attendance> all = attendanceService.getAll();
                    if (all.isEmpty()) System.out.println("No attendance records found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    int studentId = in.readInt("Student ID: ");
                    List<Attendance> results = attendanceService.findByStudent(studentId);
                    if (results.isEmpty()) System.out.println("No records found for that student.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 4: {
                    String date = in.readNonEmptyString("Date (YYYY-MM-DD): ");
                    List<Attendance> results = attendanceService.findByDate(date);
                    if (results.isEmpty()) System.out.println("No records found for that date.");
                    else results.forEach(System.out::println);
                    break;
                }
                case 5: {
                    int studentId = in.readInt("Student ID: ");
                    int[] summary = attendanceService.attendanceSummary(studentId);
                    System.out.printf("Present: %d | Absent: %d | Total Marked Days: %d%n",
                            summary[0], summary[1], summary[2]);
                    if (summary[2] > 0) {
                        double pct = (summary[0] * 100.0) / summary[2];
                        System.out.printf("Attendance Percentage: %.2f%%%n", pct);
                    }
                    break;
                }
                case 6: {
                    int id = in.readInt("Enter Attendance Record ID to delete: ");
                    System.out.println(attendanceService.delete(id) ? "Record deleted." : "Record not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== MARKS ====================
    private static void marksMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- MARKS MANAGEMENT -----");
            System.out.println("1. Add Marks Record");
            System.out.println("2. View All Marks");
            System.out.println("3. View Report Card for a Student");
            System.out.println("4. Update Marks Record");
            System.out.println("5. Delete Marks Record");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    int studentId = in.readInt("Student ID: ");
                    if (!studentService.exists(studentId)) { System.out.println("No such student."); break; }
                    int subjectId = in.readInt("Subject ID: ");
                    if (!subjectService.exists(subjectId)) { System.out.println("No such subject."); break; }
                    String examType = in.readNonEmptyString("Exam Type (e.g. Midterm, Final): ");
                    double obtained = in.readDouble("Marks Obtained: ");
                    double max = in.readDouble("Maximum Marks: ");
                    Marks m = marksService.add(studentId, subjectId, examType, obtained, max);
                    System.out.println("Marks recorded with ID: " + m.getId() + " (Grade: " + m.getGrade() + ")");
                    break;
                }
                case 2: {
                    List<Marks> all = marksService.getAll();
                    if (all.isEmpty()) System.out.println("No marks records found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    int studentId = in.readInt("Student ID: ");
                    Optional<Student> stu = studentService.findById(studentId);
                    if (stu.isEmpty()) { System.out.println("No such student."); break; }
                    List<Marks> results = marksService.findByStudent(studentId);
                    System.out.println("\n----- REPORT CARD: " + stu.get().getName() + " -----");
                    if (results.isEmpty()) {
                        System.out.println("No marks recorded yet.");
                    } else {
                        for (Marks m : results) {
                            Optional<Subject> subj = subjectService.findById(m.getSubjectId());
                            String subjectName = subj.isPresent() ? subj.get().getName() : "Subject#" + m.getSubjectId();
                            System.out.printf("%-15s %-10s %.1f/%.1f  (%.1f%%)  Grade: %s%n",
                                    subjectName, m.getExamType(), m.getMarksObtained(), m.getMaxMarks(),
                                    m.getPercentage(), m.getGrade());
                        }
                        System.out.printf("Overall Average: %.2f%%%n", marksService.averagePercentage(studentId));
                    }
                    break;
                }
                case 4: {
                    int id = in.readInt("Enter Marks Record ID to update: ");
                    Optional<Marks> opt = marksService.findById(id);
                    if (opt.isEmpty()) { System.out.println("Record not found."); break; }
                    System.out.println("Current: " + opt.get());
                    double obtained = in.readDouble("New Marks Obtained: ");
                    double max = in.readDouble("New Maximum Marks: ");
                    marksService.update(id, obtained, max);
                    System.out.println("Marks record updated.");
                    break;
                }
                case 5: {
                    int id = in.readInt("Enter Marks Record ID to delete: ");
                    System.out.println(marksService.delete(id) ? "Record deleted." : "Record not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ==================== FEE ====================
    private static void feeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- FEE MANAGEMENT -----");
            System.out.println("1. Add Fee Record");
            System.out.println("2. View All Fee Records");
            System.out.println("3. View Fees by Student");
            System.out.println("4. View All Unpaid Fees");
            System.out.println("5. Mark Fee as Paid");
            System.out.println("6. Delete Fee Record");
            System.out.println("0. Back to Main Menu");
            int choice = in.readInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    int studentId = in.readInt("Student ID: ");
                    if (!studentService.exists(studentId)) { System.out.println("No such student."); break; }
                    String term = in.readNonEmptyString("Term (e.g. 2026 Term 1): ");
                    double amount = in.readDouble("Amount Due: ");
                    String dueDate = in.readNonEmptyString("Due Date (YYYY-MM-DD): ");
                    Fee f = feeService.add(studentId, term, amount, dueDate);
                    System.out.println("Fee record created with ID: " + f.getId());
                    break;
                }
                case 2: {
                    List<Fee> all = feeService.getAll();
                    if (all.isEmpty()) System.out.println("No fee records found.");
                    else all.forEach(System.out::println);
                    break;
                }
                case 3: {
                    int studentId = in.readInt("Student ID: ");
                    List<Fee> results = feeService.findByStudent(studentId);
                    if (results.isEmpty()) System.out.println("No fee records found for that student.");
                    else {
                        results.forEach(System.out::println);
                        System.out.printf("Total Outstanding: %.2f%n", feeService.totalDueForStudent(studentId));
                    }
                    break;
                }
                case 4: {
                    List<Fee> unpaid = feeService.findUnpaid();
                    if (unpaid.isEmpty()) System.out.println("No unpaid fees. Everyone is up to date!");
                    else unpaid.forEach(System.out::println);
                    break;
                }
                case 5: {
                    int id = in.readInt("Enter Fee Record ID to mark as paid: ");
                    String paidDate = in.readString("Paid Date (YYYY-MM-DD, blank for today): ");
                    if (paidDate.isEmpty()) paidDate = LocalDate.now().toString();
                    System.out.println(feeService.markPaid(id, paidDate) ? "Fee marked as paid." : "Record not found.");
                    break;
                }
                case 6: {
                    int id = in.readInt("Enter Fee Record ID to delete: ");
                    System.out.println(feeService.delete(id) ? "Record deleted." : "Record not found.");
                    break;
                }
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
