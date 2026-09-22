package school.model;

import school.util.FileUtil;

public class Attendance {
    private int id;
    private int studentId;
    private String date;    // yyyy-MM-dd
    private String status;  // Present / Absent / Late

    public Attendance(int id, int studentId, String date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), String.valueOf(studentId), date, status);
    }

    public static Attendance fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Attendance(Integer.parseInt(f[0]), Integer.parseInt(f[1]), f[2], f[3]);
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d StudentID:%-6d Date:%-12s Status:%s", id, studentId, date, status);
    }
}
