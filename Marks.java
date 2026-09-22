package school.model;

import school.util.FileUtil;

public class Marks {
    private int id;
    private int studentId;
    private int subjectId;
    private String examType;   // e.g. Midterm, Final, Quiz
    private double marksObtained;
    private double maxMarks;

    public Marks(int id, int studentId, int subjectId, String examType, double marksObtained, double maxMarks) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.examType = examType;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public String getExamType() { return examType; }
    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }
    public double getMaxMarks() { return maxMarks; }
    public void setMaxMarks(double maxMarks) { this.maxMarks = maxMarks; }

    public double getPercentage() {
        return maxMarks == 0 ? 0 : (marksObtained / maxMarks) * 100.0;
    }

    public String getGrade() {
        double pct = getPercentage();
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F";
    }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), String.valueOf(studentId), String.valueOf(subjectId),
                examType, String.valueOf(marksObtained), String.valueOf(maxMarks));
    }

    public static Marks fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Marks(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2]),
                f[3], Double.parseDouble(f[4]), Double.parseDouble(f[5]));
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d StudentID:%-6d SubjectID:%-6d Exam:%-10s Marks:%.1f/%.1f Grade:%s",
                id, studentId, subjectId, examType, marksObtained, maxMarks, getGrade());
    }
}
