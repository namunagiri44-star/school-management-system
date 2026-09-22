package school.model;

import school.util.FileUtil;

public class SchoolClass {
    private int id;
    private String name;      // e.g. "Grade 10"
    private String section;   // e.g. "A"
    private int classTeacherId; // Teacher.id, -1 if unassigned

    public SchoolClass(int id, String name, String section, int classTeacherId) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.classTeacherId = classTeacherId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public int getClassTeacherId() { return classTeacherId; }
    public void setClassTeacherId(int classTeacherId) { this.classTeacherId = classTeacherId; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), name, section, String.valueOf(classTeacherId));
    }

    public static SchoolClass fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new SchoolClass(Integer.parseInt(f[0]), f[1], f[2], Integer.parseInt(f[3]));
    }

    @Override
    public String toString() {
        String teacher = classTeacherId == -1 ? "Unassigned" : String.valueOf(classTeacherId);
        return String.format("ID:%-4d Name:%-15s Section:%-8s ClassTeacherID:%s", id, name, section, teacher);
    }
}
