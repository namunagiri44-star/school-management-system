package school.model;

import school.util.FileUtil;

public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String classId;   // links to SchoolClass.id
    private String contact;
    private String address;

    public Student(int id, String name, int age, String gender, String classId, String contact, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.classId = classId;
        this.contact = contact;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), name, String.valueOf(age), gender, classId, contact, address);
    }

    public static Student fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Student(Integer.parseInt(f[0]), f[1], Integer.parseInt(f[2]), f[3], f[4], f[5], f[6]);
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d Name:%-20s Age:%-4d Gender:%-8s ClassID:%-6s Contact:%-15s Address:%s",
                id, name, age, gender, classId, contact, address);
    }
}
