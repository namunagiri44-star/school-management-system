package school.model;

import school.util.FileUtil;

public class Subject {
    private int id;
    private String name;
    private String code;
    private String classId;   // SchoolClass.id this subject is taught in
    private int teacherId;    // Teacher.id, -1 if unassigned

    public Subject(int id, String name, String code, String classId, int teacherId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.classId = classId;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), name, code, classId, String.valueOf(teacherId));
    }

    public static Subject fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Subject(Integer.parseInt(f[0]), f[1], f[2], f[3], Integer.parseInt(f[4]));
    }

    @Override
    public String toString() {
        String teacher = teacherId == -1 ? "Unassigned" : String.valueOf(teacherId);
        return String.format("ID:%-4d Name:%-15s Code:%-8s ClassID:%-6s TeacherID:%s", id, name, code, classId, teacher);
    }
}
