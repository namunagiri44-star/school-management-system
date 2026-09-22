package school.service;

import school.model.Attendance;
import school.util.FileUtil;

import java.util.*;

public class AttendanceService {
    private static final String FILE = "attendance.csv";
    private final List<Attendance> records = new ArrayList<>();
    private int nextId = 1;

    public AttendanceService() {
        load();
    }

    private void load() {
        records.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Attendance a = Attendance.fromCsv(line);
                records.add(a);
                if (a.getId() >= nextId) nextId = a.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Attendance a : records) lines.add(a.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Attendance mark(int studentId, String date, String status) {
        Attendance a = new Attendance(nextId++, studentId, date, status);
        records.add(a);
        save();
        return a;
    }

    public List<Attendance> getAll() {
        return Collections.unmodifiableList(records);
    }

    public List<Attendance> findByStudent(int studentId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : records) {
            if (a.getStudentId() == studentId) result.add(a);
        }
        return result;
    }

    public List<Attendance> findByDate(String date) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : records) {
            if (a.getDate().equals(date)) result.add(a);
        }
        return result;
    }

    public boolean delete(int id) {
        boolean removed = records.removeIf(a -> a.getId() == id);
        if (removed) save();
        return removed;
    }

    /** Returns [presentCount, absentCount, totalCount] for a student. */
    public int[] attendanceSummary(int studentId) {
        int present = 0, absent = 0, total = 0;
        for (Attendance a : findByStudent(studentId)) {
            total++;
            if (a.getStatus().equalsIgnoreCase("Present")) present++;
            else if (a.getStatus().equalsIgnoreCase("Absent")) absent++;
        }
        return new int[]{present, absent, total};
    }
}
